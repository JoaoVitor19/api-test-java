package com.example.apiprova.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity @Table(name = "usuario")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UsuarioEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Email
    private String email;
    private String telefone;
    private String senha;

    public static UsuarioEntity from(UsuarioForm usuarioForm){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(usuarioForm, UsuarioEntity.class);
    }

}
