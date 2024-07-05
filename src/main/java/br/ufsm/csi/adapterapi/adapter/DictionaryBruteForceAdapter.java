package br.ufsm.csi.adapterapi.adapter;

import br.ufsm.csi.adapterapi.model.BruteForceStrategy;
import br.ufsm.csi.adapterapi.service.DictionaryBruteForceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

// Representa meu adapter para um brute force de dicionário.

@Service
@AllArgsConstructor
public class DictionaryBruteForceAdapter implements BruteForceStrategy {
    private final DictionaryBruteForceService dictionaryBruteForceService;


    @Override
    public String bruteForce(String hash, String algorithm) {
        return dictionaryBruteForceService.dictionaryBruteForce(hash, algorithm);
    }
}
