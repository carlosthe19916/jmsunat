package io.github.carlosthe19916.sender.model;

public class SenderConfig {

    private final String endpoint;
    private final String username;
    private final String password;

    private SenderConfig(Builder builder) {
        this.endpoint = builder.endpoint;
        this.username = builder.username;
        this.password = builder.password;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public static class Builder {
        private String endpoint;
        private String username;
        private String password;

        public Builder endpoint(String endpoint) {
            this.endpoint = endpoint;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public SenderConfig build() {
            return new SenderConfig(this);
        }
    }

}