package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.Iterator;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    //In WORDS_FILE;
    //In COUNTS_FILE;
    String wordsFile;
    String countsFile;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        //WORDS_FILE= new In(wordsFilename);//constructor 里不要在声明，不然不会写入上面的属性，而且这里面声明的下面也不能用
        //COUNTS_FILE = new In(countsFilename);
        wordsFile = wordsFilename;
        countsFile = countsFilename;
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        In WORDS_FILE = new In(wordsFile);
        TimeSeries wordHistory = new TimeSeries();
        while (WORDS_FILE.hasNextLine()) {
            String wordsInformation = WORDS_FILE.readLine();
            String[] wordsInformationList = wordsInformation.split("\t");
            String lineWord = wordsInformationList[0];
            int lineYear = Integer.parseInt(wordsInformationList[1]);
            if (lineWord.equals(word) &&
            lineYear >= startYear &&
            lineYear <= endYear) {
                double value = Double.parseDouble(wordsInformationList[2]);
                wordHistory.put(lineYear, value);
            }
        }
        WORDS_FILE.close();
        return wordHistory;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.
        In WORDS_FILE = new In(wordsFile);
        TimeSeries wordHistory = new TimeSeries();
        while (WORDS_FILE.hasNextLine()) {
            String wordsInformation = WORDS_FILE.readLine();
            String[] wordsInformationList = wordsInformation.split("\t");
            if (wordsInformationList[0].equals(word)) {
                int year = Integer.parseInt(wordsInformationList[1]);
                double value = Double.parseDouble(wordsInformationList[2]);
                wordHistory.put(year, value);
            }
        }
        return wordHistory;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this method.
        TimeSeries countHistory = new TimeSeries();
        In COUNTS_FILE = new In(countsFile);
        while (COUNTS_FILE.hasNextLine()) {
            String countInformation = COUNTS_FILE.readLine();
            String[] countInformationList = countInformation.split(",");
            int year = Integer.parseInt(countInformationList[0]);
            double countValue = Double.parseDouble(countInformationList[1]);
            countHistory.put(year, countValue);
        }
        return countHistory;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries wordCountHistory = countHistory(word, startYear, endYear);
        wordCountHistory = wordCountHistory.dividedBy(totalCountHistory());
        return wordCountHistory;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        // TODO: Fill in this method.
        TimeSeries wordCountHistory = countHistory(word);
        wordCountHistory = wordCountHistory.dividedBy(totalCountHistory());
        return wordCountHistory;
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        // TODO: Fill in this method.
        Iterator wordsIterator = words.iterator();
        TimeSeries summedWeight = new TimeSeries();
        TimeSeries allWordsHistory = new TimeSeries();
        while (wordsIterator.hasNext()) {
            String thisWord = (String) wordsIterator.next();
            TimeSeries thisWordHistory = countHistory(thisWord, startYear, endYear);
            allWordsHistory = allWordsHistory.plus(thisWordHistory);
        }
        summedWeight = allWordsHistory.dividedBy(totalCountHistory());
        return summedWeight;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // TODO: Fill in this method.
        Iterator wordsIterator = words.iterator();
        TimeSeries summedWeight;
        TimeSeries allWordsHistory = new TimeSeries();
        while (wordsIterator.hasNext()) {
            String thisWord = (String) wordsIterator.next();
            TimeSeries thisWordHistory = countHistory(thisWord);
            allWordsHistory = allWordsHistory.plus(thisWordHistory);
        }
        summedWeight = allWordsHistory.dividedBy(totalCountHistory());
        return summedWeight;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
