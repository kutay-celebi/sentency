package tr.com.nekasoft.sentency.api.repository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import tr.com.nekasoft.sentency.api.entity.UserConfig;


@ApplicationScoped
@Transactional
public class UserConfigRepository implements BaseRepository<UserConfig> {

}
