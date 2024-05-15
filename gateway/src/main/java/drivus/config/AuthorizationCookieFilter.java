package drivus.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.ws.rs.core.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Key;

@Slf4j
@Component
public class AuthorizationCookieFilter extends AbstractGatewayFilterFactory<AuthorizationCookieFilter.Config> {

    @Value("${amp.app.jwtSecret}")
    private String jwtSecret;

    public AuthorizationCookieFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (!request.getCookies().containsKey("amp-jwt")) {
                return onError(exchange, "No JWT token in cookie");
            }

            String cookieValue = String.valueOf(request.getCookies().get("amp-jwt").getFirst()); // should be stripped of amp-jwt= prefix
            String jwtToken = cookieValue.replace("amp-jwt=", "");

            if (!isJwtValid(jwtToken)) {
                return onError(exchange, "Invalid JWT token");
            } else {
                log.info("JWT token is valid");
            }

            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        log.error(message);
        return response.setComplete();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    private boolean isJwtValid(String jwtToken) {
        boolean isValid = true;
        String subject = null;

        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key()).build();

        try {
            Jws<Claims> parsedToken = jwtParser.parseClaimsJws(jwtToken);
            subject = parsedToken.getBody().getSubject();
        } catch (Exception e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            isValid = false;
        }

        if (subject == null) {
            isValid = false;
        }

        return isValid;
    }

    public static class Config {
    }
}
