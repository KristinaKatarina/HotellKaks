package com.example.hotellkaks;

import javafx.application.Application;
import javafx.event.EventHandler;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class EsimeneLeht extends Application {
    private Color põhivärv = Color.rgb(241, 218, 196);
    private Color sekundaarneVärv = Color.rgb(112, 163, 127);
    private Color kolmasVärv = Color.rgb(68, 69, 69);
    private Color neljasVärv = Color.rgb(79, 134, 198);
    private BorderPane juur;
    private double laius = 600;
    private double kõrgus = 400;
    private Scene stseen;
    private boolean kasVIP;
    private int külastajateArv;
    private String külastajaNimi;

    private GridPane infoKast;

    public void start(Stage lava) {
        juur = new BorderPane();
        juur.setBackground(new Background(new BackgroundFill(põhivärv, null, null)));
        stseen = new Scene(juur, laius, kõrgus, põhivärv);
        esimeneLeht();
        lava.setScene(stseen);
        lava.show();
    }

    public void esimeneLeht() {
        infoKast = new GridPane(); // roheline kast
        infoKast.setMaxSize(juur.getWidth() / 1.15, juur.getHeight() / 1.15);
        infoKast.setBackground(new Background(new BackgroundFill(sekundaarneVärv, new CornerRadii(20), null)));
        // kood, et rohelisele kastile raam ümber panna (ma ei suutnud otsustada kuidas parem välja näeb)
        //infoKast.setBorder(new Border(new BorderStroke(kolmasVärv, BorderStrokeStyle.SOLID, new CornerRadii(20), new BorderWidths(1.5))));
        // infokasti alad
        infoKast.getColumnConstraints().addAll(new ColumnConstraints(110), // ruum enne nime
                new ColumnConstraints(50), //  ruum enne kriipsu
                new ColumnConstraints(30),  // ruum enne tervitust
                new ColumnConstraints(20),  // ruum enne nuppu
                new ColumnConstraints(100));
        infoKast.getRowConstraints().addAll(new RowConstraints(30), // tühiruum
                new RowConstraints(50), // ruum tekstile
                new RowConstraints(10), // ruum kriipsule
                new RowConstraints(100), // ruum hotelli nimele
                new RowConstraints(60)); // ruum nupule

        // teeme kriipsu
        Line kriips = new Line(210, 50, 410, 50);
        kriips.setFill(kolmasVärv);
        infoKast.add(kriips, 2, 2);
        // tervitutekst
        Text tervitus = new Text("Tere tulemast hotelli");
        tervitus.setFill(kolmasVärv);
        tervitus.setFont(new Font("Amagro Amagro", 15));
        infoKast.add(tervitus, 3, 1);
        // hotelli nimi
        Text nimi = new Text("Paragon");
        nimi.setFill(kolmasVärv);
        nimi.setFont(new Font("Amagro Amagro", 80));
        infoKast.add(nimi, 1, 3);
        // nupu tegemine
        Button nupp = nupp("Broneerima", 12, 100, 50, põhivärv, kolmasVärv);
        // nupu vajutamine
        nupp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                infoKast.getChildren().clear();
                infoKast.getRowConstraints().clear();
                infoKast.getColumnConstraints().clear();
                juur.setCenter(infoKast);
                teineLeht();
            }
        });
        infoKast.add(nupp, 4, 5); // nupu paigutus
        juur.setCenter(infoKast);

    }

    public Button nupp(String tekst, int tekstiSuurus, double nupuLaius, double nupuKõrgus, Color taustavärv, Color tekstiVärv) {
        Button nupp = new Button(tekst);
        nupp.setFont(new Font("Amagro Amagro", tekstiSuurus));
        nupp.setPrefSize(nupuLaius, nupuKõrgus);
        // nupu tausta värv
        Background taustNupule = new Background(new BackgroundFill(taustavärv, null, null));
        nupp.setBackground(taustNupule);
        nupp.setTextFill(tekstiVärv);
        // nupu ümara kuju tekitamine
        Rectangle nupuKuju = new Rectangle(100, 100);
        nupuKuju.setArcHeight(50);
        nupuKuju.setArcWidth(25);
        nupp.setShape(nupuKuju);
        Border ääris = new Border(new BorderStroke(tekstiVärv, BorderStrokeStyle.SOLID, null, new BorderWidths(1.5)));
        nupp.setBorder(ääris);
        return nupp;
    }

    public void teineLeht() {
        infoKast.getColumnConstraints().addAll(new ColumnConstraints(20),
                new ColumnConstraints(infoKast.getWidth() / 2 - 10),
                new ColumnConstraints(10),
                new ColumnConstraints(20),
                new ColumnConstraints(5));
        //infoKast.getRowConstraints().addAll(new RowConstraints(20)); // tühi ruum
        infoKast.setVgap(20);
        // eesnimi
        TextField eesnimi = infoSisestamine("Sisestage eesnimi");
        infoKast.add(eesnimi, 1, 1);
        // perenimi
        TextField perenimi = infoSisestamine("Sisestage perenimi");
        infoKast.add(perenimi, 1, 2);
        // külastajate arv
        TextField külastajad = infoSisestamine("Sisestage külastajate arv (2 või 4)");
        infoKast.add(külastajad, 1, 3);
        // nupud vip ja tava toa jaoks
        Button tavalineTuba = nupp("Broneeri tavaline tuba", 12, 150, 50, kolmasVärv, sekundaarneVärv);
        infoKast.add(tavalineTuba, 1, 4);
        // nuppude vajutamine
        nupuVajutamine(tavalineTuba, eesnimi, perenimi, külastajad, false);
        Button vipTuba = nupp("Broneeri VIP tuba", 12, 150, 50, kolmasVärv, sekundaarneVärv);
        infoKast.add(vipTuba, 1, 5);
        nupuVajutamine(vipTuba, eesnimi, perenimi, külastajad, true);
        // taust
        Rectangle poolTausta = new Rectangle(20, infoKast.getHeight(), kolmasVärv);
        infoKast.add(poolTausta, 3, 0, 1, 6);
        Rectangle ümarTaust = new Rectangle(infoKast.getWidth() / 2 - 10, infoKast.getHeight(), kolmasVärv);
        ümarTaust.setArcWidth(40);
        ümarTaust.setArcHeight(40);
        infoKast.add(ümarTaust, 3, 0, 2, 6);
        // pilt
        Image pilt = new Image("https://th.bing.com/th/id/R.aa9c5a5c0893f16b624d475cd429e2d6?rik=kWxHis1fd%2fDLDw&riu=http%3a%2f%2f2.bp.blogspot.com%2f-MSgpbdbIEz4%2fTyJqMo_J36I%2fAAAAAAAAC4I%2fZsaUFjHX3Z8%2fw1200-h630-p-k-no-nu%2fgothia_towers_166180343.jpg&ehk=YjCoYIajjl9dATs6RlFFwOhsRhMf5n0UaOdVBdV9zAQ%3d&risl=&pid=ImgRaw&r=0",
                infoKast.getWidth() / 2 - 50, infoKast.getHeight() - 40, true, true);
        Node hotelliPilt = new ImageView(pilt);
        infoKast.add(hotelliPilt, 4, 0, 2, 6);
    }

    private void kolmasLeht(int külastajad, boolean kasVIP) throws IOException {

        LisaBroneering lisaBroneering = new LisaBroneering(); // andmebaasi hakatakse siin kirjutama.
        String broneering = lisaBroneering.kirjutaBroneering(külastajad, kasVIP); // Lisab broneeringu andmebaasi ja tagastab toa sõne kujul.

        //infoKast.getColumnConstraints().addAll(new ColumnConstraints(0), // 0
        //new ColumnConstraints(400)); // 1
        ColumnConstraints column1 = new ColumnConstraints();

        column1.setPercentWidth(100);
        infoKast.getColumnConstraints().add(column1); // 0

        infoKast.getRowConstraints().addAll(new RowConstraints(50), // 0
                new RowConstraints(30),
                new RowConstraints(30),
                new RowConstraints(30),
                new RowConstraints(100));

        // Õnnestus leida tuba
        Label nimeTeavitus = new Label("Lugupeetud " + külastajaNimi);
        nimeTeavitus.setFont(new Font("Amagro Amagro", 25));

        // Tuba leiti broneeringuks
        if (broneering != null){
            Label õnnestumiseTeavitus = new Label("Teie hotelli tuba on broneeritud!");
            Label toaNumbriTeavitus = new Label( "Toa number: " + broneering.split(";")[0]);

            õnnestumiseTeavitus.setFont(new Font("Amagro Amagro", 15));
            toaNumbriTeavitus.setFont(new Font("Amagro Amagro", 15));

            GridPane.setFillWidth(toaNumbriTeavitus, true);
            toaNumbriTeavitus.setMaxWidth(Double.MAX_VALUE);
            toaNumbriTeavitus.setAlignment(Pos.CENTER);
            õnnestumiseTeavitus.setMaxWidth(Double.MAX_VALUE);
            õnnestumiseTeavitus.setAlignment(Pos.CENTER);

            infoKast.add(õnnestumiseTeavitus, 0, 2);
            infoKast.add(toaNumbriTeavitus, 0, 3);
        }

        else {
            Label eiÕnnestunudTeavitus = new Label("Kahjuks me ei leidnud ühtegi otsitavat vaba tuba");
            Label prooviUuesti = new Label("Palun proovi mõne aja pärast uuesti");

            eiÕnnestunudTeavitus.setFont(new Font("Amagro Amagro", 15));
            prooviUuesti.setFont(new Font("Amagro Amagro", 15));

            GridPane.setFillWidth(prooviUuesti, true);
            prooviUuesti.setMaxWidth(Double.MAX_VALUE);
            prooviUuesti.setAlignment(Pos.CENTER);
            eiÕnnestunudTeavitus.setMaxWidth(Double.MAX_VALUE);
            eiÕnnestunudTeavitus.setAlignment(Pos.CENTER);

            infoKast.add(eiÕnnestunudTeavitus, 0, 2);
            infoKast.add(prooviUuesti, 0, 3);
        }
        Button tagasiAlgusesse = nupp("Tagasi algusesse", 13, 150, 40, põhivärv, kolmasVärv);
        VBox vboxNupile = new VBox(tagasiAlgusesse);
        vboxNupile.setAlignment(Pos.CENTER);

        nimeTeavitus.setMaxWidth(Double.MAX_VALUE);
        nimeTeavitus.setAlignment(Pos.CENTER);

        tagasiAlgusesse.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                infoKast.getChildren().clear();
                infoKast.getRowConstraints().clear();
                infoKast.getColumnConstraints().clear();
                juur.setCenter(infoKast);
                esimeneLeht();
            }
        });

        infoKast.add(nimeTeavitus, 0, 1);
        infoKast.add(vboxNupile, 0, 4);
    }

    public void nupuVajutamine(Button tuba, TextField eesnimi, TextField perenimi, TextField külastajad, boolean vip) {
        tuba.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                // erindid
                if (eesnimi.getText().length() <= 1) {
                    try {
                        throw new NimiEiSobiErind(eesnimi.getText());
                    } catch (NimiEiSobiErind e) {
                        eesnimi.setText(e.toString());
                    }
                } else {
                    if (perenimi.getText().length() <= 1) {
                        try {
                            throw new NimiEiSobiErind(perenimi.getText());
                        } catch (NimiEiSobiErind e) {
                            perenimi.setText(e.toString());
                        }
                    } else {
                        if (!külastajad.getText().equals("2") && !külastajad.getText().equals("4")) {
                            try {
                                throw new KülastajateArvEiSobi(külastajad.getText());
                            } catch (KülastajateArvEiSobi e) {
                                külastajad.setText(e.toString());
                            }
                        } else {
                            // siin tuleks kontrollida, kas sellist tuba leidub
                            if (vip == false) {
                                kasVIP = false;
                            } else {
                                kasVIP = true;
                            }
                            külastajateArv = Integer.parseInt(külastajad.getText());
                            külastajaNimi = eesnimi.getText() + " " + perenimi.getText();

                            infoKast.getColumnConstraints().clear();
                            infoKast.getRowConstraints().clear();
                            infoKast.getChildren().clear();

                            // siia meetod, kus on kolmanda lehe kujundus
                            // Smirksi kood:

                            try {
                                kolmasLeht(külastajateArv, kasVIP);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }
                }
            }
        });

    }

    private TextField infoSisestamine(String tekst) {
        TextField sisestamine = new TextField();
        sisestamine.setText(tekst);
        sisestamine.setPrefSize(infoKast.getWidth() / 2.5, 30);
        Background taust = new Background(new BackgroundFill(põhivärv, null, null));
        sisestamine.setBackground(taust);
        // ümara kuju tekitamine
        Rectangle kuju = new Rectangle(100, 100);
        kuju.setArcHeight(40);
        kuju.setArcWidth(5);
        sisestamine.setShape(kuju);
        Border ääris = new Border(new BorderStroke(kolmasVärv, BorderStrokeStyle.SOLID, null, new BorderWidths(1)));
        sisestamine.setBorder(ääris);
        return sisestamine;
    }

    public static void main(String[] args) throws IOException {
        Vaikekaivitus vaikekaivitus = new Vaikekaivitus();
        vaikekaivitus.koostaAndmebaasiFail();
        launch();
    }
}