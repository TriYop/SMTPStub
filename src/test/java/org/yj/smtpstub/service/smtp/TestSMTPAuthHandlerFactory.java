package org.yj.smtpstub.service.smtp;

import org.junit.Before;

/**
 * SMTPStub
 * --------------------------------------------
 *
 * @author TriYop
 */
public class TestSMTPAuthHandlerFactory {
    SMTPAuthHandlerFactory factory;

    @Before
    public void setup() {
        factory = new SMTPAuthHandlerFactory();
    }
}
