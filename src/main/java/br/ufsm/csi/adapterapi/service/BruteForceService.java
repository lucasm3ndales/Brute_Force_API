package br.ufsm.csi.adapterapi.service;

import br.ufsm.csi.adapterapi.adapter.DictionaryBruteForceAdapter;
import br.ufsm.csi.adapterapi.adapter.SimpleBruteForceAdapter;
import br.ufsm.csi.adapterapi.model.HashReqDTO;
import br.ufsm.csi.adapterapi.model.HashResDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

// Serviço comum ao controller para efetuar o brute force attack.

@Service
@AllArgsConstructor
public class BruteForceService {


    private final SimpleBruteForceAdapter simpleBruteForceAdapter;
    private final DictionaryBruteForceAdapter dictionaryBruteForceAdapter;

    public HashResDTO bruteForce(HashReqDTO dto) {

        if(evaluateHash(dto.hash())) {
            // Hash Simples.
            String password = simpleBruteForceAdapter.bruteForce(dto.hash());

            if(password == null) {
                password = dictionaryBruteForceAdapter.bruteForce(dto.hash());
            }

           return HashResDTO.builder().password(password).build();
        }
        // Hash Complexo, Inviável tentar quebrar a criptografia.
        return null;
    }

    // Verifica a complexidade do hash.
    private boolean evaluateHash(String hash) {
        return hash != null && hash.length() <= 32;
    }

}
