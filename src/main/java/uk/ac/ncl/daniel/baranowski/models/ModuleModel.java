package uk.ac.ncl.daniel.baranowski.models;

import java.util.List;
import java.util.Objects;

public final class ModuleModel extends ModuleReferenceModel {
    private List<UserReferenceModel> moduleLeaders;
    private List<CandidateModel> students;

    public List<CandidateModel> getStudents() {
        return students;
    }
    public List<UserReferenceModel> getModuleLeaders() {
        return moduleLeaders;
    }

    public ModuleModel setModuleLeaders(List<UserReferenceModel> moduleLeaders) {
        this.moduleLeaders = moduleLeaders;
        return this;
    }

    public ModuleModel setStudents(List<CandidateModel> students) {
        this.students = students;
        return this;
    }

    /* Read this: http://www.artima.com/lejava/articles/equality.html Pitfall #4 */
    public final boolean canEqual(Object other) {
        return other instanceof ModuleModel;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj.getClass() == this.getClass())) return false;  //NOSONAR
        ModuleModel that = (ModuleModel) obj;
        return  Objects.equals(getModuleLeaders(), that.getModuleLeaders()) &&
                Objects.equals(getStudents(), that.getStudents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(moduleLeaders, students);
    }
}
