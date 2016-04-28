package biz.cosee.talks.loaning;

import com.sun.org.apache.bcel.internal.generic.DUP;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class LoanService {

    private final LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan createLoanStartingNowAndStore(User user, Book book) {
        Loan loan = new Loan(user, book, new Date());

        checkWhetherUserCanCreateLoan(user, book);

        return loanRepository.save(loan);
    }

    private void checkWhetherUserCanCreateLoan(User user, Book book) {
        if (hasAlreadyLoanedBook(user, book)) {
            throw new DuplicateLoanException();
        }

        if (user.hasMoreThanNumberOfLoans(5)) {
            throw new LoanLimitExceededException();
        }
    }

    private boolean hasAlreadyLoanedBook(User user, Book book) {
        return user.getLoans().stream().anyMatch(l -> l.getBook().equals(book));
    }
}
