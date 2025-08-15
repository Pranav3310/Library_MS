package library.services;

import library.models.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileStorage {
    private final Path dataDir = Paths.get("data");
    private final Path booksFile = dataDir.resolve("books.csv");
    private final Path membersFile = dataDir.resolve("members.csv");
    private final Path loansFile = dataDir.resolve("loans.csv");

    public FileStorage() {
        ensureFiles();
    }

    private void ensureFiles() {
        try {
            if (!Files.exists(dataDir)) Files.createDirectories(dataDir);
            if (!Files.exists(booksFile)) Files.createFile(booksFile);
            if (!Files.exists(membersFile)) Files.createFile(membersFile);
            if (!Files.exists(loansFile)) Files.createFile(loansFile);
        } catch (IOException e) {
            throw new RuntimeException("Failed to prepare data directory", e);
        }
    }

    // ---------- Books ----------
    public List<Book> loadBooks() {
        try {
            List<String> lines = Files.readAllLines(booksFile);
            List<Book> list = new ArrayList<>();
            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()) continue;
                Book b = Book.fromCSV(line);
                if (b != null) list.add(b);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void saveBooks(List<Book> books) {
        List<String> lines = books.stream().map(Book::toCSV).collect(Collectors.toList());
        try {
            Files.write(booksFile, lines, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ---------- Members ----------
    public List<Member> loadMembers() {
        try {
            List<String> lines = Files.readAllLines(membersFile);
            List<Member> list = new ArrayList<>();
            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()) continue;
                Member m = Member.fromCSV(line);
                if (m != null) list.add(m);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void saveMembers(List<Member> members) {
        List<String> lines = members.stream().map(Member::toCSV).collect(Collectors.toList());
        try {
            Files.write(membersFile, lines, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ---------- Loans ----------
    public List<Loan> loadLoans() {
        try {
            List<String> lines = Files.readAllLines(loansFile);
            List<Loan> list = new ArrayList<>();
            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()) continue;
                Loan loan = Loan.fromCSV(line);
                if (loan != null) list.add(loan);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void saveLoans(List<Loan> loans) {
        List<String> lines = loans.stream().map(Loan::toCSV).collect(Collectors.toList());
        try {
            Files.write(loansFile, lines, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}