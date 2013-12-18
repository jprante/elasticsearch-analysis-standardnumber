package org.xbib.standardnumber;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ISWCTest extends Assert {

    @Test
    public void testISWC() throws Exception {
        ISWC iswc = new ISWC().set("T-034524680-1").normalize().verify();
        assertEquals(iswc.normalizedValue(), "T0345246801");
        assertEquals(iswc.format(), "ISWC T-034524680-1");
    }

}
