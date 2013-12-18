
package org.xbib.standardnumber;

import org.testng.Assert;
import org.testng.annotations.Test;

public class EANTest extends Assert {

    @Test
    public void testEAN() throws Exception {
        String value = "4 007630 000110";
        EAN ean = new EAN().set(value).checksum().normalize().verify();
        assertEquals("4007630000116", ean.normalizedValue());
        assertEquals("4007630000116", ean.format());
    }

    @Test
    public void testEAN2() throws Exception {
        String value = "7501031311309";
        EAN ean = new EAN().set(value).checksum().normalize().verify();
        assertEquals("7501031311309", ean.normalizedValue());
        assertEquals("7501031311309", ean.format());
    }

}
