package br.ufsm.csi.adapterapi.factory;

import br.ufsm.csi.adapterapi.adapter.MD5AttackAdapter;
import br.ufsm.csi.adapterapi.adapter.SHA1AttackAdapter;
import br.ufsm.csi.adapterapi.model.Algorithm;
import br.ufsm.csi.adapterapi.model.BruteForceAttack;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BruteForceAttackFactory {
    private ApplicationContext applicationContext;

    public BruteForceAttack getAdapter(Algorithm algorithm) {
        return switch (algorithm) {
            case Algorithm.MD5 -> applicationContext.getBean(MD5AttackAdapter.class);
            case Algorithm.SHA1 -> applicationContext.getBean(SHA1AttackAdapter.class);
            default -> null;
        };
    }

}
