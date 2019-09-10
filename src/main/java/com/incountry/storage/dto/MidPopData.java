package com.incountry.storage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.api.client.util.Key;

import java.util.Objects;

@JsonDeserialize(builder = MidPopData.Builder.class)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MidPopData {
    @Key
    private String country;
    @Key
    private String key;
    @Key
    private String body;
    @Key("profile_key")
    private String profileKey;
    @Key("range_key")
    private String rangeKey;
    @Key
    private String key2;
    @Key
    private String key3;

    public static Builder builder() {
        return new Builder();
    }

    public String getCountry() {
        return country;
    }

    public String getKey() {
        return key;
    }

    public String getBody() {
        return body;
    }

    public String getProfileKey() {
        return profileKey;
    }

    public String getRangeKey() {
        return rangeKey;
    }

    public String getKey2() {
        return key2;
    }

    public String getKey3() {
        return key3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MidPopData that = (MidPopData) o;
        return Objects.equals(country, that.country) &&
                Objects.equals(key, that.key) &&
                Objects.equals(body, that.body) &&
                Objects.equals(profileKey, that.profileKey) &&
                Objects.equals(rangeKey, that.rangeKey) &&
                Objects.equals(key2, that.key2) &&
                Objects.equals(key3, that.key3);
    }

    @Override
    public int hashCode() {

        return Objects.hash(country, key, body, profileKey, rangeKey, key2, key3);
    }

    @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
    public static class Builder {
        String country;
        String key;
        String body;

        String profileKey;
        String rangeKey;
        String key2;
        String key3;

        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        @JsonProperty("profile_key")
        public Builder setProfileKey(String profileKey) {
            this.profileKey = profileKey;
            return this;
        }

        @JsonProperty("range_key")
        public Builder setRangeKey(String rangeKey) {
            this.rangeKey = rangeKey;
            return this;
        }

        public Builder setKey2(String key2) {
            this.key2 = key2;
            return this;
        }

        public Builder setKey3(String key3) {
            this.key3 = key3;
            return this;
        }

        public MidPopData build() {
            MidPopData data = new MidPopData();
            data.country = this.country;
            data.key = this.key;
            data.key2 = this.key2;
            data.key3 = this.key3;
            data.body = this.body;
            data.profileKey = this.profileKey;
            data.rangeKey = this.rangeKey;
            return data;
        }
    }
}
