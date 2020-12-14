package restassured.tests;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class GetData {

	private static Logger Log = Logger.getLogger(GetData.class.getName());

	// non-static block
	{
		BasicConfigurator.configure();
		String log4jConfPath = System.getProperty("user.dir") + "\\log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
	}

	public final class EndPoints {
		public static final String api = "https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22";
		public static final String demoApi = "http://demo.guru99.com/V4/sinkministatement.php?CUSTOMER_ID=68195&PASSWORD=1234!&Account_No=1";
	}

	@Test
	public void testGoogleCode() {
		given().when().get("http://www.google.com").then().statusCode(200);
		Log.info("testGoogleCode");
		Log.warn("testGoogleCode");
		Log.error("testGoogleCode");
		Log.fatal("testGoogleCode");
		Log.debug("testGoogleCode");
	}

	@Test
	public void testGoogleMap() {
		// BaseURL or Host
		RestAssured.baseURI = "https://maps.googleapis.com";
		String result = given().param("location", "33.8670522,151.1957362").param("radius", "500")
				.param("key", "AIzaSyDahQkqdxmUihrC0_3Gi7hRBZQWDrV1xI0").when().get("/maps/api/place/nearbysearch/json")
				.then().assertThat().statusCode(200).extract().asString();

		System.out.println(" API Response :" + result);
	}

	@Test
	public void testResponceCode() {
		int code = get(EndPoints.api).getStatusCode();
		System.out.println("Code: " + code);
		Assert.assertEquals(code, 200);
	}

	@Test
	public void testbody() {
		Long time = get(EndPoints.api).getTime();
		System.out.println("response time: " + time);
	}

	@Test
	public void testPostmanEcho() {
		get("https://postman-echo.com/GET").then().statusCode(200);
	}

}
