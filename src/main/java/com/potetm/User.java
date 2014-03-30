package com.potetm;

import java.util.List;
import java.util.Optional;

public class User {
    private Library library;

    public User(Library library) {
        this.library = library;
    }

    public Optional<Library> getLibrary() {
        return Optional.ofNullable(library);
    }

    public static class Library {
        private Song song;
        private List<Song> songs;

        public Library(Song song) {
            this.song = song;
        }

        public Library(List<Song> songs) {
            this.songs = songs;
        }

        public Optional<Song> getSong() {
            return Optional.ofNullable(song);
        }

        public Optional<List<Song>> getSongs() {
            return Optional.ofNullable(songs);
        }
    }

    public static class Song {
        public Album album;

        public Song(Album album) {
            this.album = album;
        }

        public Optional<Album> getAlbum() {
            return Optional.ofNullable(album);
        }
    }

    public static class Album {
        public Artist artist;

        public Album(Artist artist) {
            this.artist = artist;
        }

        public Optional<Artist> getArtist() {
            return Optional.ofNullable(artist);
        }
    }

    public static class Artist {
        public String name;

        public Artist(String name) {
            this.name = name;
        }

        public Optional<String> getName() {
            return Optional.ofNullable(name);
        }
    }
}
