package ufrn.com.AvaliacaoWeb.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name="comidas_tbl")
public class Comidas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Size(min=2, max=50, message = "Adicione um nome válido.")
    @NotBlank(message = "Os nome não podem ser em branco.")
    @NotNull(message="O nome é obrigatório")
    String nome;
    @Size(min=2, max=50,message = "Adicione uma descrição válida.")
    @NotBlank(message ="A categoria não podem ser em branco." )
    @NotNull(message="A descrição é obrigatória")
    String descricao;
    @Size(min=2, max=50,message = "Adicione uma descrição válida.")
    @NotBlank(message ="A categoria não podem ser em branco." )
    @NotNull(message="A categoria é obrigatória")
    String categoria;
    String imageUri;
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
    float preco;
    Date isDeleted;

}
