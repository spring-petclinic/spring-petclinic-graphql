package org.springframework.samples.petclinic.security;

import com.nimbusds.jose.jwk.RSAKey;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 *
 * Based on Code from Dan Vega: https://github.com/danvega/jwt-username-password/blob/master/src/main/java/dev/danvega/jwt/security/Jwks.java
 */
@Component
public class RSAKeyProvider {
    private static final Logger log = LoggerFactory.getLogger(RSAKeyProvider.class);

    private final String privateKeyString;
    private final String publicKeyString;

    private RSAKey rsaKey;

    public RSAKeyProvider(@Value("${publicKey}") String publicKeyString, @Value("${privateKey}") String privateKeyString) {
        this.privateKeyString = privateKeyString;
        this.publicKeyString = publicKeyString;
    }

    public RSAKey getRsaKey() {
        return rsaKey;
    }

    @PostConstruct
    private void generateRsaKey() throws Exception {
        log.info("Generating RSA KEY......");
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyString));
        RSAPrivateKey privateKey = (RSAPrivateKey) kf.generatePrivate(privateSpec);

        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyString));
        RSAPublicKey publicKey = (RSAPublicKey) kf.generatePublic(keySpecX509);

        this.rsaKey = new RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .keyID(UUID.randomUUID().toString())
            .build();
    }
}
