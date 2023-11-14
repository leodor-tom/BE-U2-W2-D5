package TommasoEleodori.BEU2W2D5.security;


import TommasoEleodori.BEU2W2D5.exceptions.UnauthorizedException;
import TommasoEleodori.BEU2W2D5.users.User;
import TommasoEleodori.BEU2W2D5.users.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools tools;
    @Autowired
    private UserService us;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String autHeader = request.getHeader("Authorization");
        if (autHeader == null || !autHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Please add the Bearer Token in the Authorization header");
        } else {
            String token = autHeader.substring(7);
            tools.verifyToken(token);
            String id = tools.extractSubject(token);
            User currentU = us.findById(UUID.fromString(id));
            Authentication authentication = new UsernamePasswordAuthenticationToken(currentU, null, currentU.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
