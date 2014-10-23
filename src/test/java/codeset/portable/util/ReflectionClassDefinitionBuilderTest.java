package codeset.portable.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.hazelcast.nio.serialization.ClassDefinition;
import com.hazelcast.nio.serialization.FieldType;

public class ReflectionClassDefinitionBuilderTest {

    @Test
    public void testId() {

        ReflectionClassDefinitionBuilder builder = new ReflectionClassDefinitionBuilder();
        ClassDefinition classDefinition = builder.build(PortableClass.class);
        assertEquals(1, classDefinition.getClassId());
        assertEquals(1, classDefinition.getFactoryId());

    }

    @Test
    public void testSimpleFields() {

        ReflectionClassDefinitionBuilder builder = new ReflectionClassDefinitionBuilder();
        ClassDefinition classDefinition = builder.build(PortableClass.class);
        assertEquals("dateProperty", classDefinition.getField("dateProperty").getName());
        assertEquals(FieldType.LONG, classDefinition.getField("dateProperty").getType());
        assertEquals("intProperty", classDefinition.getField("intProperty").getName());
        assertEquals(FieldType.INT, classDefinition.getField("intProperty").getType());
        assertEquals("doubleProperty", classDefinition.getField("doubleProperty").getName());
        assertEquals(FieldType.DOUBLE, classDefinition.getField("doubleProperty").getType());
        assertEquals("stringProperty", classDefinition.getField("stringProperty").getName());
        assertEquals(FieldType.UTF, classDefinition.getField("stringProperty").getType());
        assertEquals("booleanProperty", classDefinition.getField("booleanProperty").getName());
        assertEquals(FieldType.BOOLEAN, classDefinition.getField("booleanProperty").getType());
        assertEquals("byteProperty", classDefinition.getField("byteProperty").getName());
        assertEquals(FieldType.BYTE, classDefinition.getField("byteProperty").getType());
        assertEquals("charProperty", classDefinition.getField("charProperty").getName());
        assertEquals(FieldType.CHAR, classDefinition.getField("charProperty").getType());
        assertEquals("floatProperty", classDefinition.getField("floatProperty").getName());
        assertEquals(FieldType.FLOAT, classDefinition.getField("floatProperty").getType());
        assertEquals("shortProperty", classDefinition.getField("shortProperty").getName());
        assertEquals(FieldType.SHORT, classDefinition.getField("shortProperty").getType());

    }

    @Test
    public void testNestedFields() {

        ReflectionClassDefinitionBuilder builder = new ReflectionClassDefinitionBuilder();
        ClassDefinition classDefinition = builder.build(PortableClass.class);
        assertEquals("nestedProperty", classDefinition.getField("nestedProperty").getName());
        assertEquals(FieldType.PORTABLE, classDefinition.getField("nestedProperty").getType());

    }

    @Test
    public void testMapFields() {
        
    }

    @Test
    public void testCollectionFields() {

        ReflectionClassDefinitionBuilder builder = new ReflectionClassDefinitionBuilder();
        ClassDefinition classDefinition = builder.build(PortableClass.class);
        assertEquals("listProperty", classDefinition.getField("listProperty").getName());
        assertEquals(FieldType.PORTABLE_ARRAY, classDefinition.getField("listProperty").getType());
        assertEquals("setProperty", classDefinition.getField("setProperty").getName());
        assertEquals(FieldType.PORTABLE_ARRAY, classDefinition.getField("setProperty").getType());

    }

}
