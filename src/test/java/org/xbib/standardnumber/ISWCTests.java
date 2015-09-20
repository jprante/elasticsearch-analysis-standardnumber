package org.xbib.standardnumber;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ISWCTests {

    @Test
    public void testISWC() throws Exception {
        ISWC iswc = new ISWC().set("T-034524680-1").normalize().verify();
        assertEquals("T0345246801", iswc.normalizedValue());
        assertEquals("ISWC T-034524680-1", iswc.format());
    }

}
