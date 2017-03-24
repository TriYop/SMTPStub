package org.yj.smtpstub.processor;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;

/**
 * SMTPStub
 * --------------------------------------------
 *
 * @author TriYop
 */
public class TestEmailProcessor {

    @Before
    public void setup() {

    }

    @Test
    @Ignore
    public void testGetStore() {}

    @Test
    public void testProcess() {
        EmailProcessor.process(null, null, null);
        assert true;
    }

    @Test
    @Ignore
    public void testSetStore() {}

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
