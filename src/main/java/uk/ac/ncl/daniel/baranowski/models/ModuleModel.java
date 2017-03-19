package uk.ac.ncl.daniel.baranowski.models;

import uk.ac.ncl.daniel.baranowski.data.access.pojos.Module;

import java.util.List;
import java.util.Objects;

public final class ModuleModel extends ModuleReferenceModel {
    private final List<UserReferenceModel> moduleLeaders;
    private final  List<CandidateModel> students;

    public ModuleModel(Module module, List<UserReferenceModel> moduleLeaders, List<CandidateModel> students) {
        super(module);
        this.moduleLeaders = moduleLeaders;
        this.students = students;
    }

    public List<CandidateModel> getStudents() {
        return students;
    }
    public List<UserReferenceModel> getModuleLeaders() {
        return moduleLeaders;
    }

    /* Read this: http://www.artima.com/lejava/articles/equality.html Pitfall #4 */
    @Override
    public final boolean canEqual(Object other) {
        return other instanceof ModuleModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; //NOSONAR
        if (o == null || getClass() != o.getClass()) return false; //NOSONAR
        if (!super.equals(o)) return false; //NOSONAR
        ModuleModel that = (ModuleModel) o;
        return Objects.equals(getStudents(), that.getStudents()) && //NOSONAR
                Objects.equals(getModuleLeaders(), that.getModuleLeaders()) &&
                that.canEqual(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getStudents(),getModuleLeaders());
    }

}
