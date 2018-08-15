package com.qantas.api.fbchatbot.datasetup.Entity;

import com.qantas.api.fbchatbot.datasetup.Entity.entitylist.Entities2;
import com.qantas.api.fbchatbot.datasetup.FetchConfigProperties;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
//import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class FetchEntities {

//    @Test
    public void FetchEntityList() {
        FetchConfigProperties fetchConfigPropertiesObj = new FetchConfigProperties();
        fetchConfigPropertiesObj = fetchConfigPropertiesObj.getConfigProperties();

        /////////////////////////////
        //////// CONNECTION /////////
        /////////////////////////////
        String endpopint = fetchConfigPropertiesObj.getEndpoint();
        ;
        String protocolValue = fetchConfigPropertiesObj.getDialogflowProtocolValue();  // Used for /intents and /entities endpoints.
        String developerAccesstoken = fetchConfigPropertiesObj.getDeveloperAccesstoken();  // Used for /intents and /entities endpoints.
        String clientAccesstoken = fetchConfigPropertiesObj.getClientAccesstoken();  // Used for /query, /contexts, and /userEntities endpoints.


        Response entityListResponse = given().
                header("Authorization", "Bearer " + developerAccesstoken).
                header("Content-Type", "application/json").
                get(endpopint + "entities?v=" + protocolValue + "&lang=en").
                then().statusCode(200)./*log().all().*/extract().response();


        //////////// FINAL ///////////////
        String response=entityListResponse.asString();
        JsonPath jsonPath = new JsonPath(response);

        Entities2 entitiesObj = new Entities2();
        entitiesObj.setId(jsonPath.getList("id"));
        entitiesObj.setName(jsonPath.getList("name"));
        entitiesObj.setCount(jsonPath.getList("count"));
        entitiesObj.setPreview(jsonPath.getList("preview"));


        //////////////////////////////////

        /*JSONArray entityListArray = new JSONArray(entityListResponse.body().asString());

        ArrayList<Entities> entityList = new ArrayList<Entities>();

        for (int i = 0; i < entityListArray.length(); i++) {
            Entities entitiesObj = new Entities();

            JSONArray tempJsonArray;
            ArrayList<String> temp = new ArrayList<String>();


            JSONObject obj = entityListArray.getJSONObject(i);
            try {
                entitiesObj.setId(obj.getString("id"));
                entitiesObj.setName(obj.getString("name"));
                entitiesObj.setCount(obj.getInt("count"));
                entitiesObj.setPreview(obj.getString("preview"));
            } catch (Exception e) {

            }

            entityList.add(entitiesObj);

        }

        FetchEntityDetail fetchEntityDetailObj = new FetchEntityDetail();
        ArrayList<EntityDetail> entityDetailArray = new ArrayList<EntityDetail>();
        entityDetailArray = fetchEntityDetailObj.FetchEntityDetail(entityList);*/

    }
}

