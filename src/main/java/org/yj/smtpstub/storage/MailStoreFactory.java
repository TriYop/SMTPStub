package org.yj.smtpstub.storage;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yj.smtpstub.exception.InvalidStoreException;

/**
 * Created by TriYop on 01/03/2017.
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
                throw new InvalidStoreException();
            }
        } catch (ClassNotFoundException e) {
            logger.error("Mail storage engine {} is not a valid Storage engine", type, e);
            throw new InvalidStoreException();
        } catch (InstantiationException e) {
            logger.error("Mail storage engine {} could not be instanciated", type, e);
            throw new InvalidStoreException();
        } catch (IllegalAccessException e) {
            logger.error("Access to class {} is not granted.", type, e);
            throw new InvalidStoreException();
        }

    }
}
