package uk.ac.ncl.daniel.baranowski.data.mappers;

import uk.ac.ncl.daniel.baranowski.data.access.pojos.TestDay;
import uk.ac.ncl.daniel.baranowski.models.TestDayModel;

public class TestDayModelMapper {
    private TestDayModelMapper() {
        //Hiding implicit public constructor
    }

    static TestDayModel mapTestDayModelFrom(TestDay day) {
        final TestDayModel result = new TestDayModel();
        result.setLocation(day.getLocation());
        result.setDate(day.getDate());


        return result;
    }

    public static TestDay mapTestDayModel(TestDayModel model, int testDayId) {
        return new TestDay.Builder()
                .setId(testDayId)
                .setDate(model.getDate())
                .setLocation(model.getLocation())
                .build();
    }
}
