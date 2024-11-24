package ngrams;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /** If it helps speed up your code, you can assume year arguments to your NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here. */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        // TODO: Fill in this constructor.
        Iterator<Integer> yearsIterator = ts.years().iterator();
        while (yearsIterator.hasNext()) {
            Integer year = yearsIterator.next();
            this.put(year, ts.get(year));
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        // TODO: Fill in this method.
        List<Integer> yearsList;
        yearsList = new ArrayList<>(keySet());
        return yearsList;
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        // TODO: Fill in this method.
        List<Double> dataList = new ArrayList<>();
        List<Integer> yearsList = years();
        Iterator<Integer> yearsIterator = yearsList.iterator();
        while (yearsIterator.hasNext()) {
            Double data = get(yearsIterator.next());
            dataList.add(data);
        }
        return dataList;
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        // TODO: Fill in this method.
        List<Integer> yearsList = ts.years();
        List<Double> dataList = ts.data();
        Iterator<Integer> yearsIterator = yearsList.iterator();
        Iterator<Double> dataIterator = dataList.iterator();
        while (yearsIterator.hasNext() || dataIterator.hasNext()) {
            Integer yearsKey = yearsIterator.next();
            double dataValue = dataIterator.next();
            if (! this.containsKey(yearsKey)) {
                this.put(yearsKey, dataValue);
            } else {
                double originValue = this.get(yearsKey);
                dataValue = dataValue + originValue;
                this.put(yearsKey, dataValue);
            }
        }
        return this;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        // TODO: Fill in this method.
        TimeSeries quotientSeries = new TimeSeries();
        List<Integer> yearsList = years();
        List<Double> dataList = data();
        Iterator<Integer> yearsIterator = yearsList.iterator();
        Iterator<Double> dataIterator = dataList.iterator();
        while (yearsIterator.hasNext() || dataIterator.hasNext()) {
            Integer beDividedKey = yearsIterator.next();
            Double beDividedValue = dataIterator.next();
            if (ts.containsKey(beDividedKey)) {
                double divisor = ts.get(beDividedKey);
                if (divisor == 0) {
                    throw new ArithmeticException("divisor is 0");
                }
                beDividedValue = beDividedValue / divisor;
                quotientSeries.put(beDividedKey, beDividedValue);
            } else {
                throw new  IllegalArgumentException("TS  miss a year that exists in this TimeSeries");
            }
        }
        return quotientSeries;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
