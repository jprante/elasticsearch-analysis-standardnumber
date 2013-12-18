package org.xbib.standardnumber.iso7064;

import org.testng.annotations.Test;
import org.xbib.standardnumber.check.iso7064.MOD3736;

import static org.testng.Assert.assertEquals;

public class MOD3736Test {

    @Test
    public void testMOD3736() {
        MOD3736 mod = new MOD3736();
        assertEquals(mod.compute("A12425GABC1234002M"), 1);
        assertEquals(mod.compute("B159D8FA01240000K"), 1);
        assertEquals(mod.compute("188166C7342000003"), 1);
    }
}
