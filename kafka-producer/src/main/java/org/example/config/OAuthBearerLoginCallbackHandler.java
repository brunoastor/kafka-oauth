package org.example.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.security.auth.AuthenticateCallbackHandler;
import org.apache.kafka.common.security.oauthbearer.OAuthBearerTokenCallback;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.AppConfigurationEntry;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OAuthBearerLoginCallbackHandler implements AuthenticateCallbackHandler {

    private String clientId;
    private String clientSecret;
    private String tokenEndpointUri;

    @Override
    public void configure(Map<String, ?> configs, String saslMechanism, List<AppConfigurationEntry> jaasConfigEntries) {
        if (!"OAUTHBEARER".equals(saslMechanism)) {
            throw new IllegalArgumentException("Unsupported SASL mechanism: " + saslMechanism);
        }

        String clientId = (String) configs.get("oauth.client.id");
        String clientSecret = (String) configs.get("oauth.client.secret");
        String tokenEndpointUri = (String) configs.get("oauth.token.endpoint.uri");

        if (clientId == null || clientSecret == null || tokenEndpointUri == null) {
            throw new IllegalArgumentException("Missing required OAuth configuration properties: "
                    + "oauth.client.id, oauth.client.secret, oauth.token.endpoint.uri");
        }

        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.tokenEndpointUri = tokenEndpointUri;
    }

    @Override
    public void handle(Callback[] callbacks) throws UnsupportedCallbackException {

        for (Callback callback : callbacks) {
            if (callback instanceof OAuthBearerTokenCallback) {
                OAuthBearerTokenCallback tokenCallback = (OAuthBearerTokenCallback) callback;
                String token;
                try {
                    token = getTokenKeycloak();
                    DecodedJWT decodedJWT = JWT.decode(token);

                    Instant lifetime = Instant.ofEpochSecond(decodedJWT.getExpiresAt().getTime() / 1000);
                    String principalName = decodedJWT.getSubject();
                    Set<String> scopes = new HashSet<>(Arrays.asList(
                            decodedJWT.getClaim("scope").asString().split(" ")
                    ));

                    Token oauthToken = new Token(token, lifetime, principalName, scopes);
                    tokenCallback.token(oauthToken);
                } catch (IOException ex) {
                    Logger.getLogger(OAuthBearerLoginCallbackHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                throw new UnsupportedCallbackException(callback);
            }
        }
    }

    private String getTokenKeycloak() throws IOException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(tokenEndpointUri))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(
                        "grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecret
                ))
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(response.body());
                return jsonNode.get("access_token").asText();
            } else {
                throw new RuntimeException("Failed to obtain token from Keycloak: " + response.body());
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(OAuthBearerLoginCallbackHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException();
    }
}
