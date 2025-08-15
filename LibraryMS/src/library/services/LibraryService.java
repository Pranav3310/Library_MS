package library.services;

import library.models.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class LibraryService {
    private final FileStorage storage;
    private final List<Book> books;
    private final List<Member> members;
    private final List<Loan> loans;

    public LibraryService() {
        this.storage = new FileStorage();
        this.books = storage.loadBooks();
        this.members = storage.loadMembers();
        this.loans = storage.loadLoans();
    }

    // ------------ Helpers ------------
    private int nextBookId() {
        return books.stream().mapToInt(Book::getId).max().orElse(0) + 1;
    }

    private int nextMemberId() {
        return members.stream().mapToInt(Member::getId).max().orElse(0) + 1;
    }

    public Optional<Book> findBookById(int id) {
        return books.stream().filter(b -> b.getId() == id).findFirst();
    }

    public Optional<Member> findMemberById(int id) {
        return members.stream().filter(m -> m.getId() == id).findFirst();
    }

    public List<Book> listBooks() { return new ArrayList<>(books); }
    public List<Member> listMembers() { return new ArrayList<>(members); }
    public List<Loan> listActiveLoans() { return loans.stream().filter(Loan::isActive).collect(Collectors.toList()); }

    // ------------ Core ops ------------
    public Book addBook(String title, String author) {
        Book book = new Book(nextBookId(), title, author, true);
        books.add(book);
        storage.saveBooks(books);
        return book;
    }

    public Member addMember(String name, String type) {
        Member m;
        if ("faculty".equalsIgnoreCase(type)) {
            m = new Faculty(nextMemberId(), name);
        } else {
            m = new Student(nextMemberId(), name); // default
        }
        members.add(m);
        storage.saveMembers(members);
        return m;
    }

    public List<Book> searchByTitle(String query) {
        String q = query.toLowerCase();
        return books.stream().filter(b -> b.getTitle().toLowerCase().contains(q)).collect(Collectors.toList());
    }

    public List<Book> searchByAuthor(String query) {
        String q = query.toLowerCase();
        return books.stream().filter(b -> b.getAuthor().toLowerCase().contains(q)).collect(Collectors.toList());
    }

    public String borrowBook(int memberId, int bookId) {
        Optional<Member> om = findMemberById(memberId);
        Optional<Book> ob = findBookById(bookId);

        if (!om.isPresent()) return "Member not found.";
        if (!ob.isPresent()) return "Book not found.";

        Book book = ob.get();
        Member member = om.get();

        if (!book.isAvailable()) return "Book is already on loan.";

        LocalDate borrow = LocalDate.now();
        LocalDate due = borrow.plusDays(member.getLoanPeriodDays());

        Loan loan = new Loan(book.getId(), member.getId(), borrow, due, null);
        loans.add(loan);

        book.setAvailable(false);

        storage.saveBooks(books);
        storage.saveLoans(loans);

        return "Borrowed successfully. Due date: " + due;
    }

    public String returnBook(int bookId) {
        Optional<Book> ob = findBookById(bookId);
        if (!ob.isPresent()) return "Book not found.";

        // find active loan for the book
        Optional<Loan> ol = loans.stream()
                .filter(Loan::isActive)
                .filter(l -> l.getBookId() == bookId)
                .findFirst();

        if (!ol.isPresent()) return "No active loan found for this book.";

        Loan loan = ol.get();
        loan.markReturned(LocalDate.now());

        double fine = loan.calculateFine();

        Book book = ob.get();
        book.setAvailable(true);

        storage.saveBooks(books);
        storage.saveLoans(loans);

        if (fine > 0) {
            return "Returned. Late by " + Math.round(fine / 5.0) + " day(s). Fine: ₹" + fine;
        } else {
            return "Returned on time. No fine.";
        }
    }
}