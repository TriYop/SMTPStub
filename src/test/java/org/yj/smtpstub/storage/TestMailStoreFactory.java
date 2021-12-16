package org.yj.smtpstub.storage;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.yj.smtpstub.configuration.Configuration;
import org.yj.smtpstub.configuration.PropertiesConfigurationLoader;
import org.yj.smtpstub.exception.InvalidStoreException;

import static org.junit.Assert.assertEquals;

/**
 * SMTPStub
 * --------------------------------------------
 *
 * @author TriYop
 */
public class TestMailStoreFactory {

    @Before
    public void setUp() {
        Configuration.getInstance(new PropertiesConfigurationLoader("/etc/smtpstub.conf"));
    }

    @After
    public void tearDown() {
        Configuration.getInstance().set("emails.storage.engine", FSMailStore.class.getCanonicalName());
    }

    @Test
    public void testGetMailStoreValidType() throws InvalidStoreException {
        String type = FSMailStore.class.getCanonicalName();
        Object obj = MailStoreFactory.getMailStore();
        assertEquals("", FSMailStore.class, obj.getClass());

    }

    @Test(expected = InvalidStoreException.class)
    public void testGetMailStoreInvalidType() throws InvalidStoreException {
        Configuration.getInstance().set("emails.storage.engine", "invalidType");
        MailStoreFactory.getMailStore();
    }

}
