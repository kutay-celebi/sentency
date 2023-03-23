package tr.com.nekasoft.sentency.api.repository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import tr.com.nekasoft.sentency.api.entity.User;


@ApplicationScoped
@Transactional
public class UserRepository implements BaseRepository<User> {
}
