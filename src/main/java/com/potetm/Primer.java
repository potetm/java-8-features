package com.potetm;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Primer {
    public static List<Integer> PRIMES;

    static {
        Properties properties = new Properties();
        try {
            properties.load(Primer.class.getResourceAsStream("primes.properties"));
            String[] primes = properties.getProperty("primes").split("\\s+");
            PRIMES = Arrays.asList(primes).parallelStream().map(Integer::parseInt).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean isPrime(int n) {
        if (n < 2) {
            return false;
        } else if (n % 2 == 0) {
            return (n == 2);
        } else {
            for (int i = 3; i * i <= n; i += 2) {
                if (n % i == 0) {
                    return false;
                }
            }

            return true;
        }
    }
}
