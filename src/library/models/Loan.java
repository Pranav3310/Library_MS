package library.models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Loan {
    private int bookId;
    private int memberId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate; // null if active

    public Loan(int bookId, int memberId, LocalDate borrowDate, LocalDate dueDate, LocalDate returnDate) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public int getBookId() { return bookId; }
    public int getMemberId() { return memberId; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public boolean isActive() { return returnDate == null; }

    public void markReturned(LocalDate date) {
        this.returnDate = date;
    }

    /** ₹5 per day late (only if returned after due date) */
    public double calculateFine() {
        if (returnDate == null) return 0.0;
        long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
        return daysLate > 0 ? daysLate * 5.0 : 0.0;
    }

    public String toCSV() {
        return bookId + "|" + memberId + "|" + borrowDate + "|" + dueDate + "|" + (returnDate == null ? "" : returnDate.toString());
    }

    public static Loan fromCSV(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length < 5) return null;
        int bookId = Integer.parseInt(p[0]);
        int memberId = Integer.parseInt(p[1]);
        LocalDate borrowDate = LocalDate.parse(p[2]);
        LocalDate dueDate = LocalDate.parse(p[3]);
        LocalDate returnDate = p[4].isEmpty() ? null : LocalDate.parse(p[4]);
        return new Loan(bookId, memberId, borrowDate, dueDate, returnDate);
    }

    @Override
    public String toString() {
        return "Loan{bookId=" + bookId + ", memberId=" + memberId +
               ", borrow=" + borrowDate + ", due=" + dueDate +
               (returnDate == null ? ", ACTIVE" : ", returned=" + returnDate + ", fine=₹" + calculateFine()) + "}";
    }
}
