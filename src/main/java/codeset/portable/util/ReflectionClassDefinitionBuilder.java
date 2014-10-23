package codeset.portable.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.hazelcast.nio.serialization.ClassDefinition;
import com.hazelcast.nio.serialization.ClassDefinitionBuilder;
import com.hazelcast.nio.serialization.Portable;

/**
 * Builds ClassDefinitions using reflection.
 * 
 * @author ingemar.svensson
 *
 */
public class ReflectionClassDefinitionBuilder {

    /**
     * Return a ClassDefinition for the supplied Portable class. This method is
     * called recursively if a nested Portable field is discovered.
     * 
     * Note: Collection fields will only be defined if they are of generic type
     * Portable e.g. Set<Portable>.
     * 
     * @param type the Portable class to build for.
     * @return the ClassDefinition, will not return null.
     * @throws IllegalStateException if a Portable instance can't be created.
     */
    @SuppressWarnings("unchecked")
    public ClassDefinition build(Class<? extends Portable> type) {

        // We need an instance so we can read factoryId and classId
        Portable portable;
        try {
            portable = (Portable) type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("Failed to instantiate Portable from " + type.getName(), e);
        }
        ClassDefinitionBuilder builder = new ClassDefinitionBuilder(portable.getFactoryId(), portable.getClassId());

        // Read all the fields on the class including inherited fields
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {

            if (Modifier.isTransient(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            Class<?> fieldType = field.getType();
            String fieldName = field.getName();

            // Add null check field
            String hasFieldName = "_has__" + fieldName;
            builder.addBooleanField(hasFieldName);

            if (int.class == fieldType) {
                builder.addIntField(fieldName);
            } else if (Integer.class == fieldType) {
                builder.addIntField(fieldName);
            } else if (long.class == fieldType) {
                builder.addLongField(fieldName);
            } else if (Long.class == fieldType) {
                builder.addLongField(fieldName);
            } else if (String.class == fieldType) {
                builder.addUTFField(fieldName);
            } else if (boolean.class == fieldType) {
                builder.addBooleanField(fieldName);
            } else if (Boolean.class == fieldType) {
                builder.addBooleanField(fieldName);
            } else if (byte.class == fieldType) {
                builder.addByteField(fieldName);
            } else if (Byte.class == fieldType) {
                builder.addByteField(fieldName);
            } else if (char.class == fieldType) {
                builder.addCharField(fieldName);
            } else if (Character.class == fieldType) {
                builder.addCharField(fieldName);
            } else if (double.class == fieldType) {
                builder.addDoubleField(fieldName);
            } else if (Double.class == fieldType) {
                builder.addDoubleField(fieldName);
            } else if (float.class == fieldType) {
                builder.addFloatField(fieldName);
            } else if (Float.class == fieldType) {
                builder.addFloatField(fieldName);
            } else if (short.class == fieldType) {
                builder.addShortField(fieldName);
            } else if (Short.class == fieldType) {
                builder.addShortField(fieldName);
            } else if (Enum.class.isAssignableFrom(fieldType)) {
                builder.addUTFField(fieldName);
            } else if (Date.class.isAssignableFrom(fieldType)) {
                builder.addLongField(fieldName);
            } else if (Portable.class.isAssignableFrom(fieldType)) {
                builder.addPortableField(fieldName, build((Class<? extends Portable>) fieldType));
            } else if (Map.class.isAssignableFrom(fieldType)) {
                Class<?> valueType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[1];
                if(Portable.class.isAssignableFrom(valueType)) {
                    builder.addPortableArrayField(fieldName, build((Class<? extends Portable>) valueType));
                }
            } else if (Collection.class.isAssignableFrom(fieldType)) {
                Class<?> collectionType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                if(Portable.class.isAssignableFrom(collectionType)) {
                    builder.addPortableArrayField(fieldName, build((Class<? extends Portable>) collectionType));
                }
            } else if (fieldType.isArray()) {
                addArrayField(builder, fieldType, fieldName);
            } else {
                throw new IllegalArgumentException("Undefined field type '" + fieldType + "'");
            }
        }

        return builder.build();

    }

    /**
     * Add an array field.
     * 
     * @param builder the builder used.
     * @param type the type of the field.
     * @param fieldName the name of the field.
     */
    @SuppressWarnings("unchecked")
    private void addArrayField(ClassDefinitionBuilder builder, Class<?> type, String fieldName) {

        Class<?> componentType = type.getComponentType();
        if (Portable.class.isAssignableFrom(componentType)) {
            builder.addPortableArrayField(fieldName, build((Class<? extends Portable>) componentType));
        } else {
            addArrayField(builder, type, fieldName);
        }
        if (byte[].class == type || Byte[].class == type) {
            builder.addByteArrayField(fieldName);
        } else if (char[].class == type || Character[].class == type) {
            builder.addCharArrayField(fieldName);
        } else if (int[].class == type || Integer[].class == type) {
            builder.addIntArrayField(fieldName);
        } else if (long[].class == type || Long[].class == type) {
            builder.addLongArrayField(fieldName);
        } else if (double[].class == type || Double[].class == type) {
            builder.addDoubleArrayField(fieldName);
        } else if (float[].class == type || Float[].class == type) {
            builder.addFloatArrayField(fieldName);
        } else if (short[].class == type || Short[].class == type) {
            builder.addShortArrayField(fieldName);
        } else {
            throw new IllegalArgumentException("Unsupported array type '" + type + "'");
        }

    }

}