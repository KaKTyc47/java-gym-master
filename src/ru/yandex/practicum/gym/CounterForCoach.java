package ru.yandex.practicum.gym;
import java.util.Objects;

import java.util.Objects;

public class CounterForCoach implements Comparable<CounterForCoach> {
    private final Coach coach;
    private final int count;

    public CounterForCoach(Coach coach, int count) {
        this.coach = coach; this.count = count;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getCount() {
        return count;
    }

    @Override
    public int compareTo(CounterForCoach o) {
        int countComparison = Integer.compare(o.count, this.count);
        if (countComparison != 0) {
            return countComparison;
        }
        return this.coach.getSurname().compareTo(o.coach.getSurname());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CounterForCoach that = (CounterForCoach) o;
        return count == that.count && coach.equals(that.coach);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coach, count);
    }
}
