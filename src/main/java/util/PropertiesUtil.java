package util;

import java.io.IOException;
import java.util.Properties;

/**
 * Classe permettant d'acceder directement aux properties
 */
public class PropertiesUtil {
    private static Properties prop;

    static {
	prop = new Properties();

	try {
	    prop.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties"));
	} catch (IOException e) {
	    throw new RuntimeException(e.getCause());
	}
    }

    private PropertiesUtil() {
    }

    public static String getProperty(String key) {
	return prop.getProperty(key);
    }
}
