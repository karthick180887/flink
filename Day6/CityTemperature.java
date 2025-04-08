package org.apache.flink;

import org.apache.commons.lang3.time.DateFormatUtils;

public class CityTemperature {
    private String city;
    private float temperature;
    private long timestamp;

    public CityTemperature() {}

    public CityTemperature(String city, float temperature, long timestamp) {
        this.city = city;
        this.temperature = temperature;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return String.format(
                "CityTemperature(city=%s, temperature=%.1f, timestamp=%d)",
                city, temperature, timestamp
        );
    }

    // Manual builder implementation
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String city;
        private float temperature;
        private long timestamp;

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder temperature(float temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public CityTemperature build() {
            return new CityTemperature(city, temperature, timestamp);
        }
    }

    // Getters and setters
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public float getTemperature() { return temperature; }
    public void setTemperature(float temperature) { this.temperature = temperature; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}