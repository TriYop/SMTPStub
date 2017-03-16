package org.yj.smtpstub.storage;

import org.yj.smtpstub.exception.IncompleteEmailException;
import org.yj.smtpstub.model.EmailModel;

import java.util.Collection;

/**
 * SMTPStub
 * --------------------------------------------
 * Abstract storage engine that all mail storage engines must implement.
 *
 * @author TriYop
 * @since 1.0
 */
public interface MailStore {
    /**
     * Save.
     *
     * @param email the email
     * @throws IncompleteEmailException the incomplete email exception
     */
    public void save(EmailModel email) throws IncompleteEmailException;

    /**
     * Gets all emails.
     *
     * @return the all emails
     */
    public Collection<EmailModel> getAllEmails();

    /**
     * Gets email.
     *
     * @param id the id
     * @return the email
     */
    public EmailModel getEmail(int id);
}
