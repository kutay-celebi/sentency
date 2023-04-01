package tr.com.nekasoft.sentency.api.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import tr.com.nekasoft.sentency.api.data.auth.JwtResponse;
import tr.com.nekasoft.sentency.api.data.auth.LoginRequest;
import tr.com.nekasoft.sentency.api.data.auth.RegisterRequest;

public interface AuthService {

  void register(RegisterRequest request);

  JwtResponse loginWithGoogle(GoogleIdToken idToken);

  JwtResponse login(LoginRequest request);
}
