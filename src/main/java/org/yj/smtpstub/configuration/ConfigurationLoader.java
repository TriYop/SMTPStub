package org.yj.smtpstub.configuration;

public interface ConfigurationLoader {
    /**
     * Loads configuration data from its source into configuration
     *
     * @param conf
     */
    public void load(Configuration conf);
}
