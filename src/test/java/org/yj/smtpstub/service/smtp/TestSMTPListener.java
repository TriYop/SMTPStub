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
    public void testAccept_emptyBoth() {
        assertTrue("all inputs are currently accepted", listener.accept("", ""));
    }

    @Test
    public void testAccept_emptyFrom() {
        assertTrue("all inputs are currently accepted", listener.accept("", "somebody@somewhere.com"));
    }

    @Test
    public void testAccept_emptyRecipient() {
        assertTrue("", listener.accept("somebody@somewhere.com", ""));
    }

    @Test
    public void testAccept_invalidBoth() {
        assertTrue("all inputs are currently accepted", listener.accept("invalid@somewher@somehow", "invalid@somewhere@somehow"));
    }

    @Test
    public void testAccept_invalidFrom() {
        assertTrue("all inputs are currently accepted", listener.accept("invalid@somewhere@somehow", "somebody@somewhere.com"));
    }

    @Test
    public void testAccept_invalidRecipient() {
        assertTrue("", listener.accept("somebody@somewhere.com", "invalid@somewhere@somehow"));
    }

    @Test
    public void testAccept_nominal() {
        assertTrue(listener.accept("from@somewhere.com", "recipient@somewhere.com"));
    }


}
