package com.graincare.configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

public class TokenBasedRememberMeService extends TokenBasedRememberMeServices {

	private static final String CREDENTIALS = "credentials";

	public TokenBasedRememberMeService(String key, UserDetailsService userDetailsService) {
		super(key, userDetailsService);
	}

	/**
	 * Locates the Spring Security remember me token in the request and returns its value.
	 *
	 * @param request the submitted request which is to be authenticated
	 * @return the value of the request header (which was originally provided by the cookie - API expects it in header)
	 */
	@Override
	protected String extractRememberMeCookie(HttpServletRequest request) {
		String token = request.getHeader(CREDENTIALS);
		if ((token == null) || (token.length() == 0)) {
			return null;
		}

		return token;
	}
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		cancelCookie(request, response);
		response.setStatus(HttpServletResponse.SC_OK);
	}
	
}
