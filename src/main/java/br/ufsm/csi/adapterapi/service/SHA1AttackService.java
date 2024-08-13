package br.ufsm.csi.adapterapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class SHA1AttackService {
    // Alfabeto alfanumérico
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    // Tamanho máximo das strings geradas para quebrar o hash, limitando o uso de recursos computacionais
    private static final int MAX_LENGTH = 20;
    // Cinco Minutos em Milisegundos
    private static final long TIME_LIMIT_MS = 10 * 60 * 1000;
    // Número de threads a serem executadas, ajustado para ser proporcional ao número de núcleos do processador
    private static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 12;

    // Método para iniciar o ataque
    public String startAttack(String hash) {
        long timeout = System.currentTimeMillis() + TIME_LIMIT_MS;
        // Contador de recursão
        AtomicInteger counter = new AtomicInteger(0);
        // Notifier que indica se o hash foi ou não quebrado
        AtomicBoolean found = new AtomicBoolean(false);
        // Senha encontrada
        AtomicReference<String> foundPassword = new AtomicReference<>(null);

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        try {
            // Submete uma tarefa para cada caractere do alfabeto
            for (char c : ALPHABET.toCharArray()) {
                if (found.get()) break;
                executor.submit(() -> bruteForce(String.valueOf(c), hash, timeout, found, counter, foundPassword));
            }
        } finally {
            executor.shutdown();
            try {
                executor.awaitTermination(TIME_LIMIT_MS, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        return foundPassword.get(); // Retorna a senha encontrada, ou null se não encontrar
    }

    // Método de força bruta para quebrar o hash
    private void bruteForce(String current, String targetHash, long timeout, AtomicBoolean found, AtomicInteger counter, AtomicReference<String> foundPassword) {
        // Incrementa o contador de chamadas
        int count = counter.incrementAndGet();
        //System.out.println("Calls: " + count + " Current string: " + current);

        // Se o hash for encontrado por outra chamada recursiva ou se o tempo esgotar
        if (found.get() || System.currentTimeMillis() > timeout || current.length() > MAX_LENGTH) {
            return;
        }

            // Gerar o hash SHA-1 com a string atual
        String currentHash = hashSHA1(current);

        if (currentHash.equals(targetHash)) {
            found.set(true);
            foundPassword.set(current); // Atualiza a senha encontrada
            System.out.println("\nHash: " + targetHash + " Password: " + current + "\n");
            System.out.println("Chamadas recursivas: " + count + "\n");
            return;
        }

        // Recursão para gerar combinações de caracteres
        for (char c : ALPHABET.toCharArray()) {
            if (found.get()) break;
            bruteForce(current + c, targetHash, timeout, found, counter, foundPassword);
        }
    }

    // Método que converte uma string em um hash SHA-1
        private String hashSHA1(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
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
