package com.potetm;

import org.junit.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.Assert.assertArrayEquals;

public class NIOTest {
    @Test
    public void listFiles() throws IOException {
        try (Stream<Path> list = Files.list(FileSystems.getDefault().getPath("/Users/potetm/bin"))) {
            assertArrayEquals(
                    new String[]{"/Users/potetm/bin/fia", "/Users/potetm/bin/ktmux", "/Users/potetm/bin/postgresql"},
                    list.map(Object::toString).toArray()
            );
        }
    }

    @Test
    public void walkPath() throws IOException {
        try (Stream<Path> list = Files.walk(FileSystems.getDefault().getPath("/Users/potetm/Music"))) {
            assertArrayEquals(MusicFiles.MUSIC_FILES, list.map(Object::toString).toArray());
        }
    }

    @Test
    public void readLines() throws IOException {
        try (Stream<String> list = Files.lines(FileSystems.getDefault().getPath("/Users/potetm/test.txt"))) {
            assertArrayEquals(new String[]{"line1", "line2", "line3"}, list.toArray());
        }
    }

    @Test
    public void funWithReadLines() throws IOException {
        try (Stream<String> list = Files.lines(FileSystems.getDefault().getPath("/Users/potetm/test.txt"))) {
            assertArrayEquals(new String[]{"line10", "line20", "line30"}, list.map((s) -> s.concat("0")).toArray());
        }
    }

    @Test(expected = UncheckedIOException.class)
    public void unchececkIO() throws IOException {
        try (Stream<Path> list = Files.walk(FileSystems.getDefault().getPath("/private/tmp"))) {
            list.toArray();
        }
    }
}
