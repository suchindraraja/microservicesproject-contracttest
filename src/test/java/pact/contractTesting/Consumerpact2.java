package pact.contractTesting;


import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;

public class Consumerpact2 
{
	//About provider microservice information
	@Rule
	public PactProviderRuleMk2 mkprovider
	                  =new PactProviderRuleMk2("infoprovider","localhost", 9090, this);
	
	//Give Consumer expectations in terms of request and response
	@Pact(consumer="consumer_B")
	public RequestResponsePact createPact(PactDslWithProvider builder) 
	{
	    Map<String, String> headers=new HashMap<>();
	    headers.put("Content-Type","application/json");
	    return(builder.given("test GET")
	        .uponReceiving("GET REQUEST")
	        .path("/info")
	        .method("GET")
	        .willRespondWith()
	        .status(200)
	        .headers(headers)
	        .body("{\"id\": \"11\", \"age\": \"78\"}")
	        .toPact());
	}
	
	//Verify correctness of contract/pact/agreement in b/w consumer_B and provider microservice
	@Test
	@PactVerification
	public void testconsumercontract() 
	{
		RestTemplate rt=new RestTemplate();
	    ResponseEntity<String> res=rt.getForEntity(mkprovider.getUrl() +"/info",String.class);
	    System.out.println(res.getStatusCode().value());
	    System.out.println(res.getHeaders().get("Content-Type"));
	    System.out.println(res.getBody());
	}
}
