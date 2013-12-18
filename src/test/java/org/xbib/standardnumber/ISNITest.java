
package org.xbib.standardnumber;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ISNITest extends Assert {

    @Test
    public void testISNI() throws Exception {
        String value = "0000-0002-1825-0097";
        ISNI isni = new ISNI().set(value).normalize().verify();
        assertEquals("0000000218250097", isni.normalizedValue());
        assertEquals("0000000218250097", isni.format());
    }

    @Test
    public void testISNI2() throws Exception {
        String value = "ISNI 1422 4586 3573 0476";
        ISNI isni = new ISNI().set(value).normalize().verify();
        assertEquals("1422458635730476", isni.normalizedValue());
        assertEquals("1422458635730476", isni.format());
    }

    @Test
    public void testISNI3() throws Exception {
        // fix wrong checksum in MARBI example http://www.loc.gov/marc/marbi/2010/2010-dp03.html
        String value = "ISNI 8462 8328 5653 6435";
        ISNI isni = new ISNI().set(value).checksum().normalize().verify();
        assertEquals("8462832856536436", isni.normalizedValue());
        assertEquals("8462832856536436", isni.format());
    }

    @Test
    public void testISNI4() throws Exception {
        // fix wrong checksum in MARBI example http://www.loc.gov/marc/marbi/2010/2010-dp03.html
        String value = "ISNI 0023 0000 1001 1234";
        ISNI isni = new ISNI().set(value).checksum().normalize().verify();
        assertEquals("0023000010011235", isni.normalizedValue());
        assertEquals("0023000010011235", isni.format());
    }

    @Test
    public void testISNI5() throws Exception {
        String value = "0000 0001 2195 3271";
        ISNI isni = new ISNI().set(value).normalize().verify();
        assertEquals("0000000121953271", isni.normalizedValue());
        assertEquals("0000000121953271", isni.format());
    }
}
