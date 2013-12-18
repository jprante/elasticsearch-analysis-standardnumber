package org.xbib.standardnumber;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ISO 10444 International standard technical report number
 *
 * Z39.23 Standard Technical Report Number (STRN)
 *
 * Z39.50 BIB-1 Use Attribute 1093
 *
 * A registration scheme for a globally unique International Standard Technical Report Number (ISRN)
 * was standardized in 1994 (ISO 10444), but was never implemented in practice.
 *
 * ISO finally withdrew this standard in December 2007.
 *
 * Example:
 *
 * ISRN UIUCLIS--2001/9+EARCH
 *
 * ISRN INRIA/RR--4855--FR+ENG
 *
 */
public class ISRN implements Comparable<ISRN>, StandardNumber {

    private static final Pattern PATTERN = Pattern.compile("ISRN [\\p{Graph}\\p{Punct}]{4,36}");

    private String value;

    private String formatted;

    @Override
    public int compareTo(ISRN isrn) {
        return isrn != null ? normalizedValue().compareTo(isrn.normalizedValue()) : -1;
    }

    @Override
    public ISRN set(CharSequence value) {
        this.value = value != null ? value.toString() : null;
        return this;
    }

    @Override
    public ISRN checksum() {
        return this;
    }

    @Override
    public ISRN normalize() {
        Matcher m = PATTERN.matcher(value);
        if (m.find()) {
            this.value = clean(value.substring(m.start(), m.end()));
        }
        return this;
    }

    @Override
    public ISRN verify() throws NumberFormatException {
        return this;
    }

    @Override
    public String normalizedValue() {
        return value;
    }

    @Override
    public String format() {
        return formatted;
    }

    private String clean(String raw) {
        if (raw == null) {
            return raw;
        }
        StringBuilder sb = new StringBuilder(raw);
        if (sb.indexOf("ISRN") == 0) {
            sb = new StringBuilder(sb.substring(4));
        }
        this.formatted = "ISRN "
                + sb.toString();
        return sb.toString();
    }
}
