package com.example.exemplocrud.repository;

import com.example.exemplocrud.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório de dados JPA.
 * Camada de persistência: abstrai o acesso ao banco.
 * Spring Data JPA já fornece os métodos save, findAll, findById, delete, etc.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca usuários cujo nome contenha a string informada (case-insensitive).
     */
    List<Usuario> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca um usuário pelo e-mail.
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Verifica se existe algum usuário com o e-mail informado.
     */
    boolean existsByEmail(String email);

    /**
     * Busca usuários por faixa de idade.
     */
    @Query("SELECT u FROM Usuario u WHERE u.idade BETWEEN :minIdade AND :maxIdade")
    List<Usuario> findByIdadeBetween(@Param("minIdade") Integer minIdade,
                                     @Param("maxIdade") Integer maxIdade);
}