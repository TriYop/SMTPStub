package org.yj.smtpstub.service.smtp;

import org.junit.Before;
import org.junit.Test;
import org.subethamail.smtp.AuthenticationHandler;

import static org.junit.Assert.assertNotNull;

/**
 * SMTPStub
 * --------------------------------------------
 *
 * @author TriYop
 */
public class TestSMTPAuthHandlerFactory {
    private SMTPAuthHandlerFactory factory;

    @Before
    public void setUp() {
        factory = new SMTPAuthHandlerFactory();
    }

    @Test
    public void testCreate() {
        AuthenticationHandler handler = factory.create();
        assertNotNull(handler);
    }
}
