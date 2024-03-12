import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class RestApiForPets {
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = TestData.BASE_URI;
    }

    @Test
    void petsAddPostTest() {

        PetsRequest petsRequest = PetsRequest.builder()
                .id(1000)
                .photoUrls(List.of("black","old"))
                .category(Category.builder()
                        .name("Rex")
                        .id(5)
                        .build())
                .tags(List.of(TagsItem.builder()
                        .name("Rex")
                        .id(5)
                        .build()))
                .status("available")
                .build();

        PetsRequest responseAsObject = given()
                .when()
                .contentType(ContentType.JSON)
                .body(petsRequest)
                .post("/v2/pet")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(PetsRequest.class);

        assertThat(responseAsObject).isEqualTo(petsRequest);

    }
    @Test
    void petsGetTestForId() {
        PetsRequest expectedPet = PetsRequest.builder()
                .id(5)
                .name("doggie")
                .photoUrls(List.of("string"))
                .category(Category.builder()
                        .name("string")
                        .id(0)
                        .build())
                .tags(List.of(TagsItem.builder()
                        .name("string")
                        .id(0)
                        .build()))
                .status("string")
                .build();

        PetsRequest responseAsObject = given()
                .when()
                .contentType(ContentType.JSON)
                .get("/v2/pet/5")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(PetsRequest.class);

        assertThat(responseAsObject.getId()).isEqualTo(expectedPet.getId());
        assertThat(responseAsObject.getName()).isEqualTo(expectedPet.getName());
        assertThat(responseAsObject.getPhotoUrls()).isEqualTo(expectedPet.getPhotoUrls());
        assertThat(responseAsObject.getCategory()).isEqualTo(expectedPet.getCategory());
        assertThat(responseAsObject.getTags()).isEqualTo(expectedPet.getTags());
    }

    @Test
    void petsPutTest() {
        PetsRequest petsPutRequest = PetsRequest.builder()
                .id(5)
                .name("Max")
                .photoUrls(List.of("string"))
                .category(Category.builder()
                        .name("string")
                        .id(2)
                        .build())
                .tags(List.of(TagsItem.builder()
                        .name("string")
                        .id(0)
                        .build()))
                .status("string")
                .build();

        PetsRequest responseAsObject = given()
                .when()
                .contentType(ContentType.JSON)
                .body(petsPutRequest)
                .put("/v2/pet")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(PetsRequest.class);

        assertThat(responseAsObject.getId()).isEqualTo(petsPutRequest.getId());
        assertThat(responseAsObject.getName()).isEqualTo(petsPutRequest.getName());
        assertThat(responseAsObject.getPhotoUrls()).isEqualTo(petsPutRequest.getPhotoUrls());
        assertThat(responseAsObject.getCategory()).isEqualTo(petsPutRequest.getCategory());
        assertThat(responseAsObject.getTags()).isEqualTo(petsPutRequest.getTags());
    }

    @Test
    void petsGetTestFindByStatus() {
        List<PetsRequest> petsRequests = given()
                .when()
                .contentType(ContentType.JSON)
                .get("/v2/pet/findByStatus?status=sold")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().jsonPath().getList(".", PetsRequest.class);

        assertThat(petsRequests.size()).isGreaterThan(10);
    }

}
