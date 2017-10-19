package com.netcracker.lab02.serializer;

import com.netcracker.lab02.writer.JsonWriter;

/**
 * Created by Sergiy on 07.12.2016.
 */
public class ObjectArrayMapper<E> extends JsonMapper<E[]> {

    public ObjectArrayMapper(JsonSerializer parent) {
        super(parent);
    }

    @Override
    public void write(E[] obj, JsonWriter writer) throws Exception {

        for (int i = 0; i < obj.length; i++) {
            parent.serialize(obj[i], writer);
            if (i != obj.length - 1) writer.writeSeparator();
        }
    }
}
