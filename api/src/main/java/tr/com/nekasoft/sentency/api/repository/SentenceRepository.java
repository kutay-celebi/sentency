package tr.com.nekasoft.sentency.api.repository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import tr.com.nekasoft.sentency.api.entity.Sentence;


@Transactional
@ApplicationScoped
public class SentenceRepository implements BaseRepository<Sentence> {

}
