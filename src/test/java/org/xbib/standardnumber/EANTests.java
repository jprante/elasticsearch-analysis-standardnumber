
package org.xbib.standardnumber;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EANTests {

    @Test
    public void testEAN() throws Exception {
        String value = "4 007630 000110";
        EAN ean = new EAN().set(value).createChecksum(true).normalize().verify();
        assertEquals("4007630000116", ean.normalizedValue());
        assertEquals("4007630000116", ean.format());
    }

    @Test
    public void testEAN2() throws Exception {
        String value = "7501031311309";
        EAN ean = new EAN().set(value).createChecksum(true).normalize().verify();
        assertEquals("7501031311309", ean.normalizedValue());
        assertEquals("7501031311309", ean.format());
    }

    @Test
    public void testEAN3() throws Exception {
        String value = "9781617291623";
        EAN ean = new EAN().set(value).normalize().verify();
        assertEquals("9781617291623", ean.normalizedValue());
        assertEquals("9781617291623", ean.format());
    }


}
