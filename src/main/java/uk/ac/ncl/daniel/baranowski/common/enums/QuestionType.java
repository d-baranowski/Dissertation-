package uk.ac.ncl.daniel.baranowski.common.enums;

public enum QuestionType {
    CODE("Code"),
    DRAWING("Drawing"),
    ESSAY("Essay"),
    MULTIPLE_CHOICE("Multiple Choice");

    private final String columnName;

    QuestionType(String tableName) {
        this.columnName = tableName;
    }

    @Override
    public String toString() {
        return columnName;
    }
}