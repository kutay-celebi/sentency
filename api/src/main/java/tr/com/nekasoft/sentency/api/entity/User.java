package tr.com.nekasoft.sentency.api.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tr.com.nekasoft.sentency.api.data.UserRole;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "snt_user")
public class User extends BaseEntity {

  private static final long serialVersionUID = -5922947942944130632L;

  @Column(name = "username")
  private String username;
  @Column(name = "password")
  private String password;
  @Builder.Default
  @Enumerated(EnumType.STRING)
  @Column(name = "role")
  private UserRole role = UserRole.USER;

  @OneToOne(mappedBy = "user", cascade = {CascadeType.ALL})
  private UserConfig userConfig;

}
