package org.xbib.elasticsearch.index.analysis;

import org.xbib.standardnumber.ISBN;
import org.xbib.standardnumber.StandardNumber;

import java.util.Collection;

import static org.elasticsearch.common.collect.Lists.newLinkedList;

public class Detector {

    public Collection<StandardNumber> detect(CharSequence content) {
        Collection<StandardNumber> candidates = newLinkedList();
        try {
            ISBN isbn = new ISBN().set(content).normalize().verify();
            candidates.add(isbn);
        } catch (NumberFormatException e) {
            // ignore ISBN
        }
        return candidates;
    }

    public Collection<CharSequence> lookup(CharSequence content) {
        Collection<CharSequence> variants = newLinkedList();
        try {
            ISBN isbn = new ISBN().set(content).normalize().verify();
            if (!isbn.isEAN()) {
                // create up to 4 variants: ISBN, ISBN normalized, ISBN-13, ISBN-13 normalized
                variants.add(isbn.ean(false).format());
                variants.add(isbn.ean(false).normalizedValue());
                isbn = isbn.ean(true).set(content).normalize().verify();
                variants.add(isbn.format());
                variants.add(isbn.normalizedValue());
            } else {
                // 2 variants, do not create ISBN-10 for an ISBN-13
                variants.add(isbn.ean(true).format());
                variants.add(isbn.ean(true).normalizedValue());
            }
        } catch (NumberFormatException e) {
            // ignore ISBN
        }
        return variants;
    }
}
