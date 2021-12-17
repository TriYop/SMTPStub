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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    /**
     * defines default directory for storing received emails
     */
    public static final String DEFAULT_MAILS_DIRECTORY = "/var/smtpstub/inbox";
    /**
     * defines default index file name
     */
    public static final String DEFAULT_MAILS_INDEX_FILE = "/var/smtpstub/index.json";
    /**
     * defines received emails default file extension
     */
    public static final String DEFAULT_MAILS_EXTENSION = "eml";
    /**
     * defines index date format to be used for storage
     */
    public static final String INDEX_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss.SSS";
    /**
     * logs events to a dedicated stream
     */
    private static final Logger logger = LoggerFactory.getLogger(FSMailStore.class);
    /**
     * The Index idx file.
     */
    private static final String INDEX_IDX_FILE = "file";
    /**
     * The Index idx to.
     */
    private static final String INDEX_IDX_TO = "to";
    /**
     * The Index idx from.
     */
    private static final String INDEX_IDX_FROM = "from";
    /**
     * The Index idx subject.
     */
    private static final String INDEX_IDX_SUBJECT = "subject";
    /**
     * The Index idx date.
     */
    private static final String INDEX_IDX_DATE = "date";


    private static final String MAILS_DIRECTORY_KEY = "emails.storage.fs.path";
    private static final String MAILS_INDEX_FILE_KEY = "emails.storage.fs.indexfile";
    private static final String MAILS_SUFFIX_KEY = "emails.storage.fs.suffix";


    /**
     * DateFormat utility instance to parse and format Index stored dates
     */
    private final DateFormat indexDateFormat = new SimpleDateFormat(INDEX_DATE_FORMAT);
    /**
     * JSON Array to store in memory the emails index
     */
    private static JSONArray emailsList = new JSONArray();


    private String storeRootDirectory;
    private String indexFilePath;
    /**
     * DateFormat utility instance to format received emails filenames
     */
    private final transient SimpleDateFormat filenameDateFormat = new SimpleDateFormat("ddMMyyhhmmssSSS");

    public FSMailStore() {
        try {
            String root = Configuration.getInstance().getStringValue(MAILS_DIRECTORY_KEY, DEFAULT_MAILS_DIRECTORY);
            Path p = Paths.get(root);
            if (!Files.isDirectory(p) && !Files.notExists(p)) {
                Files.createDirectories(p);

            }
            storeRootDirectory = p.toString();

        } catch (IOException e) {
            logger.warn("Could nout ceeate missing store directory.", e);
        }

        try {
            String root = Configuration.getInstance().getStringValue(MAILS_INDEX_FILE_KEY, DEFAULT_MAILS_INDEX_FILE);
            Path p = Paths.get(root);
            if (!Files.isRegularFile(p) && !Files.notExists(p)) {
                Files.createFile(p);
                saveIndex();
            }
            indexFilePath = p.toString();
            loadIndex();
        } catch (IOException e) {
            logger.warn("Could nout ceeate missing index file", e);
        } catch (InvalidStoreException e) {
            logger.error("Could not load store content.", e);
        }
    }


    /**
     * Gets a uniquely named filedescriptor for writing mails to disk
     *
     * @param baseName base name
     * @return the file descriptor based on the base name and an iterator
     * @throws IOException the io exception
     */
    protected synchronized File getUniqueFile(String baseName) throws IOException {
        int indx = 0;

        File file = null;
        StringBuilder filename = new StringBuilder();

        String fname = "";
        if (!storeRootDirectory.endsWith("/")) {
            fname = storeRootDirectory.concat("/").concat(baseName);
        } else {
            fname = storeRootDirectory.concat(baseName);
        }
        String extension = Configuration.getInstance().getStringValue(MAILS_SUFFIX_KEY, DEFAULT_MAILS_EXTENSION);
        while (file == null || file.exists()) {
            filename.delete(0, filename.length());
            filename.append(fname);
            indx++;
            if (indx > 1) {
                filename.append("_").append(indx).append('.').append(extension);
            } else {
                filename.append('.').append(extension);
            }
            file = FileUtils.getFile(filename.toString());
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
    protected synchronized void addToIndex(EmailModel email) {
        if (email == null || email.hasEmptyField()) {
            logger.warn("A null or incomplete email was sent for indexing ...");
            return;
        }
        JSONObject emailObj = new JSONObject();

        emailObj.put(INDEX_IDX_FILE, email.getFilePath());
        emailObj.put(INDEX_IDX_SUBJECT, email.getSubject());
        emailObj.put(INDEX_IDX_FROM, email.getEmitter());
        emailObj.put(INDEX_IDX_TO, email.getRecipient());
        emailObj.put(INDEX_IDX_DATE, indexDateFormat.format(email.getReceivedDate()));
        synchronized (emailsList) {
            emailsList.add(emailObj);
        }
    }

    /**
     * Save index.
     */
    protected synchronized void saveIndex() {
        synchronized (emailsList) {
            logger.info("Mail index set to " + indexFilePath);
            JSONObject obj = new JSONObject();
            obj.put("list", emailsList);
            try (FileWriter file = new FileWriter(indexFilePath)) {

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
    protected static void loadIndex() throws InvalidStoreException {
        String indexFile = Configuration.getInstance().getStringValue(MAILS_INDEX_FILE_KEY, DEFAULT_MAILS_INDEX_FILE);
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
     * common method for parsing email models from JSON Objects
     *
     * @param emailObj
     * @return
     */
    private EmailModel getEmailFromJSONObject(JSONObject emailObj) {
        EmailModel email = new EmailModel();
        email.setFilePath((String) emailObj.get(INDEX_IDX_FILE));
        email.setSubject((String) emailObj.get(INDEX_IDX_SUBJECT));
        email.setEmitter((String) emailObj.get(INDEX_IDX_FROM));
        email.setRecipient((String) emailObj.get(INDEX_IDX_TO));
        try {
            email.setReceivedDate(indexDateFormat.parse((String) emailObj.get(INDEX_IDX_DATE)));
        } catch (java.text.ParseException ex) {
            logger.warn(ex.getMessage());
        }
        return email;
    }

    /**
     * @param email
     */
    @Override
    public synchronized void save(@Nullable EmailModel email) throws IncompleteEmailException {

        if (email == null || email.getReceivedDate() == null || email.getEmailStr() == null) {
            throw new IncompleteEmailException();
        }

        String filePath = String.format(filenameDateFormat.format(new Date()));

        try {
            File file = getUniqueFile(filePath);
            email.setFilePath(file.getPath());
            logger.debug(file.getPath());

            FileUtils.writeStringToFile(file, email.getEmailStr(), StandardCharsets.UTF_8);
            // we want to update the index only when email is well stored onto disk
            addToIndex(email);
            saveIndex();
        } catch (IOException e) {
            logger.error("Error: Can't save email to {} : {}", filePath, e.getMessage(), e);
        }
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
     * @param emailId
     * @return
     */
    @Override
    public EmailModel getEmail(int emailId) {
        JSONObject emailObj = (JSONObject) emailsList.get(emailId);
        return getEmailFromJSONObject(emailObj);
        // TODO: load email content from file


    }
}
