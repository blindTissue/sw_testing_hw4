import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class JsonplaceholderRestAPITest {
    static {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";
    }

    @Test
    void testAlbumTitleExists() {
        Response response = given()
                .when()
                .get("albums")
                .then()
                .statusCode(200)
                .extract()
                .response();
        assertThat(response.jsonPath().getList("title"), hasItem("omnis laborum odio"));
    }

    @Test
    void testCommentCount() {
        Response response = given()
                .when()
                .get("comments")
                .then()
                .statusCode(200)
                .extract()
                .response();
        assertThat(response.jsonPath().getList("id"), hasSize(500));
    }

    @Test
    void testUserInfo() {
        Response response = given()
                .when()
                .get("users")
                .then()
                .statusCode(200)
                .extract()
                .response();
        //System.out.println(response.asString());
        assertThat(response.jsonPath().getString("find { it.name == 'Ervin Howell' }.username"), equalTo("Antonette"));
        assertThat(response.jsonPath().getString("find { it.name == 'Ervin Howell' }.address.zipcode"), equalTo("90566-7771"));
    }

    @Test
    void testCommentEmailInfo() {
        Response response = given()
                .when()
                .get("comments")
                .then()
                .statusCode(200)
                .extract()
                .response();

        //System.out.println(response.asString());
        assertThat(response.jsonPath().getList("findAll { it.email.endsWith('.biz') }").size(), greaterThan(0));
    }

    @Test
    void testCreatePost() {
        String newPost = "{\n" +
                "  \"title\": \"this assignment\",\n" +
                "  \"body\": \"is stupid\",\n" +
                "  \"userId\": 1\n" +
                "}";
        given()
                .header("Content-type", "application/json; charset=UTF-8")
                .body(newPost)
                .when()
                .post("posts")
                .then()
                .statusCode(201)
                .body("title", equalTo("this assignment"))
                .body("body", equalTo("is stupid"))
                .body("userId", equalTo(1));
    }
}
