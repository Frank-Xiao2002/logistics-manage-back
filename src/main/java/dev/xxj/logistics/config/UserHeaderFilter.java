package dev.xxj.logistics.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/**
 * Filter to add the current logged-in user to the response headers.
 * <p>
 * It extends from {@link OncePerRequestFilter} so that the current logged-in user
 * is added to the response headers in every {@link HttpServletResponse}.
 *
 * @author Frank-Xiao
 * @see OncePerRequestFilter
 * @see HttpServletResponse
 */
@Component
public class UserHeaderFilter extends OncePerRequestFilter {

    /**
     * Add the current logged-in user to the response headers
     *
     * @param request     The request to process
     * @param response    The response associated with the request
     * @param filterChain Provides access to the next filter in the chain for this filter
     *                    to pass the request and response to for further processing
     * @throws ServletException If an exception has occurred that interferes with the filter's normal operation
     * @throws IOException      If an I/O error occurs during the processing of the request
     */
    @SuppressWarnings("NullableProblems")
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
