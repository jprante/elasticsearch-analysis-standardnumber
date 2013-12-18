
package org.xbib.standardnumber;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PPNTest extends Assert {
    
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
