package tr.com.nekasoft.sentency.api;

import com.github.javafaker.Faker;
import javax.inject.Inject;
import org.apache.sshd.common.config.keys.loader.openssh.kdf.BCrypt;
import tr.com.nekasoft.sentency.api.entity.User;
import tr.com.nekasoft.sentency.api.repository.UserRepository;

public abstract class AbstractUserTestSuite extends AbstractTestSuite {

  @Inject
  protected UserRepository userRepository;

  protected User saveUser() {
    return saveUser("password");
  }

  protected User saveUser(String rawPassword) {
    String password = BCrypt.hashpw(rawPassword, BCrypt.gensalt(12));
    User user = User.builder().username(Faker.instance().internet().emailAddress()).password(password).build();
    userRepository.persistAndFlush(user);
    return user;
  }
}
