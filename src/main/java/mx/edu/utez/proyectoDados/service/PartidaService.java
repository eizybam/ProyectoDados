package mx.edu.utez.proyectoDados.service;

import mx.edu.utez.proyectoDados.controller.Dtos.IniciarPartidaDTO;
import mx.edu.utez.proyectoDados.exception.BadRequestException;
import mx.edu.utez.proyectoDados.exception.NotFoundException;
import mx.edu.utez.proyectoDados.model.jugador.Jugador;
import mx.edu.utez.proyectoDados.model.partida.Estado;
import mx.edu.utez.proyectoDados.model.partida.Partida;
import mx.edu.utez.proyectoDados.model.tiro.Tiro;
import mx.edu.utez.proyectoDados.repository.JugadorRepository;
import mx.edu.utez.proyectoDados.repository.PartidaRepository;
import mx.edu.utez.proyectoDados.repository.TiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class PartidaService {

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private JugadorRepository jugadorRepository;

    @Autowired
    private TiroRepository tiroRepository;

    @Autowired
    private JugadorService jugadorService;

    private Random random = new Random();

    public Partida buscarPartida(Long id) {
        return partidaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("La partida con id " + id + " no existe"));
    }

    public Partida iniciarPartida(IniciarPartidaDTO dto) {
        Jugador jugador = jugadorService.buscarJugador(dto.getJugadorId());

        if (!jugador.isActivo()) {
            throw new BadRequestException("El jugador no esta activo");
        }

        if (jugador.getSaldo() < dto.getApuesta()) {
            throw new BadRequestException("Saldo insuficiente");
        }

        jugador.setSaldo(jugador.getSaldo() - dto.getApuesta());
        jugadorRepository.save(jugador);

        Partida partida = new Partida();
        partida.setFecha(LocalDateTime.now());
        partida.setEstado(Estado.EN_JUEGO);
        partida.setJugador(jugador);
        partida.setApuesta(dto.getApuesta());
        return partidaRepository.save(partida);
    }

    public Tiro realizarTiro(Long partidaId) {
        Partida partida = buscarPartida(partidaId);

        if (partida.getEstado() != Estado.EN_JUEGO) {
            throw new BadRequestException("La partida no esta en juego");
        }

        int dado1 = random.nextInt(6) + 1;
        int dado2 = random.nextInt(6) + 1;
        int suma = dado1 + dado2;

        Tiro tiro = new Tiro();
        tiro.setPartida(partida);
        tiro.setValorDado1(dado1);
        tiro.setValorDado2(dado2);
        tiro.setSuma(suma);
        tiro.setEsGanador(false);

        if (suma == 7 || suma == 11) {
            tiro.setEsGanador(true);
            partida.setEstado(Estado.FINALIZADA);

            Jugador jugador = partida.getJugador();
            jugador.setSaldo(jugador.getSaldo() + partida.getApuesta() * 2);
            jugadorRepository.save(jugador);
        } else if (suma == 2 || suma == 3 || suma == 12) {
            partida.setEstado(Estado.FINALIZADA);
        }

        partidaRepository.save(partida);
        return tiroRepository.save(tiro);
    }

    public Partida finalizarPartidaManual(Long id) {
        Partida partida = buscarPartida(id);

        if (partida.getEstado() == Estado.FINALIZADA) {
            throw new BadRequestException("La partida ya esta finalizada");
        }

        partida.setEstado(Estado.FINALIZADA);
        return partidaRepository.save(partida);
    }
}
