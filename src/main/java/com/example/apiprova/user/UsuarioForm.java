package com.example.apiprova.user;

import lombok.*;

import javax.validation.constraints.Email;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class UsuarioForm {

    private String nome;
    @Email
    private String email;
    private String telefone;
    private String senha;

}
