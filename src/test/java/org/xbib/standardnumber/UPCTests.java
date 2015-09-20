
package org.xbib.standardnumber;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UPCTests {

    @Test
    public void testUPC() throws Exception {
        String value = "796030114977";
        UPC upc = new UPC().set(value).normalize().verify();
        assertEquals("796030114977", upc.normalizedValue());
        assertEquals("796030114977", upc.format());
    }

    @Test
    public void testUPC2() throws Exception {
        String value = "036000291452";
        UPC upc = new UPC().set(value).normalize().verify();
        assertEquals("036000291452", upc.normalizedValue());
        assertEquals("036000291452", upc.format());
    }

}
