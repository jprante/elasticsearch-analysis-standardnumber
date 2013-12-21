package org.xbib.elasticsearch.index.analysis;

import org.elasticsearch.common.collect.Sets;
import org.elasticsearch.common.settings.Settings;
import org.xbib.standardnumber.ISBN;
import org.xbib.standardnumber.StandardNumber;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import static org.elasticsearch.common.collect.Lists.newLinkedList;

public class Detector {

    private final Set<StandardNumber> stdnums;

    public Detector(Settings settings) {
        String[] s = settings.getAsArray("types", null);
        Set<String> types = s != null ? Sets.newTreeSet(Arrays.asList(s)) : null;
        this.stdnums = Sets.newLinkedHashSet();
        stdnums.addAll(types == null ?
                StandardNumberService.create() :
                StandardNumberService.create(types));
    }

    public Collection<StandardNumber> detect(CharSequence content) {
        Collection<StandardNumber> candidates = newLinkedList();
        for (StandardNumber stdnum : stdnums) {
            stdnum.reset();
            try {
                candidates.add(stdnum.set(content).normalize().verify());
            } catch (NumberFormatException e) {
                // skip
            }
        }
        return candidates;
    }

    public Collection<CharSequence> lookup(CharSequence content) {
        Collection<CharSequence> variants = newLinkedList();
        for (StandardNumber stdnum : stdnums) {
            stdnum.reset();
            if (stdnum instanceof ISBN) {
                lookupISBN(content, variants);
            } else {
                stdnum = stdnum.set(content).normalize();
                if (stdnum.isValid()) {
                    variants.add(stdnum.type().toUpperCase() + " " + stdnum.format());
                    variants.add(stdnum.type().toUpperCase() + " " + stdnum.normalizedValue());
                }
            }
        }
        return variants;
    }

    private void lookupISBN(CharSequence content, Collection<CharSequence> variants) throws NumberFormatException {
        ISBN isbn = new ISBN().set(content).normalize();
        if (!isbn.isValid()) {
            return;
        }
        if (!isbn.isEAN()) {
            // create up to 4 variants: ISBN, ISBN normalized, ISBN-13, ISBN-13 normalized
            if (isbn.isValid()) {
                variants.add("ISBN " + isbn.ean(false).format());
                variants.add("ISBN " + isbn.ean(false).normalizedValue());
            }
            isbn = isbn.ean(true).set(content).normalize();
            if (isbn.isValid()) {
                variants.add("ISBN " + isbn.format());
                variants.add("ISBN " + isbn.normalizedValue());
            }
        } else {
            // 2 variants, do not create ISBN-10 for an ISBN-13
            if (isbn.isValid()) {
                variants.add("ISBN " + isbn.ean(true).format());
                variants.add("ISBN " + isbn.ean(true).normalizedValue());
            }
        }
    }

}
