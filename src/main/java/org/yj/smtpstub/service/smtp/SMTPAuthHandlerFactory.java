package org.yj.smtpstub.service.smtp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subethamail.smtp.AuthenticationHandler;
import org.subethamail.smtp.AuthenticationHandlerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * The factory interface for creating authentication handlers.
 * with TLS capabilities
 */
final class SMTPAuthHandlerFactory implements AuthenticationHandlerFactory {
    private static final Logger logger = LoggerFactory.getLogger(SMTPAuthHandler.class);
    private static final String LOGIN_MECHANISM = "LOGIN";

    @Override
    public AuthenticationHandler create() {
        logger.debug( "Created new SMTP Authentication handler" );
        return new SMTPAuthHandler();
    }

    @Override
    public List<String> getAuthenticationMechanisms() {
        List<String> result = new ArrayList<>();
        result.add(SMTPAuthHandlerFactory.LOGIN_MECHANISM);
        return result;
    }

    /*
    public SSLSocket createSSLSocket(Socket socket) throws IOException {
        //try {
        SSLServerSocketFactory sf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

        InetSocketAddress remoteAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
        SSLServerSocket sslserversocket = (SSLServerSocket) sf.createServerSocket(socket.getPort());
        SSLSocket sslsocket = (SSLSocket) sslserversocket.accept();


        // SSLSocket s = (SSLSocket) (sf.createServerSocket(socket, remoteAddress.getHostName(), socket.getPort(), true));

        // s.setUseClientMode(false);

        for (String suite : sslsocket.getSupportedCipherSuites()) {
            logger.info("Supported suite: {}", suite);
        }
        // we do not want to enable all old broken cipherSuites
        // s.setEnabledCipherSuites(s.getSupportedCipherSuites());

        return sslsocket;
        //} catch (GeneralSecurityException e) {
        //    logger.error("A security exception occurred while creating TLS socket.", e);
        //    throw new IOException(e.getMessage());
        //}
    }*/
}
