package org.xbib.standardnumber;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Global Trade Item Number (GTIN)
 *
 * GTIN describes a family of GS1 (EAN.UCC) global data structures that employ
 * 14 digits and can be encoded into various types of data carriers.
 *
 * Currently, GTIN is used exclusively within bar codes, but it could also be used
 * in other data carriers such as radio frequency identification (RFID).
 * The GTIN is only a term and does not impact any existing standards, nor does
 * it place any additional requirements on scanning hardware.
 *
 * For North American companies, the UPC is an existing form of the GTIN.
 *
 * Since 2005, EAN International and American UCC merged to GS1 and also
 * EAN and UPC is now named GTIN.
 *
 * The EAN/UCC-13 code is now officially called GTIN-13 (Global Trade Identifier Number).
 * Former 12-digit UPC codes can be converted into EAN/UCC-13 code by simply
 * adding a preceeding zero.
 *
 * As of January 1, 2007, the former ISBN numbers have been integrated into
 * the EAN/UCC-13 code. For each old ISBN-10 code, there exists a proper translation
 * into EAN/UCC-13 by adding "978" as prefix.
 *
 * The family of data structures comprising GTIN include:
 *
 * GTIN-8 (EAN/UCC-8): this is an 8-digit number
 * GTIN-12 (UPC-A): this is a 12-digit number
 * GTIN-13 (EAN/UCC-13): this is a 13-digit number
 * GTIN-14 (EAN/UCC-14 or ITF-14): this is a 14-digit number
 *
 * @see <a href="http://www.gtin.info/">GTIN info</a>
 */
public class GTIN implements Comparable<GTIN>, StandardNumber {

    private static final Pattern PATTERN = Pattern.compile("[\\p{Digit}\\-]{8,18}");

    private String value;

    private boolean createWithChecksum;

    @Override
    public int compareTo(GTIN gtin) {
        return gtin != null ? normalizedValue().compareTo(gtin.normalizedValue()) : -1;
    }

    @Override
    public GTIN set(CharSequence value) {
        this.value = value != null ? value.toString() : null;
        return this;
    }

    @Override
    public GTIN checksum() {
        this.createWithChecksum = true;
        return this;
    }

    @Override
    public GTIN normalize() {
        Matcher m = PATTERN.matcher(value);
        if (m.find()) {
            this.value = dehyphenate(value.substring(m.start(), m.end()));
        }
        return this;
    }

    @Override
    public GTIN verify() throws NumberFormatException {
        check();
        return this;
    }

    @Override
    public String normalizedValue() {
        return value;
    }

    @Override
    public String format() {
        return value;
    }

    private void check() throws NumberFormatException {
        if (value == null) {
            throw new NumberFormatException("null is invalid");
        }
        int l = value.length() - 1;
        int checksum = 0;
        int weight;
        int val;
        for (int i = 0; i < l; i++) {
            val = value.charAt(i) - '0';
            if (val < 0 || val > 9) {
                throw new NumberFormatException("not a digit: " + val );
            }
            weight = i % 2 == 0 ? 1 : 3;
            checksum += val * weight;
        }
        int chk = 10 - checksum % 10;
        if (createWithChecksum) {
            char ch = (char)('0' + chk);
            value = value.substring(0, l) + ch;
        }
        boolean valid = chk == (value.charAt(l) - '0');
        if (!valid) {
            throw new NumberFormatException("invalid checksum: " + chk + " != " + value.charAt(l));
        }
    }

    private String dehyphenate(String isbn) {
        StringBuilder sb = new StringBuilder(isbn);
        int i = sb.indexOf("-");
        while (i >= 0) {
            sb.deleteCharAt(i);
            i = sb.indexOf("-");
        }
        return sb.toString();
    }
}
