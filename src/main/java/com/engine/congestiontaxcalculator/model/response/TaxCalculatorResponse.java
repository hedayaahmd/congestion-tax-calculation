package com.engine.congestiontaxcalculator.model.response;

public class TaxCalculatorResponse {

    private String error;
    private Double tax;
    private String message;

    public TaxCalculatorResponse(String error, Double tax, String message) {
        this.error = error;
        this.tax = tax;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
