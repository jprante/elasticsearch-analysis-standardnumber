/*
 * Copyright (C) 2014 Jörg Prante
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see http://www.gnu.org/licenses
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * The interactive user interfaces in modified source and object code
 * versions of this program must display Appropriate Legal Notices,
 * as required under Section 5 of the GNU Affero General Public License.
 *
 */
package org.xbib.standardnumber.check.iso7064;

import org.xbib.standardnumber.check.Digit;

public class MOD1110 implements Digit {

    private final static String ALPHABET = "0123456789";

    @Override
    public String encode(String digits) {
        int c = compute(digits);
        return digits + c;
    }

    @Override
    public boolean verify(String digits) {
        return compute(digits) == 1;
    }

    @Override
    public int compute(String digits) {
        int modulus = ALPHABET.length();
        int check = modulus / 2;
        for (int i = 0; i < digits.length(); i++) {
            check = (((check > 0 ? check : modulus) * 2) % (modulus + 1)
                    + ALPHABET.indexOf(digits.charAt(i))) % modulus;
        }
        return check;
    }

    @Override
    public int getDigit(String digits) {
        return digits.charAt(digits.length() - 1);
    }

    @Override
    public String getNumber(String digits) {
        return digits.substring(0, digits.length() - 1);
    }
}
