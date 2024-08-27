package com.appDevelopment.app.services;

import com.appDevelopment.app.repositories.ArtWorkRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class ArtWorkService {
    @Getter
    @Setter
    private final ArtWorkRepository artWorkRepository;

}
