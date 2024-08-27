package com.appDevelopment.app.repositories;

import com.appDevelopment.app.entities.Artist;
import com.appDevelopment.app.entities.Artwork;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer> {

}
