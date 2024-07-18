package org.example.crud_template.config.jwt;

import org.example.crud_template.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {


    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = getJWTFromHttpRequest(request);
            if(token != null && jwtTokenProvider.validateToken(token)){
                System.out.println("token validated");

                String userName = jwtTokenProvider.getUsernameFromToken(token);
                UserDetails userDetails = userService.loadUserByUsername(userName);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request,response);
        }catch (AuthenticationException e){
            jwtAuthenticationEntryPoint.commence(request,response,e);
        }



    }


    private String getJWTFromHttpRequest(HttpServletRequest httpServletRequest){
        String bearerToken = httpServletRequest.getHeader("Authorization");

        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth");
//        return path.equals("/api/auth/login") || path.equals("/api/auth/register");
    }
}
