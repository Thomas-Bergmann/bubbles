package de.hatoka.common.capi.value;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.Optional;

/**
 * An incomplete date describes a date with unknown information of year, month or date. For example the birthday is
 * known, but not the year.
 */
public class IncompleteDate
{
    private static final LocalDate DATE_1970 = LocalDate.of(1970, 1, 1);
    private static final String PLACEHOLDER_DAY = "dd";
    private static final String PLACEHOLDER_MONTH = "mm";
    private static final String PLACEHOLDER_YEARYYYY = "yyyy";
    private static final IncompleteDate UNKNOWN_DATE = new IncompleteDate(Optional.empty(), Optional.empty(),
                    Optional.empty());

    /**
     * @param year year or null if unknown
     * @param month month 1..12 or null if unknown
     * @param day day 1..31 or null if unknown
     * @return incomplete date representation
     */
    public static IncompleteDate valueOf(Integer year, Integer month, Integer day)
    {
        if (year == null && month == null && day == null)
        {
            return UNKNOWN_DATE;
        }
        return new IncompleteDate(Optional.ofNullable(year), Optional.ofNullable(month), Optional.ofNullable(day));
    }

    /**
     * @param incompleteDate yyyy/mm/dd with valued of present
     * @return incomplete date representation
     */
    public static IncompleteDate valueOf(String incompleteDate)
    {
        if (incompleteDate == null || incompleteDate.isBlank())
        {
            return UNKNOWN_DATE;
        }
        String[] parts = incompleteDate.split("/");
        if (parts.length != 3)
        {
            throw new IllegalArgumentException("Can't parse incomplete date: '" + incompleteDate + "'");
        }
        return new IncompleteDate(parse(parts[0], PLACEHOLDER_YEARYYYY), parse(parts[1], PLACEHOLDER_MONTH),
                        parse(parts[2], PLACEHOLDER_DAY));
    }

    /**
     * @param number
     * @param placeHolder for specific position
     * @return number
     */
    private static Optional<Integer> parse(String number, String placeHolder)
    {
        if (placeHolder.equals(number))
        {
            return Optional.empty();
        }
        return Optional.of(Integer.valueOf(number));
    }

    private final Optional<Integer> year;
    private final Optional<Integer> month;
    private final Optional<Integer> day;

    private IncompleteDate(Optional<Integer> year, Optional<Integer> month, Optional<Integer> day)
    {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Optional<Integer> getYear()
    {
        return year;
    }

    public Optional<Integer> getMonth()
    {
        return month;
    }

    public Optional<Integer> getDay()
    {
        return day;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(day, month, year);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        IncompleteDate other = (IncompleteDate)obj;
        return Objects.equals(day, other.day) && Objects.equals(month, other.month) && Objects.equals(year, other.year);
    }

    @Override
    public String toString()
    {
        return year.map(y -> String.format("%04d", y)).orElse(PLACEHOLDER_YEARYYYY) + "/"
                        + month.map(m -> String.format("%02d", m)).orElse(PLACEHOLDER_MONTH) + "/"
                        + day.map(d -> String.format("%02d", d)).orElse(PLACEHOLDER_DAY);
    }

    /**
     * Calculates the age of a person or duration of a relation in years. In case the month or day is unknown "1" is
     * used as fallback.
     * 
     * @param date1
     * @param date2
     * @return duration between date1 and date2
     */
    public static Optional<Period> between(IncompleteDate date1, IncompleteDate date2)
    {
        return between(date1, date2, LocalDate.now());
    }

    /**
     * Calculates the age of a person or duration of a relation in years. In case the month or day is unknown "1" is
     * used as fallback.
     * @param date1
     * @param date2
     * @param fallback for now
     * @return duration between date1 and date2
     */
    static Optional<Period> between(IncompleteDate date1, IncompleteDate date2, LocalDate fallback)
    {
        // no chance if the start year is unknown
        if (date1.year.isEmpty())
        {
            return Optional.empty();
        }
        return Optional.of(Period.between(date1.getFittingDate(DATE_1970), date2.getFittingDate(fallback)));
    }

    /**
     * Calculates the LocalDate of this object, unknown parts are retrieved from fallbackDate
     * @param fallbackDate
     * @return
     */
    LocalDate getFittingDate(LocalDate fallbackDate)
    {
        return LocalDate.of(year.orElse(fallbackDate.getYear()), month.orElse(fallbackDate.getMonth().getValue()), day.orElse(fallbackDate.getDayOfMonth()));
    }
}
