package com.qantas.api.fbchatbot.datasetup.Intents.intentdetailresponse;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@lombok.Data
@Getter
@Setter
public class ResponsesFinal {
    private List<Boolean> resetContexts;
    private List<String> action;
    private ResponsesParametersFinal responseParameters;
    private AffectedContextsFinal affectedContexts;
    private List<ResponsesMessagesFinal> responseMessages; /// added newly

    private String[] speech; // No data available
    private String defaultResponsePlatforms; // No data available

    private List<ResponsesMessagesPayloadMessages> responsesMessagesPayloadMessages;/// added newly


}
