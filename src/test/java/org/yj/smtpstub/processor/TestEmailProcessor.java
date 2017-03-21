package org.yj.smtpstub.processor;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

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


}
