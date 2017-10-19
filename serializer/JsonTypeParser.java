package com.netcracker.lab02.serializer;

import com.netcracker.lab02.annotations.JsonIgnore;
import com.netcracker.lab02.annotations.JsonProperty;
import com.netcracker.lab02.writer.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Sergiy on 08.12.2016.
 */
public class JsonTypeParser {

    private Class type;
    private JsonSerializer parent;

    public JsonTypeParser(Class type, JsonSerializer parent) {
        this.type = type;
        this.parent = parent;
    }

    public JsonMapper mapper() {
        return new JsonMapper(parent) {
            @Override
            public void write(Object obj, JsonWriter writer) throws Exception {
                writer.writeObjectBegin();
                Set<Field> requiredFields = getRequiredFields();
                int size = requiredFields.size();
                final int[] i = {0};
                requiredFields.forEach(field -> {
                    try {
                        writeFieldName(writer, field);
                        writer.writePropertySeparator();
                        parent.serialize(field.get(obj), writer);
                        if (i[0]++ != size - 1) writer.writeSeparator();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
                writer.writeObjectEnd();
            }

            private void writeFieldName(JsonWriter writer, Field field) throws IOException {
                JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
                String fieldName = field.getName();
                if (jsonProperty != null) {
                    String value = jsonProperty.value().trim();
                    writer.writeString(!value.isEmpty() ? value : fieldName);
                } else {
                    writer.writeString(fieldName);
                }
            }
        };
    }

    private Set<Field> getRequiredFields() {
        Field[] declaredFields = type.getDeclaredFields();
        return Arrays.stream(declaredFields)
                .filter(this::filter)
                .collect(Collectors.toSet());

    }

    private boolean filter(Field field) {
        if (!field.isAnnotationPresent(JsonIgnore.class)) {
            int modifiers = field.getModifiers();
            if (Modifier.isPublic(modifiers)) {
                return !Modifier.isTransient(modifiers) || field.isAnnotationPresent(JsonProperty.class);
            } else {
                if (field.isAnnotationPresent(JsonProperty.class)) {
                    field.setAccessible(true);
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}
