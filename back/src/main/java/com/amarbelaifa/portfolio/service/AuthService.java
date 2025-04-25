package com.amarbelaifa.portfolio.service;

import com.amarbelaifa.portfolio.model.dto.LoginRequest;
import com.amarbelaifa.portfolio.model.dto.LoginResponse;
import com.amarbelaifa.portfolio.model.dto.RegisterRequest;
import com.amarbelaifa.portfolio.model.User;
import com.amarbelaifa.portfolio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String KEYCLOAK_URL = "http://localhost:7080/realms/master/protocol/openid-connect/token";
    private final String CLIENT_ID = "springboot-client";
    private final String CLIENT_SECRET = "MIICsTCCAZkCBgGWZJXkSzANBgkqhkiG9w0BAQsFADAcMRowGAYDVQQDDBFzcHJpbmdib290LWNsaWVudDAeFw0yNTA0MjMyMTM2MDdaFw0zNTA0MjMyMTM3NDdaMBwxGjAYBgNVBAMMEXNwcmluZ2Jvb3QtY2xpZW50MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqSOXHGPTIRHgrVZgWxm96KZPO+tJhjT1nGZLCMetNEqnEqSkzejTFDyb/6MoPm73GVjFoFl5x/KziAV6JWLqk7O6/G9uJWXqgxmNQPDe7bK+wSucg6Y3AqB1+8hJCHotkJF613knOFC/3rZBHFEIM/eg4U4xzpfJMrN+jxyJ7aq65p2o74q8FtMmuBw+bsGaX6PW9keqVmm4tkYAlWzPwjgeg38onZKORFICaj284o+n1xvb32NrkNZp730kNICImCy8OPQYqwQaNsQFebjg3JB40Oa4SfSYUjMo4GqAWqA2TeR0Bli/paR3b/0HAco2SlkH6HAbAZ25ULBU6Tj4fwIDAQABMA0GCSqGSIb3DQEBCwUAA4IBAQBoXt/LLX9Zmbb7sIKVj+i92uLYEhiVyjvTE0Dhiuhujmu8C5qSpg8+UhFlrVj5UrIvFncfomlqQxliP1/doTMo2+3TFdtjKyqfrfuiDyMqhYiK7buNeAeJy7qS6gMNaEG1sMg+vmUA+tZBO5YRkI8bW4cLfAweU87CTsgVYBKQBxKQ/II02YpQYuSpx0t06bsY3gRr4rrBmcz4LzIsB2zB1P1C4dboxxBQYPhBWP1Qs8qnrH6BVKTRLrAJWB4QI1u8blQUKXBWCdEqfxvaiy/C25eGJMluHbnXTEDaupfUDTZScaDsXDcdJM7xyIS/85aWDGKW+3hAQlskrt+/kqwe"; // à remplacer par ton vrai secret

    public LoginResponse login(LoginRequest request) {
        try {
            // Appel Keycloak pour obtenir un token
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("client_id", CLIENT_ID);
            formData.add("client_secret", CLIENT_SECRET);
            formData.add("grant_type", "password");
            formData.add("username", request.getEmail());
            formData.add("password", request.getPassword());

            HttpEntity<MultiValueMap<String, String>> httpRequest = new HttpEntity<>(formData, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(KEYCLOAK_URL, httpRequest, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                String token = (String) response.getBody().get("access_token");
                return new LoginResponse("Login successful", token);
            } else {
                return new LoginResponse("Login failed", null);
            }

        } catch (Exception e) {
            return new LoginResponse("Login error: " + e.getMessage(), null);
        }
    }

    public LoginResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return new LoginResponse("Email already exists");
        }

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(request.getPassword()); // ou encode selon logique (mais Keycloak peut aussi gérer les utilisateurs)
        userRepository.save(newUser);

        return new LoginResponse("Registration successful");
    }

    public LoginResponse logout() {
        return new LoginResponse("Logout successful");
    }
}
