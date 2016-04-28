package biz.cosee.talks.loaning;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanService loanService;

    private User user = new User();

    private Book sampleBook = new Book();

    @Before
    public void mockLoanRepository() {
        when(loanRepository.save(any(Loan.class)))
                .thenAnswer((InvocationOnMock invocationOnMock) -> {

                            Loan loan = (Loan) invocationOnMock.getArguments()[0];

                            return new Loan(4711L,
                                    loan.getUser(),
                                    loan.getBook(),
                                    loan.getStartDate());
                        }
                );
    }

    @Test
    public void createLoanSuccessfully() {

        Loan loan = loanService.createLoanStartingNowAndStore(user, sampleBook);

        verifyLoanPropertiesAreCorrect(loan);
        verifyPersistentLoanIsReturned(loan);
    }

    private void verifyLoanPropertiesAreCorrect(Loan loan) {
        assertThat(loan).isNotNull();
        assertThat(loan.getUser()).isEqualTo(user);
        assertThat(loan.getBook()).isEqualTo(sampleBook);
        assertThat(loan.getStartDate()).isEqualToIgnoringHours(new Date());
    }

    private void verifyPersistentLoanIsReturned(Loan loan) {
        verify(loanRepository).save(any(Loan.class));
        assertThat(loan.getId()).isNotNull();
    }

    @Test (expected = DuplicateLoanException.class)
    public void failOnDuplicateLoan() {

        createLoanHistoryContainingSampleBook();

        loanService.createLoanStartingNowAndStore(user, sampleBook);
    }

    private void createLoanHistoryContainingSampleBook() {
        user.setLoans(asList(
                new Loan(user, sampleBook, new Date())
        ));
    }

    @Test (expected = LoanLimitExceededException.class)
    public void failOnLoanLimitExceeded() {
        createLoanHistoryWithFiveBooks();

        loanService.createLoanStartingNowAndStore(user, sampleBook);
    }

    private void createLoanHistoryWithFiveBooks() {
        user.setLoans(asList(
                new Loan(user, new Book(), new Date()),
                new Loan(user, new Book(), new Date()),
                new Loan(user, new Book(), new Date()),
                new Loan(user, new Book(), new Date()),
                new Loan(user, new Book(), new Date())
        ));
    }
}
