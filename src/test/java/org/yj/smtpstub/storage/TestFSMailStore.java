package org.yj.smtpstub.storage;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yj.smtpstub.configuration.Configuration;
import org.yj.smtpstub.exception.IncompleteEmailException;
import org.yj.smtpstub.exception.InvalidStoreException;
import org.yj.smtpstub.model.EmailModel;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * SMTPStub
 * --------------------------------------------
 *
 * @author TriYop
 */
public class TestFSMailStore {
    private static final String STORAGE_INDEX_FILE_KEY = "emails.storage.fs.indexfile";
    private static final Logger logger = LoggerFactory.getLogger(TestFSMailStore.class);
    private FSMailStore store;
    private EmailModel sampleEmail;


    @Before
    public void setUp() {
        store = new FSMailStore();
        sampleEmail = new EmailModel();
        sampleEmail.setEmailStr("Email content");
        sampleEmail.setSubject("This is the subject");
        sampleEmail.setTo("Somebody<somebody@example.com>");
        sampleEmail.setFrom("Someone<Someone@example.com>");
        sampleEmail.setFilePath("sample_path");
        sampleEmail.setReceivedDate(new Date());


        Configuration.set(STORAGE_INDEX_FILE_KEY, getResourceFile("/valid_index.json"));

    }

    @Test(expected = IOException.class)
    public void testGetUniqueFileInvalidPath() throws IOException {
        FSMailStore.getUniqueFile("xw:///toto");

    }

    @Test(expected = IOException.class)
    public void testGetUniqueFileMissingDir() throws IOException {
        String filename = "/TMPMAILS/toto";
        FSMailStore.getUniqueFile(filename);
    }

    @Test
    public void testGetUniqueFileWithoutCollision() throws IOException {
        String baseName = "test_emaill" + String.valueOf(System.currentTimeMillis());
        File uniqueFile = FSMailStore.getUniqueFile(baseName);
        assertEquals("File name should be equal to provided name + .eml extension", baseName + ".eml", uniqueFile.getName());
    }

    @Test
    public void testGetUniqueFileWithCollision() throws IOException {
        String baseName = "test_emaill" + String.valueOf(System.currentTimeMillis());
        File uniqueFile = FSMailStore.getUniqueFile(baseName);
        uniqueFile.createNewFile();
        File uniqueFile1 = FSMailStore.getUniqueFile(baseName);
        assertFalse("File names should not be respectively equal", uniqueFile.getPath().equals(uniqueFile1.getPath()));
    }

    @Test
    public void testAddToIndexNullModel() {
        FSMailStore.addToIndex(null);
        assert true;
    }


    @Test
    public void testAddToIndexEmptyModel() {
        EmailModel model = new EmailModel();
        FSMailStore.addToIndex(model);
        assert true;
    }

    @Test
    public void testAddToIndexNominal() {
        FSMailStore.addToIndex(sampleEmail);
        assertFalse(store.getAllEmails().isEmpty());
    }

    @Test
    public void testSaveNull() throws IncompleteEmailException {
        store.save(null);
        assertTrue(true);
    }

    @Test(expected = IncompleteEmailException.class)
    public void testSaveEmpty() throws IncompleteEmailException {
        store.save(new EmailModel());
    }

    @Test(expected = IncompleteEmailException.class)
    public void testSaveIncomplete1() throws IncompleteEmailException {
        EmailModel model = new EmailModel();
        model.setReceivedDate(new Date());
        store.save(model);
        fail("An IncompleteEmailException should have been thrown");
    }

    @Test(expected = IncompleteEmailException.class)
    public void testSaveIncomplete2() throws IncompleteEmailException {
        EmailModel model = new EmailModel();
        model.setEmailStr("not empty string");
        store.save(model);
        assert true;
    }

    @Test
    public void testSaveNominal() throws IncompleteEmailException {
        store.save(sampleEmail);
        assert true;
    }


    @Test
    @Ignore
    public void testGetEmailNominal() {

        Collection<EmailModel> emails = store.getAllEmails();

        if (emails.iterator().hasNext()) {
            EmailModel expect = emails.iterator().next();

            EmailModel result = store.getEmail(0);

            assertEquals(expect.getFilePath(), result.getFilePath());
            assertEquals(expect.getFrom(), result.getFrom());
            assertEquals(expect.getTo(), result.getTo());
            assertEquals(expect.getReceivedDate(), result.getReceivedDate());
            assertEquals(expect.getSubject(), result.getSubject());
            assertEquals(expect.getEmailStr(), result.getEmailStr());
            assert true;
        } else {
            fail("the test store should contain at least one email meta-data");
        }

    }


    @Test(expected = InvalidStoreException.class)
    public void testLoadIndexInvalidFile() throws InvalidStoreException {
        Configuration.set("emails.storage.fs.indexfile", "");
        FSMailStore.loadIndex();
    }

    @Test
    public void testLoadIndexEmptyFile() throws InvalidStoreException {
        Configuration.set(STORAGE_INDEX_FILE_KEY, getResourceFile("/empty_index.json"));
        FSMailStore.loadIndex();
        assert store.getAllEmails().isEmpty();
    }

    @Test(expected = InvalidStoreException.class)
    public void testLoadIndexNoJSONFile() throws InvalidStoreException {
        Configuration.set("emails.storage.fs.indexfile", getResourceFile("/invalid_index.json"));
        FSMailStore.loadIndex();
    }


    @Test
    public void testLoadIndexNominal() throws InvalidStoreException {
        logger.warn("Index file is set to {}", Configuration.get("emails.storage.fs.indexfile", FSMailStore.DEFAULT_MAILS_INDEX_FILE));
        FSMailStore.loadIndex();
        logger.warn("Loaded store without exploding : {}.", store.toString());
        logger.info("Store contains {} emails", store.getAllEmails().size());
        assertFalse(store.getAllEmails().isEmpty());
    }


    @Test
    @Ignore
    public void testSaveIndexNominal() {
        //FIXME: implement this test with valid in and out index.
        FSMailStore.saveIndex();
        assert true;
    }

    public final String getResourceFile(String filename) {
        return getClass().getResource(filename).getPath();
    }
}
