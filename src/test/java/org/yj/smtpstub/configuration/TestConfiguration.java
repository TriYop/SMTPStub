package org.yj.smtpstub.configuration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by TriYop on 08/03/2017.
 */
public class TestConfiguration {

    Configuration config;

    private static final String TEST_STRING_KEY = "test.key";
    private static final String TEST_STRING_VALUE = "test.value";

    private static final String TEST_INT_KEY = "test.int";
    private static final int TEST_INT_VALUE = 5;

    @Before
    public void setup() {
        config = new Configuration();

    }

    @After
    public void tearDown() {
        // nothing to do here

    }

    @Test
    public void testGet_existingKey() {
        Configuration.set(TEST_STRING_KEY, TEST_STRING_VALUE);
        assertEquals("either get or set method doesn't do the job.", TEST_STRING_VALUE, Configuration.get(TEST_STRING_KEY, "modified"+TEST_STRING_VALUE));
    }

    @Test
    public void testGet_absentKey() {
        assertEquals("Get method should return an empty string when key not found", "defaultValue", Configuration.get("", "defaultValue"));
    }

    @Test
    public void testGetInt_existingKey() {
        Configuration.set(TEST_INT_KEY, String.valueOf(TEST_INT_VALUE));
        assertEquals(TEST_INT_VALUE, Configuration.getInt(TEST_INT_KEY, -1));
    }

    @Test
    public void testGetInt_absentKey() {
        assertEquals(-1, Configuration.getInt("", -1));
    }

    @Test
    public void testGetInt_invalidValue() {
        Configuration.set(TEST_STRING_KEY, TEST_STRING_VALUE);
        assertEquals( -123, Configuration.getInt(TEST_STRING_KEY, -123));
    }


}
