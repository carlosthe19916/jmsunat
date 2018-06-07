package io.github.carlosthe19916.jms.sender.model;

public class BillConsultBean {

    private final String rucComprobante;
    private final String tipoComprobante;
    private final String serieComprobante;
    private final Integer numeroComprobante;

    private BillConsultBean(Builder builder) {
        this.rucComprobante = builder.rucComprobante;
        this.tipoComprobante = builder.tipoComprobante;
        this.serieComprobante = builder.serieComprobante;
        this.numeroComprobante = builder.numeroComprobante;
    }

    public String getRucComprobante() {
        return rucComprobante;
    }

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public String getSerieComprobante() {
        return serieComprobante;
    }

    public Integer getNumeroComprobante() {
        return numeroComprobante;
    }

    public static class Builder {
        private String rucComprobante;
        private String tipoComprobante;
        private String serieComprobante;
        private Integer numeroComprobante;

        public Builder rucComprobante(String rucComprobante) {
            this.rucComprobante = rucComprobante;
            return this;
        }

        public Builder tipoComprobante(String tipoComprobante) {
            this.tipoComprobante = tipoComprobante;
            return this;
        }

        public Builder serieComprobante(String serieComprobante) {
            this.serieComprobante = serieComprobante;
            return this;
        }

        public Builder numeroComprobante(Integer numeroComprobante) {
            this.numeroComprobante = numeroComprobante;
            return this;
        }

        public BillConsultBean build() {
            return new BillConsultBean(this);
        }
    }

}
