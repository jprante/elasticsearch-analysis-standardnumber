package org.xbib.standardnumber;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ISSNTest extends Assert {

    @Test
    public void testISSN() throws Exception {
        ISSN issn = new ISSN().set("1869-7127").normalize().verify();
        assertEquals("18697127", issn.normalizedValue());
        assertEquals("1869-7127", issn.format());
        assertEquals("9771869712038", issn.checksum().toGTIN("03").normalizedValue());
    }


}
