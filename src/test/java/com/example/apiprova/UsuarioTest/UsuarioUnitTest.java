package com.example.apiprova.UsuarioTest;

import com.example.apiprova.user.UsuarioEntity;
import com.example.apiprova.user.UsuarioForm;
import com.example.apiprova.user.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioUnitTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    @Rollback(false)
    public void verifyUsuarioEntity_WhenHasCreated() {
        UsuarioForm usuarioForm = new UsuarioForm("Jean", "jeanrojas@email.com", "9955588744", "jean12345");

        UsuarioEntity usuarioFinal = usuarioRepository.save(UsuarioEntity.from(usuarioForm));

        assertEquals(usuarioFinal.getEmail(), usuarioForm.getEmail());
        assertEquals(usuarioFinal.getNome(), usuarioForm.getNome());
    }

    @Test
    public void verifyUsuarioEntity_WhenHasUpdated() {

        UsuarioForm usuarioSaveForm = new UsuarioForm("Jean", "jeanrojas@email.com", "9955588744", "jean12345");

        Long searchId = 1L;

        usuarioRepository.save(UsuarioEntity.from(usuarioSaveForm));

        UsuarioForm usuarioForm = new UsuarioForm("Jean", "jeanrojas@email.com", "9955588744", "jean12345");

        var modelMapper = new ModelMapper();
        UsuarioEntity usuarioFound = usuarioRepository.getById(searchId);

        modelMapper.map(usuarioForm, usuarioFound);
        UsuarioEntity usuarioFinal = usuarioRepository.save(usuarioFound);

        assertEquals(usuarioForm.getNome(), usuarioFinal.getNome());
        assertEquals(usuarioFinal.getEmail(), usuarioForm.getEmail());

    }@Test
    public void verifyUsuarioEntity_WhenHasDeleted() {
        UsuarioForm usuarioSaveForm = new UsuarioForm("Jean", "jeanrojas@email.com", "9955588744", "jean12345");

        usuarioRepository.save(UsuarioEntity.from(usuarioSaveForm));

        Long id = 2L;

        boolean isExistBeforeDelete = usuarioRepository.findById(id).isPresent();

        usuarioRepository.deleteById(id);

        boolean notExistAfterDelete = usuarioRepository.findById(id).isPresent();

        assertTrue(isExistBeforeDelete);
        assertFalse(notExistAfterDelete);
    }
}
