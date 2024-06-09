package de.gruppe1.studydash.configurations;

import com.auth0.jwt.exceptions.TokenExpiredException;
import de.gruppe1.studydash.exceptions.JwtAuthException;
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
                    SecurityContextHolder.getContext()
                            .setAuthentication(userAuthProvider.validateToken(authElements[1]));
                } catch (JwtAuthException e) {
                    if (e.getCause() instanceof TokenExpiredException) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().write("Anmeldetoken ist abgelaufen. Bitte erneut einloggen.");
                        return;
                    } else {
                        SecurityContextHolder.clearContext();
                        throw e;
                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
