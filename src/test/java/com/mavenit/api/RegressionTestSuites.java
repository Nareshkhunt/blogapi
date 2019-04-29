package com.mavenit.api;

import com.google.gson.JsonObject;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RegressionTestSuites extends RestServices {


    @Test
    public void getUserTest() {
        Response response = given()
                .when()
                .get("http://ec2-52-14-141-208.us-east-2.compute.amazonaws.com:9089/register");
     List<Integer> ids= response.then().extract().path("id");
     System.out.println(ids);
     //Hamcreset style
     assertThat(ids,hasItem(8));



     //restassured
     response.then().body("id",contains(8))
            .body("username",contains("admin"))
             .statusCode(200);
        //     .body("roles",contains(""));
    }


    @Test
    public void userRegisterTest(){
        Response response=null;
        JsonObject payload=new JsonObject();
        payload.addProperty("username","vedant13");
        payload.addProperty("passwordConfirmation","vedant3");
        payload.addProperty("password","vedant3");


        response=postService(payload,"http://ec2-52-14-141-208.us-east-2.compute.amazonaws.com:9089/register");

        int id = response.then().extract().path("id");
        String msg1=response.then().extract().path("message");
        System.out.println(msg1);
        System.out.println(id);
        //complete hamcrest
        assertThat(msg1,is(equalToIgnoringCase("successful")));
        assertThat(response.getStatusCode(),is(equalTo(200)));

        response=getByIdServices("http://ec2-52-14-141-208.us-east-2.compute.amazonaws.com:9089/register/",id);
        response.then().statusCode(200);

        response=deleteByIdServices("http://ec2-52-14-141-208.us-east-2.compute.amazonaws.com:9089/register/",id);
        String msg2=response.then().extract().path("message");
        System.out.println(msg2);
      response.then()
             // .body("message",is(equalToIgnoringCase("User has been removed")))
             // .body("message",is(equalToIgnoringCase("User has been removed")))
             .statusCode(200);

    }

}
