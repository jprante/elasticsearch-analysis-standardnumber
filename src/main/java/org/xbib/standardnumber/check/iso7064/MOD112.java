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

public class MOD112 implements Digit {

    @Override
    public String encode(String digits) {
        int c = compute(digits);
        return digits + (c == 10 ? 'X' : c);
    }

    @Override
    public boolean verify(String digits) {
        return compute(getNumber(digits)) == getDigit(digits);
    }

    @Override
    public int compute(String digits) {
        int p = 0;
        for (int i = 0; i < digits.length(); ++i) {
            int c = digits.charAt(i) - '0';
            p = 2 * (p + c);
        }
        p = p % 11;
        return (12 - p) % 11;
    }

    @Override
    public int getDigit(String digits) {
        char c = digits.charAt(digits.length() -1);
        return  c == 'X' || c == 'x' ? 10 : c - '0';
    }

    @Override
    public String getNumber(String digits) {
        return digits.substring(0, digits.length() -1);
    }

}
