package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession session = new TrainingSession(group, coach, DayOfWeek.MONDAY,
                new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(session);

        TreeMap<TimeOfDay, List<TrainingSession>> monday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        assertEquals(1, monday.size());
        assertEquals(1, monday.get(new TimeOfDay(13, 0)).size());
        assertEquals(session, monday.get(new TimeOfDay(13, 0)).get(0));

        TreeMap<TimeOfDay, List<TrainingSession>> tuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertTrue(tuesday.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdult = new TrainingSession(groupAdult, coach, DayOfWeek.THURSDAY,
                new TimeOfDay(20, 0));
        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChild = new TrainingSession(groupChild, coach, DayOfWeek.MONDAY,
                new TimeOfDay(13, 0));
        TrainingSession thursdayChild = new TrainingSession(groupChild, coach, DayOfWeek.THURSDAY,
                new TimeOfDay(13, 0));
        TrainingSession saturdayChild = new TrainingSession(groupChild, coach, DayOfWeek.SATURDAY,
                new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(thursdayAdult);
        timetable.addNewTrainingSession(mondayChild);
        timetable.addNewTrainingSession(thursdayChild);
        timetable.addNewTrainingSession(saturdayChild);

        TreeMap<TimeOfDay, List<TrainingSession>> monday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, monday.size());
        assertEquals(1, monday.get(new TimeOfDay(13, 0)).size());

        TreeMap<TimeOfDay, List<TrainingSession>> thursday = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        assertEquals(2, thursday.size());
        // Проверяем сортировку: 13:00 идет раньше 20:00
        assertEquals(thursdayChild, thursday.firstEntry().getValue().get(0));
        assertEquals(thursdayAdult, thursday.lastEntry().getValue().get(0));

        TreeMap<TimeOfDay, List<TrainingSession>> tuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertTrue(tuesday.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession session = new TrainingSession(group, coach, DayOfWeek.MONDAY,
                new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(session);

        List<TrainingSession> found = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0));
        assertEquals(1, found.size());

        List<TrainingSession> notFound = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(14, 0));
        assertTrue(notFound.isEmpty());
    }

    @Test
    void testGetCountByCoaches_Sorting() {
        Timetable timetable = new Timetable();
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Семенов", "Виктор", "Константинович");
        Coach coach3 = new Coach("Иванов", "Алексей", "Петрович");

        Group group = new Group("Бокс", Age.ADULT, 90);

        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.MONDAY,
                new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.WEDNESDAY,
                new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.FRIDAY,
                new TimeOfDay(10, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, coach2, DayOfWeek.TUESDAY,
                new TimeOfDay(12, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, coach3, DayOfWeek.MONDAY,
                new TimeOfDay(15, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach3, DayOfWeek.THURSDAY,
                new TimeOfDay(15, 0)));

        List<CounterForCoach> result = timetable.getCountByCoaches();

        assertEquals(3, result.size());
        assertEquals("Васильев", result.get(0).getCoach().getSurname());
        assertEquals(3, result.get(0).getCount());
        assertEquals("Иванов", result.get(1).getCoach().getSurname());
        assertEquals(2, result.get(1).getCount());
        assertEquals("Семенов", result.get(2).getCoach().getSurname());
        assertEquals(1, result.get(2).getCount());
    }

    @Test
    void testGetCountByCoaches_Empty() {
        Timetable timetable = new Timetable();
        List<CounterForCoach> result = timetable.getCountByCoaches();
        assertTrue(result.isEmpty());
    }
}
