package com.appDevelopment.app.controller;

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
@RequestMapping("art")
public class ArtController {
    @Autowired
    ArtWorkRepository artWorkRepository;


    // GET /api/artworks/forsale
    @GetMapping("/forsale")
    public List<Artwork> getArtworksForSale() {
        return artWorkRepository.findBySoldFalse();
    }

    // GET /api/artworks/mostexpensive
    @GetMapping("/mostexpensive")
    public List<Artwork> getMostExpensiveArtworks() {
        return artWorkRepository.findBySoldTrueOrderByPriceDesc();
    }

    // POST /api/artworks
    @PostMapping
    public ResponseEntity<Artwork> addArtwork(@Validated @RequestBody Artwork artwork) {
        return new ResponseEntity<>(artWorkRepository.save(artwork), HttpStatus.CREATED);
    }

    // DELETE /api/artworks/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtwork(@PathVariable int id) {
        if (artWorkRepository.existsById(id)) {
            artWorkRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // PATCH /api/artworks/{id}/price
    @PatchMapping("/{id}/price")
    public ResponseEntity<Artwork> changePrice(@PathVariable ("id")int id, @RequestBody double newPrice) {
        try {
            Artwork artwork = artWorkRepository.findArtWorkById(id);

            if ( artwork!=null){
              artwork.setPrice(newPrice);
              return new ResponseEntity<>(artwork,HttpStatus.CREATED);

          }else {
              new ResponseEntity<>(HttpStatus.BAD_REQUEST);
          }


        }catch (Exception e){
            System.out.println(e);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // PATCH /api/artworks/{id}/sold
    @PatchMapping("/{id}/sold")
    public ResponseEntity<Artwork> markAsSold(@PathVariable int id, @RequestBody boolean status) {
        try {
            Artwork artwork = artWorkRepository.findArtWorkById(id);
            if (artwork!= null){
                artwork.setSold(status);
                artWorkRepository.save(artwork);
                return new ResponseEntity<>(artwork,HttpStatus.CREATED);
            }

        }catch (Exception e){
        System.out.println(e);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}


