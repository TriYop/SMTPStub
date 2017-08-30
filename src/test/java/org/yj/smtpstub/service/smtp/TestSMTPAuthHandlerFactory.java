package org.yj.smtpstub.service.smtp;

import org.junit.Before;
import org.junit.Test;

/**
 * SMTPStub
 * --------------------------------------------
 *
 * @author TriYop
 */
public class TestSMTPAuthHandlerFactory {
    SMTPAuthHandlerFactory factory;

    @Before
    public void setUp() {
        factory = new SMTPAuthHandlerFactory();
    }

    @Test
    public void testCreate() {
        factory.create();
    }
}
