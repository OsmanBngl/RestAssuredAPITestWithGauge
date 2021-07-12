package step;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import io.restassured.response.Response;
import methods.Methods;
import org.junit.Assert;

import java.util.List;

import static api.RegresAPI.PATH_PARAM;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

import static methods.Methods.*;
import static org.hamcrest.CoreMatchers.equalTo;


public class StepImplementationRegres {

    private Methods methods;

    public StepImplementationRegres( ) {

        methods= new Methods();
    }

    @Step("<user Name> kullanıcı listesindeki ilk ismi getir")
    public void getUserFirstName(String userName){
        given().when().get(PATH_PARAM).then().body("data.first_name[0]", equalTo(userName));
    }

    @Step("<first Name> kullanıcı listesindeki son ismi getir")
    public void getUserLastName(String firstName){
        given().when().get(PATH_PARAM).then().body(getLastValue("data.first_name"), equalTo(firstName));
    }

    @Step("cevab kodu <response code> olmalı")
    public void responseCode(int responseCode) {
        int actualResult = get().getStatusCode();
        int expectedResult = 200;
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Step("bütün kullanıcı isimlerini çıkar <table>")
    public void extractAllUserName(Table allUserName){
        Response response = given().when().get(PATH_PARAM).then().extract().response();
        List<String> allNames = response.path("data.first_name");
        List<String> firstAllName = searchInTable(allUserName,"FIRST_NAME");
        for (int i = 0; i<firstAllName.size(); i++){
            Assert.assertEquals(allNames.get(i), firstAllName.get(i));
        }
    }



}