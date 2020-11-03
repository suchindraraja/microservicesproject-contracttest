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
@Provider("postprovider")
@PactFolder("target\\pacts")
public class PostProviderPactTest
{
	private static final int WIREMOCK_PORT=9090;  
	
	//Start provider microservice under contract testing
	@BeforeClass  
	public static void setup() 
	{ 
		//Need to start Provider microservice
		WireMockServer wireMockServer;
	    wireMockServer=new WireMockServer(WIREMOCK_PORT);  
	    wireMockServer.start(); 
	    wireMockServer.stubFor(post(urlEqualTo("/create"))
                .withRequestBody(equalToJson("{\"name\": \"kalam\"}"))
                .willReturn(aResponse().withStatus(201)));   
	}
	
	//Set that microservce as target
	@TestTarget
	public final Target target=new HttpTarget("http","localhost",WIREMOCK_PORT);
	
	//Run specified state related to contracts on that provder microservice
	@State("test POST")
	public void toPostState() 
	{ 	
	}
}
