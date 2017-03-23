package org.yj.smtpstub.processor;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

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




}
