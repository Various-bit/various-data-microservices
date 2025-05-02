package org.example.scrap.config;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthorityFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String HeaderAuthority = request.getHeader("X-Authority");
        if ((request.getRequestURI().equals("/api/scrap/companies") ||
                request.getRequestURI().equals("/api/scrap/products") ||
                request.getRequestURI().equals("/api/scrap/accepted-company") ||
                request.getRequestURI().equals("/api/scrap/accepted-product")) &&
                (HeaderAuthority != null && HeaderAuthority.equals(Authority.ADMIN.name()))) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if ((request.getRequestURI().equals("/api/scrap/product") ||
                request.getRequestURI().equals("/api/scrap/company")) &&
                (HeaderAuthority != null && (HeaderAuthority.equals(Authority.ADMIN.name()) ||
                HeaderAuthority.equals(Authority.SUBSCRIBER.name())))) {
            filterChain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
