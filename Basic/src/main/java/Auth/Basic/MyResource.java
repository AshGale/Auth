package Auth.Basic;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Auth.Data.DummyService;
import Auth.Module.Login;
import Auth.Module.User;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MyResource {

	DummyService dummyService = new DummyService();
	
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
    
    @GET
    @Path("secure")
    public Response getData(){
    	System.out.println("in secure section");
    	
    	Response response = Response.status(Response.Status.ACCEPTED)
				.entity(dummyService.alldata())
				.build();
		return response;
    }
    
    //C
    @POST
    @Path("secure")
    public Response post(User newData){
    	System.out.println("in secure section post: " + newData);
    	
    	Response response = Response.status(Response.Status.CREATED)
				.entity(dummyService.adddata(newData))
				.build();
		return response;
    }
    
    //R
    @GET
    @Path("secure/{id}")
    public Response get(@PathParam("id") Long id){
    	System.out.println("in secure section get: " + id);

    	Response response = Response.status(Response.Status.ACCEPTED)
				.entity(dummyService.getdata(id))
				.build();
		return response;
    }
    
    //U
    @PUT
    @Path("secure/{id}")
    public Response put(@PathParam("id") Long id, User newData ){
    	System.out.println("in secure section put: " + newData + " in " + id);
    	
    	Response response = Response.status(Response.Status.ACCEPTED)
				.entity(dummyService.setdata(id, newData))
				.build();
		return response;
    }
    
    //D
    @DELETE
    @Path("secure/{id}")
    public Response delete(@PathParam("id") Long id){
    	System.out.println("in secure section delete: " + id);
    	
    	Response response = Response.status(Response.Status.UNAUTHORIZED)
				.entity(dummyService.removedata(id))
				.build();
		return response;
    }
    
    //has username, password, token infomration
    @POST
    @Path("login")
    public Response login(Login userCredentials){
    	System.out.println("Login Credentials: " + userCredentials);
    	
    	Response response = Response.status(Response.Status.CREATED)
				.entity(dummyService.checkCredentials(
						userCredentials.getUserName(),
						userCredentials.getPassword()
						))
				.build();
		return response;
    }
}
