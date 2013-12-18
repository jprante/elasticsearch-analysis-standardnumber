
package org.xbib.standardnumber;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ISBNTest extends Assert {

    @Test
    public void testDehypenate() {
        assertEquals(new ISBN().set("000-111-333").normalize().normalizedValue(), "000111333");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testISBNTooShort() throws Exception {
        new ISBN().set("12-7").normalize().verify();
    }

    @Test
    public void testDirtyISBN() throws Exception {
        String value = "ISBN 3-9803350-5-4 kart. : DM 24.00";
        ISBN isbn = new ISBN().set(value).normalize().verify();
        assertEquals(isbn.normalizedValue(), "3980335054");
    }

    @Test(expectedExceptions = NumberFormatException.class)
    public void testTruncatedISBN() throws Exception {
        String value = "ISBN";
        new ISBN().set(value).normalize().verify();
    }

    @Test
    public void fixChecksum() throws Exception {
        String value = "3616065810";
        ISBN isbn = new ISBN().set(value).checksum().normalize().verify();
        assertEquals(isbn.normalizedValue(), "361606581X");
    }

    @Test
    public void testEAN() throws Exception {
        String value = "978-3-551-75213-0";
        ISBN ean = new ISBN().set(value).ean(true).normalize().verify();
        assertEquals(ean.normalizedValue(), "9783551752130");
        assertEquals(ean.format(), "978-3-551-75213-0");
    }

    @Test
    public void testEAN2() throws Exception {
        String value = "978-3-551-75213-1";
        ISBN ean = new ISBN().set(value).ean(true).checksum().normalize().verify();
        assertEquals(ean.normalizedValue(), "9783551752130");
        assertEquals(ean.format(), "978-3-551-75213-0");
    }

    @Test(expectedExceptions = NumberFormatException.class)
    public void testWrongAndDirtyEAN() throws Exception {
        // correct ISBN-10 is 3-451-04112-X
        String value = "ISBN ISBN 3-451-4112-X kart. : DM 24.80";
        new ISBN().set(value).ean(false).checksum().normalize().verify();
    }
}
