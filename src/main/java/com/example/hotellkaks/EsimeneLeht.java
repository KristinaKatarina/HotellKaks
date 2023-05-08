package com.example.hotellkaks;

import javafx.application.Application;
import javafx.event.EventHandler;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EsimeneLeht extends Application {
    private Color põhivärv = Color.rgb(241, 218, 196);
    private Color sekundaarneVärv = Color.rgb(112, 163, 127);
    private Color kolmasVärv = Color.rgb(68, 69, 69);
    private Color neljasVärv = Color.rgb(186, 86, 36);
    private BorderPane juur;
    private double laius = 600;
    private double kõrgus = 400;
    private Scene stseen;
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
        infoKast.getColumnConstraints().addAll(new ColumnConstraints(infoKast.getWidth() / 2));

        Rectangle poolTausta = new Rectangle(infoKast.getWidth() / 2, infoKast.getHeight(), kolmasVärv);
        Border pt = new Border(new BorderStroke(kolmasVärv, BorderStrokeStyle.SOLID, null, new BorderWidths(4)));
        infoKast.add(poolTausta, 1, 0);


    }

    public static void main(String[] args) {
        launch();
    }
}