package pact.contractTesting;


import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;

public class Consumerpostpact 
{
	//Give provider microservice details
	@Rule
	public PactProviderRuleMk2 mkprovider
	                  =new PactProviderRuleMk2("postprovider","localhost", 9090, this);
	
	//Give consumer expectations in terms of request and response
	@Pact(consumer="consumer_D")
	public RequestResponsePact createPact(PactDslWithProvider builder) 
	{
	    Map<String, String> headers=new HashMap<>();
	    headers.put("Content-Type","application/json");
	    return(builder.given("test POST")
	    		.uponReceiving("POST REQUEST")
	    		  .method("POST")
	    		  .headers(headers)
	    		  .body("{\"name\": \"kalam\"}")
	    		  .path("/create")
	    		  .willRespondWith()
	    		  .status(201)
	    		  .toPact());
	}
	
	//Verify correctness of created contract in b/w "consumer_D" and "postprovider" service
	@Test
	@PactVerification
	public void testconsumercontract() 
	{
		String jb="{\"name\": \"kalam\"}";
		HttpHeaders hh=new HttpHeaders();
		hh.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity he=new HttpEntity(jb,hh);
		RestTemplate rt=new RestTemplate();
		ResponseEntity res=
				rt.exchange(mkprovider.getUrl()+"/create",HttpMethod.POST,he,String.class);
	    System.out.println(res.getStatusCode().value());
	}
}
