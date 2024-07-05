package br.ufsm.csi.adapterapi.service;

import br.ufsm.csi.adapterapi.factory.BruteForceAttackFactory;
import br.ufsm.csi.adapterapi.model.Algorithm;
import br.ufsm.csi.adapterapi.model.BruteForceAttack;
import br.ufsm.csi.adapterapi.model.HashReqDTO;
import br.ufsm.csi.adapterapi.model.HashResDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

// Service que inicia o ataque de brute force

@Service
@AllArgsConstructor
public class BruteForceService {
    private final BruteForceAttackFactory bruteForceAttackFactory;

    public HashResDTO getAttack(HashReqDTO dto) {

        if (evaluateHash(dto.hash()) && !dto.algorithm().isEmpty()) {
            // Hash Simples

            var algorithm = Algorithm.valueOf(dto.algorithm());

            if(algorithm == null) {
                return null;
            }

            // Factory Adapter
            BruteForceAttack attacker = bruteForceAttackFactory.getAdapter(algorithm);

            // Efetua o ataque com base no adapter do algoritmo do hash
            String password = attacker.attack(dto.hash());

            return HashResDTO.builder().password(password).build();
        }
        // Hash Complexo, Inviável tentar quebrar a criptografia (128 Bits)
        return null;
    }

    // Verifica a complexidade do hash
    private boolean evaluateHash(String hash) {
        return hash != null && hash.length() <= 64;
    }

}
