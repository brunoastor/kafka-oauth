package org.example.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerCreator {

    public static Producer<Long, String> createProducer() {

        Properties properties = new Properties();

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ConfigManager.getProperty("oauth.bootstrap_servers_config"));
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, ConfigManager.getProperty("oauth.server.client.id"));
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        properties.put("security.protocol", "SASL_PLAINTEXT");
        properties.put("sasl.mechanism", "OAUTHBEARER");

        properties.setProperty("oauth.client.id", ConfigManager.getProperty("oauth.server.client.id"));
        properties.setProperty("oauth.client.secret", ConfigManager.getProperty("oauth.server.client.secret"));
        properties.setProperty("oauth.token.endpoint.uri", ConfigManager.getProperty("oauth.server.base.uri") + ConfigManager.getProperty("oauth.server.token.endpoint.path"));

        properties.setProperty("sasl.jaas.config", "org.apache.kafka.common.security.oauthbearer.OAuthBearerLoginModule required "
                + "clientId=\"" + ConfigManager.getProperty("oauth.server.client.id")
                + "\" clientSecret=\"" + ConfigManager.getProperty("oauth.server.client.secret")
                + "\" oauth.token.endpoint.uri=\"" + ConfigManager.getProperty("oauth.server.base.uri")
                + "\";");

        properties.put("sasl.login.callback.handler.class", "org.example.config.OAuthBearerLoginCallbackHandler");

        return new KafkaProducer<>(properties);
    }
}
