package org.example.config;

import org.apache.kafka.common.security.oauthbearer.OAuthBearerToken;

import java.time.Instant;
import java.util.Collections;
import java.util.Set;

public class Token implements OAuthBearerToken {

    private final String tokenValue;
    private final Instant lifetime;
    private final String principalName;
    private final Set<String> scopes;

    public Token(String tokenValue, Instant lifetime, String principalName, Set<String> scopes) {
        this.tokenValue = tokenValue;
        this.lifetime = lifetime;
        this.principalName = principalName;
        this.scopes = scopes != null ? scopes : Collections.emptySet();
    }

    @Override
    public String value() {
        return tokenValue;
    }

    @Override
    public long lifetimeMs() {
        return lifetime.toEpochMilli();
    }

    @Override
    public String principalName() {
        return principalName;
    }

    @Override
    public Set<String> scope() {
        return scopes;
    }

    @Override
    public Long startTimeMs() {
        return null;
    }
}
