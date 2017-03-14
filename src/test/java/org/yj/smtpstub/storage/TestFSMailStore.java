package org.yj.smtpstub.storage;

import com.sun.corba.se.spi.orbutil.fsm.FSM;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;
/**
 * SMTPStub
 * --------------------------------------------
 *
 * @author TriYop
 */
public class TestFSMailStore {
    @Before
    public void setup() {
        FSMailStore store = new FSMailStore();

    }

    @After
    public void tearOff() {

    }

    @Test
    public void testGetUniqueFile_invalidPath() {
        try {
            File f = FSMailStore.getUniqueFile("w:\\toto");
            System.out.println(f.getCanonicalPath());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown: "+ e.getMessage());
        }
    }

    @Test
    @Ignore
    public void testGetUniqueFile_missingDir() {

    }

    @Test
    @Ignore
    public void testGetUniqueFile_withoutCollision() {

    }

    @Test
    @Ignore
    public void testGetUniqueFile_withCollision() {

    }

    @Test
    @Ignore
    public void testAddToIndex_emptyIndex() {

    }

    @Test
    @Ignore
    public void testAddToIndex_nominal() {

    }

    @Test
    @Ignore
    public void testSave() {

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
    @Ignore
    public void testGetEmail() {

    }
}
