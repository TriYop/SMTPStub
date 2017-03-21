package org.yj.smtpstub.service.smtp;

import org.junit.Before;
import org.junit.Test;
import org.yj.smtpstub.exception.InvalidHostException;
import org.yj.smtpstub.exception.PortException;

import static org.junit.Assert.fail;


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
    public void testGetRunningServer_invalidParameters() {
        try {
            SMTPServerFactory.getRunningServer(0, null);
            fail("An exception was expected");
        } catch (PortException | InvalidHostException e) {
            assert true;

        }
    }

    @Test
    public void testGetRunningServer_invalidPort() {
        try {
            SMTPServerFactory.getRunningServer(0, "localhost");
            fail("A port exception was expected");
        } catch (PortException  e) {
            assert true;
        } catch (InvalidHostException e) {
            fail("A port exception was expected instead of a InvalidHost");
        }
    }

    @Test
    public void testGetRunningServer_invalidHost() {
        try {
            SMTPServerFactory.getRunningServer(2525, "");
            fail("A host exception was expected");
        } catch (PortException  e) {
            fail("A port exception was expected instead of a InvalidHost");
        } catch (InvalidHostException e) {
            assert true;

        }
    }
}
