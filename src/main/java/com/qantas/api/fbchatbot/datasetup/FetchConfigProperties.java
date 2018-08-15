package com.qantas.api.fbchatbot.datasetup;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@lombok.Data
public class FetchConfigProperties {

    private String endpoint;
    private String dialogflowProtocolValue;
    private String developerAccesstoken;
    private String clientAccesstoken;

    public FetchConfigProperties getConfigProperties() {
        FetchConfigProperties fetchConfigPropertiesObj = new FetchConfigProperties();
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("config.properties");
            // load a properties file
            prop.load(input);
            // get the property value and print it out

            fetchConfigPropertiesObj.setEndpoint(prop.getProperty("endpopint"));
            fetchConfigPropertiesObj.setDialogflowProtocolValue(prop.getProperty("dialogflowProtocolValue"));
            fetchConfigPropertiesObj.setDeveloperAccesstoken(prop.getProperty("developerAccesstoken"));
            fetchConfigPropertiesObj.setClientAccesstoken(prop.getProperty("clientAccesstoken"));

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fetchConfigPropertiesObj;
    }
}
