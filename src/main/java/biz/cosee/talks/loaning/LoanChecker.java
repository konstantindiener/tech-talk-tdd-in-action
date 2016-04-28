package biz.cosee.talks.loaning;

public class LoanChecker {

    private int loanThreshold = 5;

    public LoanChecker() {
    }

    public void checkWhetherUserCanCreateLoan(User user, Book book) {
        if (hasAlreadyLoanedBook(user, book)) {
            throw new DuplicateLoanException();
        }

        if (user.hasMoreThanNumberOfLoans(loanThreshold)) {
            throw new LoanLimitExceededException();
        }
    }

    private boolean hasAlreadyLoanedBook(User user, Book book) {
        return user.getLoans().stream().anyMatch(l -> l.getBook().equals(book));
    }
}
