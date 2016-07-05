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
package org.xbib.elasticsearch.common.standardnumber.check.iso7064;

import org.xbib.elasticsearch.common.standardnumber.check.Digit;

import java.math.BigDecimal;

public class MOD9710 implements Digit {

    private static final BigDecimal CONSTANT_97 = new BigDecimal(97);

    @Override
    public String encode(String digits) {
        int c = compute(digits);
        if (c == 0) {
            return digits + "00";
        } else if (c < 10) {
            return digits + '0' + c;
        } else {
            return digits + c;
        }
    }

    @Override
    public boolean verify(String digits) {
        return new BigDecimal(digits != null ? digits : "0").remainder(CONSTANT_97).intValue() == 1;
    }

    @Override
    public int compute(String digits) {
        return new BigDecimal(digits).remainder(CONSTANT_97).intValue();
    }

    @Override
    public int getDigit(String digits) {
        return Integer.parseInt(digits.substring(digits.length() - 2));
    }

    @Override
    public String getNumber(String digits) {
        return digits.substring(0, digits.length() - 2);
    }
}
