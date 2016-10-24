import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Scanner;

public class TransientCreator {
    private ArrayList<Transient> transients = new ArrayList<Transient>();

    public TransientCreator() throws IOException {
        createAllTransients(parseLSSTData());
    }

    /*
     * Connects to the LSST Database with Jsoup, parses all the data and returns
     * it as one very long string.
     */

    private String parseLSSTData() throws IOException {
        Document doc = Jsoup.connect(
                                     "http://nesssi.cacr.caltech.edu/MLS/CRTSII_Allns.html#34")
        .get();
        String pageText = doc.body().text();
        String allTransientData = pageText.substring(353, pageText.length());
        return allTransientData;
    }

    /*
     * Takes the long string of all the transient data returned by
     * parseLSSTData() and breaks it up into several smaller strings, with each
     * smaller string containing the data for a single transient. Transient
     * objects are created one at a time in a while loop by passing a
     * single-transient data string to the createSingleTransient() method, then
     * the transient objects are inserted into the transients ArrayList.
     */

    private void createAllTransients(String allTransientData) {
        Scanner allDataScanner = new Scanner(allTransientData);
        allDataScanner.useDelimiter("MLS");
        while (allDataScanner.hasNext()) {
            String singleTransientData = "MLS" + allDataScanner.next();
            Transient t = createSingleTransient(singleTransientData);
            transients.add(t);
        }
    }

    /*
     * Returns a Transient object by taking in a single-transient data string
     * created inside the createAllTransients() method. The data string is then
     * broken up into several doubles, integers and smaller strings, with each
     * representing a specific attribute of the transient such as id, magnitude,
     * classification etc. A transient object is then created and the parsed
     * attribute variables are passed into its constructor.
     */

    private Transient createSingleTransient(String singleTransientData) {
        Scanner singleTransientDataScanner = new Scanner(singleTransientData);
        String id = singleTransientDataScanner.next();
        double ra = singleTransientDataScanner.nextDouble();
        double dec = singleTransientDataScanner.nextDouble();
        String dDate = singleTransientDataScanner.next();
        double mag = singleTransientDataScanner.nextDouble();
        String sdss = singleTransientDataScanner.next();
        String lDate = singleTransientDataScanner.next();
        int lc = singleTransientDataScanner.nextInt();
        String fc = singleTransientDataScanner.next();
        Transient t = new Transient(id, ra, dec, dDate, mag,
                                    sdssOrFcToBoolean(sdss), lDate, lc, sdssOrFcToBoolean(fc),
                                    parseClassification(singleTransientDataScanner));
        return t;
    }

    /*
     * The classification attribute on the LSST database is made up of several
     * spaced strings, and the amount of strings varies for each transient, so
     * this method concatenates them into one string by taking in the string
     * scanner created in the createSingleTransient() method. By the time the
     * scanner is passed to this method, it is already 9 tokens into the
     * single-transient data string, and the classification is the string's final
     * piece of data, so it just grabs the rest of the tokens in the string and
     * shoves them all in a single classification string variable which this
     * method returns.
     */

    private String parseClassification(Scanner singleTransientDataScanner) {
        String classification = "";
        while (singleTransientDataScanner.hasNext()) {
            classification += singleTransientDataScanner.next() + " ";
        }
        String output = classification.substring(0, classification.length() - 1);
        return output;
    }

    /*
     * The sdss and fc variables are parsed as strings that read "yes" or "no", this
     * method returns a boolean based on those strings, either true or false respectively.
     */

    private boolean sdssOrFcToBoolean(String sdssOrFc) {
        if (sdssOrFc.equals("yes")) {
            return true;
        }
        return false;
    }

    public ArrayList<Transient> getTransients() {
        return transients;
    }
}
