package org.xbib.standardnumber;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ISO 26324: Digital Object Identifier System (DOI)
 *
 * Z39.50 BIB-1 Use Attribute 1094
 *
 * DOI is an acronym for "digital object identifier", meaning a "digital identifier of an object"
 * rather than an "identifier of a digital object". The DOI system was initiated by the
 * International DOI Foundation in 1998, and initially developed with the collaboration
 * of some participants in ISO/TC46/SC9. Due to its application in the fields of
 * information and documentation and previous collaboration with some ISO/TC46/SC9 participants,
 * it was introduced as a possible work item in 2004 and further developed from 2006 to 2010.
 *
 * The DOI system is designed to work over the Internet. A DOI name is permanently assigned
 * to an object to provide a resolvable persistent network link to current information about
 * that object, including where the object, or information about it, can be found on the
 * Internet. While information about an object can change over time, its DOI name will not
 * change. A DOI name can be resolved within the DOI system to values of one or more types
 * of data relating to the object identified by that DOI name, such as a URL, an e-mail address,
 * other identifiers and descriptive metadata.
 *
 * The DOI system enables the construction of automated services and transactions.
 * Applications of the DOI system include but are not limited to managing information
 * and documentation location and access; managing metadata; facilitating electronic
 * transactions; persistent unique identification of any form of any data; and commercial
 * and non-commercial transactions.
 *
 * The content of an object associated with a DOI name is described unambiguously
 * by DOI metadata, based on a structured extensible data model that enables the object
 * to be associated with metadata of any desired degree of precision and granularity
 * to support description and services. The data model supports interoperability
 * between DOI applications.
 *
 * The scope of the DOI system is not defined by reference to the type of content
 * (format, etc.) of the referent, but by reference to the functionalities it provides
 * and the context of use. The DOI system provides, within networks of DOI applications,
 * for unique identification, persistence, resolution, metadata and semantic interoperability.
 *
 */
public class DOI implements Comparable<DOI>, StandardNumber {

    private static final Pattern PATTERN = Pattern.compile("\\b10\\.\\d{4}([.][0-9]+)*/[/-a-z0-9.()<>_:;\\\\]+\\b");

    private static final Pattern URI_PATTERN = Pattern.compile("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]\\b");

    private String prefix = "http://doi.org/";

    private Object raw;

    private URI value;

    @Override
    public String type() {
        return "doi";
    }

    @Override
    public int compareTo(DOI doi) {
        return doi != null ? normalizedValue().compareTo(doi.normalizedValue()) : -1;
    }

    @Override
    public DOI set(CharSequence value) {
        this.raw = value;
        return this;
    }

    @Override
    public DOI createChecksum(boolean checksum) {
        return this;
    }

    @Override
    public DOI normalize() {
        if (raw == null) {
            return this;
        }
        this.value = make(raw.toString());
        return this;
    }

    @Override
    public boolean isValid() {
        return value != null;
    }

    @Override
    public DOI verify() throws NumberFormatException {
        if (value == null) {
            throw new NumberFormatException();
        }
        return this;
    }

    @Override
    public String normalizedValue() {
        return value != null ? value.toString() : null;
    }

    @Override
    public String format() {
        return value != null ? value.toString() : null;
    }

    public DOI prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public URI asURI() {
        return value;
    }

    public DOI reset() {
        this.value = null;
        return this;
    }

    private URI make(String content) {
        // is it an already a DOI URI?
        Matcher m = URI_PATTERN.matcher(content);
        if (m.find()) {
            String t = content.substring(m.start(), m.end());
            URI u = URI.create(t);
            if ("http".equals(u.getScheme()) && ("dx.doi.org".equals(u.getHost()) || "doi.org".equals(u.getHost()))) {
                m = PATTERN.matcher(u.getRawPath());
                if (m.find()) {
                    return u;
                }
            }
        }
        // not an URI, just the path?
        m = PATTERN.matcher(content);
        if (m.find()) {
            return URI.create(prefix + content.substring(m.start(), m.end()));
        } else {
            return null;
        }
    }

}
