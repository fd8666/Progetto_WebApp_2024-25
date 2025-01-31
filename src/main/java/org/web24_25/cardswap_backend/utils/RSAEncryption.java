package org.web24_25.cardswap_backend.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Logger;

import static java.nio.charset.StandardCharsets.UTF_8;

public class RSAEncryption {
    public static final Logger logger = Logger.getLogger(RSAEncryption.class.getName());
    public static KeyPair generateKeyPairs() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            return generator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            logger.severe("Error while generating key pairs: " + e.getMessage());
        }
        return null;
    }

    public static String publicKeyToString(Key publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public static Key stringToPublicKey(String publicKey) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] publicKeyBytes = decoder.decode(publicKey);
        try {
            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            logger.severe("Error while converting public key string to key: " + e.getMessage());
        }
        return null;
    }

    public static String privateKeyToString(Key privateKey) {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    public static Key stringToPrivateKey(String privateKey) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] privateKeyBytes = decoder.decode(privateKey);
        try {
            return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            logger.severe("Error while converting private key string to key: " + e.getMessage());
        }
        return null;
    }

    public static String encrypt(String secret_message, Key publicKey) {
        Cipher encryptCipher;
        try {
            encryptCipher = Cipher.getInstance("RSA");
            encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] secretMessageBytes = secret_message.getBytes(UTF_8);
            byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
            return Base64.getEncoder().encodeToString(encryptedMessageBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            logger.severe("Error while encrypting message: " + e.getMessage());
        }
        return null;
    }

    public static String decrypt(String encrypted_message, Key privateKey) {
        Cipher decryptCipher;
        try {
            decryptCipher = Cipher.getInstance("RSA");
            decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] decryptedMessageBytes = decryptCipher.doFinal(decoder.decode(encrypted_message));
            return new String(decryptedMessageBytes, UTF_8);
        } catch (NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException e) {
            logger.severe("Error while decrypting message: " + e.getMessage());
        }
        return null;
    }
}
