/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.capture.Capture;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.NumericSpinner;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.Date;
import service.habchkleu;

/**
 *
 * @author MBM info
 */
public class InsertProduit {

    Form current;
MultipartRequest cr;
    private Resources theme;
    String libelle, description, etat, duree, image_name, image_name2, image_name3, created_date, marque;
    Double prixProduit;
    Integer quantiteStock, pourcentagePromotion, seller;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");
        Toolbar.setGlobalToolbar(true);

    }

    public void start() {
        if (current != null) {
            current.show();
            return;
        }
        Form formAffiche = new Form(new BorderLayout());
        new habchkleu().insertHabchkleu(formAffiche,false);
        TextField txtlibelle = new TextField();
        txtlibelle.setHint("Libelle");

        TextField txtDescription = new TextField();
        txtDescription.setHint("Description");

        TextField txtmarque = new TextField();
        txtmarque.setHint("Marque");

        Button btnAjouterPhoto = new Button("Photo");

        ComboBox<String> comboEtat, comboDuree, chCategorie;
        comboEtat = new ComboBox<>(
                "nouveau",
                "occasion"
        );
        comboDuree = new ComboBox<>(
                "15 jours",
                "30 jours", "45 jours"
        );

        Button btnAjouter = new Button("Ajouter");
        Container n = new Container();

        n.add(txtlibelle);
        n.add(txtDescription);

        Container containery = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container containery2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        Container containerx = new Container(new BoxLayout(BoxLayout.X_AXIS));

        NumericSpinner prix = new NumericSpinner();
        NumericSpinner stock = new NumericSpinner();
        containery.add(new Label("Prix"));
        containery.add(prix);
        containery2.add(new Label("Stock"));
        containery2.add(stock);
        containerx.add(containery);
        containerx.add(containery2);

        n.add(containerx);
        n.add(txtmarque);
        n.add(comboEtat);
        n.add(comboDuree);
        n.add(btnAjouterPhoto);
        n.setScrollableY(true);
        formAffiche.add(BorderLayout.CENTER, n);
        formAffiche.add(BorderLayout.SOUTH, btnAjouter);
        formAffiche.show();
        ConnectionRequest con = new ConnectionRequest();
        btnAjouterPhoto.addActionListener(e -> {

            try {
                 cr = new MultipartRequest();
                String filePath = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
                cr.setUrl("http://localhost/pidev2017/addimage.php");
                cr.setPost(true);
                String mime = "image/jpeg";
                if (cr != null) {

                    cr.addData("file", filePath, mime);
                    cr.setFilename("file", "MyImage.jpg");//any unique name you want                
                    InfiniteProgress prog = new InfiniteProgress();
                    NetworkManager.getInstance().addToQueueAndWait(cr);

                }
            } catch (IOException ex) {
            }

        });
        btnAjouterPhoto.addActionListener(ee -> {
            String filePath = null;

            try {
                MultipartRequest cr = new MultipartRequest();
                filePath = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
                cr.setUrl("http://localhost/pidev2017/addimage.php");
                cr.setPost(true);
                String mime = "image/jpeg";
                cr.addData("file", filePath, mime);
                cr.setFilename("file", "MyImage.jpg");//any unique name you want                
                System.out.println(filePath);
                Dialog.show(null, " Ajouter une image", "ok", null);
                if (filePath == null) {

                    btnAjouter.addActionListener(e -> {
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = new Date();
                        String datenow = new String(dateFormat.format(date));
                        boolean controledesaisie = false;
                        if (txtlibelle.getText().equals("")) {
                            Dialog.show(null, " Ajouter votre Libelle ", "ok", null);
                        } else if (txtDescription.getText().equals("")) {
                            Dialog.show(null, " Ajouter votre Description ", "ok", null);
                        } else if (prix.getValue() == 0) {
                            Dialog.show(null, " Ajouter votre Prix ", "ok", null);
                        } else if (txtmarque.getText().equals("")) {
                            Dialog.show(null, " Ajouter votre marque ", "ok", null);
                        } else if (stock.getValue() == 0) {
                            Dialog.show(null, " Ajouter votre Stock ", "ok", null);
                        } else {
                            Dialog.show(null, " Ajouter votre Image ", "ok", null);

                        }

                    });
                } else {
                    NetworkManager.getInstance().addToQueueAndWait(cr);
                    System.out.println(filePath);
                    NetworkManager.getInstance().addToQueue(con);
                    new ListProduit().start();

                }
            } catch (IOException ex) {
            }

//                con.setUrl("http://localhost/pidev2017/insert.php?"
//                        + "libelle=" + txtlibelle.getText()
//                        + "&description=" + txtDescription.getText()
//                        + "&marque=" + txtmarque.getText()
//                        + "&etat=" + comboEtat.getSelectedItem()
//                        + "&prixProduit=" + prix.getValue()
//                        + "&quantiteStock=" + stock.getValue()
//                        + "&created_date=" + datenow
//                        + "&duree=" + comboDuree.getSelectedItem()
//                        + "&pourcentagePromotion=" + pourcentagePromotion
//                        + "&image_name=" + image_name
//                        + "&image_name2=" + image_name2
//                        + "&image_name3=" + image_name3
//                        + "&seller=" + 9);
        });

    }

}
