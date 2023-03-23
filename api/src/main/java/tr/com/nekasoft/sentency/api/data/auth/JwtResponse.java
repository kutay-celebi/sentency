package tr.com.nekasoft.sentency.api.data.auth;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.nekasoft.sentency.api.data.UserRole;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class JwtResponse implements Serializable {
    private static final long serialVersionUID = 4317869064411722861L;

    private String token;
    private String userId;
    private UserRole role;
}
