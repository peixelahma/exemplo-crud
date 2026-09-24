package com.example.exemplocrud.controller;

import com.example.exemplocrud.dto.UsuarioDTO;
import com.example.exemplocrud.exception.RecursoNaoEncontradoException;
import com.example.exemplocrud.exception.RegraDeNegocioException;
import com.example.exemplocrud.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST.
 * Camada de controle: recebe requisições HTTP, delegando lógica ao serviço.
 * Rotas: /api/usuarios
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarTodos() {
        List<UsuarioDTO> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/buscar-nome")
    public ResponseEntity<List<UsuarioDTO>> buscarPorNome(@RequestParam String nome) {
        List<UsuarioDTO> usuarios = usuarioService.buscarPorNome(nome);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/buscar-faixa-idade")
    public ResponseEntity<List<UsuarioDTO>> buscarPorFaixaIdade(
            @RequestParam Integer minIdade,
            @RequestParam Integer maxIdade) {
        List<UsuarioDTO> usuarios = usuarioService.buscarPorFaixaIdade(minIdade, maxIdade);
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTO criado = usuarioService.criar(usuarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(criado);
        } catch (RegraDeNegocioException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new UsuarioDTO(null, e.getMessage(), null, null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTO atualizado = usuarioService.atualizar(id, usuarioDTO);
            return ResponseEntity.ok(atualizado);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new UsuarioDTO(null, e.getMessage(), null, null));
        } catch (RegraDeNegocioException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new UsuarioDTO(null, e.getMessage(), null, null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        try {
            usuarioService.remover(id);
            return ResponseEntity.noContent().build();
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}