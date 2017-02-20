package uk.ac.ncl.daniel.baranowski.data.access.pojos;

import java.util.Objects;

public final class Module {
    private final int id;
    private final String moduleCode;
    private final String referenceName;

    public String getModuleCode() {
        return moduleCode;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public int getId() {
        return id;
    }

    private Module(Builder builder) {
        this.moduleCode = builder.moduleCode;
        this.referenceName = builder.referenceName;
        this.id = builder.id;
    }

    @Override
    public boolean equals(Object o) { //NOSONAR
        if (this == o) return true; //NOSONAR
        if (o == null || getClass() != o.getClass()) return false; //NOSONAR
        Module module = (Module) o;
        return Objects.equals(getId(), module.getId()) && //NOSONAR
                Objects.equals(getReferenceName(), module.getReferenceName()) &&
                Objects.equals(getModuleCode(), module.getModuleCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getReferenceName(), getModuleCode());
    }

    public static class Builder {
        private String moduleCode;
        private String referenceName;
        private int id;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setModuleCode(String moduleCode) {
            this.moduleCode = moduleCode;
            return this;
        }

        public Builder setReferenceName(String referenceName) {
            this.referenceName = referenceName;
            return this;
        }

        public Module build() {
            return new Module(this);
        }
    }
}