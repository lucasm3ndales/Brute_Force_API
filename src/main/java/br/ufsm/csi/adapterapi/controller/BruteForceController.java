package br.ufsm.csi.adapterapi.controller;

import br.ufsm.csi.adapterapi.model.HashReqDTO;
import br.ufsm.csi.adapterapi.model.HashResDTO;
import br.ufsm.csi.adapterapi.service.BruteForceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Controller que recebe o hash do client

@RestController
@RequestMapping("/bruteForce")
@AllArgsConstructor
public class BruteForceController {
    private final BruteForceService bruteForceService;

    @PostMapping("/attack")
    public ResponseEntity<HashResDTO> getBruteForceAttack(@RequestBody HashReqDTO request) {
        HashResDTO response = bruteForceService.getAttack(request);
        if(response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().build();
    }

}
