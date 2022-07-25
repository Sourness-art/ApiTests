import io.restassured.http.ContentType;

import org.junit.*;
import pojos.FullUserPojo;
import pojos.UserPojo;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class ApiTests {

    @Test
    public void getUsers() {
        given()
                .baseUri("https://reqres.in/api")
                .basePath("/users")
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(200);
    }

    @Test
    public void getSingleUserInfAsString(){

        String inf =  given()
                .baseUri("https://reqres.in/api")
                .basePath("/users/2")
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(200)
                .extract().asString();

        System.out.println(inf);
    }

    @Test
    public void testInformation(){
        given()
                .baseUri("https://reqres.in/api")
                .basePath("/users")
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(200)
                .body("data[0].email", equalTo("george.bluth@reqres.in"));
    }

    @Test
    public void testInformationWithLambda(){
        given()
                .baseUri("https://reqres.in/api")
                .basePath("/users")
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(200)
                .body("data.find{it.email=='george.bluth@reqres.in'}.first_name", equalTo("George"));
    }
    @Test
    public void getAllEmails(){

        List <String> emails =
        given()
                .baseUri("https://reqres.in/api")
                .basePath("/users")
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(200)
                .extract().jsonPath().getList("data.email");

        System.out.println(emails);
    }

    @Test
    public void getInfWithDeserialization(){

        List <UserPojo> users =
                given()
                        .baseUri("https://reqres.in/api")
                        .basePath("/users")
                        .contentType(ContentType.JSON)
                        .when().get()
                        .then().statusCode(200)
                        .extract().jsonPath().getList("data", UserPojo.class);

    }

    @Test
    public void getFullInfWithDeserialization(){

        List <FullUserPojo> users =
                given()
                        .baseUri("https://reqres.in/api")
                        .basePath("/users")
                        .contentType(ContentType.JSON)
                        .when().get()
                        .then().statusCode(200)
                        .extract().jsonPath().getList("data", FullUserPojo.class);

        System.out.println(users);
    }

    @Test
    public void getInfWithDeserializationUsingLombok(){

        List <FullUserPojo> users =
                given()
                        .baseUri("https://reqres.in/api")
                        .basePath("/users")
                        .contentType(ContentType.JSON)
                        .when().get()
                        .then().statusCode(200)
                        .extract().jsonPath().getList("data", FullUserPojo.class);

        System.out.println(users);
    }
}
