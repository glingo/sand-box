package templating.lexer;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

public class Source implements CharSequence {

    /**
     * The characters found within the template.
     */
    private char source[];

    /**
     * Number of characters stored in source array remaining to be tokenized
     */
    private int size = 0;

    /**
     * Default capacity
     */
    private static final int DEFAULT_CAPACITY = 1024;

    /**
     * An index of the first character for the remaining un-tokenized source.
     */
    private int offset = 0;

    /**
     * Tracking the line number that we are currently tokenizing.
     */
    private int lineNumber = 1;

    /**
     * Filename of the template
     */
    private final String filename;

    public Source(Reader reader, String filename) throws IOException {
        this.filename = filename;
        this.source = new char[DEFAULT_CAPACITY];
        copyReaderIntoCharArray(reader);
    }

    /**
     * Read the contents of the template into the internal char[].
     *
     * @param reader
     * @return
     * @throws IOException
     */
    private void copyReaderIntoCharArray(Reader reader) throws IOException {
        char[] buffer = new char[1024 * 4];
        int amountJustRead = 0;
        while ((amountJustRead = reader.read(buffer)) != -1) {

            ensureCapacity(size + amountJustRead);
            append(buffer, amountJustRead);
        }
        reader.close();
    }

    /**
     * Append characters to the internal array.
     *
     * @param characters
     * @param amount
     */
    private void append(char[] characters, int amount) {
        for (int i = 0; i < amount; ++i) {
            this.source[size + i] = characters[i];
        }
        size += amount;
    }

    /**
     * Ensure that the internal array has a minimum capacity.
     *
     * @param minCapacity
     */
    private void ensureCapacity(int minCapacity) {
        if (source.length - minCapacity < 0) {
            grow(minCapacity);
        }
    }

    /**
     * Grow the internal array to at least the desired minimum capacity.
     *
     * @param minCapacity
     */
    private void grow(int minCapacity) {
        int oldCapacity = source.length;

        /*
         * double the capacity of the array and if that's not enough, just use
         * the minCapacity
         */
        int newCapacity = Math.max(oldCapacity << 1, minCapacity);

        this.source = Arrays.copyOf(source, newCapacity);
    }

    /**
     * Moves the start index a certain amount. While traversing this amount we
     * will count how many newlines have been encountered.
     *
     * @param amount Amount of characters to advance by
     */
    public void advance(int amount) {

        int index = 0;
        while (index < amount) {
            int sizeOfNewline = advanceThroughNewline(index);

            if (sizeOfNewline > 0) {
                index += sizeOfNewline;
            } else {
                index++;
            }
        }

        this.size -= amount;
        this.offset += amount;
    }

    public void advanceThroughWhitespace() {
        int index = 0;

        while (Character.isWhitespace(this.charAt(index))) {
            int sizeOfNewline = advanceThroughNewline(index);

            if (sizeOfNewline > 0) {
                index += sizeOfNewline;
            } else {
                index++;
            }
        }

        this.size -= index;
        this.offset += index;
    }

    /**
     * Advances through possible newline character and returns how many
     * characters were used to represent the newline (windows uses two
     * characters to represent one newline).
     *
     * @param index The index of the potential newline character
     * @return
     */
    private int advanceThroughNewline(int index) {
        char character = this.charAt(index);
        int numOfCharacters = 0;

        // windows newline
        if ('\r' == character && '\n' == this.charAt(index + 1)) {

            this.lineNumber++;
            numOfCharacters = 2;

            // various other newline characters
        } else if ('\n' == character || '\r' == character || '\u0085' == character || '\u2028' == character
                || '\u2029' == character) {

            this.lineNumber++;
            numOfCharacters = 1;
        }
        return numOfCharacters;
    }

    public String substring(int start, int end) {
        return new String(Arrays.copyOfRange(source, this.offset + start, this.offset + end));
    }

    public String substring(int end) {
        return new String(Arrays.copyOfRange(source, offset, offset + end));
    }

    @Override
    public int length() {
        return size;
    }

    @Override
    public char charAt(int index) {
        return source[offset + index];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return new String(Arrays.copyOfRange(source, this.offset + start, this.offset + end));
    }

    @Override
    public String toString() {
        return new String(Arrays.copyOfRange(source, offset, offset + size));
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getFilename() {
        return filename;
    }
}
