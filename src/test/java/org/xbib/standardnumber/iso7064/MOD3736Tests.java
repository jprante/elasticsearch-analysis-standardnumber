package org.xbib.standardnumber.iso7064;

import org.junit.Test;
import org.xbib.standardnumber.check.iso7064.MOD3736;

import static org.junit.Assert.assertEquals;


public class MOD3736Tests {

    @Test
    public void testMOD3736() {
        MOD3736 mod = new MOD3736();
        assertEquals(1, mod.compute("A12425GABC1234002M"));
        assertEquals(1, mod.compute("B159D8FA01240000K"));
        assertEquals(1, mod.compute("188166C7342000003"));
    }
}
