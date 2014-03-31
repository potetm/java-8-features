package com.potetm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.potetm.Page.*;
import static org.junit.Assert.assertEquals;

public class ConcurrencyAPITest {
    private long time;

    @Before
    public void startTimer() {
        time = System.currentTimeMillis();
    }

    @After
    public void printTime() {
        long totalTime = System.currentTimeMillis() - time;

        System.err.println("Test took:" + totalTime);
    }

    @Test
    public void forLoop() throws ExecutionException, InterruptedException {
        System.err.println("forLoop");
        List<Integer> found = new ArrayList<>();
        for (int i = 1; i < 1_000_000; i++) {
            if (Primer.isPrime(i)) {
                found.add(i);
            }
        }

        assertEquals(Primer.PRIMES, found);
    }

    @Test
    public void noParallel() throws ExecutionException, InterruptedException {
        System.err.println("noParallel");
        List<Integer> found = IntStream.range(1, 1_000_000).boxed().filter(Primer::isPrime).collect(Collectors.toList());

        assertEquals(Primer.PRIMES, found);
    }

    @Test
    public void parallel() throws ExecutionException, InterruptedException {
        System.err.println("parallel");

        List<Integer> found = IntStream.range(1, 1_000_000).parallel().boxed().filter(Primer::isPrime).collect(Collectors.toList());

        assertEquals(Primer.PRIMES, found);
    }

    @Test
    public void forkJoinPool() throws ExecutionException, InterruptedException {
        System.err.println("forkJoinPool");
        ForkJoinPool commonPool = ForkJoinPool.commonPool();

        List<Integer> found = commonPool.submit(() ->
                        IntStream.range(1, 1_000_000).parallel().boxed().filter(Primer::isPrime).collect(Collectors.toList())
        ).get();

        assertEquals(Primer.PRIMES, found);
    }

    @Test
    public void completeableFuture() throws ExecutionException, InterruptedException {
        System.err.println("completeableFuture");
        CompletableFuture<Author> author = CompletableFuture.supplyAsync(() -> PageService.getAuthorByName("Tim"));
        CompletableFuture<Metrics> pageMetricPromise = author.thenApplyAsync(PageService::getMetricsByAuthor);
        CompletableFuture<List<Summary>> summariesPromise = author.thenApplyAsync(PageService::getSummariesByAuthor);

        CompletableFuture<List<Summary>> resultPromise = summariesPromise.thenCombineAsync(pageMetricPromise, (summaries, metrics) ->
                        summaries.stream().map(
                                (s) -> new Summary(s.accountId, s.description, metrics)
                        ).collect(Collectors.toList())
        );

        List<Summary> summaryFound = resultPromise.get();
        assertEquals(PageService.DUMMY_COMBINED, summaryFound);
    }

    @Test
    public void adder() {
        System.err.println("adder");
        // Some pretty charts and extra info to be found here: https://minddoterr.wordpress.com/2013/05/11/java-8-concurrency-longadder/
        final LongAdder adder = new LongAdder();
        IntStream.range(0, 1_000_000).parallel().forEach((i) -> adder.increment());

        assertEquals(adder.longValue(), 1_000_000);
    }
}
