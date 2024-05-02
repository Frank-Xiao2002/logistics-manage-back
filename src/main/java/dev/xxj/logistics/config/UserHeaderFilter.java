package dev.xxj.logistics.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class UserHeaderFilter extends OncePerRequestFilter {

    /**
     * Add the current logged-in user to the response headers
     *
     * @param request     The request to process
     * @param response    The response associated with the request
     * @param filterChain Provides access to the next filter in the chain for this filter
     *                    to pass the request and response to for further processing
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var userPrincipal = request.getUserPrincipal();
        if (Objects.nonNull(userPrincipal)) {
            response.addHeader("Current-User", userPrincipal.getName());
        }
        filterChain.doFilter(request, response);
    }
}
