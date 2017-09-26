package org.yj.smtpstub.service.smtp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * SMTPStub
 * --------------------------------------------
 *
 * @author TriYop
 */
public class TestSMTPAuthHandler {
    private SMTPAuthHandler smtpAuthHandler;

    @Before
    public void setUp() {
        smtpAuthHandler = new SMTPAuthHandler();
    }

    @Test
    public void testGetIdentity() {
        assertEquals("User", smtpAuthHandler.getIdentity());
    }

    @Test
    public void testAuth() {
        assertEquals(SMTPAuthHandler.CALLBACK_USERNAME, smtpAuthHandler.auth(""));
        assertEquals(SMTPAuthHandler.CALLBACK_PASSWORD, smtpAuthHandler.auth(""));
        assertEquals(null, smtpAuthHandler.auth(""));
    }
}
