package com.potetm;

import org.junit.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.Assert.assertArrayEquals;

/**
 *  I apologize, but *none* of these tests will work on any machine but my own. I honestly can't
 *  think of a good way around it besides writing a file structure to a tmp directory before getting
 *  started with this test, which seems like more trouble than it's worth.
 *
 *  If anyone has any better ideas, I'm more than happy to hear about them.
 *
 *  For now, suffice it to say that this test indeed runs and passes from my machine.
 */
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
