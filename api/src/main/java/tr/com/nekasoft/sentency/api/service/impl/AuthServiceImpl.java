package tr.com.nekasoft.sentency.api.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import io.quarkus.panache.common.Parameters;
import io.smallrye.jwt.build.Jwt;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.sshd.common.config.keys.loader.openssh.kdf.BCrypt;
import tr.com.nekasoft.sentency.api.data.DefaultQueryRequest;
import tr.com.nekasoft.sentency.api.data.auth.JwtResponse;
import tr.com.nekasoft.sentency.api.data.auth.LoginRequest;
import tr.com.nekasoft.sentency.api.data.auth.RegisterRequest;
import tr.com.nekasoft.sentency.api.entity.User;
import tr.com.nekasoft.sentency.api.entity.UserConfig;
import tr.com.nekasoft.sentency.api.exception.ExceptionCode;
import tr.com.nekasoft.sentency.api.repository.UserRepository;
import tr.com.nekasoft.sentency.api.service.AuthService;

@ApplicationScoped
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;

  @Transactional
  @Override
  public void register(RegisterRequest request) {
    Optional<User> user = userRepository
        .softFind(DefaultQueryRequest
            .builder()
            .query("username = :username")
            .parameters(Parameters.with("username", request.getUsername()))
            .build())
        .firstResultOptional();
    if (user.isPresent()) {
      throw ExceptionCode.USER_USERNAME_EXIST.toException();
    }

    String password = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt(12));
    User toBeSaved = User.builder().username(request.getUsername()).password(password).build();

    UserConfig userConfig = UserConfig.builder().user(toBeSaved).build();
    toBeSaved.setUserConfig(userConfig);

    userRepository.persistAndFlush(toBeSaved);
  }

  @Transactional
  @Override
  public JwtResponse loginWithGoogle(GoogleIdToken idToken) {
    String pass = BCrypt.hashpw(BCrypt.gensalt(), BCrypt.gensalt(10));
    User user = userRepository
        .softFind(DefaultQueryRequest
            .builder()
            .query("username = :username")
            .parameters(Parameters.with("username", idToken.getPayload().getEmail()))
            .build())
        .firstResultOptional()
        .orElseGet(() -> {
          User toBeSaved = User.builder().username(idToken.getPayload().getEmail()).password(pass).build();

          UserConfig userConfig = UserConfig.builder().user(toBeSaved).build();
          toBeSaved.setUserConfig(userConfig);

          userRepository.persistAndFlush(toBeSaved);
          return toBeSaved;
        });

    return JwtResponse
        .builder()
        .userId(user.getId())
        .role(user.getRole())
        .token(generateJwt(user))
        .targetLanguage(user.getUserConfig().getTargetLanguage())
        .build();

  }

  @Override
  @Transactional
  public JwtResponse login(LoginRequest request) {
    User user = userRepository
        .softFind(DefaultQueryRequest
            .builder()
            .query("username = :username")
            .parameters(Parameters.with("username", request.getUsername()))
            .build())
        .firstResultOptional()
        .orElseThrow(ExceptionCode.BAD_CREDENTIAL::toException);

    if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
      throw ExceptionCode.BAD_CREDENTIAL.toException();
    }

    if (user.getUserConfig() == null) {
      UserConfig userConfig = UserConfig.builder().user(user).build();
      user.setUserConfig(userConfig);
      userRepository.persistAndFlush(user);
    }

    return JwtResponse
        .builder()
        .userId(user.getId())
        .role(user.getRole())
        .token(generateJwt(user))
        .targetLanguage(user.getUserConfig().getTargetLanguage())
        .build();
  }

  private String generateJwt(User user) {
    return Jwt
        .subject(user.getUsername())
        .groups(user.getRole().name())
        .issuedAt(Instant.now())
        .expiresIn(Duration.ofSeconds(3600))
        .jws()
        .sign();
  }

}
