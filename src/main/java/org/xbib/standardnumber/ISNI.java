package org.xbib.standardnumber;

import org.xbib.standardnumber.check.iso7064.MOD112;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ISO 27729: International Standard Name Identifier (ISNI)
 *
 * The International Standard Name Identifier (ISNI) is a method for uniquely identifying
 * the public identities of contributors to media content such as books, TV programmes,
 * and newspaper articles. Such an identifier consists of 16 numerical digits divided
 * into four blocks.
 *
 * Checksum is in accordance to ISO/IEC 7064:2003, MOD 11-2
 */
public class ISNI implements Comparable<ISNI>, StandardNumber {

    private final static Pattern PATTERN = Pattern.compile("[\\p{Digit}xX\\-\\s]{16,24}");

    private String value;

    private String formatted;

    private boolean createWithChecksum;

    /**
     * Creates a new ISNI
     *
     * @param value the value
     */
    @Override
    public ISNI set(CharSequence value) {
        this.value = value != null ? value.toString() : null;
        return this;
    }

    @Override
    public ISNI checksum() {
        this.createWithChecksum = true;
        return this;
    }

    @Override
    public int compareTo(ISNI isni) {
        return value != null ? value.compareTo((isni).normalizedValue()) : -1;
    }

    @Override
    public ISNI verify() throws NumberFormatException {
        check();
        return this;
    }

    /**
     * Returns the value representation of the standard number
     * @return value
     */
    @Override
    public String normalizedValue() {
        return value;
    }

    /**
     * Format this number
     *
     * @return the formatted number
     */
    @Override
    public String format() {
        if (formatted == null) {
            this.formatted = value;
        }
        return formatted;
    }

    @Override
    public ISNI normalize() {
        Matcher m = PATTERN.matcher(value);
        if (m.find()) {
            this.value = clean(value.substring(m.start(), m.end()));
        }
        return this;
    }

    private final static MOD112 check = new MOD112();

    private void check() throws NumberFormatException {
        if (createWithChecksum) {
            this.value = check.encode(value.length() < 16 ? value : value.substring(0, value.length()-1));
        }
        if (value.length() < 16) {
            throw new NumberFormatException("too short: " + value);
        }
        check.verify(value);
    }

    private String clean(String isbn) {
        StringBuilder sb = new StringBuilder(isbn);
        int i = sb.indexOf("-");
        while (i >= 0) {
            sb.deleteCharAt(i);
            i = sb.indexOf("-");
        }
        i = sb.indexOf(" ");
        while (i >= 0) {
            sb.deleteCharAt(i);
            i = sb.indexOf(" ");
        }
        return sb.toString();
    }

}
