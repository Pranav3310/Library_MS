package library;

import library.models.*;
import library.services.LibraryService;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final LibraryService service = new LibraryService();

    public static void main(String[] args) {
        System.out.println("===== Library Management System =====");
        while (true) {
            printMenu();
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1: handleAddBook(); break;
                case 2: handleAddMember(); break;
                case 3: handleListBooks(); break;
                case 4: handleListMembers(); break;
                case 5: handleSearchTitle(); break;
                case 6: handleSearchAuthor(); break;
                case 7: handleBorrow(); break;
                case 8: handleReturn(); break;
                case 9: handleViewActiveLoans(); break;
                case 0:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice, try again.");
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("\n1. Add Book");
        System.out.println("2. Add Member (Student/Faculty)");
        System.out.println("3. List Books");
        System.out.println("4. List Members");
        System.out.println("5. Search Book by Title");
        System.out.println("6. Search Book by Author");
        System.out.println("7. Borrow Book");
        System.out.println("8. Return Book");
        System.out.println("9. View Active Loans");
        System.out.println("0. Exit");
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String s = sc.nextLine().trim();
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private static void handleAddBook() {
        String title = readLine("Title: ");
        String author = readLine("Author: ");
        Book b = service.addBook(title, author);
        System.out.println("Added: " + b);
    }

    private static void handleAddMember() {
        String name = readLine("Name: ");
        String type = readLine("Type (Student/Faculty): ");
        Member m = service.addMember(name, type);
        System.out.println("Added: " + m);
    }

    private static void handleListBooks() {
        List<Book> books = service.listBooks();
        if (books.isEmpty()) {
            System.out.println("No books yet.");
            return;
        }
        books.forEach(System.out::println);
    }

    private static void handleListMembers() {
        List<Member> members = service.listMembers();
        if (members.isEmpty()) {
            System.out.println("No members yet.");
            return;
        }
        members.forEach(System.out::println);
    }

    private static void handleSearchTitle() {
        String q = readLine("Title contains: ");
        List<Book> books = service.searchByTitle(q);
        if (books.isEmpty()) {
            System.out.println("No matches.");
            return;
        }
        books.forEach(System.out::println);
    }

    private static void handleSearchAuthor() {
        String q = readLine("Author contains: ");
        List<Book> books = service.searchByAuthor(q);
        if (books.isEmpty()) {
            System.out.println("No matches.");
            return;
        }
        books.forEach(System.out::println);
    }

    private static void handleBorrow() {
        int memberId = readInt("Member ID: ");
        int bookId = readInt("Book ID: ");
        String msg = service.borrowBook(memberId, bookId);
        System.out.println(msg);
    }

    private static void handleReturn() {
        int bookId = readInt("Book ID: ");
        String msg = service.returnBook(bookId);
        System.out.println(msg);
    }

    private static void handleViewActiveLoans() {
        List<Loan> loans = service.listActiveLoans();
        if (loans.isEmpty()) {
            System.out.println("No active loans.");
            return;
        }
        loans.forEach(System.out::println);
    }
}
