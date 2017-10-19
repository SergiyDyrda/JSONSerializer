package com.netcracker.lab02.serializer;

import com.netcracker.lab02.writer.JsonWriter;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by Sergiy on 07.12.2016.
 */
public class CollectionMapper extends JsonMapper<Collection<Object>> {

    public CollectionMapper(JsonSerializer parent) {
        super(parent);
    }

    @Override
    public void write(Collection<Object> obj, JsonWriter writer) throws Exception {
        writer.writeArrayBegin();
        int size = obj.size();
        final int[] index = {0};
        obj.forEach(elem -> {
            try {
                parent.serialize(elem, writer);
                if (index[0]++ != size - 1) writer.writeSeparator();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        writer.writeArrayEnd();
    }
}

