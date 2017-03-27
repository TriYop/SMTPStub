package org.yj.smtpstub.storage;

import com.sun.corba.se.spi.orbutil.fsm.FSM;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
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
    FSMailStore store;
    EmailModel sampleEmail;

    @Before
    public void setup() {
        store = new FSMailStore();
        sampleEmail = new EmailModel();
        sampleEmail.setEmailStr("Email content");
        sampleEmail.setSubject("This is the subject");
        sampleEmail.setTo("Somebody<somebody@example.com>");
        sampleEmail.setFrom("Someone<Someone@example.com>");
        sampleEmail.setFilePath("sample_path");
        sampleEmail.setReceivedDate(new Date());

        Configuration.set("emails.storage.fs.indexfile", "valid_index.json");
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testGetUniqueFile_invalidPath() {
        try {
            File f = FSMailStore.getUniqueFile("xw:\\toto");
            fail("An exception should have been thrown for this invalid path.");
        } catch (IOException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage() + " should be an IOException");
        }
    }

    @Test
    public void testGetUniqueFile_missingDir() {
        String filename = "C:\\TMPMAILS\\toto";
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
    public void testGetUniqueFile_withoutCollision() {
        String baseName = "C:\\toto";
        File f = null;
        try {
            f = FSMailStore.getUniqueFile(baseName);
            assertEquals("File name should be equal to provided name + .eml extension", baseName + ".eml", f.getPath());
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
    public void testGetUniqueFile_withCollision() {
        String baseName = "e:\\toto";
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
    public void testAddToIndex_nullModel() {
        try {
            FSMailStore.addToIndex(null);
        } catch (NullPointerException e) {
            fail("Null pointer exceptions should have been thrown");
        }
    }


    @Test
    public void testAddToIndex_emptyModel() {
        EmailModel model = new EmailModel();
        try {
            FSMailStore.addToIndex(model);
        } catch (NullPointerException e) {
            fail("Null pointer exceptions should have been thrown");
        }

    }

    @Test
    @Ignore
    public void testAddToIndex_nominal() {

    }

    @Test
    public void testSave_null() {
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
    public void testSave_incomplete() {
        try {
            store.save(new EmailModel());
            fail("An IncompleteEmailException should have been thrown");
        } catch (IncompleteEmailException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("An IncompleteEmailException should have been thrown");
        }
    }

    @Test
    @Ignore
    public void testSave_nominal() {

        // store.save();
    }

    @Test
    @Ignore
    public void testGetAllEmails_empty() {

    }

    @Test
    @Ignore
    public void testGetAllEmails_invalidIndex() {

    }

    @Test
    @Ignore
    public void testGetAllEmails_many() {

    }

    @Test
    public void testGetEmail_nominal() {
        try {

            FSMailStore.addToIndex(sampleEmail);
            EmailModel result = store.getEmail(0);
            assertEquals(sampleEmail, result);


        } catch (Exception e) {
            fail("method shouldn't crash. " + e.getMessage());
        }
    }



    @Test
    public void testLoadIndex_invalidFile() {
        try {
            Configuration.set("emails.storage.fs.indexfile", "");
            FSMailStore.loadIndex();
            fail("should have thrown an InvalidStoreException");
        } catch (InvalidStoreException e) {
            assert true;
        } catch (Exception e) {
            fail("method crashed" + e.getMessage());
        }
    }

    @Test
    public void testLoadIndex_emptyFile() {
        try {
            Configuration.set("emails.storage.fs.indexfile", "empty_index.json");
            FSMailStore.loadIndex();
            assert store.getAllEmails().isEmpty();
        } catch (InvalidStoreException e) {
            fail("should not have thrown an InvalidStoreException");
        } catch (Exception e) {
            fail("method crashed" + e.getMessage());
        }
    }

    @Test
    public void testLoadIndex_noJSONFile() {
        try {
            Configuration.set("emails.storage.fs.indexfile", "invalid_index.json");
            FSMailStore.loadIndex();
            fail("should have thrown an InvalidStoreException");
        } catch (InvalidStoreException e) {
            assert true;
        } catch (Exception e) {
            fail("method crashed" + e.getMessage());
        }
    }


    @Test
    public void testLoadIndex_nominal() {
        try {
            FSMailStore.loadIndex();
            assert !store.getAllEmails().isEmpty();

        } catch (Exception e) {
            fail("method crashed" + e.getMessage());
        }
    }
}
