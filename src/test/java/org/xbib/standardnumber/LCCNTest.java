
package org.xbib.standardnumber;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LCCNTest extends Assert {

    /*
     *  "n78-890351" normalizes to "n78890351".
     *  "n78-89035" normalizes to "n78089035".
     *  "n 78890351 " normalizes to "n78890351".
     *  " 85000002 " normalizes to "85000002"
     *  "85-2 " normalizes to "85000002"
     *  "2001-000002" normalizes to "2001000002"
     *  "75-425165//r75" normalizes to "75425165"
     *  " 79139101 /AC/r932" normalizes to "79139101"
     */

    @Test
    public void normalizeLCCN1() throws Exception {
        String s = "n78-890351";
        LCCN lccn = new LCCN().set(s).normalize().verify();
        assertEquals(lccn.format(), "n78890351");
    }

    @Test
    public void normalizeLCCN2() throws Exception {
        String s = "n78-89035";
        LCCN lccn = new LCCN().set(s).normalize().verify();
        assertEquals(lccn.format(), "n78089035");
    }

    @Test
    public void normalizeLCCN3() throws Exception {
        String s = "n 78890351 ";
        LCCN lccn = new LCCN().set(s).normalize().verify();
        assertEquals(lccn.format(), "n78890351");
    }

    @Test
    public void normalizeLCCN4() throws Exception {
        String s = " 85000002 ";
        LCCN lccn = new LCCN().set(s).normalize().verify();
        assertEquals(lccn.format(), "85000002");
    }

    @Test
    public void normalizeLCCN5() throws Exception {
        String s = "85-2";
        LCCN lccn = new LCCN().set(s).normalize().verify();
        assertEquals(lccn.format(), "85000002");
    }

    @Test
    public void normalizeLCCN6() throws Exception {
        String s = "2001-000002";
        LCCN lccn = new LCCN().set(s).normalize().verify();
        assertEquals(lccn.format(), "2001000002");
    }

    @Test
    public void normalizeLCCN7() throws Exception {
        String s = "75-425165//r75";
        LCCN lccn = new LCCN().set(s).normalize().verify();
        assertEquals(lccn.format(), "75425165");
    }

    @Test
    public void normalizeLCCN8() throws Exception {
        String s = " 79139101 /AC/r932";
        LCCN lccn = new LCCN().set(s).normalize().verify();
        assertEquals(lccn.format(), "79139101");
    }
}
