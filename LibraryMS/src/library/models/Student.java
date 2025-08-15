package library.models;

public class Student extends Member {
    public Student(int id, String name) { super(id, name); }
    @Override public int getLoanPeriodDays() { return 14; }
    @Override public String getType() { return "Student"; }
}