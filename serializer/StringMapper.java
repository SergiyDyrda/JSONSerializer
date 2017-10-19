package com.netcracker.lab02.serializer;

import com.netcracker.lab02.writer.JsonWriter;

import java.io.IOException;

/**
 * Created by Sergiy on 07.12.2016.
 */
public class StringMapper extends JsonMapper<String> {

    public StringMapper(JsonSerializer parent) {
        super(parent);
    }

    @Override
    public void write(String obj, JsonWriter writer) throws IOException {
        if (checkForNull(obj, writer)) return;

        writer.writeString(obj);
    }
}
