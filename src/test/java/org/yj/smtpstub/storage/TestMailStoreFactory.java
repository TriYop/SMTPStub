package org.yj.smtpstub.storage;

import org.junit.Test;
import org.yj.smtpstub.exception.InvalidStoreException;

/**
 * SMTPStub
 * --------------------------------------------
 *
 * @author TriYop
 */
public class TestMailStoreFactory {

    @Test
    public void testGetMailStoreValidType() throws InvalidStoreException {
        String type = FSMailStore.class.getCanonicalName();
        Object obj = MailStoreFactory.getMailStore(type);
        assert FSMailStore.class.equals(obj.getClass());
    }

    @Test(expected = InvalidStoreException.class)
    public void testGetMailStoreInvalidInheritedType() throws InvalidStoreException {
        String type = String.class.getCanonicalName();
        MailStoreFactory.getMailStore(type);

    }

    @Test(expected = InvalidStoreException.class)
    public void testGetMailStoreInvalidType() throws InvalidStoreException {
        String type = "InvalidType";
        MailStoreFactory.getMailStore(type);
    }

    @Test(expected = InvalidStoreException.class)
    public void testGetMailStoreNullType() throws InvalidStoreException {
        String type = null;
        MailStoreFactory.getMailStore(type);
    }


}
