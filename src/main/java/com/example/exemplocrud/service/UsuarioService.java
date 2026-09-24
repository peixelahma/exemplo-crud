package com.example.exemplocrud.service;

import com.example.exemplocrud.dto.UsuarioDTO;
import com.example.exemplocrud.entity.Usuario;
import com.example.exemplocrud.exception.RecursoNaoEncontradoException;
import com.example.exemplocrud.exception.RegraDeNegocioException;
import com.example.exemplocrud.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Camada de serviço: contém a lógica de negócio.
 * - Orquestra o acesso aos repositórios
 * - Aplica regras de negócio
 * - Converte entre DTOs e Entities
 * - Não conhece detalhes de HTTP (controllers) nem de SQL (repositories)
 */
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Lista todos os usuários.
     */
    public List<UsuarioDTO> listarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um usuário por ID.
     */
    public UsuarioDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + id));
        return converterParaDTO(usuario);
    }

    /**
     * Busca usuários por nome (busca parcial, case-insensitive).
     */
    public List<UsuarioDTO> buscarPorNome(String nome) {
        List<Usuario> usuarios = usuarioRepository.findByNomeContainingIgnoreCase(nome);
        return usuarios.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca usuários por faixa de idade.
     */
    public List<UsuarioDTO> buscarPorFaixaIdade(Integer minIdade, Integer maxIdade) {
        List<Usuario> usuarios = usuarioRepository.findByIdadeBetween(minIdade, maxIdade);
        return usuarios.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    /**
     * Cria um novo usuário.
     */
    public UsuarioDTO criar(UsuarioDTO usuarioDTO) {
        // Validação de negócio: e-mail único
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new RegraDeNegocioException("Já existe um usuário cadastrado com este e-mail: " + usuarioDTO.getEmail());
        }

        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setIdade(usuarioDTO.getIdade());

        Usuario salvo = usuarioRepository.save(usuario);
        return converterParaDTO(salvo);
    }

    /**
     * Atualiza um usuário existente.
     */
    public UsuarioDTO atualizar(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + id));

        // Validação: se o e-mail mudou, verificar se não conflita com outro usuário
        if (!usuarioExistente.getEmail().equals(usuarioDTO.getEmail())
                && usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new RegraDeNegocioException("Já existe um usuário cadastrado com este e-mail: " + usuarioDTO.getEmail());
        }

        usuarioExistente.setNome(usuarioDTO.getNome());
        usuarioExistente.setEmail(usuarioDTO.getEmail());
        usuarioExistente.setIdade(usuarioDTO.getIdade());

        Usuario atualizado = usuarioRepository.save(usuarioExistente);
        return converterParaDTO(atualizado);
    }

    /**
     * Remove um usuário por ID.
     */
    public void remover(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    /**
     * Converte Entity para DTO.
     */
    private UsuarioDTO converterParaDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getIdade()
        );
    }
}