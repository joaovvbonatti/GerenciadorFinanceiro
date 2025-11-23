package com.gerenciadorfinanceiro.app.auth;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class AuthUtil {

    public static String gerarSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hashPassword(String senha, String salt) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(Base64.getDecoder().decode(salt));
        byte[] hashed = digest.digest(senha.getBytes());
        return Base64.getEncoder().encodeToString(hashed);
    }
}
