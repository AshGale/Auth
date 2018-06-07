package Auth.Basic;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

import Auth.Data.DummyService;
import sun.util.locale.StringTokenIterator;
//import sun.util.locale.StringTokenIterator;

@Provider
public class AuthFilter implements ContainerRequestFilter {

	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
	private static final String AUTHORIZATION_URL_SECURE = "secure";
	private static final String AUTHORIZATION_URL_LOGIN = "Login";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {		
		
		System.out.printf("requested URI %s\n", requestContext.getUriInfo().getRequestUri());
		if (requestContext.getUriInfo().getRequestUri().getPath().contains(AUTHORIZATION_URL_SECURE)) {
			
			List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
			System.out.println("testing request header: " + authHeader);
			if (authHeader != null && authHeader.size() > 0) {
				
				String authToken = authHeader.get(0);
				authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
				String decodedToken = Base64.decodeAsString(authToken);
				StringTokenIterator tokenizer = new StringTokenIterator(decodedToken, ":");
				String username = tokenizer.first();
				String password = tokenizer.next();

				System.out.format("token: %s\tuser: %s\tpassword: %s\n", decodedToken, username, password);
				
				//
				DummyService dummyService = new DummyService();
				boolean validCredentials = dummyService.checkCredentials(username,password);
				
				if(validCredentials) {
					System.out.println("Valid Token! :D");
					return;
				}
				else if (!validCredentials) {
					Response badPassword = Response.status(Response.Status.UNAUTHORIZED)
							.entity("Not a valid user!").build();
					requestContext.abortWith(badPassword);
				}
				else {
					Response noUser = Response.status(Response.Status.UNAUTHORIZED)
							.entity("Not a valid user!").build();
					requestContext.abortWith(noUser);
				}
				
//				if ("user".equals(username)) {
//					if ("password".equals(password)) {
//						System.out.println("Valid Token! :D");
//						return;// valid token
//					} else {
//						Response badPassword = Response.status(Response.Status.UNAUTHORIZED)
//								.entity("You entered the Incorect password!").build();
//						requestContext.abortWith(badPassword);
//					}
//				} else {
//					Response noUser = Response.status(Response.Status.UNAUTHORIZED).entity("Not a valid user!").build();
//					requestContext.abortWith(noUser);
//				}

			} else {
				System.out.println("user has no autherisation");
				Response secured = Response.status(Response.Status.UNAUTHORIZED)
						.entity("No Credentials Provided For Authentication").build();
				requestContext.abortWith(secured);
			}
		}
	}

}
