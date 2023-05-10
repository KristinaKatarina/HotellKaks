package com.example.hotellkaks;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LisaBroneering {

    public List<String> loeAndmebaasist() {
        // Meetod avab faili 'andmebaas.txt' ning tagastab listi kus on kõik toad sõne kujul.
        List<String> toad = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("andmebaas.txt")))){
            String rida;

            while ((rida = br.readLine()) != null){
                toad.add(rida);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return toad;
    }

    public String kirjutaBroneering(int külastajateArv, boolean kasOnVIP) throws IOException {
        // Meetod võtab argumentideks külastajate arvu ühe toa broneeringuks, tõeväärtuse kas tegemist on VIP toaga või mitte,
        // broneeringu alguskuupäeva ning lõppkuupäeva.
        // Kui failist 'andmebaas.txt' leiti esimene tuba, mis vastab üleval toodud tingimustele, siis muudetakse
        // see tuba failis kinnisesse olekusse.
        // Meetod tagastab tõeväärtuse true kui leiti sobiv tuba, vastasel juhul false.

        List<String> toad = loeAndmebaasist();

        String otsitavTuba = null;
        boolean leitudTuba = false;
        if (kasOnVIP) {
            otsitavTuba = "VIP";
        }
        else {
            otsitavTuba = "TAVALINE";
        }

        String broneeritudTuba = null;

        try(FileWriter failikirjutaja = new FileWriter("andmebaas.txt")){
            for (String tuba : toad){
                String[] tubaTykeldatud = tuba.split(";");

                // VIP tuba
                if (Integer.parseInt(tubaTykeldatud[2]) == 0 && Integer.parseInt(tubaTykeldatud[1]) >= külastajateArv && !leitudTuba && tubaTykeldatud[3].equals("VIP")){
                    failikirjutaja.write(tubaTykeldatud[0] + ";" + tubaTykeldatud[1] + ";" + "1" + ";" + "VIP" + "\n");
                    leitudTuba = true;
                    broneeritudTuba = tuba;
                }
                // TAVALINE tuba
                else if (Integer.parseInt(tubaTykeldatud[2]) == 0 && Integer.parseInt(tubaTykeldatud[1]) >= külastajateArv && !leitudTuba && tubaTykeldatud[3].equals(otsitavTuba)){
                    failikirjutaja.write(tubaTykeldatud[0] + ";" + tubaTykeldatud[1] + ";" + "1" + ";" + "TAVALINE" + "\n");
                    leitudTuba = true;
                    broneeritudTuba = tuba;
                }
                // Pole otsitav tuba või on kinni.
                else
                    failikirjutaja.write(tuba + "\n");
            }
        }
        return broneeritudTuba;
    }
}
