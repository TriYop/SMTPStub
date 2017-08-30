package org.yj.smtpstub.service.smtp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * SMTPStub
 * --------------------------------------------
 *
 * @author TriYop
 */
public class TestSMTPListener {

    private SMTPListener listener;

    @Before
    public void setUp() {
        listener = new SMTPListener();
    }

    @Test
    public void testAcceptEmptyBoth() {
        assertTrue("all inputs are currently accepted", listener.accept("", ""));
    }

    @Test
    public void testAcceptEmptyFrom() {
        assertTrue("all inputs are currently accepted", listener.accept("", "somebody@somewhere.com"));
    }

    @Test
    public void testAcceptEmptyRecipient() {
        assertTrue("", listener.accept("somebody@somewhere.com", ""));
    }

    @Test
    public void testAcceptInvalidBoth() {
        assertTrue("all inputs are currently accepted", listener.accept("invalid@somewher@somehow", "invalid@somewhere@somehow"));
    }

    @Test
    public void testAcceptInvalidFrom() {
        assertTrue("all inputs are currently accepted", listener.accept("invalid@somewhere@somehow", "somebody@somewhere.com"));
    }

    @Test
    public void testAcceptInvalidRecipient() {
        assertTrue("", listener.accept("somebody@somewhere.com", "invalid@somewhere@somehow"));
    }

    @Test
    public void testAcceptNominal() {
        assertTrue(listener.accept("from@somewhere.com", "recipient@somewhere.com"));
    }


}
