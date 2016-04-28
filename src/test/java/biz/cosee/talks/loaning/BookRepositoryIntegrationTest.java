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
public class BookRepositoryIntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    //@Test
    public void storeBook() {
        Book savedBook = bookRepository.save(new Book("title"));

        assertThat(savedBook.getId()).isNotNull();
        assertThat(bookRepository.findOne(savedBook.getId())).isNotNull();
    }
}
