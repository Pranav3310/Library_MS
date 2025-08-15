package library.models;

public class Book {
    private int id;
    private String title;
    private String author;
    private boolean available;

    public Book(int id, String title, String author, boolean available) {
        this.id = id;
        this.title = sanitize(title);
        this.author = sanitize(author);
        this.available = available;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    private static String sanitize(String s) {
        return s == null ? "" : s.replace("|", "/").trim();
    }

    public String toCSV() {
        return id + "|" + title + "|" + author + "|" + available;
    }

    public static Book fromCSV(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 4) return null;
        int id = Integer.parseInt(parts[0]);
        String title = parts[1];
        String author = parts[2];
        boolean available = Boolean.parseBoolean(parts[3]);
        return new Book(id, title, author, available);
    }

    @Override
    public String toString() {
        return "[" + id + "] " + title + " — " + author + (available ? " (Available)" : " (On loan)");
    }
}
