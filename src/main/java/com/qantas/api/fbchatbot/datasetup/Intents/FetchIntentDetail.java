package com.qantas.api.fbchatbot.datasetup.Intents;


import com.qantas.api.fbchatbot.datasetup.FetchConfigProperties;
import com.qantas.api.fbchatbot.datasetup.Intents.intentdetailresponse.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class FetchIntentDetail {


    public List<IntentDetailResponseFinal> GetIntentsDetail(List<String> intentIdsList) {

        FetchConfigProperties fetchConfigPropertiesObj = new FetchConfigProperties();
        fetchConfigPropertiesObj = fetchConfigPropertiesObj.getConfigProperties();

        /////////////////////////////
        //////// CONNECTION /////////
        /////////////////////////////
        String endpopint = fetchConfigPropertiesObj.getEndpoint();
        String protocolValue = fetchConfigPropertiesObj.getDialogflowProtocolValue();  // Used for /intents and /entities endpoints.
        String developerAccesstoken = fetchConfigPropertiesObj.getDeveloperAccesstoken();  // Used for /intents and /entities endpoints.
//        String clientAccesstoken = fetchConfigPropertiesObj.getClientAccesstoken();  // Used for /query, /contexts, and /userEntities endpoints.


        List<IntentDetailResponseFinal> intentDetailResponseList = new ArrayList<IntentDetailResponseFinal>();

        for (int i=0;i<intentIdsList.size();i++){
//            System.out.println(intentIdsList.get(i));

            Response intentListResponse = given().
                    header("Authorization", "Bearer " + developerAccesstoken).
                    header("Content-Type", "application/json").
                    get(endpopint + "intents/"+/*"da0cd7a1-83d7-4af1-a4df-370184a8313a"*/intentIdsList.get(i)+"?v=" + protocolValue).
                    then().statusCode(200)./*log().all().*/extract().response();

            //////////////// FINAL ////////////
            String respons=intentListResponse.asString();
            JsonPath jsonPath = new JsonPath(respons);

            IntentDetailResponseFinal intentDetailResponseObj = new IntentDetailResponseFinal();
            intentDetailResponseObj.setId(jsonPath.getString("id"));
            intentDetailResponseObj.setParentId(jsonPath.getString("parentId"));
            intentDetailResponseObj.setRootParentId(jsonPath.getString("rootParentId"));
            intentDetailResponseObj.setName(jsonPath.getString("name"));
            intentDetailResponseObj.setAuto(jsonPath.getBoolean("auto"));
            intentDetailResponseObj.setPriority(jsonPath.getLong("priority"));
            intentDetailResponseObj.setLastUpdate(jsonPath.getLong("lastUpdate"));
            intentDetailResponseObj.setWebhookUsed(jsonPath.getBoolean("webhookUsed"));
            intentDetailResponseObj.setWebhookForSlotFilling(jsonPath.getBoolean("webhookForSlotFilling"));
            intentDetailResponseObj.setFallbackIntent(jsonPath.getBoolean("fallbackIntent"));
            intentDetailResponseObj.setContexts(jsonPath.getList("contexts"));

            //  Utterance node
            UserSaysFinal userSaysObj = new UserSaysFinal();
            userSaysObj.setId(jsonPath.getList("userSays.id"));
            userSaysObj.setIsTemplate(jsonPath.getList("userSays.isTemplate"));
            userSaysObj.setCount(jsonPath.getList("userSays.count"));
            userSaysObj.setUpdated(jsonPath.getList("userSays.updated"));
            userSaysObj.setIsAuto(jsonPath.getList("userSays.isAuto"));
                DataFinal dataFinalObj = new DataFinal();
                JSONObject object = new JSONObject(intentListResponse.asString());
                JSONArray tempArray = object.getJSONArray("userSays");
                List<String> utteranceList = new ArrayList<>();
                for(int k = 0; k < tempArray.length(); k++){
                    JSONObject obj = tempArray.getJSONObject(k);
                    try{
                        JSONArray tempArray2 = obj.getJSONArray("data");
                        List<String> concatList = new ArrayList<>();
                        for(int j=0; j<tempArray2.length();j++){
                            JSONObject obj2 = tempArray2.getJSONObject(j);
                            try{
                                concatList.add(obj2.getString("text"));
                            } catch (Exception e){
                                continue;
                            }
                        }
                        utteranceList.add(String.join("", concatList));
                    }catch(Exception e){
                        continue;
                    }
                }
                dataFinalObj.setText(utteranceList);
            userSaysObj.setData(dataFinalObj);
            intentDetailResponseObj.setUserSays(userSaysObj);

            // responses node
            ResponsesFinal responses = new ResponsesFinal();
            responses.setResetContexts(jsonPath.getList("responses.resetContexts"));
            responses.setAction(jsonPath.getList("responses.action"));

                AffectedContextsFinal affectedContexts = new AffectedContextsFinal();
                affectedContexts.setName(jsonPath.getList("responses.affectedContexts.name"));
                affectedContexts.setLifespan(jsonPath.getList("responses.affectedContexts.lifespan"));
            responses.setAffectedContexts(affectedContexts);

                ResponsesParametersFinal responseParameters = new ResponsesParametersFinal();
                responseParameters.setId(jsonPath.getList("responses.parameters.id"));
                responseParameters.setRequired(jsonPath.getList("responses.parameters.required"));
                responseParameters.setDataType(jsonPath.getList("responses.parameters.dataType"));
                responseParameters.setName(jsonPath.getList("responses.parameters.name"));
                responseParameters.setValue(jsonPath.getList("responses.parameters.value"));
                responseParameters.setIsList(jsonPath.getList("responses.parameters.isList"));
                responseParameters.setPrompts(jsonPath.getList("responses.parameters.prompts"));
            responses.setResponseParameters(responseParameters);

            // responses node - message array
            JSONObject tempObj = new JSONObject(intentListResponse.asString());
            JSONArray responsesArray = tempObj.getJSONArray("responses");
            ArrayList<ResponsesMessagesFinal> responsesArrayList = new ArrayList<>();
            for(int m = 0; m < responsesArray.length(); m++){
                try{
                    ResponsesMessagesFinal responsesObj = new ResponsesMessagesFinal();
                    JSONArray msgArray = responsesArray.getJSONObject(m).getJSONArray("messages");
                    if(msgArray.length()==0){
//                        System.out.println("Disruption");
                        continue;
                    }else{
                        for(int n=0; n<msgArray.length();n++){
                            JSONObject msgObj =msgArray.getJSONObject(n);
                            List<ResponsesMessagesPayloadMessages> singleUtteranceResponsesMessagesList = new ArrayList<>();

                            if(msgObj.getInt("type")==0){
                                JSONArray speechArray = msgObj.getJSONArray("speech");
                                List<String> speechArrayFinal = new ArrayList<>();
                                ResponsesMessagesPayloadMessages responsesMessagesPayloadMessagesObj = new ResponsesMessagesPayloadMessages();

                                for(int x=0;x<speechArray.length();x++){
                                    speechArrayFinal.add(speechArray.get(x).toString().replace(System.lineSeparator(),""));
                                }
                                responsesMessagesPayloadMessagesObj.setSpeechArray(speechArrayFinal);
                                singleUtteranceResponsesMessagesList.add(responsesMessagesPayloadMessagesObj);
                            }
                            if(msgObj.getInt("type")==4){
                                JSONArray payloadmsgArray = msgArray.getJSONObject(n).getJSONObject("payload").getJSONArray("messages");
                                for(int j=0;j<payloadmsgArray.length();j++){
                                    ResponsesMessagesPayloadMessages responsesMessagesPayloadMessagesObj = new ResponsesMessagesPayloadMessages();
                                    JSONObject obj = payloadmsgArray.getJSONObject(j);
                                    responsesMessagesPayloadMessagesObj.setPlatform(obj.getString("platform"));
                                    responsesMessagesPayloadMessagesObj.setType(obj.getInt("type"));
                                    String platform =obj.getString("platform");
                                    int type =obj.getInt("type");
                                    if(type==0){
                                        responsesMessagesPayloadMessagesObj.setSpeech(obj.getString("speech"));
//                              System.out.println("Here 0 : "+obj.getString("senderaction"));
                                        responsesMessagesPayloadMessagesObj.setReplies(null);
                                        responsesMessagesPayloadMessagesObj.setTitle(null);
                                        responsesMessagesPayloadMessagesObj.setItems(null);
                                        responsesMessagesPayloadMessagesObj.setButtons(null);
                                    }else if(type==2){
                                        responsesMessagesPayloadMessagesObj.setTitle(obj.getString("title"));
                                        JSONArray repliesArray = obj.getJSONArray("replies");
                                        List <ResponsesMessagesPayloadMessagesReplies> repliesList = new ArrayList<>();
                                        for(int k=0;k<repliesArray.length();k++){
                                            ResponsesMessagesPayloadMessagesReplies repliesObj = new ResponsesMessagesPayloadMessagesReplies();
                                            repliesObj.setText(repliesArray.getJSONObject(k).getString("text"));
//                                            System.out.println("Here 2 : "+repliesArray.getJSONObject(k).getString("text"));
                                            repliesObj.setUrl(repliesArray.getJSONObject(k).getString("url"));
                                            repliesList.add(repliesObj);
                                        }
                                        responsesMessagesPayloadMessagesObj.setReplies(repliesList);
                                        responsesMessagesPayloadMessagesObj.setItems(null);
                                        responsesMessagesPayloadMessagesObj.setButtons(null);
                                        responsesMessagesPayloadMessagesObj.setSpeech(null);
                                        responsesMessagesPayloadMessagesObj.setSenderaction(null);
                                    }
                                    else if(type==4){
                                        JSONArray itemsArray = obj.getJSONArray("items");
                                        List<ResponsesMessagesPayloadMessagesCarousalItems> carousalItemsList = new ArrayList<>();
                                        for(int k=0;k<itemsArray.length();k++){
                                            ResponsesMessagesPayloadMessagesCarousalItems carousalItemsObj = new ResponsesMessagesPayloadMessagesCarousalItems();
                                            carousalItemsObj.setTitle(itemsArray.getJSONObject(k).getString("title"));
//                                System.out.println("Here 4: "+itemsArray.getJSONObject(k).getString("title"));
                                            carousalItemsObj.setDescription(itemsArray.getJSONObject(k).getString("description"));
                                            carousalItemsObj.setButtonPostback(itemsArray.getJSONObject(k).getJSONObject("button").getString("postback"));
                                            carousalItemsObj.setButtonText(itemsArray.getJSONObject(k).getJSONObject("button").getString("text"));
                                            carousalItemsObj.setButtonType(itemsArray.getJSONObject(k).getJSONObject("button").getInt("type"));
                                            carousalItemsList.add(carousalItemsObj);
                                        }
                                        responsesMessagesPayloadMessagesObj.setItems(carousalItemsList);
                                        responsesMessagesPayloadMessagesObj.setButtons(null);
                                        responsesMessagesPayloadMessagesObj.setSpeech(null);
                                        responsesMessagesPayloadMessagesObj.setSenderaction(null);
                                        responsesMessagesPayloadMessagesObj.setReplies(null);
                                        responsesMessagesPayloadMessagesObj.setTitle(null);
                                    }
                                    else if(type==5){
                                        responsesMessagesPayloadMessagesObj.setSpeech(obj.getString("speech"));
                                        JSONArray buttonsArray = obj.getJSONArray("buttons");
                                        List <ResponsesMessagesPayloadMessagesButtons> buttonList = new ArrayList<>();
                                        for(int k=0;k<buttonsArray.length();k++){
                                            ResponsesMessagesPayloadMessagesButtons buttonsObj = new ResponsesMessagesPayloadMessagesButtons();
                                            buttonsObj.setPostback(buttonsArray.getJSONObject(k).getString("postback"));
//                                System.out.println("Here 5 : "+buttonsArray.getJSONObject(k).getString("postback"));
                                            buttonsObj.setText(buttonsArray.getJSONObject(k).getString("text"));
                                            buttonsObj.setType(buttonsArray.getJSONObject(k).getInt("type"));
                                            buttonList.add(buttonsObj);
                                        }
                                        responsesMessagesPayloadMessagesObj.setButtons(buttonList);
                                        responsesMessagesPayloadMessagesObj.setItems(null);
                                        responsesMessagesPayloadMessagesObj.setSenderaction(null);
                                        responsesMessagesPayloadMessagesObj.setReplies(null);
                                        responsesMessagesPayloadMessagesObj.setTitle(null);
                                    }
                                    else if(type==6){
                                        responsesMessagesPayloadMessagesObj.setSenderaction(obj.getString("senderaction"));
//                            System.out.println("Here 6: "+obj.getString("senderaction"));
                                        responsesMessagesPayloadMessagesObj.setItems(null);
                                        responsesMessagesPayloadMessagesObj.setButtons(null);
                                        responsesMessagesPayloadMessagesObj.setSpeech(null);
                                        responsesMessagesPayloadMessagesObj.setReplies(null);
                                        responsesMessagesPayloadMessagesObj.setTitle(null);
                                    }
                                    singleUtteranceResponsesMessagesList.add(responsesMessagesPayloadMessagesObj);
                                }
                            }
                        responsesObj.setResponsesMessagesPayloadMessages(singleUtteranceResponsesMessagesList);
                        }
                    responsesArrayList.add(responsesObj);
                    }
                }catch(Exception e){
                    System.out.println("Elements not thereeeee : "+intentIdsList.get(i));
                    e.printStackTrace();
                }
            }
            responses.setResponseMessages(responsesArrayList);
            intentDetailResponseObj.setResponses(responses);


//            intentDetailResponseObj.setResponses(responsesArrayList);


            List<String> testList = new ArrayList<String>();
                /*testList.add(jsonPath.getString("responses.messages.payload.messages.buttons.text"));
                    System.out.println("testList : "+testList.size());
                if((testList.size()>0)){
                    System.out.println((i+1)+" - Test Here : "+testList);
                }*/



/*

                responsesMessagesPayloadMessages.setButtons(buttons);
                    ResponsesMessagesPayloadMessagesCarousalItems items = new ResponsesMessagesPayloadMessagesCarousalItems();
                    items.setDescription(jsonPath.getList("responses.messages.payload.messages.items.description"));
                    items.setTitle(jsonPath.getList("responses.messages.payload.messages.items.title"));
                        ResponsesMessagesPayloadMessagesCarousalItemsButton itemsButton = new ResponsesMessagesPayloadMessagesCarousalItemsButton();
                        itemsButton.setPostback(jsonPath.getList("responses.messages.payload.messages.items.button.postback"));
                        itemsButton.setText(jsonPath.getList("responses.messages.payload.messages.items.button.text"));
                        itemsButton.setType(jsonPath.getList("responses.messages.payload.messages.items.button.type"));
                    items.setItemsButton(itemsButton);
                responsesMessagesPayloadMessages.setItems(items);
                    ResponsesMessagesPayloadMessagesReplies replies = new ResponsesMessagesPayloadMessagesReplies();
                    replies.setText(jsonPath.getList("responses.messages.payload.messages.replies.text")); ////
                    replies.setUrl(jsonPath.getList("responses.messages.payload.messages.replies.url")); /////
                responsesMessagesPayloadMessages.setReplies(replies);
            responsesMessagesFinal.setResponsesMessagesPayloadMessages(responsesMessagesPayloadMessages);
            responsesMessagesFinal.setLang(jsonPath.getList("responses.messages.lang"));
            responses.setResponseMessages(responsesMessagesFinal);

            intentDetailResponseObj.setResponses(responses);*/
            intentDetailResponseList.add(intentDetailResponseObj);

        }








        ///////////////////////////////////







        /*ArrayList<IntentDetailResponse> intentDetailResponseArrayList = new ArrayList<IntentDetailResponse>();
        for(int m=0;m<intentIdsArrayList.size();m++){
            Response response = given().
                    header("Authorization", "Bearer " + developerAccesstoken).
                    header("Content-Type", "application/json").
                    get(endpopint + "intents/"+intentIdsArrayList.get(m).getId()+"?v=" + protocolValue).
                    then().statusCode(200).*//*log().all().*//*extract().response();

            IntentDetailResponse intentDetailResponseObjss = new IntentDetailResponse();

            try{
                intentDetailResponseObj.setId(response.jsonPath().getString("id"));
                System.out.println("Intent IDs : "+response.jsonPath().getString("id"));
                intentDetailResponseObj.setName(response.jsonPath().getString("name"));
                intentDetailResponseObj.setAuto(response.jsonPath().getBoolean("auto"));
                intentDetailResponseObj.setFallbackIntent(response.jsonPath().getBoolean("fallbackIntent"));
                intentDetailResponseObj.setWebhookUsed(response.jsonPath().getBoolean("webhookUsed"));
                intentDetailResponseObj.setLastUpdate(response.jsonPath().getLong("lastUpdate"));
                intentDetailResponseObj.setWebhookForSlotFilling(response.jsonPath().getBoolean("webhookForSlotFilling"));
                intentDetailResponseObj.setPriority(response.jsonPath().getLong("priority"));
            }catch(Exception e){
                System.out.println("Element not there");
            }


            // https://stackoverflow.com/questions/7634518/getting-jsonobject-from-jsonarray?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa




            // Usersays node
            JSONObject object = new JSONObject(response.asString());
            JSONArray tempArray = object.getJSONArray("userSays");
            ArrayList<UserSays> userSaysArrayList = new ArrayList<UserSays>();
            for(int i = 0; i < tempArray.length(); i++){
                UserSays userSaysObj = new UserSays();
                JSONObject obj = tempArray.getJSONObject(i);
                try{
                    userSaysObj.setId(obj.getString("id"));
                    userSaysObj.setTemplate(obj.getBoolean("isTemplate"));
                    userSaysObj.setCount(obj.getInt("count"));
                    userSaysObj.setUpdated(obj.getInt("updated"));
                    userSaysObj.setAuto(obj.getBoolean("isAuto"));

                    JSONArray tempArray2 = obj.getJSONArray("data");
                    ArrayList<Data> dataArrayList = new ArrayList<Data>();
                    for(int j=0; j<tempArray2.length();j++){
                        Data dataObj = new Data();
                        JSONObject obj2 = tempArray2.getJSONObject(j);
                        try{
                            dataObj.setText(obj2.getString("text"));
                            dataObj.setAlias(obj2.getString("alias"));
                            dataObj.setMeta(obj2.getString("meta"));
                            dataObj.setUserDefined(obj2.getBoolean("userDefined"));
                            dataArrayList.add(dataObj);
                        } catch (Exception e){
                            continue;
                        }
                    }
                    userSaysObj.setData(dataArrayList);
                }catch(Exception e){
                    continue;
                }


                userSaysArrayList.add(userSaysObj);
            }
            intentDetailResponseObj.setUserSays(userSaysArrayList);

            // responses node
            tempArray = object.getJSONArray("responses");
            ArrayList<Responses> responsesArrayList = new ArrayList<Responses>();
            for(int i = 0; i < tempArray.length(); i++){
                try{
                    Responses responsesObj = new Responses();
                    JSONObject obj = tempArray.getJSONObject(i);
                    responsesObj.setResetContexts(obj.getBoolean("resetContexts"));
                    responsesObj.setAction(obj.getString("action"));

                    JSONArray tempArray2 = obj.getJSONArray("affectedContexts");
                    ArrayList<AffectedContexts> affectedContextsArrayList = new ArrayList<AffectedContexts>();
                    for(int j=0;j<tempArray2.length();j++){
                        AffectedContexts affectedContextsObj = new AffectedContexts();
                        JSONObject obj2 = tempArray2.getJSONObject(j);
                        affectedContextsObj.setName(obj2.getString("name"));
                        affectedContextsArrayList.add(affectedContextsObj);
                    }
                    responsesObj.setAffectedContexts(affectedContextsArrayList);

                    tempArray2 = obj.getJSONArray("parameters");
                    ArrayList<com.qantas.api.fbchatbot.datasetup.Intents.intentdetailresponse.ResponsesParameters> parameterArrayList = new ArrayList<com.qantas.api.fbchatbot.datasetup.Intents.intentdetailresponse.ResponsesParameters>();
//                ArrayList<intentdetailresponse.ResponsesParameters> parameterArrayList = new ArrayList<intentdetailresponse.ResponsesParameters>();
                    for(int j=0;j<tempArray2.length();j++){
                        com.qantas.api.fbchatbot.datasetup.Intents.intentdetailresponse.ResponsesParameters parametersObj = new com.qantas.api.fbchatbot.datasetup.Intents.intentdetailresponse.ResponsesParameters();
                        JSONObject obj2 = tempArray2.getJSONObject(j);
                        parametersObj.setId(obj2.getString("id"));
                        parametersObj.setRequired(obj2.getBoolean("required"));
                        parametersObj.setDataType(obj2.getString("dataType"));
                        parametersObj.setName(obj2.getString("name"));
                        parametersObj.setValue(obj2.getString("value"));
                        parametersObj.setList(obj2.getBoolean("isList"));

                        ArrayList<String> promptsArrayList = new ArrayList<String>();
                        try {
                            JSONArray promptsArray = obj2.getJSONArray("prompts");
                            for(int k=0; k<promptsArray.length();k++){
                                promptsArrayList.add((String) promptsArray.get(k));
                            }
                            parametersObj.setPrompts(promptsArrayList);
                        }catch(Exception e){
                            continue;
                        }
                        parameterArrayList.add(parametersObj);
                    }
                    responsesObj.setParameters(parameterArrayList);

                    tempArray2 = obj.getJSONArray("messages");
                    ArrayList<ResponsesMessages> messagesArrayList = new ArrayList<ResponsesMessages>();
                    for(int j=0;j<tempArray2.length();j++){
                        ResponsesMessages messagesObj = new ResponsesMessages();
                        JSONObject obj2 = tempArray2.getJSONObject(j);

                        ArrayList<String> speechArrayList = new ArrayList<String>();
                        try {
                            JSONArray promptsArray = obj2.getJSONArray("speech");
                            for(int k=0; k<promptsArray.length();k++){
                                speechArrayList.add((String) promptsArray.get(k));
                            }
                            messagesObj.setSpeech(speechArrayList);
                        }catch(Exception e){
                            continue;
                        }
                        messagesArrayList.add(messagesObj);
                    }
                    responsesObj.setMessages(messagesArrayList);

                    responsesArrayList.add(responsesObj);

                } catch(Exception e){
                    System.out.println("Elements not thereeeee");
                }

            }

            /// Start here for utterance replies ********** REST ASSURED
            /*ResponsesMessagesFinal responsesMessagesFinal = new ResponsesMessagesFinal();
            responsesMessagesFinal.setType(jsonPath.getList("responses.messages.type"));
            *//*JsonPath temp = jsonPath.get("responses.messages.payload.messages");

            System.out.println("temp: "+temp);
//            System.out.println("temp.size() : "+temp.size());*//*
                ResponsesMessagesPayloadMessages responsesMessagesPayloadMessages = new ResponsesMessagesPayloadMessages();
                // Common
                responsesMessagesPayloadMessages.setPlatform(jsonPath.getList("responses.messages.payload.messages.platform"));
                responsesMessagesPayloadMessages.setType(jsonPath.getList("responses.messages.payload.messages.type")); ////
                    // Text - Type 0
                    ResponsesMessagesPayloadMessagesText text = new ResponsesMessagesPayloadMessagesText();
                    text.setTextSpeach(jsonPath.getString("responses.messages.payload.messages.speech"));
//                responsesMessagesPayloadMessages.set
                // Quick replies - Type 2
                ResponsesMessagesPayloadMessagesReplies replies = new ResponsesMessagesPayloadMessagesReplies();
                replies.setRepliesUrl(jsonPath.getList("responses.messages.payload.messages.replies.url"));
                replies.setRepliesTtext(jsonPath.getList("responses.messages.payload.messages.replies.text"));
                replies.setTitle(jsonPath.getString("responses.messages.payload.messages.title"));
                // Carousal - Type 4
                ResponsesMessagesPayloadMessagesCarousalItems carousalItems = new ResponsesMessagesPayloadMessagesCarousalItems();
                carousalItems.setDescription(jsonPath.getList("responses.messages.payload.messages.items.description"));
                carousalItems.setTitle(jsonPath.getList("responses.messages.payload.messages.items.title"));
                    List <ResponsesMessagesPayloadMessagesCarousalItemsButton> responsesMessagesPayloadMessagesCarousalItemsButtonList = new ArrayList<>();
                    ResponsesMessagesPayloadMessagesCarousalItemsButton carousalItemsButton = new ResponsesMessagesPayloadMessagesCarousalItemsButton();
                    carousalItemsButton.setPostback(jsonPath.getString("responses.messages.payload.messages.items.button.postback"));
                    carousalItemsButton.setPostback(jsonPath.getString("responses.messages.payload.messages.items.button.text"));
                    carousalItemsButton.setPostback(jsonPath.getString("responses.messages.payload.messages.items.button.type"));
                    responsesMessagesPayloadMessagesCarousalItemsButtonList.add(carousalItemsButton);
                carousalItems.setItemsButton(responsesMessagesPayloadMessagesCarousalItemsButtonList);
                // Button - Type 5
                ResponsesMessagesPayloadMessagesButtons buttons = new ResponsesMessagesPayloadMessagesButtons();
                buttons.setText(jsonPath.getList("responses.messages.payload.messages.buttons.text"));
                buttons.setPostback(jsonPath.getList("responses.messages.payload.messages.buttons.postback"));
                buttons.setType(jsonPath.getList("responses.messages.payload.messages.buttons.type"));
                buttons.setButtonSpeach(jsonPath.getString("responses.messages.payload.messages.speech"));
                // Sender Action - Type 6
                ResponsesMessagesPayloadMessagesSenderAction senderAction = new ResponsesMessagesPayloadMessagesSenderAction();
                senderAction.setSenderaction("responses.messages.payload.messages.senderaction");




                responsesMessagesPayloadMessages.setSenderaction(jsonPath.getList("responses.messages.payload.messages.senderaction"));
                responsesMessagesPayloadMessages.setSubtitle(jsonPath.getList("responses.messages.payload.messages.subtitle"));
                responsesMessagesPayloadMessages.setImageURL(jsonPath.getList("responses.messages.payload.messages.imageURL"));

            intentDetailResponseObj.setResponses(responsesArrayList);

            /// Rest of the json
            // EVENTS - No Data
            // CONTEXT - No Data
            // followUpIntents - No Data
            // templates - No Data
            // cortanaCommand - Not Required

            intentDetailResponseArrayList.add(intentDetailResponseObj);
        }
*/
        System.out.println("Done....");

        return intentDetailResponseList;

    }


}
