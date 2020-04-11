package com.housie.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

@Service
public class ApplicationProperties {

    private static Logger logger = LoggerFactory.getLogger(ApplicationProperties.class);

    private static Properties loadProps = null;
    private boolean isLoaded = false;


    public Object getProperty(String propertyName, String defaultValue) {

        if (!isLoaded)
            load();

        String result = System.getProperty(propertyName);
        if (result == null) {
            result = defaultValue;
            if (result != null)
                System.setProperty(propertyName, result);
        }
        return result;
    }

    public Object getProperty(String propertyName) {
        return getProperty(propertyName, null);
    }

    private void load() {

        if (loadProps != null) {

            Iterator<?> names = loadProps.keySet().iterator();
            while (names.hasNext()) {
                String name = (String) names.next();
                System.clearProperty(name);
            }
        }
        logger.debug("***************************Loading properties*********************");

        loadProps = new Properties();
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("application.properties");
            loadProps.load(inputStream);

            Enumeration<?> names = loadProps.propertyNames();
            while (names.hasMoreElements()) {
                String key = (String) names.nextElement();
                System.setProperty(key, loadProps.getProperty(key));
                logger.debug("Loading key " + key + ", value:" + loadProps.getProperty(key));
            }
        } catch (IOException ioe) {
            logger.error("IO Error while loading properties", ioe);
        }

        isLoaded = true;
    }
}
