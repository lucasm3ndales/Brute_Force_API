package br.ufsm.csi.adapterapi.adapter;

import br.ufsm.csi.adapterapi.model.BruteForceAttack;
import br.ufsm.csi.adapterapi.service.SHA1AttackService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class SHA1AttackAdapter implements BruteForceAttack {
    // SHA1AttackService
    private final SHA1AttackService sha1AttackService;

    // Chama o serviço responsável por iniciar o ataque SHA-1
    @Override
    public String attack(String hash) {
        return sha1AttackService.startAttack(hash);
    }


}
