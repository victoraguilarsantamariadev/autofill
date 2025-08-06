package com.licitaciones.modules.auth.services;

import com.licitaciones.modules.user.entities.User;
import com.licitaciones.modules.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    private final RestTemplate restTemplate = new RestTemplate();

    public User syncUserFromAuth0(Jwt jwt) {
        String auth0Id = jwt.getSubject();

        // Obtener información del usuario desde Auth0 /userinfo
        Map<String, Object> userInfo = getUserInfoFromAuth0(jwt.getTokenValue());

        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");

        Optional<User> existingUser = userRepository.findByAuth0Id(auth0Id);

        User user;
        if (existingUser.isPresent()) {
            user = existingUser.get();
            user.setEmail(email);
            user.setName(name);
            user.setUpdatedAt(LocalDateTime.now());
        } else {
            user = new User();
            user.setAuth0Id(auth0Id);
            user.setEmail(email);
            user.setName(name);
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
        }

        return userRepository.save(user);
    }

    private Map<String, Object> getUserInfoFromAuth0(String accessToken) {
        String userInfoEndpoint = issuerUri + "userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                userInfoEndpoint,
                HttpMethod.GET,
                entity,
                Map.class
        );

        return response.getBody();
    }

    public User getUserProfile(String auth0Id) {
        return userRepository.findByAuth0Id(auth0Id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"));
    }
}