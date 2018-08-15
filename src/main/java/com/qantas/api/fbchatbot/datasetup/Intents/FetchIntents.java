package com.qantas.api.fbchatbot.datasetup.Intents;


import com.qantas.api.fbchatbot.datasetup.FetchConfigProperties;
import com.qantas.api.fbchatbot.datasetup.Intents.intentdetailresponse.IntentDetailResponseFinal;
import com.qantas.api.fbchatbot.datasetup.Intents.intentlistresponse.ContextOutFinal;
import com.qantas.api.fbchatbot.datasetup.Intents.intentlistresponse.EventsFinal;
import com.qantas.api.fbchatbot.datasetup.Intents.intentlistresponse.IntentListResponseFinal;
import com.qantas.api.fbchatbot.datasetup.Intents.intentlistresponse.ParametersFinal;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;


public class FetchIntents {


    public List<IntentDetailResponseFinal> getIntentListandDetails() {

        FetchConfigProperties fetchConfigPropertiesObj = new FetchConfigProperties();
        fetchConfigPropertiesObj = fetchConfigPropertiesObj.getConfigProperties();

        /////////////////////////////
        //////// CONNECTION /////////
        /////////////////////////////
        String endpopint = fetchConfigPropertiesObj.getEndpoint();;
        String protocolValue = fetchConfigPropertiesObj.getDialogflowProtocolValue();  // Used for /intents and /entities endpoints.
        String developerAccesstoken = fetchConfigPropertiesObj.getDeveloperAccesstoken();  // Used for /intents and /entities endpoints.
        String clientAccesstoken = fetchConfigPropertiesObj.getClientAccesstoken();  // Used for /query, /contexts, and /userEntities endpoints.

        /////////////////////////
        //////// GLOBAL /////////
        /////////////////////////
        Response intentListResponse = given().
                header("Authorization", "Bearer " + developerAccesstoken).
                header("Content-Type", "application/json").
                get(endpopint + "intents?v=" + protocolValue).
                then().statusCode(200)./*log().all().*/extract().response();

        //////////////// FINAL ////////////////
        String response=intentListResponse.asString();
        JsonPath jsonPath = new JsonPath(response);

        IntentListResponseFinal intentListObj = new IntentListResponseFinal();
        intentListObj.setIntentListCount(jsonPath.getList("id").size());
        intentListObj.setId(jsonPath.getList("id"));
        intentListObj.setName(jsonPath.getList("name"));
        /*System.out.println("Intents : "+jsonPath.getList("name"));
        System.out.println("Intent Count : "+jsonPath.getList("name").size());*/
        intentListObj.setParentId(jsonPath.getList("parentId"));
        intentListObj.setContextIn(jsonPath.getList("contextIn"));
        intentListObj.setPriority(jsonPath.getList("priority"));
        intentListObj.setFallbackIntent(jsonPath.getList("fallbackIntent"));
        intentListObj.setActions(jsonPath.getList("actions"));

        ParametersFinal parametersObj = new ParametersFinal();
        parametersObj.setId(jsonPath.getList("parameters.id"));
        parametersObj.setIsList(jsonPath.getList("parameters.isList"));
        parametersObj.setDataType(jsonPath.getList("parameters.dataType"));
        parametersObj.setName(jsonPath.getList("parameters.name"));
        parametersObj.setValue(jsonPath.getList("parameters.value"));
        parametersObj.setRequired(jsonPath.getList("parameters.required"));
        parametersObj.setPrompts(jsonPath.getList("parameters.prompts"));
        intentListObj.setParameters(parametersObj);

        EventsFinal eventObj = new EventsFinal();
        eventObj.setName(jsonPath.getList("events.name"));
        intentListObj.setEvents(eventObj);

        ContextOutFinal contextOutObj = new ContextOutFinal();
        contextOutObj.setName(jsonPath.getList("contextOut.name"));
        contextOutObj.setParameters(jsonPath.getList("contextOut.parameters"));
        contextOutObj.setLifespan(jsonPath.getList("contextOut.lifespan"));
        intentListObj.setContextOut(contextOutObj);

        FetchIntentDetail fetchIntentDetail = new FetchIntentDetail();
        List<IntentDetailResponseFinal> intentDetailResponsesList = new ArrayList<IntentDetailResponseFinal>();
        intentDetailResponsesList = fetchIntentDetail.GetIntentsDetail(intentListObj.getId());

        /*System.out.println("Size : "+intentDetailResponsesList.size());
        for (int i=0;i<intentDetailResponsesList.size();i++){
            if(intentDetailResponsesList.get(i).getName().toLowerCase().contains("FAQ - Unknown answer (fallback)".toLowerCase())){
                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                System.out.println("Intent Name : "+intentDetailResponsesList.get(i).getName());
                System.out.println("Intent ID : "+intentDetailResponsesList.get(i).getId());
                *//*String temp =intentDetailResponsesList.get(i).getUserSays().getData().getText().toString();
                temp = (temp.replace("]]","")).replace("[[","");
                String[] utterances = temp.split("], \\[");
                List<String> utterancesList = Arrays.asList(utterances);
                System.out.println("Utterance List : "+utterancesList);*//*
                System.out.println("Utterance List : "+intentDetailResponsesList.get(i).getUserSays().getData().getText());




            }*/
            /*System.out.println("Intent ID : "+intentDetailResponsesList.get(0).getId());
            System.out.println("Intent Name : "+intentDetailResponsesList.get(0).getName());

            String temp =intentDetailResponsesList.get(0).getUserSays().getData().getText().toString();
            temp = (temp.replace("]]","")).replace("[[","");
            String[] utterances = temp.split("], \\[");
            List<String> utterancesList = Arrays.asList(utterances);
            System.out.println("Utterance List : "+utterancesList);
        }*/

        ///////////////////////////////////////

        /*JSONArray intentListArray = new   JSONArray(intentListResponse.body().asString());

        ArrayList <IntentListResponse> intentList = new ArrayList<IntentListResponse>();

        for(int i=0; i<intentListArray.length();i++){
            IntentListResponse intentListObj = new IntentListResponse();

            JSONArray tempJsonArray;
            ArrayList<String> temp = new ArrayList<String>();


            JSONObject obj = intentListArray.getJSONObject(i);
            intentListObj.setId(obj.getString("id"));
            intentListObj.setName(obj.getString("name"));
            intentListObj.setPriority(obj.getLong("priority"));
            intentListObj.setFallbackIntent(obj.getBoolean("fallbackIntent"));

            tempJsonArray = obj.getJSONArray("actions");
            for(int j=0; j<tempJsonArray.length();j++){
                temp.add((String) tempJsonArray.get(j));
            }
            intentListObj.setActions(temp);

            tempJsonArray = obj.getJSONArray("contextIn");
            for(int j=0; j<tempJsonArray.length();j++){
                temp.add((String) tempJsonArray.get(j));
            }
            intentListObj.setContextIn(temp);

            tempJsonArray = obj.getJSONArray("events");
            ArrayList<Events> eventsTempObjList = new ArrayList<Events>();
            for (int j = 0; j < tempJsonArray.length(); j++) {
                Events eventObj = new Events();
                JSONObject eventsArrayObj = tempJsonArray.getJSONObject(j);
                try {
                    eventObj.setName(eventsArrayObj.getString("name"));
                    eventsTempObjList.add(eventObj);
                } catch (Exception e) {
                    continue;
                }
            }
            intentListObj.setEvents(eventsTempObjList);

            tempJsonArray = obj.getJSONArray("parameters");
            ArrayList<ResponsesParameters> parametersTempObjList = new ArrayList<ResponsesParameters>();
            for (int j = 0; j < tempJsonArray.length(); j++) {
                ResponsesParameters parametersObj = new ResponsesParameters();
                JSONObject parametersArrayObj = tempJsonArray.getJSONObject(j);
                try {
                    parametersObj.setId(parametersArrayObj.getString("id"));
//                    System.out.println("parameterId : "+parametersObj.getId());
                    parametersObj.setRequired(parametersArrayObj.getBoolean("required"));
//                    System.out.println("parameterRequired : "+parametersArrayObj.getBoolean("required"));
                    parametersObj.setDataType(parametersArrayObj.getString("dataType"));
//                    System.out.println("parameterDataType : "+parametersArrayObj.getString("dataType"));
                    parametersObj.setName(parametersArrayObj.getString("name"));
//                    System.out.println("parameterName : "+parametersArrayObj.getString("name"));
                    parametersObj.setValue(parametersArrayObj.getString("value"));
//                    System.out.println("parameterValue : "+parametersArrayObj.getString("value"));
                    parametersObj.setList(parametersArrayObj.getBoolean("isList"));
//                    System.out.println("parameterIsList : "+parametersArrayObj.getBoolean("isList"));

                    ArrayList<String> promptsArrayList = new ArrayList<String>();
                    try {
                        JSONArray promptsArray = parametersArrayObj.getJSONArray("prompts");
                        for(int k=0; k<promptsArray.length();k++){
                            promptsArrayList.add((String) promptsArray.get(k));
                        }
//                        System.out.println("promptsArrayList : "+promptsArrayList);
                        parametersObj.setPrompts(promptsArrayList);
                    }catch(Exception e){
                        continue;
                    }

                    parametersTempObjList.add(parametersObj);
                } catch (Exception e) {

                }
            }
            intentListObj.setParameters(parametersTempObjList);

            tempJsonArray = obj.getJSONArray("contextOut");
            ArrayList<ContextOut> contextOutTempObjList= new ArrayList<ContextOut>();
            for (int j = 0; j < tempJsonArray.length(); j++) {
                ContextOut contextOutObj = new ContextOut();
                JSONObject contextOutArrayObj = tempJsonArray.getJSONObject(j);
                try {
                    contextOutObj.setName(contextOutArrayObj.getString("name"));
                    contextOutObj.setLifespan(contextOutArrayObj.getInt("lifespan"));
                    contextOutTempObjList.add(contextOutObj);
                } catch (Exception e) {
                    continue;
                }
            }
            intentListObj.setContextOut(contextOutTempObjList);

            intentList.add(intentListObj);

        }

        FetchIntentDetail fetchIntentDetail = new FetchIntentDetail();
        ArrayList<IntentDetailResponse> intentDetailResponsesArray = new ArrayList<IntentDetailResponse>();
        intentDetailResponsesArray = fetchIntentDetail.GetIntentsDetail(intentList);*/
        return intentDetailResponsesList;
    }
}
