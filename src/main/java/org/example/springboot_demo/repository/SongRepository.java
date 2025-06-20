package org.example.springboot_demo.repository;

import org.example.springboot_demo.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
}