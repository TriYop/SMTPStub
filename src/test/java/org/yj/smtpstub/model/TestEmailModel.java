package org.yj.smtpstub.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;
/**
 * SMTPStub
 * --------------------------------------------
 *
 * @author TriYop
 */
public class TestEmailModel {

    EmailModel model;

    @Before
    public void setup() {
        model = new EmailModel();

    }

    @Test
    public void testReceiveDate() {
        //tests both setter and getter at once
        assertEquals("Expecting received date to be null after initialization", null, model.getReceivedDate());
        Date now = new Date();
        model.setReceivedDate(now);
        assertEquals("Expecting the provided date to be returned after setting value", now, model.getReceivedDate());
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
        assertEquals("Expecting value to be null after initialization", null, model.getFrom());
        String testValue = "expectedValue";
        model.setFrom(testValue);
        assertEquals("Expecting provided value to be returned after setting value", testValue, model.getFrom());

    }

    @Test
    public void testTo() {
        //tests both setter and getter at once
        assertEquals("Expecting value to be null after initialization", null, model.getTo());
        String testValue = "expectedValue";
        model.setTo(testValue);
        assertEquals("Expecting provided value to be returned after setting value", testValue, model.getTo());
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



}
