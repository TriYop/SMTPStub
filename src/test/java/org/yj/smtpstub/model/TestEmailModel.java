package org.yj.smtpstub.model;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;
/**
 * SMTPStub
 * --------------------------------------------
 *
 * @author TriYop
 */
public class TestEmailModel {

    EmailModel model;

    @Before
    public void setup() {
        model = new EmailModel();

    }

    @Test
    public void testReceiveDate() {
        //tests both setter and getter at once
        assertEquals(null, model.getReceivedDate());
        Date now = new Date();
        model.setReceivedDate(now);
        assertEquals(now, model.getReceivedDate());
    }


}
