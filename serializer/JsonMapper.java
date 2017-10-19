package com.netcracker.lab02.serializer;

import com.netcracker.lab02.writer.JsonWriter;

import java.io.IOException;

/**
 * Created by Sergiy on 07.12.2016.
 */
public abstract class JsonMapper<T> {

    protected JsonSerializer parent;

    protected JsonMapper(JsonSerializer parent) {
        this.parent = parent;
    }

    protected boolean checkForNull(Object obj, JsonWriter writer) throws IOException {
        if (obj == null) {
            writer.writeNull();
            return true;
        }
        return false;
    }


    public abstract void write(T obj, JsonWriter writer) throws Exception;
}
