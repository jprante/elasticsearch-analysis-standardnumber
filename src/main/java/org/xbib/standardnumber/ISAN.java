package org.xbib.standardnumber;

import org.xbib.standardnumber.check.iso7064.MOD3736;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ISO 15706 International Standard Audiovisual Number (ISAN)
 *
 * The International Standard Audiovisual Number (ISAN) is a unique identifier for
 * audiovisual works and related versions, similar to ISBN for books.
 *
 * It was developed within an ISO (International Organisation for Standardisation) TC46/SC9
 * working group. ISAN is managed and run by ISAN-IA.
 *
 * The ISAN standard (ISO standard 15706:2002 & ISO 15706-2) is recommended or required
 * as the audiovisual identifier of choice for producers, studios, broadcasters,
 * Internet media providers and video games publishers who need to encode, track, and
 * distribute video in a variety of formats.
 *
 * It provides a unique, internationally recognized and permanent reference number for each
 * audiovisual work and related versions registered in the ISAN system.
 *
 * ISAN identifies works throughout their entire life cycle from conception, to production,
 * to distribution and consumption.
 *
 * ISANs can be incorporated in both digital and physical media, such as theatrical
 * release prints, DVDs, publications, advertising, marketing materials and packaging,
 * as well as licensing contracts to uniquely identify works.
 *
 * The ISAN identifier is incorporated in many draft and final standards such as
 * AACS, DCI, MPEG, DVB, and ATSC.
 */
public class ISAN implements Comparable<ISAN>, StandardNumber {

    private static final Pattern PATTERN = Pattern.compile("[\\p{Alnum}\\-]{16,34}");

    private String value;

    private String formatted;

    private boolean versioned;

    private boolean createChecksum;

    @Override
    public int compareTo(ISAN isan) {
        return isan != null ? normalizedValue().compareTo(isan.normalizedValue()) : -1;
    }

    @Override
    public ISAN set(CharSequence value) {
        this.value = value != null ? value.toString() : null;
        return this;
    }

    @Override
    public ISAN checksum() {
        return this;
    }

    @Override
    public ISAN normalize() {
        Matcher m = PATTERN.matcher(value);
        if (m.find()) {
            this.value = clean(value.substring(m.start(), m.end()));
            versioned = value.length() > 17;
        }
        return this;
    }

    @Override
    public ISAN verify() throws NumberFormatException {
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

    public ISAN versioned() {
        this.versioned = true;
        return this;
    }

    private final static MOD3736 check = new MOD3736();

    private void check() throws NumberFormatException {
        if (value == null) {
            throw new NumberFormatException("null");
        }
        if (versioned) {
            int chk1 = check.compute(value.substring(0,17));
            int chk2 = check.compute(value.substring(0,16) + value.substring(17,26));
            if (chk1 != 1) {
                throw new NumberFormatException("invalid first checksum: " + chk1);
            }
            if (chk2 != 1) {
                throw new NumberFormatException("invalid second checksum: " + chk2);
            }
        } else {
            int chk = check.compute(value);
            if (chk != 1) {
                throw new NumberFormatException("invalid checksum: " + chk);
            }
        }
    }

    private String clean(String value) {
        StringBuilder sb = new StringBuilder(value);
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
        this.formatted = "ISAN "
                + sb.substring(0,4)
                + "-"
                + sb.substring(4,8)
                + "-"
                + sb.substring(8,12)
                + "-"
                + sb.substring(12,16)
                + "-"
                + sb.substring(16,17)
                + (sb.length() > 17 ? "-"
                        + sb.substring(17,21)
                        + "-"
                        + sb.substring(21,25)
                        + "-"
                        + sb.substring(25,26)
                : ""
        );
        return sb.toString();
    }
}
