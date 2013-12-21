package org.xbib.standardnumber;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ISSNTest extends Assert {

    @Test
    public void testISSN() throws Exception {
        ISSN issn = new ISSN().set("1869-7127").normalize().verify();
        assertEquals(issn.normalizedValue(), "18697127");
        assertEquals(issn.format(), "1869-7127");
        assertEquals(issn.createChecksum(true).toGTIN("03").normalizedValue(), "9771869712038");
    }


}
