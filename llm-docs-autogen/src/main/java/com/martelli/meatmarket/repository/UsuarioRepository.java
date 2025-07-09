package com.martelli.meatmarket.repository;

import com.martelli.meatmarket.model.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;


// como UsuarioRepository extiende de JpaRepository ya hereda los CRUD basicos
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByActivoTrue();
    List<Usuario> findByActivoFalse();
    List<Usuario> findByActivo(boolean activo);

    Page<Usuario> findByActivoAndNombreContainingIgnoreCase(
            boolean activo, String search, Pageable pageable);

    Page<Usuario> findAllByActivo(boolean activo, Pageable pageable);

    Page<Usuario> findByNombreContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String nombre, String email, Pageable pageable);
}

