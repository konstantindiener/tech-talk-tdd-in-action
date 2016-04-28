package biz.cosee.talks.loaning;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

/*@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TddInActionApplication.class)
@WebAppConfiguration*/
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    //@Test
    public void storeUser() {
        User savedUser = userRepository.save(new User("username"));

        assertThat(savedUser.getId()).isNotNull();
        assertThat(userRepository.findOne(savedUser.getId())).isNotNull();
    }
}