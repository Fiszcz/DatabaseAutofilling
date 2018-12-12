package pl.auto.database;

import org.junit.Assert;
import org.junit.Test;

public class Tests {

    @Test
    public void testJoin() {
        String[] parts = new String[]{"id", "name", "surname"};
        String delim = ",";
        String expected = "id,name,surname";
        String joined = OperationThread.join(parts, delim);

        Assert.assertEquals(expected, joined);
    }

    @Test
    public void testJoinNullDelim() {
        String[] parts = new String[]{"id", "name", "surname"};
        String delim = null;
        String expected = "idnamesurname";
        String joined = OperationThread.join(parts, delim);

        Assert.assertEquals(expected, joined);
    }
}