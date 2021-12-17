package org.yj.smtpstub.model;

import org.junit.Before;
import org.junit.Test;
import org.yj.smtpstub.configuration.Configuration;
import org.yj.smtpstub.configuration.PropertiesConfigurationLoader;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * SMTPStub
 * --------------------------------------------
 *
 * @author TriYop
 */
public class TestEmailModel {

    private EmailModel model;

    @Before
    public void setUp() {
        Configuration.getInstance(new PropertiesConfigurationLoader("/etc/smtpstub.conf"));
        model = new EmailModel();
    }

    @Test
    public void testReceiveDate() {
        //tests both setter and getter at once
        assertEquals("Expecting received date to be null after initialization", null, model.getReceivedDate());
        Date now = new Date();
        model.setReceivedDate(now);
        assertEquals("Expecting the provided date to be returned after setting value", now, model.getReceivedDate());
        model.setReceivedDate(null);
        assertEquals("Expecting value to be reset to null after null asignment", null, model.getReceivedDate());
    }

    @Test
    public void testFilePath() {
        //tests both setter and getter at once
        assertEquals("Expecting File path to be null after initialization", null, model.getFilePath());
        String testValue = "expectedValue";
        model.setFilePath(testValue);
        assertEquals("Expecting the file path to be returned after setting value", testValue, model.getFilePath());
    }

    @Test
    public void testFrom() {
        //tests both setter and getter at once
        assertEquals("Expecting value to be null after initialization", null, model.getEmitter());
        String testValue = "expectedValue";
        model.setEmitter(testValue);
        assertEquals("Expecting provided value to be returned after setting value", testValue, model.getEmitter());

    }

    @Test
    public void testTo() {
        //tests both setter and getter at once
        assertEquals("Expecting value to be null after initialization", null, model.getRecipient());
        String testValue = "expectedValue";
        model.setRecipient(testValue);
        assertEquals("Expecting provided value to be returned after setting value", testValue, model.getRecipient());
    }

    @Test
    public void testSubject() {
        //tests both setter and getter at once
        assertEquals("Expecting value to be null after initialization", null, model.getSubject());
        String testValue = "expectedValue";
        model.setSubject(testValue);
        assertEquals("Expecting provided value to be returned after setting value", testValue, model.getSubject());

    }

    @Test
    public void testEmailStr() {
        //tests both setter and getter at once
        assertEquals("Expecting value to be null after initialization", null, model.getEmailStr());
        String testValue = "expectedValue";
        model.setEmailStr(testValue);
        assertEquals("Expecting provided value to be returned after setting value", testValue, model.getEmailStr());

    }

    @Test
    public void testHasEmptyField() {

        assertTrue(model.hasEmptyField());

        model.setEmailStr("Subject: test\n\n");
        assertTrue(model.hasEmptyField());

        model.setFilePath("inbox/test");
        assertTrue(model.hasEmptyField());

        model.setEmitter("from_value");
        assertTrue(model.hasEmptyField());

        model.setReceivedDate(new Date());
        assertTrue(model.hasEmptyField());

        model.setSubject("subject_value");
        assertTrue(model.hasEmptyField());

        model.setRecipient("to_value");
        assertFalse(model.hasEmptyField());

    }


}
