package com.ftn.event_hopper.security.auth;

import com.ftn.event_hopper.util.TokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//this filter will intercept every client's request towards server
//except for the paths specified in WebSecurityConfig.configure(WebSecurity web)
//Filter checks if JWT exists in Authorization header in client's request
//If exists, then checks if it is valid


//filter fill be called just once per request
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private TokenUtils tokenUtils;
    private UserDetailsService userDetailsService;
    protected final Log LOGGER = LogFactory.getLog(getClass());         //for logged messages

    public TokenAuthenticationFilter(TokenUtils tokenUtils, UserDetailsService userDetailsService) {
        this.tokenUtils = tokenUtils;
        this.userDetailsService = userDetailsService;
    }


    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String username;

        // 1. getting JWT from request
        String authToken = tokenUtils.getToken(request);

        try {

            if (authToken != null && !authToken.equals("")) {

                // 2. Getting username form JWT
                username = tokenUtils.getUsernameFromToken(authToken);

                if (username != null) {

                    // 3. Getting user by username
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    // 4. Checks if forwarded token is valid
                    if (tokenUtils.validateToken(authToken, userDetails)) {

                        // 5. Create authentication
                        TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
                        authentication.setToken(authToken);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }

        } catch (ExpiredJwtException ex) {
            LOGGER.debug("Token expired!");
        }

        // forward request to next filter
        chain.doFilter(request, response);
    }

}
