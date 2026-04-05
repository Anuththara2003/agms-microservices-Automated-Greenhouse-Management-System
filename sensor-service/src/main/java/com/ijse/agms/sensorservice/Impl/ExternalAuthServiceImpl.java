package com.ijse.agms.sensorservice.Impl;

import com.ijse.agms.sensorservice.ExternalAuthService.ExternalAuthService;
import com.ijse.agms.sensorservice.dto.DeviceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExternalAuthServiceImpl implements ExternalAuthService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${external.iot.base-url}")
    private String baseUrl;

    @Value("${external.iot.username}")
    private String username;

    @Value("${external.iot.password}")
    private String password;

    private String cachedToken;

    public String getAccessToken() {
        if (cachedToken == null) {
            login();
        }
        return cachedToken;
    }

    private void login() {
        String loginUrl = baseUrl + "/auth/login";


        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("username", username);
        loginRequest.put("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(loginRequest, headers);

        try {

            ResponseEntity<Map> response = restTemplate.postForEntity(loginUrl, entity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> body = response.getBody();

                if (body.containsKey("accessToken")) {
                    this.cachedToken = (String) body.get("accessToken");
                    System.out.println("TOKEN RECEIVED: " + this.cachedToken);
                } else {
                    System.err.println("Token not found in response body!");
                }
            }
        } catch (Exception e) {
            System.err.println("Login Failed: " + e.getMessage());
        }
    }

    @Override
    public DeviceDTO registerDeviceAtExternalApi(DeviceDTO deviceDTO) {
        String url = baseUrl + "/devices";
        String token = getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<DeviceDTO> entity = new HttpEntity<>(deviceDTO, headers);

        try {

            ResponseEntity<DeviceDTO> response = restTemplate.postForEntity(url, entity, DeviceDTO.class);

            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("External Device Registration Failed: " + e.getMessage());
        }

    }
}