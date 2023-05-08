package com.example.hotellkaks;

public class NimiEiSobiErind extends Exception {
    private String nimi;

    public NimiEiSobiErind(String nimi) {
        this.nimi = nimi;
    }

    @Override
    public String toString() {
        return "nimi '" + nimi + "' on liiga lühike";
    }
}
