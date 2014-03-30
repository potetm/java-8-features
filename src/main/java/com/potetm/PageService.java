package com.potetm;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.potetm.Page.*;

public class PageService {
    public static Metrics DUMMY_METRIC = new Metrics(4932, 30845l);
    public static List<Summary> DUMMY_SUMMARIES = Arrays.asList(
            new Summary(2357l, "Summary A"),
            new Summary(2358l, "Summary B"),
            new Summary(2359l, "Summary C")
    );
    public static List<Summary> DUMMY_COMBINED = DUMMY_SUMMARIES.stream().map(
            (s) -> new Summary(s.accountId, s.description, PageService.DUMMY_METRIC)
    ).collect(Collectors.toList());

    public static Author getAuthorByName(String name) {
        sleep(10);
        return new Author(name);
    }

    public static Metrics getMetricsByAuthor(Author a) {
        sleep(500);
        return DUMMY_METRIC;
    }

    public static List<Summary> getSummariesByAuthor(Author a) {
        sleep(200);
        return DUMMY_SUMMARIES;
    }

    private static void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
