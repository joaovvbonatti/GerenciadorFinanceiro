package com.gerenciadorfinanceiro.app.auth;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class AuthFile {

    public static final String CAMINHO = "credenciais.sec";

    public static void criarArquivoSeNaoExistir(String senhaPadrao) throws Exception {

        if (!Files.exists(Path.of(CAMINHO))) {
            String salt = AuthUtil.gerarSalt();
            String hash = AuthUtil.hashPassword(senhaPadrao, salt);

            try (FileWriter fw = new FileWriter(CAMINHO)) {
                fw.write(salt + ":" + hash);
            }

            System.out.println("Arquivo de credenciais criado.");
        }
    }

    public static boolean validarSenha(String senhaDigitada) {
        try {
            String conteudo = Files.readString(Path.of(CAMINHO));
            String[] partes = conteudo.split(":");
            String salt = partes[0];
            String hashCorreto = partes[1];

            String hashTentativa = AuthUtil.hashPassword(senhaDigitada, salt);

            return hashCorreto.equals(hashTentativa);

        } catch (Exception e) {
            return false;
        }
    }

    public static void salvarHash(String novaSenha) throws Exception {
        String salt = AuthUtil.gerarSalt();
        String hash = AuthUtil.hashPassword(novaSenha, salt);

        Files.writeString(Path.of(CAMINHO), salt + ":" + hash, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

    }
}
