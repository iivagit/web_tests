package restassured.tests;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;


public class PostData {

	public static Map<String, String> map = new HashMap<String, String>();
	@BeforeTest
	public void postdata(){
		map.put("userId", "2");
		map.put("id", "19");
		map.put("title", "this is projectdebug.com");
		map.put("body", "this is REST-Assured Tutorial");	
		RestAssured.baseURI = "http://jsonplaceholder.typicode.com";
		RestAssured.basePath = "/posts/";
	}
	
	@Test
	public void testPOST(){
		given()
		.contentType("application/json")
		.body(map)
		.when()
		.post()
		.then()
		.statusCode(201)
		.and()
		.body("title", equalTo("this is projectdebug.com"));
	}

}
