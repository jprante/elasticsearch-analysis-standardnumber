package org.xbib.standardnumber;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ISSNTests {

    @Test
    public void testISSN() throws Exception {
        ISSN issn = new ISSN().set("1869-7127").normalize().verify();
        assertEquals(issn.normalizedValue(), "18697127");
        assertEquals(issn.format(), "1869-7127");
        assertEquals(issn.createChecksum(true).toGTIN("03").normalizedValue(), "9771869712038");
    }

    @Test
    public void testISSN2() throws Exception {
        ISSN issn = new ISSN().set("ISSN 1932-7447").normalize().verify();
        assertEquals(issn.normalizedValue(), "19327447");
        assertEquals(issn.format(), "1932-7447");
        assertEquals(issn.createChecksum(true).toGTIN("03").normalizedValue(), "9771932744034");
    }

    @Test
    public void testISSN3() throws Exception {
        ISSN issn = new ISSN().set("0729-011X").normalize().verify();
        assertEquals(issn.normalizedValue(), "0729011X");
        assertEquals(issn.format(), "0729-011X");
        assertEquals(issn.createChecksum(true).toGTIN("03").normalizedValue(), "9770729011038");
    }

    @Test(expected = NumberFormatException.class)
    public void testNonISSN() throws Exception {
        String value = "linux";
        new ISSN().set(value).createChecksum(true).normalize().verify();
    }
}
