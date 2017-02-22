package uk.ac.ncl.daniel.baranowski.data.mappers;

import org.joda.time.LocalTime;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.TestDay;
import uk.ac.ncl.daniel.baranowski.models.TestDayModel;

import static uk.ac.ncl.daniel.baranowski.common.Constants.TIME_PATTERN;

public class TestDayModelMapper {
    private TestDayModelMapper() {
        //Hiding implicit public constructor
    }

    public static TestDayModel mapTestDayModelFrom(TestDay day) {
        final TestDayModel result = new TestDayModel();
        result.setLocation(day.getLocation());
        result.setDate(day.getDate());
        result.setId(day.getId());
        result.setStartTime(LocalTime.parse(day.getStartTime()));
        result.setEndTime(LocalTime.parse(day.getEndTime()));
        result.setEndTimeWithExtraTime(LocalTime.parse(day.getEndTimeWithExtraTime()));

        return result;
    }

    public static TestDay mapTestDayModel(TestDayModel model, int testDayId) {
        return new TestDay.Builder()
                .setEndLocalTime(model.getEndTime().toString(TIME_PATTERN))
                .setStartTime(model.getStartTime().toString(TIME_PATTERN))
                .setId(testDayId)
                .setDate(model.getDate())
                .setLocation(model.getLocation())
                .build();
    }
}
