package uk.ac.ncl.daniel.baranowski.models;


public class SignedAttemptReferenceModel {
    private final String markerFullName;
    private final AttemptReferenceModel referenceModel;
    private final UserReferenceModel user;

    public SignedAttemptReferenceModel(AttemptReferenceModel referenceModel, UserReferenceModel user) {
        this.referenceModel = referenceModel;
        this.markerFullName = user.getName() + ' ' + user.getSurname();
        this.user = user;
    }

    public UserReferenceModel getMarkerReference() {
        return user;
    }

    public AttemptReferenceModel getReferenceModel() {
        return referenceModel;
    }

    public String getMarkerFullName() {
        return markerFullName;
    }
}
