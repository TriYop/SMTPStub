package org.yj.smtpstub.service.smtp;

import org.junit.Before;
import org.junit.Test;
import org.yj.smtpstub.exception.InvalidHostException;
import org.yj.smtpstub.exception.PortException;

import static org.junit.Assert.fail;

:

/**
 * SMTPStub
 * --------------------------------------------
 *
 * @author TriYop
 */
public class TestSMTPServerFactory {
    private SMTPServerFactory factory;

    @Before
    public void setup() {

    }

    @Test
    public void testGetRunningServer() {
        try {
            SMTPServerFactory.getRunningServer(0, null);
            fail("An exception was expected");
        } catch (PortException | InvalidHostException e) {


        }
    }
}
