package tr.com.nekasoft.sentency.api.data.auth;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest implements Serializable {
    private static final long serialVersionUID = -286023707880778972L;

    @NotNull
    @Email
    private String username;
    @NotNull
    private String password;
}
