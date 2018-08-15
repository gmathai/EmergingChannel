package com.qantas.api.fbchatbot.datasetup.Entity;

import com.qantas.api.fbchatbot.datasetup.Entity.entitydetail.EntityDetail2;
import com.qantas.api.fbchatbot.datasetup.Entity.entitydetail.Entries2;
import com.qantas.api.fbchatbot.datasetup.FetchConfigProperties;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
//import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class FetchEntityDetail {

//    @Test
    public void /*ArrayList<EntityDetail>*/ FetchEntityDetail(/*ArrayList<Entities> entityList*/) {
        FetchConfigProperties fetchConfigPropertiesObj = new FetchConfigProperties();
        fetchConfigPropertiesObj = fetchConfigPropertiesObj.getConfigProperties();

        /////////////////////////////
        //////// CONNECTION /////////
        /////////////////////////////
        String endpopint = fetchConfigPropertiesObj.getEndpoint();;
        String protocolValue = fetchConfigPropertiesObj.getDialogflowProtocolValue();  // Used for /intents and /entities endpoints.
        String developerAccesstoken = fetchConfigPropertiesObj.getDeveloperAccesstoken();  // Used for /intents and /entities endpoints.
        String clientAccesstoken = fetchConfigPropertiesObj.getClientAccesstoken();  // Used for /query, /contexts, and /userEntities endpoints.


        //////////// FINAL //////////////

        Response response = given().
        header("Authorization", "Bearer " + developerAccesstoken).
        header("Content-Type", "application/json").
        get(endpopint + "entities/"+"6f861e5d-7127-4406-b2d8-26baf4bbc6ce"/*entityList.get(m).getId()*/+"?v=" + protocolValue).
        then().statusCode(200).extract().response();

        String respons=response.asString();
        JsonPath jsonPath = new JsonPath(respons);


        EntityDetail2 entityDetail = new EntityDetail2();
        entityDetail.setId(jsonPath.getString("id"));
        entityDetail.setName(jsonPath.getString("name"));
        entityDetail.setOverridable(jsonPath.getBoolean("isOverridable"));
        entityDetail.setEnum(jsonPath.getBoolean("isEnum"));
        entityDetail.setAutomatedExpansion(jsonPath.getBoolean("automatedExpansion"));

            Entries2 entries = new Entries2();
            entries.setValue(jsonPath.getList("entries.value"));
            entries.setValue(jsonPath.getList("entries.synonyms"));
        entityDetail.setEntries(entries);





        /////////////////////////////////

//        ArrayList<EntityDetail> entityDetailsList = new ArrayList<EntityDetail>();

        /*for (int m=0; m<entityList.size();m++){
            Response response = given().
                    header("Authorization", "Bearer " + developerAccesstoken).
                    header("Content-Type", "application/json").
                    get(endpopint + "entities/"+entityList.get(m).getId()+"?v=" + protocolValue).
                    then().statusCode(200)./*log().all().extract().response();


        EntityDetail entityDetail = new EntityDetail();
        entityDetail.setId(response.jsonPath().getString("id"));
        entityDetail.setName(response.jsonPath().getString("name"));
        entityDetail.setOverridable(response.jsonPath().getBoolean("isOverridable"));
        entityDetail.setEnum(response.jsonPath().getBoolean("isEnum"));
        entityDetail.setAutomatedExpansion(response.jsonPath().getBoolean("automatedExpansion"));

        JSONObject object = new JSONObject(response.asString());
        JSONArray tempArray = object.getJSONArray("entries");
        ArrayList<Entries> entriesArrayList = new ArrayList<Entries>();
        for(int i = 0; i < tempArray.length(); i++){
            Entries entriesObj = new Entries();
            JSONObject obj = tempArray.getJSONObject(i);
            try{
                entriesObj.setValue(obj.getString("value"));

                JSONArray tempArray2;
                tempArray2 = obj.getJSONArray("synonyms");
                ArrayList<String> temp = new ArrayList<String>();
                for(int j=0; j<tempArray2.length();j++){
                    temp.add((String) tempArray2.get(j));
                }
                entriesObj.setSynonyms(temp);

            }catch(Exception e){
                continue;
            }
            entriesArrayList.add(entriesObj);
        }

            entityDetail.setEntries(entriesArrayList);

            entityDetailsList.add(entityDetail);


//            System.out.println("id "+response.jsonPath().getString("id"));

        }

        return entityDetailsList;*/
    }
}
