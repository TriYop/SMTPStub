package org.yj.smtpstub.service.smtp;

import org.junit.Before;
import org.junit.Test;

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
}
