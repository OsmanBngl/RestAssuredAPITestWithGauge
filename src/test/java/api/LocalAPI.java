package api;

import com.thoughtworks.gauge.BeforeSuite;
import io.restassured.RestAssured;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.lessThan;


public class LocalAPI {
    public static RequestSpecification regresRequestSpec;
    public static ResponseSpecification regresResponseSpec;

    @BeforeSuite
    public static void setUp(){
        regresRequestSpec = new RequestSpecBuilder()
                .setBaseUri("https://reqres.in/")
                .addFilter(new RequestLoggingFilter())
                .build();

        regresResponseSpec = new ResponseSpecBuilder()
                .expectResponseTime(lessThan(3000L))
                .build();
        RestAssured.requestSpecification = regresRequestSpec;
        RestAssured.responseSpecification = regresResponseSpec;
    }
}
