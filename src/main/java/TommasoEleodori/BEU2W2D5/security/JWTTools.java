package TommasoEleodori.BEU2W2D5.security;

import TommasoEleodori.BEU2W2D5.exceptions.UnauthorizedException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTTools {
    @Value("${spring.jwt.secret}")
    private String secret;

    public String createToken(SubjectDTO body) {
        ObjectMapper objectMapper = new ObjectMapper();
        String subject;
        try {
            subject = objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Errore durante la serializzazione del SubjectDTO", e);
        }
        return Jwts.builder().setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();
    }

    public void verifyToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build().parse(token);
        } catch (Exception e) {
            throw new UnauthorizedException("The token it's not valid! Please try again");
        }
    }

    public String extractSubject(String token) {
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .build().parseClaimsJws(token).getBody().getSubject();
    }
}
