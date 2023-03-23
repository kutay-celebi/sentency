package tr.com.nekasoft.sentency.api;

import javax.inject.Inject;

import com.github.javafaker.Faker;
import tr.com.nekasoft.sentency.api.entity.User;
import tr.com.nekasoft.sentency.api.repository.UserRepository;

public abstract class AbstractUserTestSuite extends AbstractTestSuite {

    @Inject
    protected UserRepository userRepository;

    protected User saveUser() {
        User user = User.builder().username(Faker.instance().internet().emailAddress()).password("password").build();
        userRepository.persistAndFlush(user);
        return user;
    }
}
