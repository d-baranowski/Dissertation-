package uk.ac.ncl.daniel.baranowski.data.mappers;

import uk.ac.ncl.daniel.baranowski.data.access.pojos.Candidate;
import uk.ac.ncl.daniel.baranowski.models.CandidateModel;

public class CandidateModelMapper {
    private CandidateModelMapper() {
        //Hiding implicit public constructor
    }

    public static Candidate mapCandidateFrom(CandidateModel candidate) {
        return new Candidate.Builder()
                .setId(candidate.getId())
                .setName(candidate.getFirstName())
                .setSurname(candidate.getSurname())
                .setHasExtraTime(candidate.getHasExtraTime())
                .build();
    }

    public static CandidateModel mapCandidateModelFrom(Candidate candidate) {
        final CandidateModel result = new CandidateModel();
        result.setId(candidate.getId());
        result.setFirstName(candidate.getName());
        result.setSurname(candidate.getSurname());
        result.setHasExtraTime(candidate.getHasExtraTime());
        return result;
    }
}
