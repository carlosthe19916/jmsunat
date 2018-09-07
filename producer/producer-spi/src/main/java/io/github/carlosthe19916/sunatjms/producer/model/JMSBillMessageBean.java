package io.github.carlosthe19916.sunatjms.producer.model;

public class JMSBillMessageBean {

    private final String fileName;
    private final byte[] contentFile;
    private final String partyType;

    private JMSBillMessageBean(Builder builder) {
        this.fileName = builder.fileName;
        this.contentFile = builder.contentFile;
        this.partyType = builder.partyType;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getContentFile() {
        return contentFile;
    }

    public String getPartyType() {
        return partyType;
    }

    public static class Builder {
        private String fileName;
        private byte[] contentFile;
        private String partyType;

        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder contentFile(byte[] contentFile) {
            this.contentFile = contentFile;
            return this;
        }

        public Builder partyType(String partyType) {
            this.partyType = partyType;
            return this;
        }

        public JMSBillMessageBean build() {
            return new JMSBillMessageBean(this);
        }
    }

}
