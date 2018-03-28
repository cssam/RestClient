package ca.bcit.comp4655.rest.client;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import ca.bcit.comp4655.rest.bean.Message;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;

public class RESTHelloClient 
{
	public static void main(String[] args) 
	{
		//Creating an instance of a client
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create( config );
		
		WebResource service = client.resource( getBaseURI() );
		
		// Get plain text
		System.out.println(service.path("/hello").accept(
				MediaType.TEXT_PLAIN).get( String.class ) );
		// Get XML
		System.out.println(service.path("/hello").accept(
				MediaType.TEXT_XML).get(String.class));
//		// The HTML
		System.out.println(service.path("/hello").accept(
				MediaType.TEXT_HTML).get(String.class) );
//		
//		// The URI Parameter
		System.out.println(service.path("/hello/Joe").accept(
				MediaType.TEXT_HTML).get(String.class) );
//		
//		//post
		Form form = new Form();
		form.add ( "message", "Message Two" );
		Message response = service.path( "/hello/messageUpdate/Message Nine" ).
		//entity(form).
		//post( Message.class );
		post( Message.class, form );
		System.out.println( response.getContent() );
//		
//		//Delete
//		service.path( "/hello/messageDelete/Message One" ).delete();
//				
//		//put
		service.path( "/hello/addMessage/Message Nine" ).accept( MediaType.TEXT_PLAIN ).put();
		
	}

	private static URI getBaseURI()
	{
		return UriBuilder.fromUri("http://localhost:8080/HelloRest").build();
	}

}
