
package org.xbib.standardnumber;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SICITest extends Assert {

    @Test
    public void testSICI1() throws Exception {
        SICI sici = new SICI().set("0095-4403(199502/03)21:3<12:WATIIB>2.0.TX;2-J").normalize().verify();
        assertEquals("0095-4403(199502/03)21:3<12:WATIIB>2.0.TX;2-J", sici.normalizedValue());
        assertEquals("SICI 0095-4403(199502/03)21:3<12:WATIIB>2.0.TX;2-J", sici.format());
    }

}
