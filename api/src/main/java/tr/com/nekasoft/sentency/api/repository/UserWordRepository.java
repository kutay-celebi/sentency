package tr.com.nekasoft.sentency.api.repository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import tr.com.nekasoft.sentency.api.entity.UserWord;


@ApplicationScoped
@Transactional
public class UserWordRepository implements BaseRepository<UserWord> {
}
