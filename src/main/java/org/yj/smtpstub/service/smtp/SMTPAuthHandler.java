package org.yj.smtpstub.service.smtp;

import org.subethamail.smtp.AuthenticationHandler;

/**
 * SMTPStub
 * --------------------------------------------
 * Simulates an authentication handler to allow capturing emails that are set up with login authentication.
 *
 * @author TriYop
 * @since 1.0
 */
final class SMTPAuthHandler implements AuthenticationHandler {
    /** default user identity for simulating authentication. **/
    private static final String USER_IDENTITY = "User";
    // TODO: encode messages with Base64 codec instead of hardcoding them
    /** VXNlcm5hbWU6 is base64 for "Username:" */
    public static final String CALLBACK_USERNAME = "334 VXNlcm5hbWU6";
    /** UGFzc3dvcmQ6 is base64 for "Password:" */
    public static final String CALLBACK_PASSWORD = "334 UGFzc3dvcmQ6";
    /** initializes pass number to 0 */
    private transient int pass = 0;

    /**
     * Handles authentication process.
     *
     * @param clientInput The client's input, eg "AUTH PLAIN dGVzdAB0ZXN0ADEyMzQ="
     * @return <code>null</code> if the authentication process is finished, otherwise a string to hand back to the client.
     */
    @Override
    public String auth(String clientInput) {
        String prompt;
        // increment pass number
        pass++;
        switch (pass) {
            case 1:
                prompt = SMTPAuthHandler.CALLBACK_USERNAME;
                break;
            case 2:
                prompt = SMTPAuthHandler.CALLBACK_PASSWORD;
                break;
            default:
                pass = 0;
                prompt = null;
        }
        return prompt;
    }

    /**
     * If the authentication process was successful, this returns the identity
     * of the user. The type defining the identity can vary depending on the
     * authentication mechanism used, but typically this returns a String username.
     * If authentication was not successful, the return value is undefined.
     */
    @Override
    public Object getIdentity() {
        return SMTPAuthHandler.USER_IDENTITY;
    }
}
