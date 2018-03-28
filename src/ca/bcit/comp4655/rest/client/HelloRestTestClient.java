package ca.bcit.comp4655.rest.client;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import ca.bcit.comp4655.rest.bean.Message;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;


public class HelloRestTestClient 
{

	private static WebResource service;
	
	@BeforeClass
	public static void setup()
	{
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create( config );
		URI baseURI = UriBuilder.fromUri( "http://localhost:8080/HelloRest/hello" ).build();
		service = client.resource( baseURI );
	}
	
	@Test
	public void simpleGet()
	{
		Assert.assertEquals("Hello REST (text)", 
				service.accept( MediaType.TEXT_PLAIN).get( String.class ) ); 
	}

	@Test
	public void xmlGet()
	{
		String msgString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><message><content>Hello REST(XML)</content><id>12345</id></message>";
		Assert.assertEquals( msgString, 
				service.accept( MediaType.TEXT_XML).get(String.class) );
		
		Message msg = service.accept( MediaType.TEXT_XML).get( Message.class) ;
		Assert.assertTrue( "Hello REST(XML)".equalsIgnoreCase( msg.getContent() ) );
		Assert.assertEquals( "12345", msg.getId() );
	}
	
	@Test
	public void htmlGet()
	{
		Assert.assertEquals( "<html> <title>Hello REST</title><body><h1>Hello REST (HTML)</body></h1></html>", 
				service.accept( MediaType.TEXT_HTML ).get( String.class ) );
		
	}
	
	@Test
	public void getWithParam()
	{
		Assert.assertEquals( "Hello Joe", 
				service.path( "Joe" ).accept( MediaType.TEXT_HTML).get( String.class ) );
	}
	
	@Test
	public void simplePost()
	{
		Form form = new Form();
		form.add ( "message", "Message XX" );
		Message response = service.path( "messageUpdate/Message Nine" ).
			//entity(form).
			//post( Message.class );
			post( Message.class, form );
		Assert.assertNotNull( response.getContent() );
		Assert.assertEquals( "Message XX was NOT replaced by Message Nine", response.getContent() );
	}
	
}
