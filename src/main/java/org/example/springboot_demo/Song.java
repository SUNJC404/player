package org.example.springboot_demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String artist;
    private String filePath;
    private String coverPath;
    private String link;
    private String releaseDate;
    private Integer played = 0;
    private Boolean available = true;

    public Song() {}
    public Song(String title, String artist, String filePath) {
        this.title = title;
        this.artist = artist;
        this.filePath = filePath;
    }
    public Song(String title, String artist, String filePath, String coverPath, String link,  String releaseDate) {
        this.title = title;
        this.artist = artist;
        this.filePath = filePath;
        this.coverPath = coverPath;
        this.link = link;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public String getLink() {
        return link;
    }

    public String getReleaseDate() { return releaseDate; }


    // Setter for filePath (already present, but good to confirm)
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // You might also want setters for title and artist if you plan to update them later
    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }

    public void plusPlayed() { played++; }

    public void statusChange(Boolean available) { this.available = available; }
}