package br.ufsm.csi.adapterapi.adapter;

import br.ufsm.csi.adapterapi.service.MD5AttackService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MD5AttackAdapter implements BruteForceAttack {
    // MD5AttackService
    private final MD5AttackService md5AttackService;

    // Chama o serviço responsável por iniciar o ataque MD5
    @Override
    public String attack(String hash) {
        return md5AttackService.startAttack(hash);
    }
}
