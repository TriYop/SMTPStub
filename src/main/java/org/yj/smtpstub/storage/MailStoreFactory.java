package org.yj.smtpstub.storage;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yj.smtpstub.exception.InvalidStoreException;

/**
 * SMTPStub
 * --------------------------------------------
 *
 * @author TriYop
 * @since 1.0
 */
public final class MailStoreFactory {
    private static final Logger logger = LoggerFactory.getLogger(MailStoreFactory.class);

    private MailStoreFactory() {
    }

    /**
     * Instanciates a new mail store based on the provided type
     *
     * @param type the fully qualified class name for the mail store engine
     * @return
     * @throws InvalidStoreException
     */
    public static MailStore getMailStore(String type) throws InvalidStoreException {
        try {
            Class c = Class.forName(type);
            if (MailStore.class.isAssignableFrom(c)) {
                return (MailStore) c.newInstance();
            } else {
                logger.error("Invalid configuration: {} is not a valid MailStore implementation.");
                throw new InvalidStoreException(null);
            }
        } catch (ClassNotFoundException|NullPointerException ex) {
            logger.error("Mail storage engine {} is not a valid Storage engine", type, ex);
            throw new InvalidStoreException(ex);
        } catch (InstantiationException ex) {
            logger.error("Mail storage engine {} could not be instanciated", type, ex);
            throw new InvalidStoreException(ex);
        } catch (IllegalAccessException ex) {
            logger.error("Access to class {} is not granted.", type, ex);
            throw new InvalidStoreException(ex);
        }

    }
}
