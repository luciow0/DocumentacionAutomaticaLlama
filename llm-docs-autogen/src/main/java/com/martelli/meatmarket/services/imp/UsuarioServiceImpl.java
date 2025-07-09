package com.martelli.meatmarket.services.imp;

import com.martelli.meatmarket.dtos.UsuarioRequest;
import com.martelli.meatmarket.dtos.UsuarioResponse;
import com.martelli.meatmarket.dtos.UsuarioUpdate;
import com.martelli.meatmarket.model.entidades.Usuario;
import com.martelli.meatmarket.repository.UsuarioRepository;
import com.martelli.meatmarket.services.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repo;
    private final PasswordEncoder encoder;

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponse> listar() {
        return repo.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public UsuarioResponse alternarEstado(Long id) {
        Usuario u = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        u.setActivo(!u.isActivo());

        return toDTO(repo.save(u));
    }


    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponse> listar(Boolean activo) {
        return (activo == null ? repo.findAll() : repo.findByActivo(activo)).stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponse obtener(Long id) {
        Usuario u = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        return toDTO(u);
    }

    @Override
    @Transactional
    public UsuarioResponse crear(UsuarioRequest dto) {
        if (repo.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        Usuario u = Usuario.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .rol(dto.getRol())
                .activo(true)
                .build();

        return toDTO(repo.save(u));
    }


    @Override
    @Transactional
    public UsuarioResponse actualizar(Long id, UsuarioUpdate dto) {
        if (dto.getRol() == null && dto.getActivo() == null
                && dto.getNombre() == null && dto.getApellido() == null) {
            throw new IllegalArgumentException("Debe enviar al menos un campo para actualizar");
        }

        Usuario u = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        if (dto.getRol() != null) u.setRol(dto.getRol());
        if (dto.getActivo() != null) u.setActivo(dto.getActivo());
        if (dto.getNombre() != null) u.setNombre(dto.getNombre());
        if (dto.getApellido() != null) u.setApellido(dto.getApellido());

        return toDTO(repo.save(u));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Usuario no encontrado");
        }
        repo.deleteById(id);
    }

    // ---- util privado ----
    private UsuarioResponse toDTO(Usuario u) {
        return UsuarioResponse.builder()
                .id(u.getId())
                .nombre(u.getNombre())
                .apellido(u.getApellido())
                .email(u.getEmail())
                .rol(u.getRol())
                .activo(u.isActivo())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioResponse> listar(Boolean activo, String search, Pageable pageable) {
        Page<Usuario> page;

        if (activo != null && search != null && !search.isBlank()) {
            page = repo.findByActivoAndNombreContainingIgnoreCase(activo, search, pageable);
        } else if (activo != null) {
            page = repo.findAllByActivo(activo, pageable);
        } else if (search != null && !search.isBlank()) {
            page = repo.findByNombreContainingIgnoreCaseOrEmailContainingIgnoreCase(
                    search, search, pageable);
        } else {
            page = repo.findAll(pageable);
        }

        return page.map(this::toDTO);
    }

    @Override
    @Transactional
    public void resetPassword(Long id, String nuevaPassword) {
        Usuario u = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        u.setPassword(encoder.encode(nuevaPassword));
        repo.save(u);
    }




}
