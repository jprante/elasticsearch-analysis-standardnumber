
package org.xbib.standardnumber;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PPNTests {
    
    @Test
    public void testPPN1() throws Exception {
        PPN ppn = new PPN().set("641379617").normalize().verify();
        assertEquals(ppn.normalizedValue(), "641379617");
    }

    @Test
    public void testPPN2() throws Exception {
        PPN ppn = new PPN().set("101115658X").normalize().verify();
        assertEquals(ppn.normalizedValue(), "101115658X");
    }

}
