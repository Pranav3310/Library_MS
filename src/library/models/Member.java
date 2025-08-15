package library.models;

public abstract class Member {
    protected int id;
    protected String name;

    public Member(int id, String name) {
        this.id = id;
        this.name = sanitize(name);
    }

    private static String sanitize(String s) {
        return s == null ? "" : s.replace("|", "/").trim();
    }

    public int getId() { return id; }
    public String getName() { return name; }

    /** Loan period in days. (Inheritance demo) */
    public abstract int getLoanPeriodDays();

    /** Type label for persistence: "Student" or "Faculty" */
    public abstract String getType();

    public String toCSV() {
        return id + "|" + getType() + "|" + name;
    }

    public static Member fromCSV(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length < 3) return null;
        int id = Integer.parseInt(p[0]);
        String type = p[1];
        String name = p[2];
        if ("Student".equalsIgnoreCase(type)) return new Student(id, name);
        if ("Faculty".equalsIgnoreCase(type)) return new Faculty(id, name);
        // default to Student if unknown
        return new Student(id, name);
    }

    @Override
    public String toString() {
        return "[" + id + "] " + name + " (" + getType() + ")";
    }
}
