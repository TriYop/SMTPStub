package org.yj.smtpstub.model;

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

    private Date receivedDate;
    private String from;
    private String to;
    private String subject;
    private String emailStr;
    private String filePath;


    /**
     * Instantiates a new Email model.
     */
    public EmailModel() {
        super();
    }

    /**
     * Gets received date.
     *
     * @return the received date
     */
    public Date getReceivedDate() {
        return receivedDate;
    }

    /**
     * Sets received date.
     *
     * @param receivedDate the received date
     */
    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    /**
     * Gets from.
     *
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * Sets from.
     *
     * @param from the from
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Gets to.
     *
     * @return the to
     */
    public String getTo() {
        return to;
    }

    /**
     * Sets to.
     *
     * @param to the to
     */
    public void setTo(String to) {
        this.to = to;
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
     * @param subject the subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
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
     * @param emailStr the email str
     */
    public void setEmailStr(String emailStr) {
        this.emailStr = emailStr;
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
     * @param filePath the file path
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
