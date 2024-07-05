package br.ufsm.csi.adapterapi.adapter;

import br.ufsm.csi.adapterapi.model.BruteForceStrategy;
import br.ufsm.csi.adapterapi.service.SimpleBruteForceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

// Representa meu adapter para um brute force simples.


@Service
@AllArgsConstructor
public class SimpleBruteForceAdapter implements BruteForceStrategy {
    private final SimpleBruteForceService simpleBruteForceService;

    @Override
    public String bruteForce(String hash, String algorithm) {
        return simpleBruteForceService.simpleBruteForce(hash, algorithm);
    }
}
