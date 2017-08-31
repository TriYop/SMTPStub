package org.yj.smtpstub.storage;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yj.smtpstub.configuration.Configuration;
import org.yj.smtpstub.exception.IncompleteEmailException;
import org.yj.smtpstub.exception.InvalidStoreException;
import org.yj.smtpstub.model.EmailModel;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * SMTPStub
 * --------------------------------------------
 *
 * @author TriYop
 */
public class TestFSMailStore {
    static final String STORAGE_INDEX_FILE_KEY = "emails.storage.fs.indexfile";
    private static final Logger logger = LoggerFactory.getLogger(TestFSMailStore.class);
    FSMailStore store;
    EmailModel sampleEmail;

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

    @Test
    public void testGetUniqueFile_invalidPath() {
        try {
            FSMailStore.getUniqueFile("xw:///toto");
            fail("An exception should have been thrown for this invalid path.");
        } catch (IOException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage() + " should be an IOException");
        }
    }

    @Test
    public void testGetUniqueFile_missingDir() {
        String filename = "/TMPMAILS/toto";
        File f = null;

        try {
            f = FSMailStore.getUniqueFile(filename);
            fail("An IO exception (missing directory) should have been thrown");
        } catch (IOException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage() + " should be an IOException");
        } finally {
            if (f != null && f.isFile()) {
                f.delete();
            }
        }
    }

    @Test
    public void testGetUniqueFileWithoutCollision() {
        String baseName = "toto";
        File f = null;
        try {
            f = FSMailStore.getUniqueFile(baseName);
            assertEquals("File name should be equal to provided name + .eml extension", baseName + ".eml", f.getName());
        } catch (IOException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage() + " should be an IOException");
        } finally {
            if (f != null && f.isFile()) {
                f.delete();
            }
        }
    }

    @Test
    public void testGetUniqueFileWithCollision() {
        String baseName = "toto";
        File f1 = null;
        File f2 = null;
        try {
            f1 = FSMailStore.getUniqueFile(baseName);
            f1.createNewFile();
            f2 = FSMailStore.getUniqueFile(baseName);

            assertTrue("File names should not be respectively equal", !f1.getPath().equals(f2.getPath()));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown but was not expected");
        } finally {
            if (f1 != null && f1.isFile()) {
                f1.delete();
            }

            if (f2 != null && f2.isFile()) {
                f2.delete();
            }
        }
    }


    // Test addToIndex

    @Test
    public void testAddToIndexNullModel() {
        try {
            FSMailStore.addToIndex(null);
        } catch (NullPointerException e) {
            fail("Null pointer exceptions should have been caught");
        }
    }


    @Test
    public void testAddToIndexEmptyModel() {
        EmailModel model = new EmailModel();
        try {
            FSMailStore.addToIndex(model);
            assertTrue(true);
        } catch (NullPointerException e) {
            fail("Null pointer exceptions should have been caught");
        }

    }

    @Test
<<<<<<< HEAD
    public void testAddToIndex_nominal() {
        FSMailStore.addToIndex(sampleEmail);
=======
    public void testAddToIndexNominal() {
        FSMailStore.addToIndex(sampleEmail);
>>>>>>> 4a800c802fe0e6ce538a2a61e1a5132f21711085
        assertFalse(store.getAllEmails().isEmpty());
    }

    @Test
    public void testSaveNull() {
        try {
            store.save(null);
            assertTrue(true);
        } catch (IncompleteEmailException e) {
            fail("No exception was expected to be caught here.");
        } catch (NullPointerException e) {
            fail("No exception was expected to be caught");
        }
    }

    @Test
    public void testSaveIncomplete() {
        try {
            store.save(new EmailModel());
            fail("An IncompleteEmailException should have been thrown");
        } catch (IncompleteEmailException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("An IncompleteEmailException should have been thrown");
        }

        try {
            EmailModel model = new EmailModel();
            model.setReceivedDate(new Date());
            store.save(model);
            fail("An IncompleteEmailException should have been thrown");
        } catch (IncompleteEmailException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("An IncompleteEmailException should have been thrown");
        }

        try {
            EmailModel model = new EmailModel();
            model.setEmailStr("not empty string");
            store.save(model);
            fail("An IncompleteEmailException should have been thrown");
        } catch (IncompleteEmailException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("An IncompleteEmailException should have been thrown");
        }
    }

    @Test
    public void testSaveNominal() {
        try {
            store.save(sampleEmail);
        } catch (IncompleteEmailException ex) {
            fail("Bad email model handling in test.");
        }
        // store.save();
    }


    @Test
    public void testGetEmailNominal() {
        try {
            EmailModel expect = (EmailModel) store.getAllEmails().toArray()[0];
            EmailModel result = store.getEmail(0);

            assertEquals(expect.getFilePath(), result.getFilePath());
            assertEquals(expect.getFrom(), result.getFrom());
            assertEquals(expect.getTo(), result.getTo());
            assertEquals(expect.getReceivedDate(), result.getReceivedDate());
            assertEquals(expect.getSubject(), result.getSubject());
            assertEquals(expect.getEmailStr(), result.getEmailStr());

            // assertEquals(expect, result);

        } catch (Exception e) {
            fail("method shouldn't crash. " + e.getMessage());
        }
    }


    @Test
    public void testLoadIndex_InvalidFile() {
        try {
            Configuration.set("emails.storage.fs.indexfile", "");
            FSMailStore.loadIndex();
            fail("should have thrown an InvalidStoreException");
        } catch (InvalidStoreException e) {
            assert true;
        } catch (Exception e) {
            fail("method crashed" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadIndexEmptyFile() {
        try {
            Configuration.set(STORAGE_INDEX_FILE_KEY, getResourceFile("/empty_index.json"));
            FSMailStore.loadIndex();
            assert store.getAllEmails().isEmpty();
        } catch (InvalidStoreException e) {
            fail("should not have thrown an InvalidStoreException");
        } catch (Exception e) {
            fail("method crashed with message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadIndexNoJSONFile() {
        try {
            Configuration.set("emails.storage.fs.indexfile", getResourceFile("/invalid_index.json"));
            FSMailStore.loadIndex();
            fail("should have thrown an InvalidStoreException");
        } catch (InvalidStoreException e) {
            assert true;
        } catch (Exception e) {
            fail("method crashed with message: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Test
    public void testLoadIndexNominal() {
        try {
            logger.warn("Index file is set to {}", Configuration.get("emails.storage.fs.indexfile", FSMailStore.DEFAULT_MAILS_INDEX_FILE));
            FSMailStore.loadIndex();
            assert !store.getAllEmails().isEmpty();

        } catch (Exception e) {
            e.printStackTrace();
            fail("method crashed with message: " + e.getMessage());
        }
    }


    @Test
    public void testSaveIndexNominal() {
        try {
            //FIXME: implement this test with valid in and out index.
            FSMailStore.saveIndex();
        } catch (Exception e) {
            fail("method crashed");
        }
    }

    public final String getResourceFile(String filename) {
        return getClass().getResource(filename).getPath();
    }
}
