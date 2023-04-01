package tr.com.nekasoft.sentency.api.entity;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable {

  private static final long serialVersionUID = 204684736387344548L;

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", nullable = false)
  protected String id;
  @Builder.Default
  @Column(name = "deleted")
  protected Boolean deleted = Boolean.FALSE;
  @CreationTimestamp
  @Column(name = "created_at")
  protected Instant createdAt;
  @Column(name = "deleted_at")
  protected Instant deletedAt;
}
