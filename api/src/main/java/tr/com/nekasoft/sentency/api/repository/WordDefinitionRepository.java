package tr.com.nekasoft.sentency.api.repository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import tr.com.nekasoft.sentency.api.entity.WordDefinition;


@ApplicationScoped
@Transactional
public class WordDefinitionRepository implements BaseRepository<WordDefinition> {
}
