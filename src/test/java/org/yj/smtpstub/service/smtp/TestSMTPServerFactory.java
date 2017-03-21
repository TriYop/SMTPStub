package org.yj.smtpstub.service.smtp;

import org.junit.Before;
import org.junit.Test;
import org.subethamail.smtp.server.SMTPServer;
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
            SMTPServer srvr= SMTPServerFactory.getRunningServer(0, null);
            srvr.stop();
            fail("An exception was expected");
        } catch (PortException | InvalidHostException e) {
            assert true;

        }
    }

    @Test
    public void testGetRunningServer_invalidLowPort() {
        try {
            SMTPServer srvr= SMTPServerFactory.getRunningServer(0, "localhost");
            srvr.stop();
            fail("A port exception was expected");
        } catch (PortException  e) {
            assert true;
        } catch (InvalidHostException e) {
            fail("A port exception was expected instead of a InvalidHost");
        }
    }

    @Test
    public void testGetRunningServer_invalidHighPort() {
        try {
            SMTPServer srvr= SMTPServerFactory.getRunningServer(65536, "localhost");
            srvr.stop();
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
            SMTPServer srvr = SMTPServerFactory.getRunningServer(2525, null);
            srvr.stop();
            fail("A host exception was expected");
        } catch (PortException  e) {
            fail("A port exception was expected instead of a InvalidHost");
        } catch (InvalidHostException e) {
            assert true;
        }
    }

    @Test
    public void testGetRunningServer_nominal() {
        try {
            SMTPServer srvr = SMTPServerFactory.getRunningServer(2525, null);
            assert true;
            srvr.stop();

        } catch (PortException|InvalidHostException  e) {
            fail("no exception was expected");
        }
    }
}
