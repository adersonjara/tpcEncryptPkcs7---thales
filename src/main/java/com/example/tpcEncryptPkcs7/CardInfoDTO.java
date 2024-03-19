package com.example.tpcEncryptPkcs7;

public class CardInfoDTO {
    // Campos
    private String fpan;
    private String issuerCardRefId;
    private String exp;
    private String cardholderName;
    private PostalAddressDTO postalAddress;

    public String getFpan() {
        return fpan;
    }

    public void setFpan(String fpan) {
        this.fpan = fpan;
    }

    public String getIssuerCardRefId() {
        return issuerCardRefId;
    }

    public void setIssuerCardRefId(String issuerCardRefId) {
        this.issuerCardRefId = issuerCardRefId;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public PostalAddressDTO getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(PostalAddressDTO postalAddress) {
        this.postalAddress = postalAddress;
    }

    // Clase interna para representar la dirección postal
    public static class PostalAddressDTO {
        // Campos
        private String line1;
        private String line2;
        private String city;
        private String postalCode;
        private String state;
        private String country;

        public String getLine1() {
            return line1;
        }

        public void setLine1(String line1) {
            this.line1 = line1;
        }

        public String getLine2() {
            return line2;
        }

        public void setLine2(String line2) {
            this.line2 = line2;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }
}
