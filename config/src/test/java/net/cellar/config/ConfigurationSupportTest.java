package net.cellar.config;

import org.junit.Assert;
import org.junit.Test;

import java.util.Dictionary;
import java.util.Properties;

/**
 * @author iocanel
 */
public class ConfigurationSupportTest {

    ConfigurationSupport support = new ConfigurationSupport();

    @Test
    public void testFilterDictionary() {
        Dictionary result = null;
        Dictionary source = new Properties();
        Dictionary expectedResult = new Properties();

        source.put("key1", "value1");
        source.put("key2", "value2");

        expectedResult.put("key1", "value1");
        expectedResult.put("key2", "value2");
        result = support.filterDictionary(source);

        source.put("service.pid", "value3");
        result = support.filterDictionary(source);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testConvertStrings() throws Exception {
        String absolutePath = "/somewehre/karaf/etc";
        String home = "/somewehre/karaf";
        String var = "${karaf.home}";

        String expectedResult = "${karaf.home}/etc";

        String result = support.convertStrings(absolutePath, home, var);
        Assert.assertEquals(expectedResult, result);
    }
}
