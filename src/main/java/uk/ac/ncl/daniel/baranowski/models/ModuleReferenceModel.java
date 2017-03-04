package uk.ac.ncl.daniel.baranowski.models;

import uk.ac.ncl.daniel.baranowski.data.access.pojos.Module;

public class ModuleReferenceModel {
    private Module module;

    public ModuleReferenceModel setModule(Module module) {
        this.module = module;
        return this;
    }

    public int getId() {
        return module != null ? module.getId() : 0;
    }
    public Module getModule() {
        return module;
    }
    public String getModuleCode() {
        return module.getModuleCode();
    }
    public String getReferenceName() {
        return module.getReferenceName();
    }
}
