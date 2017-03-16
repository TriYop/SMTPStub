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
import org.yj.smtpstub.model.EmailModel;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
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

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyhhmmssSSS");
    private static JSONArray emailsList = new JSONArray();

    /**
     * Gets a uniquely named filedescriptor for writing mails to disk
     *
     * @param baseName base name
     * @return the file descriptor based on the base name and an iterator
     * @throws IOException the io exception
     */
    protected static synchronized File getUniqueFile(String baseName) throws IOException {
        int i = 0;

        File file = null;
        StringBuilder filename = new StringBuilder();
        while (file == null || file.exists()) {
            filename.delete(0, filename.length());
            filename.append(baseName);
            if (i++ > 0) {
                filename.append("_").append(i).append(Configuration.get("mails.suffix"));
            } else {
                filename.append(Configuration.get("mails.suffix"));
            }
            file = new File(filename.toString());
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
    protected static synchronized void addToIndex(EmailModel email) {
        JSONObject emailObj = new JSONObject();

        emailObj.put(INDEX_IDX_FILE, email.getFilePath());
        emailObj.put(INDEX_IDX_SUBJECT, email.getSubject());
        emailObj.put(INDEX_IDX_FROM, email.getFrom());
        emailObj.put(INDEX_IDX_TO, email.getTo());
        emailObj.put(INDEX_IDX_DATE, email.getReceivedDate());
        synchronized (emailsList) {
            emailsList.add(emailObj);
        }
    }

    /**
     * Save index.
     */
    protected static synchronized void saveIndex() {
        synchronized (emailsList) {
            String indexFile = Configuration.get("emails.storage.fs.indexfile");
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
    protected static void loadIndex() {
        String indexFile = Configuration.get("emails.storage.fs.indexfile");
        JSONParser parser = new JSONParser();
        try (FileReader file = new FileReader(indexFile)) {
            Object obj = parser.parse(file);
            JSONObject jsonObject = (JSONObject) obj;

            emailsList = (JSONArray) jsonObject.getOrDefault("emailsIndex", new JSONArray());


        } catch (IOException ex) {
            logger.error("Could not load emails index file {}", indexFile, ex);
        } catch (ParseException ex) {
            logger.error("Could not parse emails index file {}", indexFile, ex);
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

        String filePath = String.format("%s%s%s", Configuration.get("emails.storage.fs.path"), File.separator,
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
     * @return
     */
    @Override
    public Collection<EmailModel> getAllEmails() {
        Set<EmailModel> models = new HashSet<>();
        emailsList.forEach((Object obj) -> {
            JSONObject emailObj = (JSONObject) obj;
            EmailModel email = new EmailModel();
            email.setFilePath((String) emailObj.get(INDEX_IDX_FILE));
            email.setSubject((String) emailObj.get(INDEX_IDX_SUBJECT));
            email.setFrom((String) emailObj.get(INDEX_IDX_FROM));
            email.setTo((String) emailObj.get(INDEX_IDX_TO));
            email.setReceivedDate((Date) emailObj.get(INDEX_IDX_DATE));
            models.add(email);
        });
        return models;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public EmailModel getEmail(int id) {
        JSONObject emailObj = (JSONObject) emailsList.get(id);
        EmailModel email = new EmailModel();
        email.setFilePath((String) emailObj.get(INDEX_IDX_FILE));
        email.setSubject((String) emailObj.get(INDEX_IDX_SUBJECT));
        email.setFrom((String) emailObj.get(INDEX_IDX_FROM));
        email.setTo((String) emailObj.get(INDEX_IDX_TO));
        email.setReceivedDate((Date) emailObj.get(INDEX_IDX_DATE));
        // TODO: load email content from file

        return email;
    }
}
