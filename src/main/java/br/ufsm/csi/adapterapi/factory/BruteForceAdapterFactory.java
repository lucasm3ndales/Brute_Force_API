package br.ufsm.csi.adapterapi.factory;

import br.ufsm.csi.adapterapi.model.Algorithm;
import br.ufsm.csi.adapterapi.adapter.BruteForceAttack;

public interface BruteForceAdapterFactory {
    BruteForceAttack getAdapter(Algorithm algorithm);
}
