package com.appDevelopment.app.controller;

import com.appDevelopment.app.entities.Artist;
import com.appDevelopment.app.entities.Artwork;
import com.appDevelopment.app.repositories.ArtWorkRepository;
import com.appDevelopment.app.repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    @Autowired
    private ArtistRepository artistRepository;

    // GET /api/artist
    @GetMapping
    public List<Artist> getAllArtist() {
        return artistRepository.findAll();
    }

    // GET /api/artists/{id}/artwork
    @GetMapping("/{id}/artwork")
    public ResponseEntity<List<Artwork>> getArtworksByArtist(@PathVariable int id) {
        return artistRepository.findById(id)
                .map(artist ->ResponseEntity.ok(artist.getArtwork()))
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/artists
    @PostMapping
    public ResponseEntity<Artist> addArtist(@Validated @RequestBody Artist artist) {
        return new ResponseEntity<>(artistRepository.save(artist), HttpStatus.CREATED);
    }

    // DELETE /api/artists/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable int id) {
        if (artistRepository.existsById(id)) {
            artistRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

