package org.xbib.standardnumber;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ISANTest extends Assert {

    @Test
    public void testISAN0() throws Exception {
        String value = "ISAN B159-D8FA-0124-0000-K";
        ISAN isan = new ISAN().set(value).checksum().normalize().verify();
        assertEquals(isan.normalizedValue(), "B159D8FA01240000K");
        assertEquals(isan.format(), "ISAN B159-D8FA-0124-0000-K");
    }

    @Test
    public void testISAN1() throws Exception {
        String value = "ISAN 0000-3BAB-9352-0000-G ";
        ISAN isan = new ISAN().set(value).checksum().normalize().verify();
        assertEquals(isan.normalizedValue(), "00003BAB93520000G");
        assertEquals(isan.format(), "ISAN 0000-3BAB-9352-0000-G");
    }

    @Test
    public void testISAN2() throws Exception {
        String value = "006A-15FA-002B-C95F-W";
        ISAN isan = new ISAN().set(value).checksum().normalize().verify();
        assertEquals(isan.normalizedValue(), "006A15FA002BC95FW");
        assertEquals(isan.format(), "ISAN 006A-15FA-002B-C95F-W");
    }

    @Test
    public void testISAN3() throws Exception {
        String value = "1881-66C7-3420-0000-3";
        ISAN isan = new ISAN().set(value).checksum().normalize().verify();
        assertEquals(isan.normalizedValue(), "188166C7342000003");
        assertEquals( isan.format(), "ISAN 1881-66C7-3420-0000-3");
    }

    @Test
    public void testVersionedISAN() throws Exception {
        String value = "0001-F54C-302A-8D98-N-0000-0121-O";
        ISAN isan = new ISAN().set(value).checksum().normalize().verify();
        assertEquals(isan.normalizedValue(), "0001F54C302A8D98N00000121O");
        assertEquals(isan.format(), "ISAN 0001-F54C-302A-8D98-N-0000-0121-O");
    }

    @Test
    public void testVersionedISAN2() throws Exception {
        String value = "1881-66C7-3420-6541-Y-9F3A-0245-O";
        ISAN isan = new ISAN().set(value).checksum().normalize().verify();
        assertEquals(isan.normalizedValue(), "188166C734206541Y9F3A0245O");
        assertEquals(isan.format(), "ISAN 1881-66C7-3420-6541-Y-9F3A-0245-O");
    }

    @Test
    public void testVersionedISAN3() throws Exception {
        String value = "0000-0001-8CFA-0000-I-0000-0000-K";
        ISAN isan = new ISAN().set(value).checksum().normalize().verify();
        assertEquals(isan.normalizedValue(), "000000018CFA0000I00000000K");
        assertEquals(isan.format(), "ISAN 0000-0001-8CFA-0000-I-0000-0000-K");
    }

    @Test
    public void testVersionedISAN4() throws Exception {
        String value = "0000-0000-D07A-0090-Q-0000-0000-X";
        ISAN isan = new ISAN().set(value).checksum().normalize().verify();
        assertEquals(isan.normalizedValue(), "00000000D07A0090Q00000000X");
        assertEquals(isan.format(), "ISAN 0000-0000-D07A-0090-Q-0000-0000-X");
    }

    @Test
    public void testVersionedISAN5() throws Exception {
        String value = "0000-3BAB-9352-0000-G-0000-0000-Q";
        ISAN isan = new ISAN().set(value).checksum().normalize().verify();
        assertEquals(isan.normalizedValue(), "00003BAB93520000G00000000Q");
        assertEquals(isan.format(), "ISAN 0000-3BAB-9352-0000-G-0000-0000-Q");
    }
}
