package com.netcracker.lab02.serializer;

import com.netcracker.lab02.writer.JsonWriter;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Sergiy on 07.12.2016.
 */
public class MapMapper extends JsonMapper<Map> {

    public MapMapper(JsonSerializer parent) {
        super(parent);
    }

    @Override
    public void write(Map obj, JsonWriter writer) throws IOException {

        int size = obj.size();
        final int[] index = {0};
        writer.writeObjectBegin();
        obj.forEach((key, value) -> {
            try {
                writer.writeString(String.valueOf(key));
                writer.writePropertySeparator();
                parent.serialize(value, writer);
                if (index[0]++ != size - 1) writer.writeSeparator();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        writer.writeObjectEnd();
    }
}
