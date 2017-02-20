package uk.ac.ncl.daniel.baranowski.data.mappers;

import org.joda.time.LocalTime;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.TestDay;
import uk.ac.ncl.daniel.baranowski.models.TestDayModel;

public class TestDayModelMapper {
    private TestDayModelMapper() {
        //Hiding implicit public constructor
    }

    public static TestDayModel mapTestDayModelFrom(TestDay day) {
        final TestDayModel result = new TestDayModel();
        result.setLocation(day.getLocation());
        result.setDate(day.getDate());
        result.setId(day.getId());
        result.setStartTime(new LocalTime(day.getStartDateTime()));
        result.setEndTime(new LocalTime(day.getEndDateTime()));

        return result;
    }

    public static TestDay mapTestDayModel(TestDayModel model, int testDayId) {
        return new TestDay.Builder()
                .setEndLocalTime(model.getEndTime().toDateTimeToday().getMillis())
                .setStartDateTime(model.getStartTime().toDateTimeToday().getMillis())
                .setId(testDayId)
                .setDate(model.getDate())
                .setLocation(model.getLocation())
                .build();
    }
}
