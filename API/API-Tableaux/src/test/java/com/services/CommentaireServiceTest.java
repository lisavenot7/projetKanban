package com.services;

import com.dtos.CommentaireDto;
import com.entities.Commentaire;
import com.entities.Tache;
import com.entities.Compte;
import com.repositories.CommentaireRepository;
import com.repositories.CompteRepository;
import com.repositories.TacheRepository;
import com.services.impl.CommentaireServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentaireServiceTest {

    @Mock
    private CommentaireRepository commentaireRepository;

    @Mock
    private TacheRepository tacheRepository;

    @Mock
    private CompteRepository compteRepository;

    @InjectMocks
    private CommentaireServiceImpl commentaireService;

    private Commentaire commentaire;
    private CommentaireDto commentaireDto;

    @BeforeEach
    void setUp() {
        commentaire = new Commentaire();
        commentaire.setId("1");
        commentaire.setContenu("Test contenu");
        commentaire.setCptId(10);
        commentaire.setTchId(1);

        commentaireDto = new CommentaireDto();
        commentaireDto.setContenu("Test contenu");
        commentaireDto.setCptId(10);
        commentaireDto.setTchId(1);
    }

    // ========================
    // TEST SAVE
    // ========================
    @Test
    void saveCommentaire_shouldReturnSavedDto() {
        when(commentaireRepository.save(any(Commentaire.class))).thenReturn(commentaire);

        CommentaireDto result = commentaireService.saveCommentaire(commentaireDto);

        assertNotNull(result);
        verify(commentaireRepository, times(1)).save(any(Commentaire.class));
    }

    // ========================
    // TEST GET BY ID
    // ========================
    @Test
    void getCommentaireById_shouldReturnDto() {
        when(commentaireRepository.findById("1")).thenReturn(Optional.of(commentaire));

        CommentaireDto result = commentaireService.getCommentaireById("1");

        assertNotNull(result);
        assertEquals("Test contenu", result.getContenu());
    }

    @Test
    void getCommentaireById_shouldThrowException_whenNotFound() {
        when(commentaireRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () ->
                commentaireService.getCommentaireById("1")
        );
    }

    // ========================
    // TEST DELETE
    // ========================
    @Test
    void deleteCommentaire_shouldReturnTrue() {
        when(commentaireRepository.findById("1")).thenReturn(Optional.of(commentaire));

        boolean result = commentaireService.deleteCommentaire("1");

        assertTrue(result);
        verify(commentaireRepository).deleteById("1");
    }

    // ========================
    // TEST GET ALL
    // ========================
    @Test
    void getAllCommentaires_shouldReturnList() {
        when(commentaireRepository.findAll()).thenReturn(List.of(commentaire));

        List<CommentaireDto> result = commentaireService.getAllCommentaires();

        assertEquals(1, result.size());
    }

    // ========================
    // TEST UPDATE
    // ========================
    @Test
    void updateCommentaire_shouldUpdateFields() {
        when(commentaireRepository.findById("1")).thenReturn(Optional.of(commentaire));
        when(commentaireRepository.save(any(Commentaire.class))).thenReturn(commentaire);

        commentaireDto.setContenu("Updated");

        CommentaireDto result = commentaireService.updateCommentaire("1", commentaireDto);

        assertEquals("Updated", result.getContenu());
    }

    // ========================
    // TEST GET BY TACHE
    // ========================
    @Test
    void getCommentairesByTache_shouldReturnList() {
        when(commentaireRepository.findByTchId(1L)).thenReturn(List.of(commentaire));

        List<CommentaireDto> result = commentaireService.getCommentairesByTache(1L);

        assertEquals(1, result.size());
    }

    // ========================
    // TEST CREATE COMMENTAIRE
    // ========================
    @Test
    void createCommentaire_shouldWork() {

        Tache tache = new Tache();
        Compte compte = new Compte();

        when(tacheRepository.findById(1L)).thenReturn(Optional.of(tache));
        when(compteRepository.findById(10L)).thenReturn(Optional.of(compte));
        when(commentaireRepository.save(any(Commentaire.class))).thenReturn(commentaire);

        CommentaireDto result = commentaireService.createCommentaire(1L, commentaireDto);

        assertNotNull(result);
        verify(commentaireRepository).save(any(Commentaire.class));
    }

    @Test
    void createCommentaire_shouldThrow_whenTacheNotFound() {
        when(tacheRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () ->
                commentaireService.createCommentaire(1L, commentaireDto)
        );
    }

    @Test
    void createCommentaire_shouldThrow_whenCompteNotFound() {

        Tache tache = new Tache();

        when(tacheRepository.findById(1L)).thenReturn(Optional.of(tache));
        when(compteRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () ->
                commentaireService.createCommentaire(1L, commentaireDto)
        );
    }
}