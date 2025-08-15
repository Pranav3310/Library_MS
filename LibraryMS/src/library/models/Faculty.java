package library.models;

public class Faculty extends Member {
    public Faculty(int id, String name) { super(id, name); }
    @Override public int getLoanPeriodDays() { return 30; }
    @Override public String getType() { return "Faculty"; }
}