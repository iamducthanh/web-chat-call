package com.webchat.webchat.utils;

import org.springframework.stereotype.Component;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class RSA2048Util {
    public void main(String[] args) throws Exception {
        // Tạo cặp khóa RSA
        KeyPair keyPair = generateKeyPair();

        // Lấy khóa công khai và khóa cá nhân từ cặp khóa
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // Chuyển đổi khóa thành chuỗi Base64
        String publicKeyBase64 = keyToBase64(publicKey);
        String privateKeyBase64 = keyToBase64(privateKey);

        // In ra chuỗi Base64 của các khóa
        System.out.println("Public Key (Base64): " + publicKeyBase64);
        System.out.println("Private Key (Base64): " + privateKeyBase64);

        // Chuyển đổi chuỗi Base64 thành khóa
        PublicKey decodedPublicKey = base64ToPublicKey(publicKeyBase64);
        PrivateKey decodedPrivateKey = base64ToPrivateKey(privateKeyBase64);

        // Kiểm tra xem quá trình chuyển đổi đã thành công chưa
        System.out.println("Decoded Public Key: " + decodedPublicKey);
        System.out.println("Decoded Private Key: " + decodedPrivateKey);
    }

    // Phương thức tạo cặp khóa RSA
    public KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        return generator.generateKeyPair();
    }
    // Phương thức chuyển đổi khóa thành chuỗi Base64
    public String keyToBase64(Key key) {
        byte[] keyBytes = key.getEncoded();
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    // Phương thức chuyển đổi chuỗi Base64 thành khóa công khai
    public PublicKey base64ToPublicKey(String base64Key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    // Phương thức chuyển đổi chuỗi Base64 thành khóa cá nhân
    public PrivateKey base64ToPrivateKey(String base64Key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }
}
