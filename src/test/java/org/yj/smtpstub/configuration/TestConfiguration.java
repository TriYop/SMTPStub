package org.yj.smtpstub.configuration;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by TriYop on 08/03/2017.
 */
public class TestConfiguration {

    private static final String TEST_STRING_KEY = "test.key";
    private static final String TEST_STRING_VALUE = "test.value";
    private static final String TEST_INT_KEY = "test.int";
    private static final int TEST_INT_VALUE = 5;
    private Configuration conf;

    @Before
    public void init() {
        conf = Configuration.getInstance(new PropertiesConfigurationLoader("/etc/smtpstub.conf"));
    }

    @Test
    public void testGetExistingKey() {
        assertEquals(TEST_STRING_VALUE, conf.getStringValue(TEST_STRING_KEY, "invalid_" + TEST_STRING_VALUE));
    }

    @Test
    public void testGetAbsentKey() {
        assertEquals("defaultValue", conf.getStringValue("", "defaultValue"));
    }

    @Test
    public void testGetIntExistingKey() {
        assertEquals(TEST_INT_VALUE, conf.getIntValue(TEST_INT_KEY, -1));
    }

    @Test
    public void testGetIntAbsentKey() {
        assertEquals(-1, conf.getIntValue("", -1));
    }

    @Test
    public void testGetIntInvalidValue() {
        assertEquals(-123, conf.getIntValue(TEST_STRING_KEY, -123));
    }

    @Test
    public void testSetValue() {
        conf.set("Key", "Value");
        assertEquals( "Value", conf.getStringValue("Key", "Default") );
    }


}
