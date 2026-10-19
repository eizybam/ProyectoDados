package mx.edu.utez.proyectoDados.controller.Dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JugadorDTO {
    @NotBlank(message = "el nombre es obligatorio")
    @Size(min = 3, message = "El nombre debe tener almenos 3 caracteres")
    private String nombre;

    @NotNull(message = "El saldo es obligatorio")
    @PositiveOrZero(message = "El saldo no puede ser negativo")
    private Double saldo;

    @NotNull(message = "Falta indicar si esta activo")
    private Boolean activo;
}
