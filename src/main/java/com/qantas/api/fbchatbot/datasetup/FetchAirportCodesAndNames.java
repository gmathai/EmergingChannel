package com.qantas.api.fbchatbot.datasetup;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
//import org.testng.annotations.Test;

import java.lang.reflect.Type;
import java.util.Map;

public class FetchAirportCodesAndNames {
//    @Test
    public void Fetchcityde() throws Exception{

        // Working code
        /*try{
            String fileContent="", filePath = "/Users/revit/Desktop/Q/PROJECTS/Emerging channel/Chatbots/mobileapiautomationsuite/airports_all.txt" ;
            fileContent = new String (Files.readAllBytes( Paths.get(filePath)));
            List <String> airportCode = new ArrayList<String>();
            List <String> airportName = new ArrayList<String>();
            JsonPath jsonPath = new JsonPath(fileContent);
            airportCode.add(jsonPath.getString("airports.code"));
            airportName.add(jsonPath.getString("airports.display_name"));
            System.out.println("airportCode : "+airportCode);
            System.out.println("airportName : "+airportName);
        }
        catch (IOException e){
            e.printStackTrace();
        }*/

        /*JsonParser parser = new JsonParser();
        JsonElement o1 = parser.parse("{a : {a : 2}, b : 2}");
        JsonElement o2 = parser.parse("{b : 2, a : {a : 2}}");
        assertEquals(o1, o2);*/

        /// Example for Checking additional nodes and incorect node values
        String json1 = "{\"name\":\"ABC1\", \"city\":\"XYZv\", \"state\":\"CA\"}";
        String json2 = "{\"city\":\"XYZ\", \"street\":\"123 anyplace\", \"name\":\"ABC\"}";
        Gson g = new Gson();
        Type mapType = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> firstMap = g.fromJson(json1, mapType);
        Map<String, Object> secondMap = g.fromJson(json2, mapType);
        System.out.println(Maps.difference(firstMap, secondMap));



    }
}