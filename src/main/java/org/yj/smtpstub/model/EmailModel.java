package org.yj.smtpstub.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


/**
 * SMTPStub
 * --------------------------------------------
 * Email object model defines email structure.
 *
 * @author TriYop
 * @since 1.0
 */
public final class EmailModel {

    /**
     * class logger
     */
    private static final Logger logger = LoggerFactory.getLogger(EmailModel.class);

    /**
     * date when message was received by server.
     * This may not be execution time due recipient asynchronous handling
     */
    private Date receivedDate;

    /**
     * message emitter
     */
    private String emitter;

    /**
     * message recipient
     */
    private String recipient;

    /**
     * message subject
     */
    private String subject;

    /**
     * message content body
     */
    private String emailStr;
    /**
     * message file path used for storing message.
     */
    private String filePath;


    /**
     * Gets received date.
     *
     * @return the received date
     */
    public Date getReceivedDate() {
        if (receivedDate == null) {
            return null;
        }
        return (Date) receivedDate.clone();
    }

    /**
     * Sets received date.
     *
     * @param incomingDate the received date
     */

    public void setReceivedDate(final Date incomingDate) {
        if (incomingDate == null) {
            logger.warn("date being set recipient NULL !");
            this.receivedDate = null;
        } else {
            this.receivedDate = (Date) incomingDate.clone();
        }
    }

    /**
     * Gets emitter.
     *
     * @return the emitter
     */
    public String getEmitter() {
        return emitter;
    }

    /**
     * Sets emitter.
     *
     * @param emitterAddress the emitter
     */
    public void setEmitter(String emitterAddress) {
        this.emitter = emitterAddress;
    }

    /**
     * Gets recipient.
     *
     * @return the recipient
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * Sets recipient.
     *
     * @param recipientAddress the recipient
     */
    public void setRecipient(String recipientAddress) {
        this.recipient = recipientAddress;
    }

    /**
     * Gets subject.
     *
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets subject.
     *
     * @param subj the subject
     */
    public void setSubject(String subj) {
        this.subject = subj;
    }

    /**
     * Gets email str.
     *
     * @return the email str
     */
    public String getEmailStr() {
        return emailStr;
    }

    /**
     * Sets email str.
     *
     * @param emailContent the email str
     */
    public void setEmailStr(String emailContent) {
        this.emailStr = emailContent;
    }

    /**
     * Gets file path.
     *
     * @return the file path
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Sets file path.
     *
     * @param filepath the file path
     */
    public void setFilePath(String filepath) {
        this.filePath = filepath;
    }

    /**
     * @return
     */
    public boolean hasEmptyField() {
        return null == this.emailStr || null == this.filePath || null == this.emitter || null == this.receivedDate || null == this.subject || null == this.recipient;
    }


}
