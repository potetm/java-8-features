package com.potetm;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static com.potetm.User.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OptionalTest {
    @Test
    public void get() {
        Optional optional = Optional.of(137);
        assertEquals(137, optional.get());
    }

    @Test
    public void isPresent() {
        Optional optional = Optional.of(137);
        Integer result;

        if (optional.isPresent()) {
            result = 1_000_000_000;
        } else {
            result = -1;
        }

        assertEquals(new Integer(1_000_000_000), result);
    }

    @Test
    public void ifPresent() {
        Optional<Integer> optional = Optional.of(137);
        optional.ifPresent(System.out::println);
    }

    @Test
    public void orElse() {
        Optional<Integer> optional = Optional.empty();
        assertEquals(new Integer(42), optional.orElse(42));
    }

    @Test(expected = IllegalAccessException.class)
    public void orElseThrow() throws IllegalAccessException {
        Optional<Integer> optional = Optional.empty();
        optional.orElseThrow(IllegalAccessException::new);
    }

    @Test
    public void mapping() {
        Optional<Integer> optional = Optional.of(137);
        assertEquals(new Integer(1370), optional.map((i) -> i * 10).orElse(-1));
    }

    @Test
    public void filtering() {
        Optional<Integer> optional = Optional.of(137);
        assertEquals(new Integer(-1), optional.filter((i) -> i < 100).orElse(-1));
    }

    @Test
    public void flatMap() {
        Album album = new Album(new Artist("Peter Fox"));
        assertEquals(Optional.of("Peter Fox"), album.getArtist().map(Artist::getName).get());
        assertEquals("Peter Fox", album.getArtist().flatMap(Artist::getName).orElse("HAHA! Sucka."));
    }

    @Test
    public void chaining() {
        User user = new User(new Library(new Song(new Album(new Artist("Vampire Weekend")))));
        String artistName = user.getLibrary()
                .flatMap(Library::getSong)
                .flatMap(Song::getAlbum)
                .flatMap(Album::getArtist)
                .flatMap(Artist::getName)
                .orElse("You GOT no artist");

        assertEquals("Vampire Weekend", artistName);
    }

    @Test
    public void chainingWithNull() {
        User user = new User(new Library(new Song(null)));
        String artistName = user.getLibrary()
                .flatMap(Library::getSong)
                .flatMap(Song::getAlbum)
                .flatMap(Album::getArtist)
                .flatMap(Artist::getName)
                .orElse("You GOT no artist");

        assertEquals("You GOT no artist", artistName);
    }

    @Test
    public void chainingWithCollections() {
        List<Song> songs = Arrays.asList(
                new Song(new Album(new Artist("Vampire Weekend"))),
                new Song(new Album(new Artist("Charles Mingus"))),
                new Song(new Album(new Artist("Kanye")))
        );

        User user = new User(new Library(songs));
        List<String> artists = user.getLibrary()
                .flatMap(Library::getSongs)
                .orElseGet(Collections::emptyList)
                .stream()
                .map((song) -> song.getAlbum()
                        .flatMap(Album::getArtist)
                        .flatMap(Artist::getName)
                        .orElse("You GOT no artist"))
                .collect(Collectors.toList());

        assertEquals(Arrays.asList("Vampire Weekend", "Charles Mingus", "Kanye"), artists);
    }

    @Test
    public void chainingWithCollections_nullCollection() {
        User user = new User(new Library((List<Song>) null));
        List<String> artists = user.getLibrary()
                .flatMap(Library::getSongs)
                .orElseGet(Collections::emptyList)
                .stream()
                .map((song) -> song.getAlbum()
                        .flatMap(Album::getArtist)
                        .flatMap(Artist::getName)
                        .orElse("You GOT no artist"))
                .collect(Collectors.toList());

        assertTrue(artists.isEmpty());
    }

    @Test
    public void chainingWithCollections_nullItem() {
        List<Song> songs = Arrays.asList(
                new Song(new Album(new Artist("Vampire Weekend"))),
                new Song(null),
                new Song(new Album(new Artist("Kanye")))
        );

        User user = new User(new Library(songs));

        List<String> artists = user.getLibrary()
                .flatMap(Library::getSongs)
                .orElseGet(Collections::emptyList)
                .stream()
                .map((song) -> song.getAlbum()
                        .flatMap(Album::getArtist)
                        .flatMap(Artist::getName)
                        .orElse("You GOT no artist"))
                .collect(Collectors.toList());

        assertEquals(Arrays.asList("Vampire Weekend", "You GOT no artist", "Kanye"), artists);
    }
}
