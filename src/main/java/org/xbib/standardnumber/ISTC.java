package org.xbib.standardnumber;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ISO 21047: International Standard Text Code (ISTC)
 *
 * The International Standard Text Code (ISTC) is a numbering system for the unique identification
 * of text-based works; the term “work” can refer to any content appearing in conventional
 * printed books, audio-books, static e-books or enhanced digital books, as well as content
 * which might appear in a newspaper or journal.
 *
 * The ISTC provides sales analysis systems, retail websites, library catalogs and other
 * bibliographic systems with a method of automatically linking together publications
 * of the “same content” and/or “related content”, thus improving discoverability of
 * products and efficiencies.
 *
 * An ISTC number is the link between a user’s search for a piece of content and the
 * ultimate sale or loan of a publication.
 *
 * The standard was formally published in March 2009
 *
 * Checksum algorithm is ISO 7064 MOD 16/3
 *
 */
public class ISTC implements Comparable<ISTC>, StandardNumber {

    private static final Pattern PATTERN = Pattern.compile("[\\p{Alnum}\\-\\s]{12,24}");

    private String value;

    private String formatted;

    private boolean createWithChecksum;

    @Override
    public int compareTo(ISTC istc) {
        return istc != null ? normalizedValue().compareTo(istc.normalizedValue()) : -1;
    }

    @Override
    public ISTC set(CharSequence value) {
        this.value = value != null ? value.toString() : null;
        return this;
    }

    @Override
    public ISTC checksum() {
        this.createWithChecksum = true;
        return this;
    }

    @Override
    public ISTC normalize() {
        Matcher m = PATTERN.matcher(value);
        if (m.find()) {
            this.value = clean(value.substring(m.start(), m.end()));
        }
        return this;
    }

    @Override
    public ISTC verify() throws NumberFormatException {
        check();
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

    private void check() throws NumberFormatException {
        if (value == null) {
            throw new NumberFormatException("null");
        }
        int l = value.length() - 1;
        int checksum = 0;
        int weight;
        int factor;
        int val;
        for (int i = 0; i < l; i++) {
            val = value.charAt(i);
            if (val >= 'A' && val <= 'Z') {
                val = 10 + (val - 'A');
            }
            if (val >= '0' && val <= '9') {
                val = val - '0';
            }
            if (val < 0 || val > 35) {
                throw new NumberFormatException("not a valid symbol: " + val );
            }
            factor = i % 4 < 2 ? 1 : 5;
            weight = (12 - 2 * (i % 4)) - factor; // --> 11,9,3,1
            checksum += val * weight;
        }
        int chk = checksum % 16;
        if (createWithChecksum) {
            char ch = chk > 9 ? (char)(10 + (chk - 'A')) : (char)('0' + chk);
            value = value.substring(0, l) + ch;
        }
        char digit = value.charAt(l);
        int chk2 = (digit >= '0' && digit <= '9') ? digit - '0' : digit -'A' + 10;
        boolean valid = chk == chk2;
        if (!valid) {
            throw new NumberFormatException("invalid checksum: " + chk + " != " + chk2);
        }
    }

    private String clean(String raw) {
        if (raw == null) {
            return raw;
        }
        StringBuilder sb = new StringBuilder(raw);
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
        if (sb.indexOf("ISTC") == 0) {
            sb = new StringBuilder(sb.substring(4));
        }
        this.formatted = "ISTC "
                + sb.substring(0,3) + "-"
                + sb.substring(3,7) + "-"
                + sb.substring(7,15) + "-"
                + sb.substring(15);
        return sb.toString();
    }
}
