package br.ufsm.csi.adapterapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SimpleBruteForceService {
    private final String aplhabet = "0123456789abcdefghijklmnopqrstuvwxyz";


    public String simpleBruteForce(String hash, String algorithm) {
        char[] hashLenght = hash.toCharArray();
        return null;

    }

//    private String attack(String hash, char[] hashLenght, int index) {
//        if(index == hash.length()) {
//            String candidate = new String(hashLenght);
//            String hashed = hash(candidate);
//            if (hashed.equals(targetHash)) {
//                return candidate;
//            }
//            return null;
//        }
//    }
//
//    private String hash() {
//
//    }
}
