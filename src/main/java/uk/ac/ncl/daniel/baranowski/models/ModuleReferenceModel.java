package uk.ac.ncl.daniel.baranowski.models;

import uk.ac.ncl.daniel.baranowski.data.access.pojos.Module;

import java.util.Objects;

public class ModuleReferenceModel {
    private final Module module;

    public ModuleReferenceModel(Module module) {
        this.module = module;
    }

    /* Read this: http://www.artima.com/lejava/articles/equality.html Pitfall #4 */
    public boolean canEqual(Object other) {
        return other instanceof ModuleReferenceModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; //NOSONAR
        if (o == null || !(o instanceof ModuleReferenceModel)) return false; //NOSONAR
        ModuleReferenceModel that = (ModuleReferenceModel) o;
        return  Objects.equals(getModule(), that.getModule()) &&
                that.canEqual(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(module);
    }

    public int getId() {
        return module != null ? module.getId() : 0;
    }
    public Module getModule() {
        return module;
    }
    public String getModuleCode() {
        return module != null ? module.getModuleCode() : "";
    }
    public String getReferenceName() {
        return module != null ? module.getReferenceName() : "";
    }
}
