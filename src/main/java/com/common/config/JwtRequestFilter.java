package com.common.config;

import com.authentication.dto.AccountDTO;
import com.authentication.dto.AuthorDTO;
import com.authentication.service.AccountService;
import com.util.StringHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
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

    private static final int ADMIN_ROLE = 1;
    private static final int MANAGER_ROLE = 2;
    private static final String USER_ID_STRING = "UserId";
    private static final String ROLE_ID_STRING = "RoleId";
    private static final String AUTHORIZATION = "Authorization";
    private static final String UNAUTHORIZED = "Unauthorized";


    @Autowired
    private List<String> adminAuthorities;
    @Autowired
    private List<String> guestAuthorities;
    @Autowired
    private List<String> managerAuthorities;
    @Autowired
    private List<String> applicationAuthorities;

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader(AUTHORIZATION);

        String username = null;
        String jwtToken = null;
        String uri = request.getRequestURI();

        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            logger.warn("Preflight request");
        }
        else if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenHelper.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                logger.error("Unable to get JWT Token: " + e.getMessage());
                response.setHeader(AUTHORIZATION, UNAUTHORIZED);
                return;
            } catch (ExpiredJwtException e) {
                logger.error("JWT Token has expired: " + e.getMessage());
                response.setHeader(AUTHORIZATION, UNAUTHORIZED);
                return;
            } catch (SignatureException e) {
                logger.error("JWT Token invalid: " + e.getMessage());
                response.setHeader(AUTHORIZATION, UNAUTHORIZED);
                return;
            }
        } else if (applicationAuthorities.contains(uri)) {
            logger.warn("Init page openai");
        } else {
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
                if (roleId == MANAGER_ROLE) {
                    if (StringHelper.isNullOrEmpty(accountDTO.getStoreId()) && !guestAuthorities.contains(uri)) {
                        response.setHeader(AUTHORIZATION, UNAUTHORIZED);
                        return;
                    } else if (managerAuthorities.contains(uri)) {
                        String userId = claims.get(USER_ID_STRING, String.class);
                        AuthorDTO authorDTO = new AuthorDTO();
                        authorDTO.setUserId(userId);
                        authorDTO.setUserName(accountDTO.getUserName());
                        authorDTO.setStoreId(accountDTO.getStoreId());
                        request.setAttribute("AUTHOR", authorDTO);
                    } else {
                        response.setHeader(AUTHORIZATION, UNAUTHORIZED);
                        return;
                    }
                } else if (roleId == ADMIN_ROLE) {
                    if (!adminAuthorities.contains(uri)) {
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
            } else {
                response.setHeader(AUTHORIZATION, UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

}
