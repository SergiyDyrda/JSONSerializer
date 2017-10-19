package com.netcracker.lab02.serializer;

import com.netcracker.lab02.writer.JsonWriter;

import java.io.IOException;

/**
 * Created by Sergiy on 07.12.2016.
 */
public class BooleanMapper extends JsonMapper<Boolean> {

    public BooleanMapper(JsonSerializer parent) {
        super(parent);

    }
    @Override
    public void write(Boolean obj, JsonWriter writer) throws IOException {
        if (checkForNull(obj, writer)) return;
        writer.writeBoolean(obj);
    }
}
