package org.skopintsev.database;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
@Getter
public class DatabaseConfig {

    private static final String DEFAULT_CONFIG_FILE = "database.properties";

    private final String url;
    private final String username;
    private final String password;
    private final String schema;
    private final String driverClassName;
    private final int connectionTimeout;
    private final int maxPoolSize;
    private final boolean useSSL;

    private static DatabaseConfig instance;



    // Private constructor for Singleton pattern
    private DatabaseConfig() {
        Properties props = loadProperties();

        // Order of precedence: Properties file > Default values
        this.url = getValue(props, "db.url", "jdbc:postgresql://localhost:5432/first_city_bank");
        this.username = getValue(props, "db.username", "postgres");
        this.password = getValue(props, "db.password", "");
        this.schema = getValue(props, "db.schema", "public");
        this.driverClassName = getValue(props, "db.driver", "org.postgresql.Driver");
        this.connectionTimeout = Integer.parseInt(getValue(props, "db.connection.timeout", "30"));
        this.maxPoolSize = Integer.parseInt(getValue(props, "db.pool.max.size", "10"));
        this.useSSL = Boolean.parseBoolean(getValue(props, "db.use.ssl", "false"));

        logConfiguration();
    }

    /**
     * Get singleton instance
     */
    public static synchronized DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }

    /**
     * Load properties from file and environment variables
     */
    private Properties loadProperties() {
        Properties props = new Properties();

        // First, try to load from classpath
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(DEFAULT_CONFIG_FILE)) {
            if (input != null) {
                props.load(input);
                log.info("Loaded database configuration from {}", DEFAULT_CONFIG_FILE);
            } else {
                log.warn("Configuration file {} not found in classpath", DEFAULT_CONFIG_FILE);
            }
        } catch (IOException e) {
            log.warn("Could not load configuration file {}", DEFAULT_CONFIG_FILE, e);
        }

        return props;
    }


    /**
     * Get value with fallback logic
     */
    private String getValue(Properties props, String key, String defaultValue) {
        // Check system property first (command line override)
        String systemProp = System.getProperty(key);
        if (systemProp != null && !systemProp.trim().isEmpty()) {
            log.debug("Using system property for {}: {}", key, maskPassword(key, systemProp));
            return systemProp;
        }

        // Check properties file
        String propValue = props.getProperty(key);
        if (propValue != null && !propValue.trim().isEmpty()) {
            log.debug("Using property file value for {}: {}", key, maskPassword(key, propValue));
            return propValue;
        }

        // Use default
        log.debug("Using default value for {}: {}", key, maskPassword(key, defaultValue));
        return defaultValue;
    }

    /**
     * Mask passwords in logs
     */
    private String maskPassword(String key, String value) {
        if (key.contains("password") || key.contains("pwd")) {
            return "***MASKED***";
        }
        return value;
    }

    /**
     * Log configuration
     */
    private void logConfiguration() {
        log.info("=== Database Configuration ===");
        log.info("URL: {}", url);
        log.info("Username: {}", username);
        log.info("Schema: {}", schema);
        log.info("Driver: {}", driverClassName);
        log.info("Connection Timeout: {} seconds", connectionTimeout);
        log.info("Max Pool Size: {}", maxPoolSize);
        log.info("Use SSL: {}", useSSL);
        log.info("==============================");
    }
}
