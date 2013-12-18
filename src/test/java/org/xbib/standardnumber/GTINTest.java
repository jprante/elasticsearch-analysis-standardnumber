
package org.xbib.standardnumber;

import org.testng.Assert;
import org.testng.annotations.Test;

public class GTINTest extends Assert {

    @Test
    public void testGTIN() throws Exception {
        String value = "4104420033801";
        GTIN gtin = new GTIN().set(value).normalize().verify();
        assertEquals("4104420033801", gtin.normalizedValue());
        assertEquals("4104420033801", gtin.format());
    }

    @Test
    public void testISSNGTIN() throws Exception {
        String value = "977-1869712-03-8";
        GTIN gtin = new GTIN().set(value).normalize().verify();
        assertEquals("9771869712038", gtin.normalizedValue());
        assertEquals("9771869712038", gtin.format());
    }

    @Test
    public void testGTIN2() throws Exception {
        String value = "4191054501707";
        GTIN gtin = new GTIN().set(value).normalize().verify();
        assertEquals("4191054501707", gtin.normalizedValue());
        assertEquals("4191054501707", gtin.format());
    }

    @Test
    public void testISBNGTIN() throws Exception {
        String value = "9783652002264";
        GTIN gtin = new GTIN().set(value).normalize().verify();
        assertEquals("9783652002264", gtin.normalizedValue());
        assertEquals("9783652002264", gtin.format());
    }

}
