package uk.ac.ncl.daniel.baranowski.data.mappers;

import uk.ac.ncl.daniel.baranowski.data.access.pojos.Module;
import uk.ac.ncl.daniel.baranowski.models.CandidateModel;
import uk.ac.ncl.daniel.baranowski.models.ModuleModel;
import uk.ac.ncl.daniel.baranowski.models.ModuleReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.UserReferenceModel;

import java.util.List;

public class ModuleModelMapper {

    public static ModuleModel mapModuleModel(Module module, List<UserReferenceModel> moduleLeaders, List<CandidateModel> candidateModelList) {
        ModuleModel result = new ModuleModel();
        result.setModule(module);
        result.setModuleLeaders(moduleLeaders);
        result.setStudents(candidateModelList);
        return result;
    }

    public static ModuleReferenceModel mapModuleReferenceModel(Module module) {
        ModuleReferenceModel result = new ModuleReferenceModel();
        result.setModule(module);
        return result;
    }
}
