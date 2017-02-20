package uk.ac.ncl.daniel.baranowski.common.enums;

public enum Roles {
    MARKER("Marker"),
    ADMIN("Admin"),
    AUTHOR("Author"),
    MODULE_LEADER("ModuleLeader");

    private final String roleName;

    Roles(String roleName) {
        this.roleName = roleName;
    }

    public static Roles getByName(String roleName) {
        switch (roleName) {
            case "Marker": return MARKER;
            case "Admin": return ADMIN;
            case "Author": return AUTHOR;
            case "ModuleLeader": return MODULE_LEADER;
            default: return null;
        }
    }

    @Override
    public String toString() {
        return roleName;
    }
}

