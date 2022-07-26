import org.junit.*;
import pojos.*;
import utils.UserGenerator;

import java.util.List;

import static config.UserApiConfig.requestSpec;
import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UserApiTests {

    @Test
    public void getUsers() {
        given()
                .spec(requestSpec)
                .when().get()
                .then().statusCode(200);
    }

    @Test
    public void getSingleUserInfAsString() {

        String inf = given()
                .spec(requestSpec)
                .basePath("/2")
                .when().get()
                .then().statusCode(200)
                .extract().asString();

        System.out.println(inf);
    }

    @Test
    public void testInformation() {
        given()
                .spec(requestSpec)
                .when().get()
                .then().statusCode(200)
                .body("data[0].email", equalTo("george.bluth@reqres.in"));
    }

    @Test
    public void testInformationWithLambda() {
        given()
                .spec(requestSpec)
                .when().get()
                .then().statusCode(200)
                .body("data.find{it.email=='george.bluth@reqres.in'}.first_name", equalTo("George"));
    }

    @Test
    public void getAllEmails() {

        List<String> emails =
                given()
                        .spec(requestSpec)
                        .when().get()
                        .then().statusCode(200)
                        .extract().jsonPath().getList("data.email");

        System.out.println(emails);
    }

    @Test
    public void getInfWithDeserialization() {

        List<UserPojo> users =
                given()
                        .spec(requestSpec)
                        .when().get()
                        .then().statusCode(200)
                        .extract().jsonPath().getList("data", UserPojo.class);

        System.out.println(users);

    }

    @Test
    public void getFullInfWithDeserialization() {

        List<FullUserPojo> users =
                given()
                        .spec(requestSpec)
                        .when().get()
                        .then().statusCode(200)
                        .extract().jsonPath().getList("data", FullUserPojo.class);

        System.out.println(users);
    }

    @Test
    public void getInfWithDeserializationUsingLombok() {

        List<FullUserPojoUsingLombok> users =
                given()
                        .spec(requestSpec)
                        .when().get()
                        .then().statusCode(200)
                        .extract().jsonPath().getList("data", FullUserPojoUsingLombok.class);

        assertThat(users).extracting(FullUserPojoUsingLombok::getEmail).contains("george.bluth@reqres.in");
    }

    @Test
    public void createUser() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setName("morpheus");
        createUserRequest.setJob("leader");

        CreateUserResponse response = given()
                .spec(requestSpec)
                .body(createUserRequest)
                .when().post()
                .then().extract().as(CreateUserResponse.class);


        assertThat(response)
                .isNotNull()
                .extracting(CreateUserResponse::getName)
                .isEqualTo(createUserRequest.getName());

        System.out.println(response);
    }

    @Test
    public void createUserWithBuilder() {

        CreateUserWithBuilderRequest createUserRequest = UserGenerator.getUser();
        CreateUserResponse response = given()
                .spec(requestSpec)
                .body(createUserRequest)
                .when().post()
                .then().extract().as(CreateUserResponse.class);


        assertThat(response)
                .isNotNull()
                .extracting(CreateUserResponse::getName)
                .isEqualTo(createUserRequest.getName());

        System.out.println(response);

    }

}
