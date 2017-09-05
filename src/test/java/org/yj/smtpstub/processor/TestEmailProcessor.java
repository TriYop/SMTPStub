package org.yj.smtpstub.processor;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yj.smtpstub.exception.IncompleteEmailException;
import org.yj.smtpstub.exception.InvalidStoreException;
import org.yj.smtpstub.storage.FSMailStore;
import org.yj.smtpstub.storage.MailStore;
import org.yj.smtpstub.storage.MailStoreFactory;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * SMTPStub
 * --------------------------------------------
 *
 * @author TriYop
 */
public class TestEmailProcessor {

    private static final Logger logger = LoggerFactory.getLogger(TestEmailProcessor.class);

    @Test(expected = IncompleteEmailException.class)
    public void testProcessAllNullValues() throws IncompleteEmailException {

        EmailProcessor.process(null, null, null);

    }

    @Test(expected = IncompleteEmailException.class)
    public void testProcessDataNullValues() throws IncompleteEmailException {
        EmailProcessor.process("", "", null);

    }

    @Test(expected = IncompleteEmailException.class)
    public void testProcessFromNullValues() throws IncompleteEmailException {
        EmailProcessor.process(null, "", new ByteArrayInputStream(new byte[0]));

    }

    @Test(expected = IncompleteEmailException.class)
    public void testProcessToNullValues() throws IncompleteEmailException {
        EmailProcessor.process("", null, new ByteArrayInputStream(new byte[0]));
    }

    @Test
    public void testProcessEmptyValues() throws InvalidStoreException, IncompleteEmailException {
        MailStore store = MailStoreFactory.getMailStore(FSMailStore.class.getCanonicalName());
        assertNotNull(store);
        EmailProcessor.setStore(store);
        EmailProcessor.process("", "", new ByteArrayInputStream(new byte[0]));
    }

    // test getter and setter at once.
    @Test
    public void testSetGetStore() throws InvalidStoreException {
        MailStore store = MailStoreFactory.getMailStore(FSMailStore.class.getCanonicalName());
        EmailProcessor.setStore(store);

        MailStore store2 = EmailProcessor.getStore();
        assertEquals(store, store2);
    }

    // Test getStringFromStream method
    @Test
    public void testGetStringFromStreamNominal() {
        String expected = "This is my test string.";
        logger.info("Expected String: \"{}\"", expected);
        //FIXME
        String result = EmailProcessor.getStringFromStream(new ByteArrayInputStream(expected.getBytes()));
        logger.info("Returned string: \"{}\"", result);
        assertEquals(expected, result);
    }

    @Test
    public void testGetStringFromStreamEmptyString() {
        String result = EmailProcessor.getStringFromStream(new ByteArrayInputStream(new byte[0]));
        assertEquals("", result);
    }


    // Test  forparseMessageSubject method
    @Test
    public void testParseMessageSubjectEmptyMesage() {
        assertEquals("", EmailProcessor.parseMessageSubject(""));
    }

    @Test
    public void testParseMessageSubjectNoSubjectMesage() {
        assertEquals("", EmailProcessor.parseMessageSubject("This is a message\nwithout subject\nwithout subject:"));
    }

    @Test
    public void testParseMessageSubjectSubjectOnly() {
        assertEquals("this is my subject", EmailProcessor.parseMessageSubject("Subject: this is my subject"));
    }

    @Test
    public void testParseMessageSubjectNominal() {
        assertEquals("this is my subject", EmailProcessor.parseMessageSubject("something:\nSubject: this is my subject\n\n and now, this is my message body"));
    }

    @Test
    public void testParseMessageSubjectSubjectInBody() {
        // Check that subject is parsed only in message headers and not in message body
        assertEquals("", EmailProcessor.parseMessageSubject("something:\n\n\nTest body\nSubject: this is my subject\n\n and now, this is my message body"));
    }


}
