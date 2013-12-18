
package org.xbib.standardnumber;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ZDBTest extends Assert {

    @Test
    public void testZDB1() throws Exception {
        ZDB zdb = new ZDB().set("127").normalize().verify();
        assertEquals("127", zdb.normalizedValue());
    }

    @Test
    public void testZDB2() throws Exception {
        ZDB zdb = new ZDB().set("127976-2").normalize().verify();
        assertEquals("1279762", zdb.normalizedValue());
        assertEquals("127976-2", zdb.format());
    }

    @Test
    public void testZDB3() throws Exception {
        ZDB zdb = new ZDB().set("1279760").checksum().normalize().verify();
        assertEquals("1279762", zdb.normalizedValue());
        assertEquals("127976-2", zdb.format());
    }

}
