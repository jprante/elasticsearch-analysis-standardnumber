package org.xbib.standardnumber;

import java.net.URI;

/**
 * ORCID
 *
 * ORCID is comptabible to International Standard Name Identifier (ISNI)  ISO 2772
 *
 * Checksum is on accordance to ISO/IEC 7064:2003, MOD 11-2
 */
public class ORCID extends ISNI {

    @Override
    public ORCID set(CharSequence value) {
        super.set(value);
        return this;
    }

    @Override
    public ORCID checksum() {
        super.checksum();
        return this;
    }

    @Override
    public ORCID normalize() {
        super.normalize();
        return this;
    }

    @Override
    public ORCID verify() throws NumberFormatException {
        super.verify();
        return this;
    }

    public URI toURI() {
        return URI.create("http://orcid.org/" + normalizedValue());
    }

}
