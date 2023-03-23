package tr.com.nekasoft.sentency.api.repository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import tr.com.nekasoft.sentency.api.entity.Word;


@ApplicationScoped
@Transactional
public class WordRepository implements BaseRepository<Word> {
}
