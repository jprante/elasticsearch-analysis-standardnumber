package org.xbib.standardnumber;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ARK Archival Resource Key
 *
 * An ARK is a Uniform Resource Locator (URL) that is a multi-purpose identifier
 * for information objects of any type. An ARK contains the label ark: after the
 * hostname, an URL request terminated by '?' returns a brief metadata record,
 * and an URL request terminated by '??' returns metadata that includes a commitment
 * statement from the current service provider.
 *
 * The ARK and its inflections ('?' and '??') gain access to three facets of a
 * provider's ability to provide persistence.
 *
 * Implicit in the design of the ARK scheme is that persistence is purely a matter
 * of service and not a property of a naming syntax.
 *
 * @see <a href="http://tools.ietf.org/html/draft-kunze-ark-18">ARK IETF RFC</a>
 * 
 * @see <a href="http://www.cdlib.org/services/uc3/docs/jak_ARKs_Berlin_2012.pdf">10 years ARK</a>
 */
public class ARK implements Comparable<ARK>, StandardNumber {

    private static final Pattern PATTERN = Pattern.compile("[\\p{Graph}\\p{Punct}]{0,48}");

    private static final Pattern URI_PATTERN = Pattern.compile("^(ark)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");

    private Object value;

    @Override
    public int compareTo(ARK ark) {
        return ark != null ? normalizedValue().compareTo(ark.normalizedValue()) : -1;
    }

    @Override
    public ARK set(CharSequence value) {
        this.value = value != null ? value.toString() : null;
        return this;
    }

    /**
     * No checksum
     * @return this ARK
     */
    @Override
    public ARK checksum() {
        return this;
    }

    @Override
    public ARK normalize() {
        if (value == null) {
            return this;
        }
        String s = value.toString();
        Matcher m = URI_PATTERN.matcher(s);
        if (m.find()) {
            this.value = URI.create(s.substring(m.start(), m.end()));
        }
        m = PATTERN.matcher(s);
        if (m.find()) {
            this.value = URI.create(s.substring(m.start(), m.end()));
        }
        return this;
    }

    /**
     * No verification.
     *
     * @return this ARK
     * @throws NumberFormatException
     */
    @Override
    public ARK verify() throws NumberFormatException {
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

    public URI asURI() {
        return (URI)value;
    }
}
