package org.example.crud_template.filter;




import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class EmailVerificationFilter extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {

            String path = request.getRequestURI();
            if ("/api/auth/register".equals(path) || "/api/auth/login".equals(path)) {
                String email = request.getParameter("email");
                if (email == null || !email.endsWith("@gmail.com")) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email must be a Gmail address.");
                    return;
                }
            }
            filterChain.doFilter(request, response);
        }
    }