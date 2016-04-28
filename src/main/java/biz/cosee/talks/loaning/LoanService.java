package biz.cosee.talks.loaning;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class LoanService {

    private final LoanRepository loanRepository;

    private final LoanChecker loanChecker;

    @Autowired
    public LoanService(LoanRepository loanRepository, LoanChecker loanChecker) {
        this.loanRepository = loanRepository;
        this.loanChecker = loanChecker;
    }

    public Loan createLoanStartingNowAndStore(User user, Book book) {
        Loan loan = new Loan(user, book, new Date());

        loanChecker.checkWhetherUserCanCreateLoan(user, book);

        return loanRepository.save(loan);
    }
}
