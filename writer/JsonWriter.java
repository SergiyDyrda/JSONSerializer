package com.netcracker.lab02.writer;

import java.io.IOException;
import java.io.Writer;

import static java.util.Objects.requireNonNull;

/**
 * Created by Sergiy on 07.12.2016.
 */
public class JsonWriter {
    private Writer writer;

    private char separator = ',';
    private char propertySeparator = ':';


    public JsonWriter(Writer writer) {
        this.writer = requireNonNull(writer);
    }

    public Writer getWriter() {
        return writer;
    }

    public void writeObjectBegin() throws IOException {
        writer.append('{');
    }

    public void writeObjectEnd() throws IOException {
        writer.append('}');
    }

    public void writeArrayBegin() throws IOException {
        writer.append('[');
    }

    public void writeArrayEnd() throws IOException {
        writer.append(']');
    }

    public void writeString(String string) throws IOException {
        writer.append('"').append(string).append('"');
    }

    public void writeNumber(Number number) throws IOException {
        writer.append(String.valueOf(number));
    }

    public void writeSeparator() throws IOException {
        writer.append(separator);
    }

    public void writePropertySeparator() throws IOException {
        writer.append(propertySeparator);
    }

    public void writeBoolean(boolean value) throws IOException {
        writer.append(Boolean.toString(value));
    }

    public void writeChar(char value) throws IOException {
        writer.append('\'').append(value).append('\'');
    }

    public void writeNull() throws IOException {
        writer.append("null");
    }

    public void flush() throws IOException {
        writer.flush();
    }

}
