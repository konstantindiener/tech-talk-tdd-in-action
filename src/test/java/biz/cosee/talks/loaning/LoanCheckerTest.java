package biz.cosee.talks.loaning;

import org.junit.Test;

import java.util.Date;

import static java.util.Arrays.asList;

public class LoanCheckerTest {

    @Test
    public void allowNewLoanOnEmptyLoanHistory() {
        new LoanChecker().checkWhetherUserCanCreateLoan(new User(), new Book());
    }

    @Test
    public void allowNewLoanOnPopulatedLoanHistory() {
        User user = new User();
        user.setLoans(asList(
                new Loan(user, new Book(), new Date())
        ));
        new LoanChecker().checkWhetherUserCanCreateLoan(user, new Book());
    }
}