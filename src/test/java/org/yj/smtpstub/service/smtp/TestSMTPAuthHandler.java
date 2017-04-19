package org.yj.smtpstub.service.smtp;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * SMTPStub
 * --------------------------------------------
 *
 * @author TriYop
 */
public class TestSMTPAuthHandler {
    private SMTPAuthHandler smtpAuthHandler;

    @Before
    public void setup() {
        smtpAuthHandler = new SMTPAuthHandler();
    }

    @Test
    public void testGetIdentity () {
        smtpAuthHandler.getIdentity();
    }

    @Test
    public void testAuth() {
        assertEquals(SMTPAuthHandler.CALLBACK_USERNAME, smtpAuthHandler.auth(""));
        assertEquals(SMTPAuthHandler.CALLBACK_PASSWORD, smtpAuthHandler.auth(""));
        assertEquals(SMTPAuthHandler."", smtpAuthHandler.auth(""));

    }
}
