package org.xbib.standardnumber.check;

public class LuhnMOD10 implements Digit {

    public String encode(String digits) {
        return digits + compute(digits);
    }

    public void verify(String digits) throws NumberFormatException {
        boolean b = (computeSum(digits) % 10) == 0;
        if (!b) {
            throw new NumberFormatException("bad checksum");
        }
    }

    public int compute(String digits) {
        int val = computeSum(digits);
        return (val == 0) ? 0 : (10 - val);
    }

    public int getDigit(String digits) {
        return digits.charAt(digits.length() - 1) - '0';
    }

    public String getNumber(String digits) {
        return digits.substring(0, digits.length() - 1);
    }

    private int computeSum(String digits) {
        int val = 0;
        for (int i = 0; i < digits.length(); i += 2) {
            int c = digits.charAt(i) - '0';
            if (c < 0 || c > 9) {
                throw new NumberFormatException("Bad digit: '" + digits.charAt(i) + "'");
            }
            val += c;
        }

        for (int i = 1; i < digits.length(); i += 2) {
            int c = digits.charAt(i) - '0';
            if (c < 0 || c > 9) {
                throw new NumberFormatException("Bad digit: '" + digits.charAt(i) + "'");
            }
            val += (c >= 5) ? (2*c - 9) : (2*c);
        }
        return val % 10;
    }
}
