package tests.testPackage1;

//import com.qantas.api.Steps.FBMessengerSteps;
import com.qantas.api.fbchatbot.datasetup.Intents.FetchIntents;
import com.qantas.api.fbchatbot.datasetup.Intents.intentdetailresponse.IntentDetailResponseFinal;
import com.qantas.api.fbchatbot.datasetup.Intents.intentdetailresponse.ResponsesMessagesPayloadMessages;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import steps.Steps.FBMessengerSteps;

import java.util.List;

@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FBMessengerTests {

    private static List<IntentDetailResponseFinal> intentDetailResponsesList;

    @Steps
    FBMessengerSteps fbMessengerSteps;

    @Test
    public void afetchDataFromDialogflowTest(){
        FetchIntents fetchIntentsObj = new FetchIntents();
        intentDetailResponsesList = fetchIntentsObj.getIntentListandDetails();
    }

    @Test
    public void verifyFAQBaggageTest() {
        for (int i=0;i<intentDetailResponsesList.size();i++){
            if(intentDetailResponsesList.get(i).getName().toLowerCase().contains("FAQ - Baggage".toLowerCase())){
                String intentName=intentDetailResponsesList.get(i).getName();
                String intentID=intentDetailResponsesList.get(i).getId();
                List<String> intentUtteranceList=intentDetailResponsesList.get(i).getUserSays().getData().getText();
                List<ResponsesMessagesPayloadMessages> intentResponseMessages=intentDetailResponsesList.get(i).getResponses().getResponseMessages().get(0).getResponsesMessagesPayloadMessages();
                fbMessengerSteps.verifyBaggage(intentName,intentID,intentUtteranceList,intentResponseMessages);
            }
        }
    }




    @Test
    public void verifyGetStartedTest() {
        System.out.println("Test verifyGetStarted");
        for (int i=0;i<intentDetailResponsesList.size();i++){
            if(intentDetailResponsesList.get(i).getName().toLowerCase().contains("Intro (Get Started / Start Again)".toLowerCase())){
                String intentName=intentDetailResponsesList.get(i).getName();
                String intentID=intentDetailResponsesList.get(i).getId();
                List<String> intentUtteranceList=intentDetailResponsesList.get(i).getUserSays().getData().getText();
                List<ResponsesMessagesPayloadMessages> intentResponseMessages=intentDetailResponsesList.get(i).getResponses().getResponseMessages().get(0).getResponsesMessagesPayloadMessages();
                fbMessengerSteps.verifyGetStarted(intentName,intentID,intentUtteranceList,intentResponseMessages);


            }



        }




    }

   /*
   @Test
    public void verifyFAQFreqFlyer() {
        System.out.println("Test verifyFAQFreqFlyer");
    }

    @Test
    public void verifyFAQManageBooking() {
        System.out.println("Test verifyFAQManageBooking");
    }

    @Test
    public void verifyFAQOther() {
        System.out.println("Test verifyFAQOther");
    }

    @Test
    public void verifyFAQUnknownAnswer() {
        System.out.println("Test verifyFAQUnknownAnswer");
    }

    @Test
    public void verifyDisruptions() {
        System.out.println("Test verifyDisruptions");
    }

    @Test
    public void verifyFinish() {
        System.out.println("Test verifyFinish");
    }


   @Test
    public void verifyMessageAPerson() {
        System.out.println("Test verifyMessageAPerson");
    }

    @Test
    public void verifyPrivacyPolicy() {
        System.out.println("Test verifyPrivacyPolicy");
    }

    @Test
    public void verifySearchFlights() {
        System.out.println("Test verifySearchFlights");
    }

    @Test
    public void verifySmallTalk() {
        System.out.println("Test verifySmallTalk");
    }

    @Test
    public void verifyFallback() {
        System.out.println("Test verifyFallback");
    }*/


}