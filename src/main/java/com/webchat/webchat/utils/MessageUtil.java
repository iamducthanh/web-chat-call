package com.webchat.webchat.utils;

import com.webchat.webchat.entities.Message;
import com.webchat.webchat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class MessageUtil {
    private final SecretKeyUtil secretKeyUtil;
    private final RSA2048Util rsa2048Util;
    private final MessageRepository messageRepo;
    public void saveMessageEncode(String publicKeyString, Message message) throws Exception {
        // Khởi tạo đối tượng mã hóa và giải mã
        Cipher cipher = Cipher.getInstance("RSA");
        PublicKey publicKey = rsa2048Util.base64ToPublicKey(secretKeyUtil.decrypt(publicKeyString));
        // Mã hóa dữ liệu
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(message.getContent().getBytes());
        // Chuyển đổi dữ liệu đã mã hóa sang base64
        String encryptedBase64 = Base64.getEncoder().encodeToString(encryptedBytes);
        message.setContent(encryptedBase64);
        messageRepo.save(message);
    }

    public Message decodeMessage(String privateKeyString, Message message) throws Exception {
        PrivateKey privateKey = rsa2048Util.base64ToPrivateKey(secretKeyUtil.decrypt(privateKeyString));
        // Khởi tạo đối tượng mã hóa và giải mã
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(message.getContent()));
        message.setContent(new String(decryptedBytes));
        return message;
    }
}
