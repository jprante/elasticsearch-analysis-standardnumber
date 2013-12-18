
package org.xbib.standardnumber;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Zeitschriftendatenbank ID
 *
 *  ZDB is the world’s largest specialized database for serial titles (journals, annuals, newspapers, also
 *  e-journals).
 *
 * @see <a href="http://support.d-nb.de/iltis/onlineRoutinen/Pruefziffernberechnung.htm"></a>Prüfziffernberechnung in ILTIS</a>
 * @see <a href="https://wiki.dnb.de/pages/viewpage.action?pageId=48139522">DNB Wiki</a>
 */
public class ZDB implements Comparable<ZDB>, StandardNumber {

    private final static Pattern PATTERN = Pattern.compile("[\\p{Digit}xX\\-]{0,11}");

    private String value;

    private String formatted;

    private boolean createWithChecksum;

    @Override
    public ZDB set(CharSequence value) {
        this.value = value != null ? value.toString() : null;
        return this;
    }

    @Override
    public int compareTo(ZDB o) {
        return value != null ?  value.compareTo(o.normalizedValue()) : -1;
    }

    @Override
    public String normalizedValue() {
        return value;
    }

    @Override
    public ZDB checksum() {
        this.createWithChecksum = true;
        return this;
    }

    @Override
    public ZDB normalize() {
        Matcher m = PATTERN.matcher(value);
        if (m.find()) {
            this.value = dehyphenate(value.substring(m.start(), m.end()));
        }
        return this;
    }

    @Override
    public ZDB verify() throws NumberFormatException {
        check();
        return this;
    }

    @Override
    public String format() {
        if (formatted == null) {
            StringBuilder sb = new StringBuilder(value);
            this.formatted = sb.insert(sb.length()-1,"-").toString();
        }
        return formatted;
    }

    private void check() throws NumberFormatException {
        int l = value.length() - 1;
        int checksum = 0;
        int weight = 2;
        int val;
        for (int i = l-1; i >= 0; i--) {
            val = value.charAt(i) - '0';
            if (val < 0 || val > 9) {
                throw new NumberFormatException("not a digit: " + val );
            }
            checksum += val * weight++;
        }
        if (createWithChecksum) {
            char ch = checksum % 11 == 10 ? 'X' : (char)('0' + (checksum % 11));
            value = value.substring(0, l) + ch;
        }
        boolean valid = checksum % 11 ==
                (value.charAt(l) == 'X' || value.charAt(l) == 'x' ? 10 : value.charAt(l) - '0');
        if (!valid) {
            throw new NumberFormatException("invalid checksum: " + value.charAt(l));
        }
    }

    private String dehyphenate(String value) {
        StringBuilder sb = new StringBuilder(value);
        int i = sb.indexOf("-");
        while (i >= 0) {
            sb.deleteCharAt(i);
            i = sb.indexOf("-");
        }
        return sb.toString();
    }
}
