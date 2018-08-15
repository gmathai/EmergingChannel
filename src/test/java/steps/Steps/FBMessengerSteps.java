package steps.Steps;

import com.qantas.api.fbchatbot.datasetup.Intents.intentdetailresponse.ResponsesMessagesPayloadMessages;
import net.thucydides.core.annotations.Step;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

public class FBMessengerSteps {
    /*private String ISO_CODE_SEARCH = "http://services.groupkt.com/country/get/iso2code/";
    private Response response;

    @Step
    public void searchCountryByCode(String code){
        response = SerenityRest.when().get(ISO_CODE_SEARCH + code);
    }

    @Step
    public void searchIsExecutedSuccesfully(){
        response.then().statusCode(200);
    }

    @Step
    public void iShouldFindCountry(String country){
        response.then().body("RestResponse.result.name", is(country));
    }*/

    @Step("{0}")
    public void  verifyBaggage(String intentName, String intentID, List<String> intentUtteranceList, List<ResponsesMessagesPayloadMessages> intentResponseMessages){

        /////////////////////
        /////// STEPS ///////
        /////////////////////
        // Step name =intentName
        // for each entry in utterance list hit the new api endpoint
        // validate the response with ResponsesMessagesPayloadMessages List - ASSERT
    }

    @Step("{0}")
    public void verifyGetStarted(String intentName, String intentID, List<String> intentUtteranceList, List<ResponsesMessagesPayloadMessages> intentResponseMessages){

        /////////////////////
        /////// STEPS ///////
        /////////////////////
        // validate the response with ResponsesMessagesPayloadMessages List - ASSERT

        List<String> failedUtterance = new ArrayList<>();

        for(int j=0;j<intentUtteranceList.size();j++){
            // Hit new api with intentUtteranceList.get(j)
            // Get the actual response for each utterance
            // INCLUDE BELOW FOR LOOP





        }
        // IF LOOP for failing testcase
        if(failedUtterance.size()>0){
            fail("Failed for utterancess : "+failedUtterance);
        }

        String actualResponse="{\"messages\":[{\"platform\":\"facebook\",\"speech\":\"Hi, I'm the Qantas Bot! \",\"type\":0},{\"platform\":\"facebook\",\"speech\":\"ng your booking and baggage questions. I can also notify you if any of your flights change. If you get stuck you can use me to chat with a Qantas representative too!\",\"type\":0},{\"platform\":\"facebook\",\"replies\":[{\"text\":\"Help and FAQs\",\"url\":\"http://www.qantas.com/img/mobile/icons/faq.png\"},{\"text\":\"Manage booking\",\"url\":\"http://www.qantas.com/img/mobile/icons/manage_booking.png\"},{\"text\":\"Flight updates\",\"url\":\"http://www.qantas.com/img/mobile/icons/flight_updates.png\"},{\"text\":\"Search flights\",\"url\":\"http://www.qantas.com/img/mobile/icons/book_flight.png\"}],\"title\":\"Start by selecting one of the options below! Or you can just type something like ‘change my flight’.\",\"type\":2}]}";

//        https://junit.org/junit4/javadoc/4.8/org/junit/Assert.html ////////Refer ASSERT Object
        boolean flag=false;
        for(int i=0;i<intentResponseMessages.size();i++){
            if(!(intentResponseMessages.get(i).getPlatform()!=null && actualResponse.contains(intentResponseMessages.get(i).getPlatform()))){
                flag=false;
            }
            if(!(intentResponseMessages.get(i).getSpeech()!=null && actualResponse.contains(intentResponseMessages.get(i).getSpeech()))){
                flag=false;
            }
            if(!(intentResponseMessages.get(i).getTitle()!=null && actualResponse.contains(intentResponseMessages.get(i).getTitle()))){
                flag=false;
            }
            if(!(intentResponseMessages.get(i).getSenderaction()!=null && actualResponse.contains(intentResponseMessages.get(i).getSenderaction()))){
                flag=false;
            }
            // Add the utterance to failedUtterance list
        }
        if(flag==false){
            fail("TC Failed");
        }






    }


}