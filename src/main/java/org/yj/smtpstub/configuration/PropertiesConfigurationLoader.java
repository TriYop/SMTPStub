package org.yj.smtpstub.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Set;

/**
 * SMTPStub
 * --------------------------------------------------------
 * Contains and returns some project-specific configuration variables.
 *
 * @author TriYop
 * @since 1.0
 */
public class PropertiesConfigurationLoader implements ConfigurationLoader {

    /**
     * logs events in a dedicated stream
     */
    private static final Logger logger = LoggerFactory.getLogger(PropertiesConfigurationLoader.class);

    private String configurationFile;

    public PropertiesConfigurationLoader(String configFile) {
        // check if provided config file exists then set property
        Path path = Paths.get(configFile).normalize();
        if (Files.isRegularFile(path) && Files.isReadable(path)) {
            configurationFile = path.toString();
        }
    }


    /**
     * Initializes
     */
    public synchronized void load(Configuration conf) {
        Properties props = new Properties();
        logger.info("Loading configuration from " + this.configurationFile);
        try (InputStream inputStream = new FileInputStream(this.configurationFile)) {
            props.load(inputStream);
        } catch (IOException e) {
            logger.error("IO Exception: ", e);
        }
        Set<String> keys = props.stringPropertyNames();
        for (String key : keys) {
            String value = props.getProperty(key);
            conf.set(key, value);
        }
    }


}
