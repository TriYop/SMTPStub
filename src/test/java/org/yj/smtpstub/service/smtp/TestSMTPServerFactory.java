package org.yj.smtpstub.service.smtp;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subethamail.smtp.server.SMTPServer;
import org.yj.smtpstub.exception.InvalidHostException;
import org.yj.smtpstub.exception.NetworkException;
import org.yj.smtpstub.exception.PortException;

import static org.junit.Assert.*;
/**
 * SMTPStub
 * --------------------------------------------
 *
 * @author TriYop
 */
public class TestSMTPServerFactory {
    private static final Logger logger = LoggerFactory.getLogger(TestSMTPServerFactory.class);
    private SMTPServerFactory factory;

    @Test(expected = NetworkException.class)
    public void testGetRunningServerInvalidParameters() throws NetworkException {
        SMTPServer srvr = SMTPServerFactory.getRunningServer(0, null);
        srvr.stop();
    }

    @Test(expected = PortException.class)
    public void testGetRunningServerInvalidLowPort() throws NetworkException {
        SMTPServer srvr = SMTPServerFactory.getRunningServer(0, "localhost");
        srvr.stop();
    }

    @Test(expected = PortException.class)
    public void testGetRunningServerInvalidHighPort() throws NetworkException {
        SMTPServer srvr = SMTPServerFactory.getRunningServer(65536, "localhost");
        srvr.stop();
    }


    @Test(expected = InvalidHostException.class)
    public void testGetRunningServerNullHost() throws NetworkException {
        SMTPServer srvr = SMTPServerFactory.getRunningServer(2525, null);
        srvr.stop();
    }

    @Test(expected = InvalidHostException.class)
    public void testGetRunningServerEmptyHost() throws NetworkException {
        SMTPServer srvr = SMTPServerFactory.getRunningServer(2525, null);
        srvr.stop();
    }

    @Test(expected = InvalidHostException.class)
    public void testGetRunningServerInvalidHost() throws NetworkException {
        SMTPServer srvr = SMTPServerFactory.getRunningServer(2525, "10.20.30.40.50");
        srvr.stop();
    }

    @Test(expected = PortException.class)
    public void testGetRunningServerOccupiedPort() throws NetworkException {
        SMTPServer srvr1 = null;
        SMTPServer srvr2 = null;

        srvr1 = SMTPServerFactory.getRunningServer(8080, "127.0.0.1");
        srvr2 = SMTPServerFactory.getRunningServer(8080, "127.0.0.1");

        if (null != srvr2 && srvr2.isRunning()) {
            srvr2.stop();
        }
        if (null != srvr1 && srvr1.isRunning()) {
            srvr1.stop();
        }
    }


    @Test
    public void testGetRunningServerNominal() throws NetworkException {
        SMTPServer srvr = SMTPServerFactory.getRunningTLSServer(2525, "localhost");
        assertNotNull(srvr);
        srvr.stop();
    }

    @Test(expected = NetworkException.class)
    public void testGetRunningTLSServerInvalidParameters() throws NetworkException {
        SMTPServer srvr = SMTPServerFactory.getRunningTLSServer(0, null);
        srvr.stop();
    }

    @Test(expected = PortException.class)
    public void testGetRunningTLSServerInvalidLowPort() throws NetworkException {
        SMTPServer srvr = SMTPServerFactory.getRunningTLSServer(0, "localhost");
        srvr.stop();
    }

    @Test(expected = PortException.class)
    public void testGetRunningTLSServerInvalidHighPort() throws NetworkException {

        SMTPServer srvr = SMTPServerFactory.getRunningTLSServer(65536, "localhost");
        srvr.stop();
    }


    @Test(expected = InvalidHostException.class)
    public void testGetRunningTLSServerNullHost() throws NetworkException {

        SMTPServer srvr = SMTPServerFactory.getRunningTLSServer(2525, null);
        srvr.stop();
    }

    @Test(expected = InvalidHostException.class)
    public void testGetRunningTLSServerEmptyHost() throws NetworkException {

        SMTPServer srvr = SMTPServerFactory.getRunningTLSServer(2525, null);
        srvr.stop();
    }

    @Test(expected = InvalidHostException.class)
    public void testGetRunningTLSServerInvalidHost() throws NetworkException {

        SMTPServer srvr = SMTPServerFactory.getRunningTLSServer(2525, "10.20.30.40.50");
        srvr.stop();
    }


    @Test(expected = PortException.class)
    public void testGetRunningTLSServerOccupiedPort() throws NetworkException {
        SMTPServer srvr1 = null;
        SMTPServer srvr2 = null;

        srvr1 = SMTPServerFactory.getRunningTLSServer(8181, "localhost");
        srvr2 = SMTPServerFactory.getRunningTLSServer(8181, "localhost");
        logger.error("Opened two sockets with same port.");
        if (null != srvr2 && srvr2.isRunning()) {
            srvr2.stop();
        }
        if (null != srvr1 && srvr1.isRunning()) {
            srvr1.stop();
        }
    }

    @Test
    public void testGetRunningTLSServerNominal() throws NetworkException {
        SMTPServer srvr = SMTPServerFactory.getRunningTLSServer(2526, "localhost");
        assertNotNull(srvr);
        srvr.stop();
    }


}
