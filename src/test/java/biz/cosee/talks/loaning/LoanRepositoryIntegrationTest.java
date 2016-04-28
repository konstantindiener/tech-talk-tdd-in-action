package biz.cosee.talks.loaning;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TddInActionApplication.class)
@WebAppConfiguration
public class LoanRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Test
    public void saveLoan() {

        User user = userRepository.save(new User());
        Book book = bookRepository.save(new Book());

        Loan loan = loanRepository.save(new Loan(user, book, new Date()));
        assertThat(loan.getId()).isNotNull();
    }
}