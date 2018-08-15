package com.qantas.api.fbchatbot.datasetup.Intents.intentdetailresponse;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@lombok.Data
@Getter
@Setter
public class Responses {
    private boolean resetContexts;
    private String action;
    private List<ResponsesParameters> parameters;
    private List<AffectedContexts> affectedContexts;
    private List<ResponsesMessages> messages;

    private String[] speech; // No data available
    private String defaultResponsePlatforms; // No data available

    private List<ResponsesMessagesPayloadMessages> responsesMessagesPayloadMessages;




}
