package com.martelli.meatmarket.services;

import com.martelli.meatmarket.dtos.UsuarioRequest;
import com.martelli.meatmarket.dtos.UsuarioResponse;
import com.martelli.meatmarket.dtos.UsuarioUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;


public interface UsuarioService {

    /**
     * Listar todos los usuarios sin filtros.
     */
    List<UsuarioResponse> listar();

    /**
     * Listar usuarios filtrando por estado activo/inactivo.
     */
    List<UsuarioResponse> listar(Boolean activo);

    /**
     * Listar usuarios con filtros opcionales, búsqueda y paginación.
     *
     * @param activo true/false para filtrar por estado
     * @param search texto a buscar en nombre o email
     * @param pageable parámetros de paginación
     * @return página de usuarios
     */
    Page<UsuarioResponse> listar(Boolean activo, String search, Pageable pageable);

    /**
     * Obtener los datos de un usuario por su ID.
     */
    UsuarioResponse obtener(Long id);

    /**
     * Crear un nuevo usuario.
     */
    UsuarioResponse crear(UsuarioRequest dto);

    /**
     * Actualizar rol y/o estado activo/inactivo de un usuario.
     */
    UsuarioResponse actualizar(Long id, UsuarioUpdate dto);

    /**
     * Eliminar físicamente un usuario.
     */
    void eliminar(Long id);

    /**
     * Resetear la contraseña de un usuario.
     */
    void resetPassword(Long id, String nuevaPassword);

    UsuarioResponse alternarEstado(Long id);



}

