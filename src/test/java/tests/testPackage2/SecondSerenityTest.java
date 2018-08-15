package tests.testPackage2;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Title;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class SecondSerenityTest {

    @Test @Title("This is test 3")
    public void getAll1Test(){
        System.out.println("Test 21");
    }

    @Test @Title("This is test 4")
    public void getAll2TestDemo(){
        System.out.println("Test 22");
    }

}
