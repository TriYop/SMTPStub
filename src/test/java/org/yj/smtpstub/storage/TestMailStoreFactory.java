package org.yj.smtpstub.storage;

import org.junit.*;
import org.yj.smtpstub.exception.InvalidStoreException;

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
            assert FSMailStore.class.isAssignableFrom(obj.getClass());
        } catch (InvalidStoreException ex) {
            Assert.fail("Invalid class returned");
        }

    }

    @Test
    public void testGetMailStore_invalidType() {
        String type= String.class.getCanonicalName();
        try {
            Object obj= MailStoreFactory.getMailStore(type);
            Assert.fail("should have thrown an exception");
        } catch (InvalidStoreException ex) {
            Assert.assertTrue(true);
        }

        type= "InvalidType";
        try {
            Object obj= MailStoreFactory.getMailStore(type);
            Assert.fail("should have thrown an exception");
        } catch (InvalidStoreException ex) {
            Assert.assertTrue(true);
        }

        type= null;
        try {
            Object obj= MailStoreFactory.getMailStore(type);
            Assert.fail("should have thrown an exception");
        } catch (InvalidStoreException ex) {
            Assert.assertTrue(true);
        }

    }


}
