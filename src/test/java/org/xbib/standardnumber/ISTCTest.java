package org.xbib.standardnumber;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ISTCTest extends Assert {

    @Test
    public void testISTC() throws Exception {
        ISTC istc = new ISTC().set("0A920021223F3320").normalize().verify();
        assertEquals("0A920021223F3320", istc.normalizedValue());
        assertEquals("ISTC 0A9-2002-1223F332-0", istc.format());
    }

    @Test
    public void testISTC2() throws Exception {
        ISTC istc = new ISTC().set("ISTC A02-2009-000004BE-A").normalize().verify();
        assertEquals("A022009000004BEA", istc.normalizedValue());
        assertEquals("ISTC A02-2009-000004BE-A", istc.format());
    }

    @Test
    public void testISTC3() throws Exception {
        ISTC istc = new ISTC().set("ISTC 0A9 2009 12B4A105 C").normalize().verify();
        assertEquals("0A9200912B4A105C", istc.normalizedValue());
        assertEquals("ISTC 0A9-2009-12B4A105-C", istc.format());
    }

    @Test
    public void testISTC4() throws Exception {
        ISTC istc = new ISTC().set("ISTC 0A9-2002-12B4A105-7").normalize().verify();
        assertEquals("0A9200212B4A1057", istc.normalizedValue());
        assertEquals("ISTC 0A9-2002-12B4A105-7", istc.format());
    }

    @Test
    public void testISTC5() throws Exception {
        ISTC istc = new ISTC().set("A02200900000A87C").normalize().verify();
        assertEquals("A02200900000A87C", istc.normalizedValue());
        assertEquals("ISTC A02-2009-00000A87-C", istc.format());
    }

    @Test
    public void testISTC6() throws Exception {
        ISTC istc = new ISTC().set("ISTC A02-2010-31F4CB2C-B").normalize().verify();
        assertEquals("A02201031F4CB2CB", istc.normalizedValue());
        assertEquals("ISTC A02-2010-31F4CB2C-B", istc.format());
    }

}
