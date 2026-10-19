package mx.edu.utez.proyectoDados.service;

import mx.edu.utez.proyectoDados.controller.Dtos.JugadorDTO;
import mx.edu.utez.proyectoDados.controller.Dtos.RecargaDTO;
import mx.edu.utez.proyectoDados.exception.NotFoundException;
import mx.edu.utez.proyectoDados.model.jugador.Jugador;
import mx.edu.utez.proyectoDados.model.partida.Partida;
import mx.edu.utez.proyectoDados.repository.JugadorRepository;
import mx.edu.utez.proyectoDados.repository.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JugadorService {

    @Autowired
    private JugadorRepository jugadorRepository;

    @Autowired
    private PartidaRepository partidaRepository;

    public Jugador crearJugador(JugadorDTO dto) {
        Jugador jugador = new Jugador();
        jugador.setNombre(dto.getNombre());
        jugador.setSaldo(dto.getSaldo());
        jugador.setActivo(dto.getActivo());
        return jugadorRepository.save(jugador);
    }

    public List<Jugador> obtenerJugadores() {
        return jugadorRepository.findAll();
    }

    public Jugador buscarJugador(Long id) {
        return jugadorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El jugador con id " + id + " no existe"));
    }

    public Jugador recargarSaldo(Long id, RecargaDTO dto) {
        Jugador jugador = buscarJugador(id);
        jugador.setSaldo(jugador.getSaldo() + dto.getMonto());
        return jugadorRepository.save(jugador);
    }

    public List<Partida> obtenerHistorialPartida(Long id) {
        buscarJugador(id);
        return partidaRepository.findByJugadorId(id);
    }
}
