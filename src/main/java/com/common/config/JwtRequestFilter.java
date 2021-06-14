package com.common.config;

import com.authentication.dto.AccountDTO;
import com.authentication.service.AccountService;
import com.filter.dto.AuthorDTO;
import com.util.ReadResourceHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    private static final String ADMIN_AUTHORITY_PATH = "authorities/Admin.properties";
    private static final String MANAGER_AUTHORITY_PATH = "authorities/Manager.properties";
    private static final String APPLICATION_AUTHORITY_PATH = "authorities/Application.properties";
    private static final int ADMIN_ROLE = 1;
    private static final int MANAGER_ROLE = 2;
    private static final String USER_ID_STRING = "UserId";
    private static final String STORE_ID_STRING = "StoreId";
    private static final String ROLE_ID_STRING = "RoleId";
    private static final String AUTHORIZATION = "Authorization";
    private static final String UNAUTHORIZED = "Unauthorized";


    private final List<String> adminAuthorities;
    private final List<String> managerAuthorities;
    private final List<String> applicationAuthorities;

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    public JwtRequestFilter() {
        adminAuthorities = ReadResourceHelper.loadResource(ADMIN_AUTHORITY_PATH);
        managerAuthorities = ReadResourceHelper.loadResource(MANAGER_AUTHORITY_PATH);
        applicationAuthorities = ReadResourceHelper.loadResource(APPLICATION_AUTHORITY_PATH);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader(AUTHORIZATION);

        String username = null;
        String jwtToken = null;
        String uri = request.getRequestURI();
        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenHelper.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                logger.error("Unable to get JWT Token: " + e.getMessage());
            } catch (ExpiredJwtException e) {
                logger.error("JWT Token has expired: " + e.getMessage());
            }
        }
        else if(applicationAuthorities.contains(uri)){
            logger.warn("Init page openai");
        }
        else{
            response.setHeader(AUTHORIZATION, UNAUTHORIZED);
            return;
        }

        // Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            AccountDTO accountDTO = this.accountService.loadUserByUsername(username);

            // if token is valid configure Spring Security to manually set
            // authentication
            if (jwtTokenHelper.validateToken(jwtToken, accountDTO)) {
                Claims claims = jwtTokenHelper.getAllClaimsFromToken(jwtToken);
                int roleId = claims.get(ROLE_ID_STRING, Integer.class);

                //manager authorities
                if(roleId == MANAGER_ROLE){
                    if(managerAuthorities.contains(uri)){
                        String storeId = claims.get(STORE_ID_STRING, String.class);
                        String userId = claims.get(USER_ID_STRING, String.class);
                        AuthorDTO authorDTO = new AuthorDTO();
                        authorDTO.setUserId(userId);
                        authorDTO.setUserName(username);
                        authorDTO.setStoreId(storeId);
                        ((HttpServletRequest) request).setAttribute("AUTHOR", authorDTO);
                    }else {
                        response.setHeader(AUTHORIZATION, UNAUTHORIZED);
                        return;
                    }
                }else if(roleId == ADMIN_ROLE){
                    if(!adminAuthorities.contains(uri)){
                        response.setHeader(AUTHORIZATION, UNAUTHORIZED);
                        return;
                    }
                }

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        accountDTO, null, null);
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, UNAUTHORIZED);
            }
        }
        filterChain.doFilter(request, response);
    }
}
