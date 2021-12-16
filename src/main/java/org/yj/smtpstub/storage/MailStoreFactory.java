package org.yj.smtpstub.storage;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yj.smtpstub.configuration.Configuration;
import org.yj.smtpstub.exception.InvalidStoreException;

/**
 * SMTPStub
 * --------------------------------------------
 *
 * @author TriYop
 * @since 1.0
 */
public final class MailStoreFactory {
    /**
     * Logs events into a dedicated stream
     */
    private static final Logger logger = LoggerFactory.getLogger(MailStoreFactory.class);
    public static final String DEFAULT_STORAGE_ENGINE = FSMailStore.class.getCanonicalName();

    /**
     *
     */
    private MailStoreFactory() {
    }

    /**
     * Instanciates a new mail store based on the provided type
     *
     * @param type the fully qualified class name for the mail store engine
     * @return
     * @throws InvalidStoreException
     */
    public static MailStore getMailStore() throws InvalidStoreException {

        String type = Configuration.getInstance().getStringValue("emails.storage.engine", DEFAULT_STORAGE_ENGINE);
        InvalidStoreException exc = null;
        try {
            Class storeClass = Class.forName(type);
            if (MailStore.class.isAssignableFrom(storeClass)) {
                return (MailStore) storeClass.newInstance();
            }
        } catch (ClassNotFoundException | NullPointerException ex) {
            logger.error("Mail storage engine {} is not a valid Storage engine", type, ex);
            exc = new InvalidStoreException(ex);
        } catch (InstantiationException ex) {
            logger.error("Mail storage engine {} could not be instanciated", type, ex);
            exc = new InvalidStoreException(ex);
        } catch (IllegalAccessException ex) {
            logger.error("Access to class {} is not granted.", type, ex);
            exc = new InvalidStoreException(ex);
        }
        if (null == exc) {
            logger.error("Invalid configuration: {} is not a valid MailStore implementation.", type);
            exc = new InvalidStoreException(new Exception("Invalid configuration"));
        }
        throw exc;

    }
}
