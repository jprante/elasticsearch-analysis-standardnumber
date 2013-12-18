package org.xbib.standardnumber;

/**
 * A standard number is a number that
 *
 * - is backed by an international standard or a de-facto community use
 *
 * - can accept alphanumeric values (digits and letters and separator characters)
 *
 * - can be normalizedValue
 *
 * - can be verified and raise en error is verification fails
 *
 * - must have a checksum
 *
 * - can be formatted to a printable representation
 *
 */
public interface StandardNumber {

    /**
     * Set the input value of this standard number. The input must be normalized
     * and verified before being accepted as valid.
     * @param value the raw input value
     * @return this standard number
     */
    StandardNumber set(CharSequence value);

    /**
     * Normalize the value by removing all unwanted characters or
     * replacing characters with the ones required for verification.
     * @return this standard number
     */
    StandardNumber normalize();

    /**
     * Verify the number.
     * @return this standard number if verification was successful
     * @throws NumberFormatException if verification failed
     */
    StandardNumber verify() throws NumberFormatException;

    /**
     * Indicate that a correct check sum should be computed.
     * @return this standard number
     */
    StandardNumber checksum();

    /**
     * Return normalized value of this standard number.
     * This is a representation without unneccessary characters, useful
     * for computation purposes.
     * @return normalized value
     */
    String normalizedValue();

    /**
     *
     * @return
     */
    String format();
}
