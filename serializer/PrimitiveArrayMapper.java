package com.netcracker.lab02.serializer;

import com.netcracker.lab02.writer.JsonWriter;

import java.lang.reflect.Array;

/**
 * Created by Sergiy on 09.12.2016.
 */
public class PrimitiveArrayMapper extends JsonMapper<Object> {


    public PrimitiveArrayMapper(JsonSerializer parent) {
        super(parent);
    }

    @Override
    public void write(Object obj, JsonWriter writer) throws Exception {

        Class<?> componentType = obj.getClass().getComponentType();
        int length = Array.getLength(obj);
        if (componentType == boolean.class) {
            for (int i = 0; i < length; i++) {
                writer.writeBoolean(Array.getBoolean(obj, i));
                if (i != length - 1) writer.writeSeparator();
            }
        } else if (componentType == char.class) {
            for (int i = 0; i < length; i++) {
                writer.writeChar(Array.getChar(obj, i));
                if (i != length - 1) writer.writeSeparator();
            }
        } else {
            for (int i = 0; i < length; i++) {
                writer.writeNumber((Number) Array.get(obj, i));
                if (i != length - 1) writer.writeSeparator();
            }
        }
    }

}
