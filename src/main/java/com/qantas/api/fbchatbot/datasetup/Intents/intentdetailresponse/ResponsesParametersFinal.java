package com.qantas.api.fbchatbot.datasetup.Intents.intentdetailresponse;

import java.util.List;

@lombok.Data
public class ResponsesParametersFinal {

    private List<String> id;
    private List<Boolean> required;
    private List<String> dataType;
    private List<String> name;
    private List<Boolean> isList;
    private List<String> value;
    private List<String> prompts;

}
