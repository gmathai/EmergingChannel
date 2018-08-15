package com.qantas.api.fbchatbot.datasetup.Intents.intentlistresponse;

import lombok.Data;

import java.util.List;

@Data
public class ParametersFinal {
    private int parameterCount;

    private List<String> id;
    private List<Boolean> isList;
    private List<String> dataType;
    private List<String> name;
    private List<String> value;
    private List<Boolean> required;
    private List<List<String>> prompts;

}
