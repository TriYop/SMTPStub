package org.yj.smtpstub.processor;

import org.junit.Test;
import org.yj.smtpstub.exception.IncompleteEmailException;
import org.yj.smtpstub.exception.InvalidStoreException;
import org.yj.smtpstub.storage.FSMailStore;
import org.yj.smtpstub.storage.MailStore;
import org.yj.smtpstub.storage.MailStoreFactory;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * SMTPStub
 * --------------------------------------------
 *
 * @author TriYop
 */
public class TestEmailProcessor {

    @Test
    public void testProcess_nullValues() {
        try {
            EmailProcessor.process(null, null, null);
            fail("test should have thrown an IncompleteEmailException.");
        } catch (IncompleteEmailException e) {
            assert true;
        }

        try {
            EmailProcessor.process("", "", null);
            fail("test should have thrown an IncompleteEmailException.");
        } catch (IncompleteEmailException e) {
            assert true;
        }

        try {
            EmailProcessor.process(null, "", new ByteArrayInputStream(new byte[0]));
            fail("test should have thrown an IncompleteEmailException.");
        } catch (IncompleteEmailException e) {
            assert true;
        }

        try {
            EmailProcessor.process("", null, new ByteArrayInputStream(new byte[0]));
            fail("test should have thrown an IncompleteEmailException.");
        } catch (IncompleteEmailException e) {
            assert true;
        }

        assert true;
    }

    @Test
    public void testProcess_emptyValues() {

        try {
            MailStore store = MailStoreFactory.getMailStore(FSMailStore.class.getCanonicalName());
            EmailProcessor.setStore(store);

            EmailProcessor.process("", "", new ByteArrayInputStream(new byte[0]));
            assert true;
        } catch (InvalidStoreException e) {
            fail("Store management seems not to be ready to be used");
        } catch (IncompleteEmailException e) {
            fail("No exception should have been thrown.");
        }


    }

    // test getter and setter at once.
    @Test
    public void testSetGetStore() {
        try {
            MailStore store = MailStoreFactory.getMailStore(FSMailStore.class.getCanonicalName());
            EmailProcessor.setStore(store);

            MailStore store2 = EmailProcessor.getStore();
            assertEquals(store, store2);
        } catch (InvalidStoreException e) {
            fail("Store management is not ready to be used");
        }
    }

    // Test getStringFromStream method
    @Test
    public void testGetStringFromStream_nominal() {
        //InputStream stream =
        EmailProcessor.getStringFromStream(new ByteArrayInputStream("This is my test string.".getBytes()));
    }

    @Test
    public void testGetStringFromStream_emptyString() {
        //InputStream stream =
        EmailProcessor.getStringFromStream(new ByteArrayInputStream(new byte[0]));
    }


    // Test  forparseMessageSubject method
    @Test
    public void testParseMessageSubject_emptyMesage() {
        assertEquals("", EmailProcessor.parseMessageSubject(""));
    }

    @Test
    public void testParseMessageSubject_noSubjectMesage() {
        assertEquals("", EmailProcessor.parseMessageSubject("This is a message\nwithout subject\nwithout subject:"));
    }

    @Test
    public void testParseMessageSubject_subjectOnly() {
        assertEquals("this is my subject", EmailProcessor.parseMessageSubject("Subject: this is my subject"));
    }

    @Test
    public void testParseMessageSubject_nominal() {
        assertEquals("this is my subject", EmailProcessor.parseMessageSubject("something:\nSubject: this is my subject\n\n and now, this is my message body"));
    }

    @Test
    public void testParseMessageSubject_subjectInBody() {
        // Check that subject is parsed only in message headers and not in message body
        assertEquals("", EmailProcessor.parseMessageSubject("something:\n\n\nTest body\nSubject: this is my subject\n\n and now, this is my message body"));
    }


}
