package org.yj.smtpstub.service.smtp;

import org.junit.Before;
import org.junit.Test;
import org.subethamail.smtp.server.SMTPServer;
import org.yj.smtpstub.exception.InvalidHostException;
import org.yj.smtpstub.exception.NetworkException;
import org.yj.smtpstub.exception.PortException;

import static org.junit.Assert.assertEquals;
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
            SMTPServer srvr = SMTPServerFactory.getRunningServer(0, null);
            srvr.stop();
            fail("An exception was expected");
        } catch (NetworkException e) {
            assert true;

        }
    }

    @Test
    public void testGetRunningServer_invalidLowPort() {
        try {
            SMTPServer srvr = SMTPServerFactory.getRunningServer(0, "localhost");
            srvr.stop();
            fail("A port exception was expected");
        } catch (PortException e) {
            assertEquals("Port " + 0 + " could not be opened.", e.getMessage());
        } catch (NetworkException e) {
            fail("A port exception was expected instead of a InvalidHost");
        }
    }

    @Test
    public void testGetRunningServer_invalidHighPort() {
        try {
            SMTPServer srvr = SMTPServerFactory.getRunningServer(65536, "localhost");
            srvr.stop();
            fail("A port exception was expected");
        } catch (PortException e) {
            assertEquals("Port " + 65536 + " could not be opened.", e.getMessage());
        } catch (NetworkException e) {
            fail("A port exception was expected instead of a InvalidHost");
        }
    }


    @Test
    public void testGetRunningServer_nullHost() {
        try {
            SMTPServer srvr = SMTPServerFactory.getRunningServer(2525, null);
            srvr.stop();
            fail("A host exception was expected");
        } catch (InvalidHostException e) {
            assert true;
        } catch (NetworkException e) {
            fail("A port exception was expected instead of a InvalidHost");
        }
    }

    @Test
    public void testGetRunningServer_emptyHost() {
        try {
            SMTPServer srvr = SMTPServerFactory.getRunningServer(2525, null);
            srvr.stop();
            fail("A host exception was expected");
        } catch (InvalidHostException e) {
            assert true;
        } catch (NetworkException e) {
            fail("A port exception was expected instead of a InvalidHost");
        }
    }

    @Test
    public void testGetRunningServer_invalidHost() {
        try {
            SMTPServer srvr = SMTPServerFactory.getRunningServer(2525, "10.20.30.40.50");
            srvr.stop();
            fail("A host exception was expected");
        } catch (InvalidHostException e) {
            assert true;
        } catch (NetworkException e) {
            fail("A port exception was expected instead of a InvalidHost");
        }
    }

    @Test
    public void testGetRunningServer_occupiedPort() {
        SMTPServer srvr1 = null;
        SMTPServer srvr2 = null;
        try {
            srvr1 = SMTPServerFactory.getRunningServer(8080, "localhost");
            srvr2 = SMTPServerFactory.getRunningServer(8080, "localhost");

            fail("A port exception was expected");
        } catch (PortException e) {
            assert true;
        } catch (NetworkException e) {
            fail("A port exception was expected instead of a InvalidHost");
        } finally {
            if (null != srvr2 && srvr2.isRunning()) {
                srvr2.stop();
            }
            if (null != srvr1 && srvr1.isRunning()) {
                srvr1.stop();
            }
        }
    }

    @Test
    public void testGetRunningServer_nominal() {
        try {
            SMTPServer srvr = SMTPServerFactory.getRunningTLSServer(2525, "localhost");
            srvr.stop();
            assert true;
        } catch (PortException | InvalidHostException e) {
            fail("no exception was expected");
        }
    }


//

    @Test
    public void testGetRunningTLSServer_invalidParameters() {
        try {
            SMTPServer srvr = SMTPServerFactory.getRunningTLSServer(0, null);
            srvr.stop();
            fail("An exception was expected");
        } catch (PortException | InvalidHostException e) {
            assert true;

        }
    }

    @Test
    public void testGetRunningTLSServer_invalidLowPort() {
        try {
            SMTPServer srvr = SMTPServerFactory.getRunningTLSServer(0, "localhost");
            srvr.stop();
            fail("A port exception was expected");
        } catch (PortException e) {
            assert true;
        } catch (InvalidHostException e) {
            fail("A port exception was expected instead of a InvalidHost");
        }
    }

    @Test
    public void testGetRunningTLSServer_invalidHighPort() {
        try {
            SMTPServer srvr = SMTPServerFactory.getRunningTLSServer(65536, "localhost");
            srvr.stop();
            fail("A port exception was expected");
        } catch (PortException e) {
            assert true;
        } catch (InvalidHostException e) {
            fail("A port exception was expected instead of a InvalidHost");
        }
    }


    @Test
    public void testGetRunningTLSServer_nullHost() {
        try {
            SMTPServer srvr = SMTPServerFactory.getRunningTLSServer(2525, null);
            srvr.stop();
            fail("A host exception was expected");
        } catch (PortException e) {
            fail("A port exception was expected instead of a InvalidHost");
        } catch (InvalidHostException e) {
            assert true;
        }
    }

    @Test
    public void testGetRunningTLSServer_emptyHost() {
        try {
            SMTPServer srvr = SMTPServerFactory.getRunningTLSServer(2525, null);
            srvr.stop();
            fail("A host exception was expected");
        } catch (PortException e) {
            fail("A port exception was expected instead of a InvalidHost");
        } catch (InvalidHostException e) {
            assert true;
        }
    }

    @Test
    public void testGetRunningTLSServer_invalidHost() {
        try {
            SMTPServer srvr = SMTPServerFactory.getRunningTLSServer(2525, "10.20.30.40.50");
            srvr.stop();
            fail("A host exception was expected");
        } catch (PortException e) {
            fail("A port exception was expected instead of a InvalidHost");
        } catch (InvalidHostException e) {
            assert true;
        }
    }

    @Test
    public void testGetRunningTLSServer_occupiedPort() {
        SMTPServer srvr1 = null;
        SMTPServer srvr2 = null;
        try {
            srvr1 = SMTPServerFactory.getRunningTLSServer(8181, "localhost");
            srvr2 = SMTPServerFactory.getRunningTLSServer(8181, "localhost");

            fail("A host exception was expected");
        } catch (PortException e) {
            assert true;
        } catch (InvalidHostException e) {
            fail("A port exception was expected instead of a InvalidHost");
        } finally {
            if (null != srvr2 && srvr2.isRunning()) {
                srvr2.stop();
            }
            if (null != srvr1 && srvr1.isRunning()) {
                srvr1.stop();
            }
        }
    }

    @Test
    public void testGetRunningTLSServer_nominal() {
        try {
            SMTPServer srvr = SMTPServerFactory.getRunningTLSServer(2526, "localhost");
            srvr.stop();
            assert true;

        } catch (PortException | InvalidHostException e) {
            fail("no exception was expected");
        }
    }


}
