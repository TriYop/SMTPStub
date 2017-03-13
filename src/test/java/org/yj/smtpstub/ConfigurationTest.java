package org.yj.smtpstub;

import org.junit.Test;
import org.yj.smtpstub.configuration.Configuration;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class ConfigurationTest {


    @Test
    public void getEmptyValueWhenKeyIsNotFound() {
        assertTrue(Configuration.get("this.key.doesnt.exist").isEmpty());
    }

    @Test
    public void getValueWhenKeyIsFound() {
        assertTrue(!Configuration.get("application.name").isEmpty());
    }
}
