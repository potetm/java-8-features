package com.potetm;

public class Page {
    public static class Author {
        final String name;

        public Author(String name) {
            this.name = name;
        }
    }

    public static class Metrics {
        final Long avgMsOnPage;
        final Integer visits;

        public Metrics(Integer visits, Long avgMsOnPage) {
            this.visits = visits;
            this.avgMsOnPage = avgMsOnPage;
        }

        public Metrics() {
            this(null, null);
        }

        @Override
        public String toString() {
            return String.format("{ avgMsOnPage=%d, visits=%d }", avgMsOnPage, visits);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Metrics metrics = (Metrics) o;

            if (avgMsOnPage != null ? !avgMsOnPage.equals(metrics.avgMsOnPage) : metrics.avgMsOnPage != null)
                return false;
            if (visits != null ? !visits.equals(metrics.visits) : metrics.visits != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = avgMsOnPage != null ? avgMsOnPage.hashCode() : 0;
            result = 31 * result + (visits != null ? visits.hashCode() : 0);
            return result;
        }
    }

    public static class Summary {
        final Long accountId;
        final String description;
        final Metrics metrics;


        public Summary(Long accountId, String description, Metrics metrics) {
            this.accountId = accountId;
            this.description = description;
            this.metrics = metrics;
        }

        public Summary(Long accountId, String description) {
            this(accountId, description, null);
        }

        public Summary() {
            this(null, null, null);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Summary summary = (Summary) o;

            if (accountId != null ? !accountId.equals(summary.accountId) : summary.accountId != null) return false;
            if (description != null ? !description.equals(summary.description) : summary.description != null)
                return false;
            if (metrics != null ? !metrics.equals(summary.metrics) : summary.metrics != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = accountId != null ? accountId.hashCode() : 0;
            result = 31 * result + (description != null ? description.hashCode() : 0);
            result = 31 * result + (metrics != null ? metrics.hashCode() : 0);
            return result;
        }
    }
}
