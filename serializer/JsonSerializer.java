package com.netcracker.lab02.serializer;

import com.netcracker.lab02.writer.IndentJsonWriter;
import com.netcracker.lab02.writer.JsonWriter;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Sergiy on 07.12.2016.
 */
public class JsonSerializer {

    private boolean indent;
    private int indentSize = 3;

    private Map<Class, JsonMapper> mappersCache;

    {
        mappersCache = new ConcurrentHashMap<>();
        mappersCache.put(Collection.class, new CollectionMapper(this));
        mappersCache.put(Map.class, new MapMapper(this));
        mappersCache.put(Number.class, new NumberMapper(this));
        mappersCache.put(Boolean.class, new BooleanMapper(this));
        mappersCache.put(Object[].class, new ObjectArrayMapper(this));
        mappersCache.put(String.class, new StringMapper(this));
        mappersCache.put(Array.class, new PrimitiveArrayMapper(this));
    }

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    public JsonSerializer setIndent(boolean indent) {
        this.indent = indent;
        return this;
    }

    public JsonSerializer setIndentSize(int indentSize) {
        this.indentSize = indentSize;
        return this;
    }

    public String serialize(Object obj) throws IllegalStateException {
        StringWriter writer = new StringWriter();
        serialize(obj, writer);
        return writer.toString();
    }

    public void serialize(Object obj, OutputStream stream) {
        serialize(obj, stream, DEFAULT_CHARSET);
    }

    public void serialize(Object obj, OutputStream stream, Charset charset) {
        serialize(obj, new OutputStreamWriter(stream, charset));
    }

    public void serialize(Object obj, Writer writer) {
        serialize(obj, !indent ? new JsonWriter(writer) : new IndentJsonWriter(writer, indentSize));
    }

    protected void serialize(Object obj, JsonWriter writer) {
        try {
            if (obj == null) {
                writer.writeNull();
                writer.flush();
                return;
            }

            Class<?> aClass = obj.getClass();
            if (aClass.isArray()) {
                doIfArray(obj, writer, aClass);
            } else if (aClass.isPrimitive()) {
                doIfPrimitive(obj, writer, aClass);
            } else doIfObject(obj, writer, aClass);

            writer.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void doIfObject(Object obj, JsonWriter writer, Class<?> aClass) throws Exception {
        if (obj instanceof Number) {
            getMapper(Number.class).write(obj, writer);
        } else if (obj instanceof Collection) {
            getMapper(Collection.class).write(obj, writer);
        } else if (obj instanceof Map) {
            getMapper(Map.class).write(obj, writer);
        } else if (obj instanceof Character) {
            getMapper(String.class).write(String.valueOf(obj), writer);
        } else {
            getMapper(aClass).write(obj, writer);
        }
    }

    private void doIfPrimitive(Object obj, JsonWriter writer, Class<?> aClass) throws Exception {
        if (aClass == int.class
                || aClass == long.class
                || aClass == short.class
                || aClass == double.class
                || aClass == byte.class
                || aClass == float.class) {
            getMapper(Number.class).write(obj, writer);
        } else if (aClass == char.class) {
            getMapper(String.class).write(obj, writer);
        } else {
            getMapper(Boolean.class).write(obj, writer);
        }
    }

    private void doIfArray(Object obj, JsonWriter writer, Class<?> aClass) throws Exception {
        writer.writeArrayBegin();
        if (aClass.getComponentType().isPrimitive()) {
            getMapper(Array.class).write(obj, writer);
        } else {
            getMapper(Object[].class).write(obj, writer);
        }
        writer.writeArrayEnd();
    }

    protected JsonMapper getMapper(Class clazz) {
        JsonMapper jsonMapper = mappersCache.get(clazz);
        return jsonMapper != null ? jsonMapper : getMapperForUnknownType(clazz);
    }

    private JsonMapper getMapperForUnknownType(Class clazz) {
        return new JsonTypeParser(clazz, this).mapper();
    }
}
