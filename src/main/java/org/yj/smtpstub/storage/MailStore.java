package org.yj.smtpstub.storage;

import org.yj.smtpstub.model.EmailModel;

import java.util.Collection;

/**
 * Created by TriYop on 01/03/2017.
 */
public interface MailStore {
    public void save(EmailModel email);

    public Collection<EmailModel> getAllEmails();

    public EmailModel getEmail(int id);
}
