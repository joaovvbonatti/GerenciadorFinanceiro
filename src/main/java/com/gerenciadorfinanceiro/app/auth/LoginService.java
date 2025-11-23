package com.gerenciadorfinanceiro.app.auth;

public class LoginService {

    // verifica login
    public static boolean autenticar(String senhaDigitada) {
        return AuthFile.validarSenha(senhaDigitada);
    }

    // troca a senha
    public static void redefinirSenha(String novaSenha) throws Exception {
        AuthFile.salvarHash(novaSenha);
    }
}
