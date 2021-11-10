package com.example.apiprova.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    public List<UsuarioDTO> findAll(){
        List<UsuarioEntity> usuarioEntities = usuarioRepository.findAll();
        return usuarioEntities.stream().map(UsuarioDTO::from).collect(Collectors.toList());
    }

    public UsuarioDTO findById(Long id){
        return UsuarioDTO.from(usuarioRepository.findById(id).orElseThrow(() -> {
            logger.error("Id não encontrado {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }));
    }

    public UsuarioDTO create(UsuarioForm usuarioForm){
        if(usuarioRepository.findByEmail(usuarioForm.getEmail()).isPresent()){
           logger.error("Este Email Já Existe{}",usuarioForm.getEmail());
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email Já Existe");
        }
        return UsuarioDTO.from(usuarioRepository.save(UsuarioEntity.from(usuarioForm)));
    }

    public UsuarioDTO update (Long id, UsuarioForm usuarioForm){
        UsuarioEntity usuarioEntity = usuarioRepository.findById(id).orElseThrow(() -> {
        logger.error("Id não encontrado {}", id);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
        usuarioEntity.setNome(usuarioForm.getNome());
        usuarioEntity.setEmail(usuarioForm.getEmail());
        usuarioEntity.setTelefone(usuarioEntity.getTelefone());
        return UsuarioDTO.from(usuarioRepository.save(usuarioEntity));
    }

    public void delete(Long id){
        UsuarioEntity usuarioEntity = usuarioRepository.findById(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
        usuarioRepository.delete(usuarioEntity);
    }
}
