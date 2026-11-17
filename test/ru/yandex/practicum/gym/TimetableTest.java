package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        List<TrainingSession> monday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, monday.size(), "В понедельник должно быть 1 занятие");
        Assertions.assertEquals(singleTrainingSession, monday.get(0), "Занятие должно совпадать с добавленным");

        List<TrainingSession> tuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertTrue(tuesday.isEmpty(), "Во вторник не должно быть занятий");

    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        List<TrainingSession> monday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, monday.size());
        Assertions.assertEquals(mondayChildTrainingSession, monday.get(0));

        List<TrainingSession> thursday = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Assertions.assertEquals(2, thursday.size());

        Assertions.assertEquals(thursdayChildTrainingSession, thursday.get(0));
        Assertions.assertEquals(thursdayAdultTrainingSession, thursday.get(1));

        List<TrainingSession> tuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertTrue(tuesday.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> found = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0));
        Assertions.assertEquals(1, found.size()); Assertions.assertEquals(singleTrainingSession, found.get(0));

        List<TrainingSession> notFound = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(14, 0));
        Assertions.assertTrue(notFound.isEmpty());
    }

    @Test void testGetCountByCoaches_Sorting() {
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

        List<Map.Entry<Coach, Integer>> result = timetable.getCountByCoaches();

        Assertions.assertEquals(3, result.size());

        Assertions.assertEquals("Васильев", result.get(0).getKey().getSurname());
        Assertions.assertEquals(3, result.get(0).getValue());

        Assertions.assertEquals("Иванов", result.get(1).getKey().getSurname());
        Assertions.assertEquals(2, result.get(1).getValue());

        Assertions.assertEquals("Семенов", result.get(2).getKey().getSurname());
        Assertions.assertEquals(1, result.get(2).getValue());
    }

    @Test void testGetCountByCoaches_Empty() {
        Timetable timetable = new Timetable();
        List<Map.Entry<Coach, Integer>> result = timetable.getCountByCoaches();
        Assertions.assertTrue(result.isEmpty());
    }

}
