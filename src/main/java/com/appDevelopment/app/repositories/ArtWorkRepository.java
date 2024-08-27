package com.appDevelopment.app.repositories;

import aj.org.objectweb.asm.commons.Remapper;
import com.appDevelopment.app.entities.Artwork;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.yaml.snakeyaml.events.Event;

import java.util.List;
import java.util.Optional;


public interface ArtWorkRepository extends JpaRepository<Artwork,Integer> {
    List<Artwork> findBySoldFalse();
    List<Artwork> findBySoldTrueOrderByPriceDesc();
    boolean existsById(int id);
    void deleteById(int id);

    Artwork findArtWorkById(int id);
    Remapper findById(int id);
}

