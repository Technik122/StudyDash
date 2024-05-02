package de.gruppe1.studydash.configurations;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpHeaders;

import java.io.IOException;


@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserAuthProvider userAuthProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null) {
            String[] authElements = header.split(" ");

            if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
                try {

                    if ("POST".equals(request.getMethod())) {

                        SecurityContextHolder.getContext()
                                .setAuthentication(userAuthProvider.validateToken(authElements[1]));
                    } else {
                        // Wenn keine xxx-Methode, sondern PUT etc. -> strengere Validation
                        SecurityContextHolder.getContext()
                                .setAuthentication(userAuthProvider.validateTokenStrongly(authElements[1]));
                    }
                } catch (RuntimeException e) {
                    SecurityContextHolder.clearContext();
                    throw e;
                }

            }
        }
        filterChain.doFilter(request, response);
    }
}
