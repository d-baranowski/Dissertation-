package uk.ac.ncl.daniel.baranowski.common.enums;

public enum ExamStatus {
    CREATED("Created"),
    STARTED("Started"),
    FINISHED("Finished"),
    MARKING_ONGOING("Marking Ongoing"),
    MARKED("Marked");

    private final String status;

    ExamStatus(String status) {
        this.status = status;
    }

    public static ExamStatus getByName(String name) {
        switch (name.toUpperCase()) {
            case "CREATED" : return CREATED;
            case "STARTED" : return STARTED;
            case "FINISHED" : return FINISHED;
            case "MARKING ONGOING" : return MARKING_ONGOING;
            case "MARKED" : return MARKED;
            default: throw new IllegalArgumentException("No such status");
        }
    }

    @Override
    public String toString() {
        return status;
    }
}

