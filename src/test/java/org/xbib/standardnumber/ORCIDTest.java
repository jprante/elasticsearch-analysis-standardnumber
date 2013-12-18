
package org.xbib.standardnumber;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ORCIDTest extends Assert {

    @Test
    public void testORCID() throws Exception {
        String value = "0000-0002-1825-0097";
        ORCID orcid = new ORCID().set(value).normalize().verify();
        assertEquals("0000000218250097", orcid.normalizedValue());
        assertEquals("0000000218250097", orcid.format());
        assertEquals("http://orcid.org/0000000218250097", orcid.toURI().toString());
    }

    @Test
    public void testORCID2() throws Exception {
        String value = "0000-0001-5109-3700";
        ORCID orcid = new ORCID().set(value).normalize().verify();
        assertEquals("0000000151093700", orcid.normalizedValue());
        assertEquals("0000000151093700", orcid.format());
        assertEquals("http://orcid.org/0000000151093700", orcid.toURI().toString());
    }

    @Test
    public void testORCID3() throws Exception {
        String value = "0000-0002-1694-233X";
        ORCID orcid = new ORCID().set(value).normalize().verify();
        assertEquals("000000021694233X", orcid.normalizedValue());
        assertEquals("000000021694233X", orcid.format());
        assertEquals("http://orcid.org/000000021694233X", orcid.toURI().toString());
    }
}
