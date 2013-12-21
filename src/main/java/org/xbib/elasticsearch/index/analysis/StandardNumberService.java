package org.xbib.elasticsearch.index.analysis;

import org.xbib.standardnumber.ARK;
import org.xbib.standardnumber.DOI;
import org.xbib.standardnumber.EAN;
import org.xbib.standardnumber.GTIN;
import org.xbib.standardnumber.IBAN;
import org.xbib.standardnumber.ISAN;
import org.xbib.standardnumber.ISBN;
import org.xbib.standardnumber.ISMN;
import org.xbib.standardnumber.ISNI;
import org.xbib.standardnumber.ISSN;
import org.xbib.standardnumber.ISTC;
import org.xbib.standardnumber.ISWC;
import org.xbib.standardnumber.ORCID;
import org.xbib.standardnumber.PPN;
import org.xbib.standardnumber.SICI;
import org.xbib.standardnumber.StandardNumber;
import org.xbib.standardnumber.UPC;
import org.xbib.standardnumber.ZDB;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.elasticsearch.common.collect.Lists.newLinkedList;

public class StandardNumberService {

    public static StandardNumber create(String type) {
        if  ("ark".equals(type)) {
            return new ARK();
        }
        if  ("doi".equals(type)) {
            return new DOI();
        }
        if  ("ean".equals(type)) {
            return new EAN();
        }
        if  ("gtin".equals(type)) {
            return new GTIN();
        }
        if  ("iban".equals(type)) {
            return new IBAN();
        }
        if  ("isan".equals(type)) {
            return new ISAN();
        }
        if  ("isbn".equals(type)) {
            return new ISBN();
        }
        if  ("ismn".equals(type)) {
            return new ISMN();
        }
        if  ("isni".equals(type)) {
            return new ISNI();
        }
        if  ("issn".equals(type)) {
            return new ISSN();
        }
        if  ("istc".equals(type)) {
            return new ISTC();
        }
        if  ("iswc".equals(type)) {
            return new ISWC();
        }
        if  ("orcid".equals(type)) {
            return new ORCID();
        }
        if  ("ppn".equals(type)) {
            return new PPN();
        }
        if  ("sici".equals(type)) {
            return new SICI();
        }
        if  ("upc".equals(type)) {
            return new UPC();
        }
        if  ("zdb".equals(type)) {
            return new ZDB();
        }
        return null;
    }

    public static Collection<StandardNumber> create(Collection<String> types) {
        List<StandardNumber> stdnums = newLinkedList();
        for (String type : types) {
            stdnums.add(create(type));
        }
        return stdnums;
    }

    public static Collection<StandardNumber> create() {
        StandardNumber[] array = new StandardNumber[] {
                new ARK(),
                new DOI(),
                new EAN(),
                new GTIN(),
                new IBAN(),
                new ISAN(),
                new ISBN(),
                new ISMN(),
                new ISNI(),
                new ISSN(),
                new ISTC(),
                new ISWC(),
                new ORCID(),
                new PPN(),
                new SICI(),
                new UPC(),
                new ZDB()
        };
        return Arrays.asList(array);
    }

}
