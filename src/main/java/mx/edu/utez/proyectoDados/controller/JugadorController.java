package mx.edu.utez.proyectoDados.controller;

import jakarta.validation.Valid;
import mx.edu.utez.proyectoDados.controller.Dtos.JugadorDTO;
import mx.edu.utez.proyectoDados.controller.Dtos.RecargaDTO;
import mx.edu.utez.proyectoDados.model.jugador.Jugador;
import mx.edu.utez.proyectoDados.model.partida.Partida;
import mx.edu.utez.proyectoDados.service.JugadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin({"*"})
@RequestMapping("/jugadores")
public class JugadorController {

    @Autowired
    private JugadorService jugadorService;

    @PostMapping
    public ResponseEntity<Jugador> crear(@RequestBody @Valid JugadorDTO body) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(jugadorService.crearJugador(body));
    }

    @GetMapping
    public ResponseEntity<List<Jugador>> obtenerTodos() {
        return ResponseEntity.ok(jugadorService.obtenerJugadores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jugador> obtenerUno(@PathVariable Long id) {
        return ResponseEntity.ok(jugadorService.buscarJugador(id));
    }

    @PutMapping("/{id}/recargar")
    public ResponseEntity<Jugador> recargarSaldo(@PathVariable Long id, @RequestBody @Valid RecargaDTO body) {
        return ResponseEntity.ok(jugadorService.recargarSaldo(id, body));
    }

    @GetMapping("/{id}/partidas")
    public ResponseEntity<List<Partida>> obtenerHistorialPartida(@PathVariable Long id) {
        return ResponseEntity.ok(jugadorService.obtenerHistorialPartida(id));
    }
}
