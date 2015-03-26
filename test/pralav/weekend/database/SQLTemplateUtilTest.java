package pralav.weekend.database;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;

import freemarker.template.TemplateException;

public class SQLTemplateUtilTest {
    @Test
    public void testSQLTemplateUtil() throws IOException, TemplateException {
        SQLTemplateUtil stu = new SQLTemplateUtil("sql");

        System.out.println(stu.getSQLFromTemplate(new HashMap<String, Object>(), "project.sql"));
    }
}
