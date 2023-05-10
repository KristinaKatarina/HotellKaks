package com.example.hotellkaks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Vaikekaivitus {

    public boolean andmebaasOlemas(){
        // Meetod kontrollib kas fail "andmebaas.txt" on olemas. Kui fail on leitud, tagastab true,
        // vastasel juhul false.

        Path failiteePath = Paths.get("");
        String failitee = failiteePath.toAbsolutePath().toString();

        if (new File(failitee + File.separatorChar + "andmebaas.txt").isFile())
            return true;
        return false;
    }

    public void lisaAndmebaasiAndmed(String failitee){
        // Meetod võtab argumendiks faili 'andmebaas.txt' faili asukoha ning kirjutab
        // faili kõik toad mida hotellis broneerida saab.

        int kahesedToadTavaline = 10;
        int neljasedToadTavaline = 7;
        int neljasedToadVIP = 3;
        int toaLoendur = 1;

        try(FileWriter failikirjutaja = new FileWriter(failitee + File.separatorChar + "andmebaas.txt")){

            for (int i = 0; i <= 20; i++) {
                if (i <= 6 && kahesedToadTavaline >= 5){
                    // Toad 1-6 kahele (Tavalised toad).
                    failikirjutaja.write(toaLoendur + ";" + "2" + ";" + "0" + ";" + "TAVALINE" + "\n");
                    kahesedToadTavaline--;
                    toaLoendur++;
                }

                else if (i <= 10 && neljasedToadTavaline > 4){
                    // Toad 7-9 neljale (Tavalised toad)
                    failikirjutaja.write(toaLoendur + ";" + "4" + ";" + "0" + ";" + "TAVALINE" + "\n");
                    neljasedToadTavaline--;
                    toaLoendur++;
                }

                else if (i <= 12 && kahesedToadTavaline > 0){
                    // Toad 10-13 kahele (Tavalised toad)
                    failikirjutaja.write(toaLoendur + ";" + "2" + ";" + "0" + ";" + "TAVALINE" + "\n");
                    kahesedToadTavaline--;
                    toaLoendur++;
                }

                else if (i <= 16 && neljasedToadTavaline > 0){
                    // Toad 14 - 16 kahele (Tavalised toad)
                    failikirjutaja.write(toaLoendur + ";" + "4" + ";" + "0" + ";" + "TAVALINE" + "\n");
                    neljasedToadTavaline--;
                    toaLoendur++;
                }

                else if (i > 16 && neljasedToadVIP > 0){
                    // Toad 18 - 20 (VIP)
                    failikirjutaja.write(toaLoendur + ";" + "4" + ";" + "0" + ";" + "VIP" + "\n");
                    neljasedToadVIP--;
                    toaLoendur++;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("'Andmebaas.txt' faili kirjutamisel tekkis viga.");
        }
    }

    public void koostaAndmebaasiFail() throws IOException {
        // Meetod koostab andmebaasi faili 'andmebaas.txt' kui sellist faili ei ole olemas kaustas kus
        // programmi failid on salvestatud.

        try {
            if (!andmebaasOlemas()){
                Path failiteePath = Paths.get("");
                String failitee = failiteePath.toAbsolutePath().toString();

                File andmebaasiFail = new File(failitee + File.separatorChar + "andmebaas.txt");
                andmebaasiFail.getParentFile().mkdirs();
                andmebaasiFail.createNewFile();

                lisaAndmebaasiAndmed(failitee);
            }
            else {
                System.out.println("'andmebaas.txt' fail on olemas.");
            }
        }
        catch (IOException exept){
            throw new IOException("'andmebaas.txt' faili genereerimisel tekkis viga.");
        }
    }


}
