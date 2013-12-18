package org.xbib.standardnumber;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ISO 15420 Universal Product Code (UPC)
 *
 * The Universal Product Code (UPC) is a barcode symbology (i.e., a specific type of barcode)
 * that is widely used in the United States, Canada, the United Kingdom, Australia,
 * New Zealand and in other countries for tracking trade items in stores.
 * Its most common form, the UPC-A, consists of 12 numerical digits, which are uniquely
 * assigned to each trade item.
 *
 * Along with the related EAN barcode, the UPC is the barcode mainly used for scanning
 * of trade items at the point of sale, per GS1 specifications.
 *
 * UPC data structures are a component of GTINs (Global Trade Item Numbers).
 *
 * All of these data structures follow the global GS1 specification which bases on
 * international standards.
 *
 */
public class UPC implements Comparable<UPC>, StandardNumber {

    private static final Pattern PATTERN = Pattern.compile("[\\p{Digit}]{0,12}");

    private String value;

    private boolean createWithChecksum;

    @Override
    public int compareTo(UPC upc) {
        return upc != null ? normalizedValue().compareTo(upc.normalizedValue()) : -1;
    }

    @Override
    public UPC set(CharSequence value) {
        this.value = value != null ? value.toString() : null;
        return this;
    }

    @Override
    public UPC checksum() {
        this.createWithChecksum = true;
        return this;
    }

    @Override
    public UPC normalize() {
        Matcher m = PATTERN.matcher(value);
        if (m.find()) {
            this.value = value.substring(m.start(), m.end());
        }
        return this;
    }

    @Override
    public UPC verify() throws NumberFormatException {
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
        int l = value.length() - 1;
        int checksum = 0;
        int weight;
        int val;
        for (int i = 0; i < l; i++) {
            val = value.charAt(i) - '0';
            if (val < 0 || val > 9) {
                throw new NumberFormatException("not a digit: " + val );
            }
            weight = i%2 == 0 ? 3 : 1;
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
}
