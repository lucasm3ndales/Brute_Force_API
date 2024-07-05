package br.ufsm.csi.adapterapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@AllArgsConstructor
public class MD5AttackService {
    // Alfabeto alfanumérico
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    // Tamanho máximo das strings geradas para quebrar o hash, limitando o uso de recursos computacionais
    private static final int MAX_LENGTH = 6;
    // Cinco Minutos em Milisegundos
    private static final long TIME_LIMIT_MS = 5 * 60 * 1000;

    // Método para iniciar o ataque
    public String startAttack(String hash) {
        var timeout = System.currentTimeMillis() + TIME_LIMIT_MS;
        AtomicBoolean found = new AtomicBoolean(false);
        return bruteForce("", hash, timeout, found);
    }

    private String bruteForce(String current, String targetHash, long timeout, AtomicBoolean found) {

        // Se o hash for encontrado por outra chamada recursiva ou se o tempo esgotar
        if(found.get() || System.currentTimeMillis() > timeout) {
            return null;
        }

        // Se uma string for gerada com tamaho maior que o permitido
        if (current.length() > MAX_LENGTH) {
            return null;
        }

        // Gerar o hash MD5 com a string atual
        var currentHash = hashMD5(current);

        if(currentHash.equals(targetHash)) {
            found.set(true);
            return current;
        }

        // Recursão para gerar combinações de characteres
        for (char c : ALPHABET.toCharArray()) {
            String res =    bruteForce(current + c, targetHash, timeout, found);

            if(res != null) {
                return res;
            }
        }

        // Se nenhuma chamada recursiva encontrar o hash dentro das permissões ele retornar nulo
        return null;
    }

    // Método que converte uma string em um hash MD5
    private Object hashMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
