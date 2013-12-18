
package org.xbib.standardnumber;

import org.testng.Assert;
import org.testng.annotations.Test;

public class IBANTest extends Assert {

    @Test
    public void testIBAN1() throws Exception {
        String value = "GB82 WEST 1234 5698 7654 32";
        IBAN iban = new IBAN().set(value).normalize().verify();
        assertEquals(iban.normalizedValue(), "GB82WEST12345698765432");
        assertEquals(iban.format(), "GB82WEST12345698765432");
    }

    @Test
    public void testIBAN2() throws Exception {
        String value = "NL91ABNA0417164300";
        IBAN iban = new IBAN().set(value).normalize().verify();
        assertEquals("NL91ABNA0417164300", iban.normalizedValue());
        assertEquals("NL91ABNA0417164300", iban.format());
    }

    public void testIncorrectIBANS() throws Exception {
        String[] str = new String[] {
                "MT87MALT011000012345MTLCAST001S",
                "SE1212312345678901234561",
                "TN5912345678901234567890"
        };
        for (String value : str) {
            new IBAN().set(value).normalize().verify();
        }
    }

    @Test
    public void testCorrectIBANS() throws Exception {
        String[] str = new String[] {
                "AD1200012030200359100100",
                "AT611904300234573201",
                "BA391290079401028494",
                "BE68539007547034",
                "BG80BNBG96611020345678",
                "CH9300762011623852957",
                "CY17002001280000001200527600",
                "CZ6508000000192000145399",
                "DE89370400440532013000",
                "DE92600501017486501274",
                "DK5000400440116243",
                "EE382200221020145685",
                "ES9121000418450200051332",
                "FI2112345600000785",
                "FO2000400440116243",
                "FR1420041010050500013M02606",
                "GB29NWBK60161331926819",
                "GI75NWBK000000007099453",
                "GL2000400440116243",
                "GR1601101250000000012300695",
                "GR4101402940294002320000587",
                "GR7303801150000000001208017",
                "HR1210010051863000160",
                "HU42117730161111101800000000",
                "IE29AIBK93115212345678",
                "IS140159260076545510730339",
                "IT60X0542811101000000123456",
                "LI0900762011623852957",
                "LI21088100002324013AA",
                "LT121000011101001000",
                "LU280019400644750000",
                "LV80BANK0000435195001",
                "MC9320041010050500013M02606",
                "ME25505000012345678951",
                "MK07300000000042425",
                "MT84MALT011000012345MTLCAST001S",
                "MU17BOMM0101101030300200000MUR",
                "NL91ABNA0417164300",
                "NO9386011117947",
                "PL27114020040000300201355387",
                "PL61109010140000071219812874",
                "PT50000201231234567890154",
                "RO49AAAA1B31007593840000",
                "RS35260005601001611379",
                "SE3550000000054910000003",
                "SI56191000000123438",
                "SK3112000000198742637541",
                "SM88X0542811101000000123456",
                "TN5914207207100707129648",
                "TR330006100519786457841326"
        };
        for (String value : str) {
            new IBAN().set(value).normalize().verify();
        }
    }

}
