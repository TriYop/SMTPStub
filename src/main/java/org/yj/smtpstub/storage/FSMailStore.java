package org.yj.smtpstub.storage;


import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yj.smtpstub.configuration.Configuration;
import org.yj.smtpstub.exception.IncompleteEmailException;
import org.yj.smtpstub.exception.InvalidStoreException;
import org.yj.smtpstub.model.EmailModel;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * SMTPStub
 * --------------------------------------------
 * Stores received emails into filesystem-based storage.
 *
 * @author TriYop
 * @since 1.0
 */
public class FSMailStore implements MailStore {
    private static final Logger logger = LoggerFactory.getLogger(FSMailStore.class);
    public static final String DEFAULT_MAILS_DIRECTORY = "inbox";
    public static final String DEFAULT_MAILS_INDEX_FILE = "index.json";
    public static final String DEFAULT_MAILS_EXTENSION = "eml";
    public static final String INDEX_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss.SSS";
    /**
     * The Index idx file.
     */
    static final String INDEX_IDX_FILE = "file";
    /**
     * The Index idx to.
     */
    static final String INDEX_IDX_TO = "to";
    /**
     * The Index idx from.
     */
    static final String INDEX_IDX_FROM = "from";
    /**
     * The Index idx subject.
     */
    static final String INDEX_IDX_SUBJECT = "subject";
    /**
     * The Index idx date.
     */
    static final String INDEX_IDX_DATE = "date";

    static final DateFormat indexDateFormat = new SimpleDateFormat(INDEX_DATE_FORMAT);

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyhhmmssSSS");
    private static JSONArray emailsList = new JSONArray();

    /**
     * Gets a uniquely named filedescriptor for writing mails to disk
     *
     * @param baseName base name
     * @return the file descriptor based on the base name and an iterator
     * @throws IOException the io exception
     */
    static synchronized File getUniqueFile(String baseName) throws IOException {
        int i = 0;

        File file = null;
        StringBuilder filename = new StringBuilder();
        while (file == null || file.exists()) {
            filename.delete(0, filename.length());
            filename.append(baseName);
            if (i++ > 0) {
                filename.append("_").append(i).append(Configuration.get("mails.suffix", DEFAULT_MAILS_EXTENSION));
            } else {
                filename.append(Configuration.get("mails.suffix", DEFAULT_MAILS_EXTENSION));
            }
            file = new File(filename.toString());
            logger.info("Checking if filename '{}' is valid", filename);
        }
        if (!file.createNewFile()) {
            throw new IOException("File " + filename.toString() + " could not be created");
        }
        return file.getCanonicalFile();
    }


    /**
     * Add to index.
     *
     * @param email the email
     */
    static synchronized void addToIndex(EmailModel email) {
        if (email == null || email.hasEmptyField()) {
            logger.warn("A null or incomplete email was sent for indexing ...");
            return;
        }
        JSONObject emailObj = new JSONObject();

        emailObj.put(INDEX_IDX_FILE, email.getFilePath());
        emailObj.put(INDEX_IDX_SUBJECT, email.getSubject());
        emailObj.put(INDEX_IDX_FROM, email.getFrom());
        emailObj.put(INDEX_IDX_TO, email.getTo());
        emailObj.put(INDEX_IDX_DATE, indexDateFormat.format(email.getReceivedDate()));
        synchronized (emailsList) {
            emailsList.add(emailObj);
        }
    }

    /**
     * Save index.
     */
    protected static synchronized void saveIndex() {
        synchronized (emailsList) {
            String indexFile = Configuration.get("emails.storage.fs.indexfile", DEFAULT_MAILS_INDEX_FILE);
            JSONObject obj = new JSONObject();
            obj.put("list", emailsList);
            try (FileWriter file = new FileWriter(indexFile)) {

                file.write(obj.toJSONString());
                file.flush();

            } catch (IOException ex) {
                logger.error("An error occurred while saving index file.", ex);
            }
        }
    }

    /**
     * Load index.
     *
     * @return
     */
    static void loadIndex() throws InvalidStoreException {
        String indexFile = Configuration.get("emails.storage.fs.indexfile", DEFAULT_MAILS_INDEX_FILE);
        JSONParser parser = new JSONParser();
        try (FileReader file = new FileReader(indexFile)) {
            if (file.ready()) {
                // ...
                Object obj = parser.parse(file);
                JSONObject jsonObject = (JSONObject) obj;
                emailsList = (JSONArray) jsonObject.getOrDefault("list", new JSONArray());
            } else {
                emailsList = new JSONArray();
            }
        } catch (IOException ex) {
            logger.error("Could not load emails index file {}", indexFile, ex);
            throw new InvalidStoreException(ex);
        } catch (ParseException ex) {
            logger.error("Could not parse emails index file {}", indexFile, ex);
            throw new InvalidStoreException(ex);
        }
    }

    /**
     * @param email
     */
    @Override
    public synchronized void save(@Nullable EmailModel email) throws IncompleteEmailException {
        if (email == null) {
            return;
        }
        if (email.getReceivedDate() == null || email.getEmailStr() == null) {
            throw new IncompleteEmailException();
        }

        String filePath = String.format("%s%s%s", Configuration.get("emails.storage.fs.path", DEFAULT_MAILS_DIRECTORY), File.separator,
                dateFormat.format(new Date()));

        try {
            File file = getUniqueFile(filePath);
            email.setFilePath(file.getPath());
            FileUtils.writeStringToFile(file, email.getEmailStr());
        } catch (IOException e) {
            logger.error("Error: Can't save email: {}", e.getMessage(), e);
        }

        addToIndex(email);
        saveIndex();
    }

    /**
     * common method for parsing email models from JSON Objects
     *
     * @param emailObj
     * @return
     */
    private static final EmailModel getEmailFromJSONObject(JSONObject emailObj) {
        EmailModel email = new EmailModel();
        email.setFilePath((String) emailObj.get(INDEX_IDX_FILE));
        email.setSubject((String) emailObj.get(INDEX_IDX_SUBJECT));
        email.setFrom((String) emailObj.get(INDEX_IDX_FROM));
        email.setTo((String) emailObj.get(INDEX_IDX_TO));
        try {
            email.setReceivedDate(indexDateFormat.parse((String) emailObj.get(INDEX_IDX_DATE)));
        } catch (java.text.ParseException ex) {
            logger.warn(ex.getMessage());
        }
        return email;
    }

    /**
     * @return
     */
    @Override
    public Collection<EmailModel> getAllEmails() {
        Set<EmailModel> models = new LinkedHashSet<>();
        emailsList.forEach((Object obj) -> {
            JSONObject emailObj = (JSONObject) obj;
            models.add(getEmailFromJSONObject(emailObj));
        });
        return models;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public EmailModel getEmail(int id) throws InvalidStoreException {
        JSONObject emailObj = (JSONObject) emailsList.get(id);
        return getEmailFromJSONObject(emailObj);
        // TODO: load email content from file


    }
}
