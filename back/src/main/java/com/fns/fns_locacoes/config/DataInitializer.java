package com.fns.fns_locacoes.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fns.fns_locacoes.model.Usuario;
import com.fns.fns_locacoes.model.enums.PerfilUsuario;
import com.fns.fns_locacoes.repository.UsuarioRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsuarios(
            UsuarioRepository usuarioRepository) {

        return args -> {
            if (usuarioRepository.count() == 0) {
                Usuario admin = new Usuario();
                admin.setNome("Administrador");
                admin.setLogin("admin");
                admin.setSenhaHash("admin");
                admin.setPerfilUsuario(PerfilUsuario.GESTOR);
                usuarioRepository.save(admin);
            }
        };
    }
}
