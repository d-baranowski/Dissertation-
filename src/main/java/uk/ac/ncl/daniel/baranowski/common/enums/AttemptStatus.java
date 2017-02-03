package uk.ac.ncl.daniel.baranowski.common.enums;

public enum AttemptStatus {
    CREATED(),
    STARTED(),
    FINISHED(),
    MARKING_ONGOING(),
    MARKED();

    public static AttemptStatus getByName(String name) {
        switch (name) {
            case "CREATED" : return CREATED;
            case "STARTED" : return STARTED;
            case "FINISHED" : return FINISHED;
            case "MARKING_ONGOING" : return MARKING_ONGOING;
            case "MARKED" : return MARKED;
            default: throw new IllegalArgumentException("No such status");
        }
    }
}

