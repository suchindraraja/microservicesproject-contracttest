package pact.contractTesting;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;

@RunWith(PactRunner.class)
@Provider("infoprovider")
@PactFolder("target\\pacts")
public class InfoProviderPactTest
{
	private static final int WIREMOCK_PORT=9090;
	
	//Start microservice under contract testing
	@BeforeClass  
	public static void setup() 
	{ 
		//Need to start Provider microservice
		WireMockServer wireMockServer;
	    wireMockServer=new WireMockServer(WIREMOCK_PORT);  
	    wireMockServer.start(); 
	    wireMockServer.stubFor(get(  
	            urlEqualTo("/info"))  
	            .willReturn(aResponse()  
	            .withStatus(200)  
	            .withHeader("Content-Type", "application/json")  
	            .withBody("{\"id\": \"11\", \"name\": \"kalam\", \"age\": \"78\"}")  
	            ));   
	}
	
	//Set that microservice as target
	@TestTarget
	public final Target target=new HttpTarget("http","localhost",WIREMOCK_PORT);
	
	//Run specified state related contracts one after other
	@State("test GET")
	public void toGetState() 
	{ 		
	}
}
