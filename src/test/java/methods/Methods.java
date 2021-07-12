package methods;


import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static api.RegresAPI.PATH_PARAM;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class Methods {


    public static Map<String, ?> map;

    public static List<String> searchInTable(Table table, String rowName){
        List<String> searchResult = new ArrayList<>();
        for(TableRow row : table.getTableRows()){
            String name = row.getCell(rowName);
            searchResult.add(name);
        }
        return searchResult;
    }

    public static void verifyRowValues(Table table, String firstRowName, String anotherRowName){
        for (TableRow row : table.getTableRows()) {
            String urlParam = row.getCell(firstRowName);
            int responseCode =
                    get(urlParam).then()
                            .extract().statusCode();
            int expectedCode = Integer.parseInt(row.getCell(anotherRowName));
            int actualResult = responseCode;
            Assert.assertEquals(expectedCode, actualResult);
        }
    }

    public static String getLastValue(String bodyValue){
        Response response =
                given().
                        when().get(PATH_PARAM).then().extract().response();

        List<String> allValues = response.path(bodyValue);
        return bodyValue + "[" + (allValues.size()-1) + "]";
    }

    public static Map extractMapOfObjects(String brewery_type, String type, String state, String nameOfState){
        Response response = get(PATH_PARAM);
        Map<String, ?> breweryOfCertainPosition = response.path(
                "findAll { it."+brewery_type+" == '"+type+"' }.find { it."+state+" == '"+nameOfState+"' }",
                brewery_type, state);
        return breweryOfCertainPosition;
    }

    public static void findNameWithIdFromMap(Map<String, ?> map, String name, int id){
        Assert.assertTrue(map.containsValue(name));
        Assert.assertTrue(map.containsValue(id));
    }


}
