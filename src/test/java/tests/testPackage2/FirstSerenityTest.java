package tests.testPackage2;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Title;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class FirstSerenityTest {
    @Test @Title("This is test 1")
    public void getAll1Test(){
        System.out.println("Test 1");

    }

    @Test @Title("This is test 2")
    public void getAll2Test(){
        System.out.println("Test 2");

    }
}
