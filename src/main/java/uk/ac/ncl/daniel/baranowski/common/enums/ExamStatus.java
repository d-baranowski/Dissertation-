package uk.ac.ncl.daniel.baranowski.common.enums;

public enum ExamStatus {
    CREATED("Created"),
    STARTED("Started"),
    FINISHED("Finished"),
    MARKING_ONGOING("Marking Ongoing"),
    MARKED("Marked");

    private final String roleName;

    ExamStatus(String roleName) {
        this.roleName = roleName;
    }

    public static ExamStatus getByName(String name) {
        switch (name.toUpperCase()) {
            case "CREATED" : return CREATED;
            case "STARTED" : return STARTED;
            case "FINISHED" : return FINISHED;
            case "MARKING_ONGOING" : return MARKING_ONGOING;
            case "MARKED" : return MARKED;
            default: throw new IllegalArgumentException("No such status");
        }
    }

    @Override
    public String toString() {
        return roleName;
    }
}

