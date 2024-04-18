package com.webchat.webchat.utils;

import com.webchat.webchat.entities.Message;
import com.webchat.webchat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

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
        List<String> contentSplit = Arrays.asList(message.getContent().split(" "));

        StringBuilder contentEncode = new StringBuilder();

        contentSplit.forEach(text -> {
            byte[] encryptedBytes = new byte[0];
            try {
                encryptedBytes = cipher.doFinal(text.getBytes());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            // Chuyển đổi dữ liệu đã mã hóa sang base64
            contentEncode.append(Base64.getEncoder().encodeToString(encryptedBytes) + "&");
        });
        message.setContent(contentEncode.toString());
        messageRepo.save(message);
    }

    public Message decodeMessage(String privateKeyString, Message message) throws Exception {
        PrivateKey privateKey = rsa2048Util.base64ToPrivateKey(secretKeyUtil.decrypt(privateKeyString));
        // Khởi tạo đối tượng mã hóa và giải mã
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        StringBuilder contentDecode = new StringBuilder();

        List<String> contentEncode = Arrays.asList(message.getContent().split("&"));

        contentEncode.forEach(content -> {
            byte[] decryptedBytes = new byte[0];
            try {
                decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(content));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            contentDecode.append(new String(decryptedBytes) + " ");

        });
        message.setContent(contentDecode.toString());
        return message;
    }
}
