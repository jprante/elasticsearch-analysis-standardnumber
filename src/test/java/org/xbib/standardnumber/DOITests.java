package org.xbib.standardnumber;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DOITests {

    @Test
    public void testDOI() throws Exception {
        DOI doi = new DOI().set("10.1016/0032-3861(93)90481-o").normalize().verify();
        assertEquals("10.1016/0032-3861(93)90481-o", doi.normalizedValue());
        assertEquals("http://doi.org/10.1016/0032-3861(93)90481-o", doi.format());
    }


}
