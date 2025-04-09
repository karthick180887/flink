package org.apache.flink;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TemperatureStats {
    @JsonProperty("CITY")
    private String city;
    @JsonProperty("MAX")
    private float max;
    @JsonProperty("MIN")
    private float min;
    @JsonProperty("AVERAGE")
    private float average;

    public TemperatureStats() {
    }

    public TemperatureStats(String city, float max, float min, float average) {
        this.city = city;
        this.max = max;
        this.min = min;
        this.average = average;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    public static TemperatureStatsBuilder builder() {
        return new TemperatureStatsBuilder();
    }

    public static TemperatureStats build(TemperatureStats that) {
        return builder()
                .city(that.getCity())
                .max(that.getMax())
                .min(that.getMin())
                .average(that.getAverage())
                .build();
    }

    public static class TemperatureStatsBuilder {
        private String city;
        private float max;
        private float min;
        private float average;

        public TemperatureStatsBuilder city(String city) {
            this.city = city;
            return this;
        }

        public TemperatureStatsBuilder max(float max) {
            this.max = max;
            return this;
        }

        public TemperatureStatsBuilder min(float min) {
            this.min = min;
            return this;
        }

        public TemperatureStatsBuilder average(float average) {
            this.average = average;
            return this;
        }

        public TemperatureStats build() {
            return new TemperatureStats(city, max, min, average);
        }
    }

    @Override
    public String toString() {
        return "TemperatureStats{" +
                "city='" + city + '\'' +
                ", max=" + max +
                ", min=" + min +
                ", average=" + average +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TemperatureStats that = (TemperatureStats) o;

        if (Float.compare(that.max, max) != 0) return false;
        if (Float.compare(that.min, min) != 0) return false;
        if (Float.compare(that.average, average) != 0) return false;
        return city != null ? city.equals(that.city) : that.city == null;
    }

    @Override
    public int hashCode() {
        int result = city != null ? city.hashCode() : 0;
        result = 31 * result + (max != +0.0f ? Float.floatToIntBits(max) : 0);
        result = 31 * result + (min != +0.0f ? Float.floatToIntBits(min) : 0);
        result = 31 * result + (average != +0.0f ? Float.floatToIntBits(average) : 0);
        return result;
    }
}