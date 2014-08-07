package org.xbib.standardnumber;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DOITest extends Assert {

    @Test
    public void testDOI() throws Exception {
        DOI doi = new DOI().set("10.1016/0032-3861(93)90481-o").normalize().verify();
        assertEquals(doi.normalizedValue(), "10.1016/0032-3861(93)90481-o");
        assertEquals(doi.format(), "http://doi.org/10.1016/0032-3861(93)90481-o");
    }


}
