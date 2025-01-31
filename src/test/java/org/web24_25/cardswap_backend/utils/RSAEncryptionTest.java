package org.web24_25.cardswap_backend.utils;

import org.junit.jupiter.api.Test;

import java.security.Key;
import java.security.KeyPair;

import static org.junit.jupiter.api.Assertions.*;

class RSAEncryptionTest {

    @Test
    void generateKeyPairs() {
        KeyPair pair = RSAEncryption.generateKeyPairs();
        assert pair != null;
        assert pair.getPublic() != null;
        assert pair.getPrivate() != null;
        assertEquals("RSA", pair.getPublic().getAlgorithm());
        assertEquals("RSA", pair.getPrivate().getAlgorithm());
    }

    @Test
    void test_encrypt_decrypt() {
        KeyPair pair = RSAEncryption.generateKeyPairs();
        assert pair != null;
        String encrypted = RSAEncryption.encrypt("Hello World", pair.getPublic());
        System.out.println(encrypted);

        assert encrypted != null;
        String decrypted = RSAEncryption.decrypt(encrypted, pair.getPrivate());
        System.out.println(decrypted);

        assert decrypted != null;
        assertEquals("Hello World", decrypted);
    }

    @Test
    void test_publicKeyToString_stringToPublicKey() {
        KeyPair pair = RSAEncryption.generateKeyPairs();
        assert pair != null;
        String publicKeyString = RSAEncryption.publicKeyToString(pair.getPublic());
        assert publicKeyString != null;
        Key publicKey = RSAEncryption.stringToPublicKey(publicKeyString);
        assert publicKey != null;
        assertEquals(pair.getPublic(), publicKey);
    }

    @Test
    void test_privateKeyToString_stringToPrivateKey() {
        KeyPair pair = RSAEncryption.generateKeyPairs();
        assert pair != null;
        String privateKeyString = RSAEncryption.privateKeyToString(pair.getPrivate());
        assert privateKeyString != null;
        Key privateKey = RSAEncryption.stringToPrivateKey(privateKeyString);
        assert privateKey != null;
        assertEquals(pair.getPrivate(), privateKey);
    }
}