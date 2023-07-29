package assembler;

public enum Segments {
    CS( "CS"), DS("DS"), SS("SS");

    private String label;

    Segments(String id) {
        label = id;
    }

    @Override
    public String toString() {
        return label;
    }
}
