package com.example.demo.until;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class RoleCryptor {
    private static final String USER_ROLE_NAME = "ROLE_USER";

    private static final String ADMIN_ROLE_NAME = "ROLE_ADMIN";

    private static final String SECRET_KEY = "thisIsMySecretKey";

    public static String getUserRoleName() {
        return USER_ROLE_NAME;
    }

    public static String getAdminRoleName() {
        return ADMIN_ROLE_NAME;
    }

    public static String encryptRole(String role) {
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(role.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decryptRole(String encryptedRole) {
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedRole));
            return new String(decrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
