//package com.webchat.webchat.filter;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;
//
//import static java.util.Arrays.stream;
//import static org.springframework.http.HttpStatus.FORBIDDEN;
//
///**
// * @author GMO_ThanhND
// * @version 1.0
// * @since 11/16/2021
// */
//@Slf4j
//public class CustomerAuthorizationFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, ServletException, ServletException {
//        System.out.println("on filter");
//        System.out.println(request.getRequestURI());
//
//        if (request.getRequestURI().contains("/api/admin")) {
//            System.out.println("on filter 123");
//            String authorizationHeader = request.getHeader("Authorization");
//            System.out.println(authorizationHeader);
//            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//                try {
//                    System.out.println("read token");
//                    String token = authorizationHeader.substring("Bearer ".length());
//                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
//                    JWTVerifier verifier = JWT.require(algorithm).build();
//                    DecodedJWT decoderJWT = verifier.verify(token);
//                    String username = decoderJWT.getSubject();
//                    String[] roles = decoderJWT.getClaim("roles").asArray(String.class);
//                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//                    stream(roles).forEach(role -> {
//                        authorities.add(new SimpleGrantedAuthority(role));
//                    });
//                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
//                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                    filterChain.doFilter(request, response);
//                } catch (Exception e) {
//                    log.error("Error logging in: {}", e.getMessage());
//                    response.setHeader("error", e.getMessage());
//                    response.setStatus(FORBIDDEN.value());
//                    Map<String, String> errors = new HashMap<String, String>();
//                    errors.put("error_message", e.getMessage());
//                    response.setContentType("application/json");
//                    new ObjectMapper().writeValue(response.getOutputStream(), errors);
//                }
//            } else {
//                filterChain.doFilter(request, response);
//            }
//        } else {
//            filterChain.doFilter(request, response);
//        }
//    }
//}
