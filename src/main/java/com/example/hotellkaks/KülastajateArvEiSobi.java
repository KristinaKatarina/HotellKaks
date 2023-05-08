package com.example.hotellkaks;

public class KülastajateArvEiSobi extends Exception{
    private String sisestatu;

    public KülastajateArvEiSobi(String sisestatu) {
        this.sisestatu = sisestatu;
    }

    @Override
    public String toString() {
        return "Külastajate arv ei saa olla: " + sisestatu;
    }
}
