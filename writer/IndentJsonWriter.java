package com.netcracker.lab02.writer;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by Sergiy on 07.12.2016.
 */
public class IndentJsonWriter extends JsonWriter {


    private final Writer writer;
    private int indentSize;
    private int currentLevel;

    private char whiteSpace = '\u0020';
    private char enter = '\n';

    public IndentJsonWriter(Writer writer) {
        super(writer);
        this.writer = writer;
        indentSize = 3;
        currentLevel = 0;
    }

    public IndentJsonWriter(Writer writer, int indentSize) {
        this(writer);
        if (indentSize < 0) throw new IllegalArgumentException("indentSize required greater than or equals 0: " + indentSize);
        this.indentSize = indentSize;
    }

    private void writerCurrentLevel() throws IOException {
        for (int i = 0; i < currentLevel; i++) {
            for (int j = 0; j < indentSize; j++) {
                writer.append(whiteSpace);
            }
        }
    }

    @Override
    public void writeObjectBegin() throws IOException {
        super.writeObjectBegin();
        currentLevel++;
        writer.append(enter);
        writerCurrentLevel();
    }

    @Override
    public void writeObjectEnd() throws IOException {
        currentLevel--;
        writer.append(enter);
        writerCurrentLevel();
        super.writeObjectEnd();
    }

    @Override
    public void writeArrayBegin() throws IOException {
        super.writeArrayBegin();
        writer.append(enter);
        currentLevel++;
        writerCurrentLevel();
    }

    @Override
    public void writeArrayEnd() throws IOException {
        currentLevel--;
        writer.append(enter);
        writerCurrentLevel();
        super.writeArrayEnd();
    }

    @Override
    public void writeSeparator() throws IOException {
        super.writeSeparator();
        writer.write(enter);
        writerCurrentLevel();
    }

    @Override
    public void writePropertySeparator() throws IOException {
        writer.write(whiteSpace);
        super.writePropertySeparator();
        writer.write(whiteSpace);
    }

}
