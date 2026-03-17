package com.repositories;

import com.entities.Commentaire;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentaireRepository extends MongoRepository<Commentaire, String> {
    List<Commentaire> findByTchId(Long tchId);
}
