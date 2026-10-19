package mx.edu.utez.proyectoDados.controller;

import jakarta.validation.Valid;
import mx.edu.utez.proyectoDados.controller.Dtos.IniciarPartidaDTO;
import mx.edu.utez.proyectoDados.model.partida.Partida;
import mx.edu.utez.proyectoDados.model.tiro.Tiro;
import mx.edu.utez.proyectoDados.service.PartidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin({"*"})
@RequestMapping("/partidas")
public class PartidaController {

    @Autowired
    private PartidaService partidaService;

    @PostMapping
    public ResponseEntity<Partida> iniciarPartida(@RequestBody @Valid IniciarPartidaDTO body) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(partidaService.iniciarPartida(body));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Partida> obtenerUna(@PathVariable Long id) {
        return ResponseEntity.ok(partidaService.buscarPartida(id));
    }

    @PostMapping("/{id}/tiro")
    public ResponseEntity<Tiro> realizarTiro(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(partidaService.realizarTiro(id));
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Partida> finalizarPartidaManual(@PathVariable Long id) {
        return ResponseEntity.ok(partidaService.finalizarPartidaManual(id));
    }
}
