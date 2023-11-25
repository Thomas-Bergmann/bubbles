package de.hatoka.common.capi.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.Period;

import org.junit.jupiter.api.Test;

class IncompleteDateTest
{
    private static final String FULL_DATE_STRING = "2013/06/07";
    private static final String YEAR_ONLY_STRING = "2013/mm/dd";
    private static final String NO_YEAR_STRING = "yyyy/06/07";
    private static final LocalDate NOW = LocalDate.of(2023, 11, 11);
    private static final IncompleteDate NOW_INCOMPLETE = IncompleteDate.valueOf("2023/11/11");

    @Test
    void testFullDate()
    {
        IncompleteDate fullDate = IncompleteDate.valueOf(FULL_DATE_STRING);
        assertEquals(2013, fullDate.getYear().get());
        assertEquals(6, fullDate.getMonth().get());
        assertEquals(7, fullDate.getDay().get());
        assertEquals(FULL_DATE_STRING, fullDate.toString());
    }

    @Test
    void testPartlyUnknown()
    {
        IncompleteDate yearOnly = IncompleteDate.valueOf(YEAR_ONLY_STRING);
        assertEquals(2013, yearOnly.getYear().get());
        assertTrue(yearOnly.getMonth().isEmpty());
        assertTrue(yearOnly.getDay().isEmpty());
        assertEquals(YEAR_ONLY_STRING, yearOnly.toString());

        IncompleteDate noYear = IncompleteDate.valueOf(NO_YEAR_STRING);
        assertTrue(noYear.getYear().isEmpty());
        assertEquals(6, noYear.getMonth().get());
        assertEquals(7, noYear.getDay().get());
        assertEquals(NO_YEAR_STRING, noYear.toString());
    }

    @Test
    void testFittingDate()
    {
        LocalDate onlyYear = IncompleteDate.valueOf("2013/mm/dd").getFittingDate(NOW);
        assertEquals(2013, onlyYear.getYear());
        assertEquals(11, onlyYear.getMonth().getValue());
        assertEquals(11, onlyYear.getDayOfMonth());
        LocalDate noDay = IncompleteDate.valueOf("2013/06/dd").getFittingDate(NOW);
        assertEquals(2013, noDay.getYear());
        assertEquals(6, noDay.getMonth().getValue());
        assertEquals(11, noDay.getDayOfMonth());
    }

    @Test
    void testBetween()
    {
        // both dates are defined
        Period periodSimple = IncompleteDate.between(IncompleteDate.valueOf("2013/01/01"), NOW_INCOMPLETE).get();
        assertEquals(10, periodSimple.getYears());
        assertEquals(10, periodSimple.getMonths());
        assertEquals(10, periodSimple.getDays());
        // same, because fallback is January first
        assertEquals(periodSimple, IncompleteDate.between(IncompleteDate.valueOf("2013/mm/dd"), NOW_INCOMPLETE).get());
        // empty be cause start date has no year
        assertTrue(IncompleteDate.between(IncompleteDate.valueOf("yyyy/01/01"), NOW_INCOMPLETE).isEmpty());
        // fallback used
        assertEquals(periodSimple, IncompleteDate.between(IncompleteDate.valueOf("2013/01/01"), IncompleteDate.valueOf("yyyy/mm/dd"), NOW).get());
    }
}
