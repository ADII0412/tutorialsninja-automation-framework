package base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseAPI {
    protected Properties prop;
    protected final Logger logger = LogManager.getLogger(this.getClass());
    protected RequestSpecification requestSpec;

    @BeforeClass(alwaysRun = true)
    public void setupApi() {
        prop = initializeProperties();
        RestAssured.baseURI = prop.getProperty("apiBaseUrl");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }

    protected Properties initializeProperties() {
        Properties properties = new Properties();
        String env = System.getProperty("env", "default").trim().toLowerCase();
        String configFileName = switch (env) {
            case "staging" -> "config-staging.properties";
            case "prod" -> "config-prod.properties";
            case "default" -> "config.properties";
            default -> throw new IllegalArgumentException("Unsupported environment: " + env);
        };

        try (FileInputStream fis = new FileInputStream(
                System.getProperty("user.dir") + "/src/test/resources/config/" + configFileName
        )) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + configFileName, e);
        }

        if (System.getProperty("baseUrl") != null) {
            properties.setProperty("baseUrl", System.getProperty("baseUrl"));
        }
        if (System.getProperty("apiBaseUrl") != null) {
            properties.setProperty("apiBaseUrl", System.getProperty("apiBaseUrl"));
        }

        return properties;
    }
}
