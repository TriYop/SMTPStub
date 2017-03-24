package org.yj.smtpstub.storage;

import org.junit.*;
import org.yj.smtpstub.exception.IncompleteEmailException;
import org.yj.smtpstub.exception.InvalidStoreException;
import org.yj.smtpstub.model.EmailModel;

import java.util.Collection;
import static org.junit.Assert.*;

/**
 * SMTPStub
 * --------------------------------------------
 *
 * @author TriYop
 */
public class TestMailStoreFactory {
    @Before
    public void setup() {}

    @After
    public void tearOff() {}

    @Test
    public void testGetMailStore_validType() {
        String type= FSMailStore.class.getCanonicalName();
        try {
            Object obj= MailStoreFactory.getMailStore(type);
            assert FSMailStore.class.equals(obj.getClass());
        } catch (InvalidStoreException ex) {
            Assert.fail("Invalid class returned");
        }

    }

    @Test
    public void testGetMailStore_invalidInheritedType() {
        String type = String.class.getCanonicalName();
        try {
            Object obj = MailStoreFactory.getMailStore(type);
            Assert.fail("should have thrown an exception");
        } catch (InvalidStoreException ex) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testGetMailStore_invalidType() {
        String type = "InvalidType";
        try {
            Object obj = MailStoreFactory.getMailStore(type);
            Assert.fail("should have thrown an exception");
        } catch (InvalidStoreException ex) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testGetMailStore_nullType() {
        String type= null;
        try {
            Object obj= MailStoreFactory.getMailStore(type);
            Assert.fail("should have thrown an exception");
        } catch (InvalidStoreException ex) {
            Assert.assertTrue(true);
        }
    }




}
