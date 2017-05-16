/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.produit;

import Entities.Categorie;
import com.codename1.capture.Capture;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
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
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.NumericSpinner;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.Coupon.magasin.Login;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import service.CategorieService;
import service.ToolbarSideMenu;

/**
 *
 * @author MBM info
 */
public class InsertProduit {

    String imageName;
    Form current;
    MultipartRequest cr = null;

    private Resources theme;
    String libelle, description, etat, duree, created_date, marque;
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
        new ToolbarSideMenu().insertSetting(formAffiche, false);
        TextField txtlibelle = new TextField();
        txtlibelle.setHint("Libelle");

        TextField txtDescription = new TextField();
        txtDescription.setHint("Description");

        TextField txtmarque = new TextField();
        txtmarque.setHint("Marque");

        Button btnAjouterPhoto = new Button("Photo");

        ComboBox<String> comboEtat, comboDuree;
        ComboBox<String> chCategorie = new ComboBox<>();
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
        ConnectionRequest concategorie = new ConnectionRequest();
        concategorie.setUrl("http://localhost/pidev2017/produit/selectcategorie.php");
        concategorie.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ArrayList<Categorie> list = new ArrayList<>();
                list = new CategorieService().getListCategorie(new String(concategorie.getResponseData()));
                for (Categorie categorie : list) {
                    chCategorie.addItem(categorie.getNomCategorie());
                }
                n.add(chCategorie);
            }

        });
        NetworkManager.getInstance().addToQueue(concategorie);

        n.add(btnAjouterPhoto);
        n.setScrollableY(true);
        formAffiche.add(BorderLayout.CENTER, n);
        formAffiche.add(BorderLayout.SOUTH, btnAjouter);
        formAffiche.show();
        ConnectionRequest con = new ConnectionRequest();

        btnAjouter.addActionListener(e -> {
            System.out.println("okkkkkkkkkkkkkkkkkkk");

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
            } else if (imageName == null) {

                Dialog.show(null, " Ajouter votre Photo ", "ok", null);

            } else {
                String duree;
                if (comboDuree.getSelectedItem() == "15 jours") {
                    duree = "15";
                } else if (comboDuree.getSelectedItem() == "30 jours") {
                    duree = "30";
                } else {
                    duree = "45";
                }

                con.setUrl("http://localhost/pidev2017/produit/insert.php?"
                        + "libelle=" + txtlibelle.getText()
                        + "&description=" + txtDescription.getText()
                        + "&marque=" + txtmarque.getText()
                        + "&etat=" + comboEtat.getSelectedItem()
                        + "&prixProduit=" + prix.getValue()
                        + "&quantiteStock=" + stock.getValue()
                        + "&created_date=" + datenow
                        + "&duree=" + duree
                        + "&pourcentagePromotion=" + 5
                        + "&produitCategorie=" + 2//chCategorie.getSelectedItem()

                        + "&image_name=" + imageName
                        + "&seller_id=" + Login.user.getId()
                );
                System.out.println("http://localhost/pidev2017/produit/insert.php?"
                        + "libelle=" + txtlibelle.getText()
                        + "&description=" + txtDescription.getText()
                        + "&marque=" + txtmarque.getText()
                        + "&etat=" + comboEtat.getSelectedItem()
                        + "&prixProduit=" + prix.getValue()
                        + "&quantiteStock=" + stock.getValue()
                        + "&created_date=" + datenow
                        + "&duree=" + duree
                        + "&pourcentagePromotion=" + 8
                                                + "&produitCategorie=" + 2

                        + "&image_name=" + imageName
                        + "&seller_id=" + Login.user.getId());

                NetworkManager.getInstance().addToQueue(con);
                new ListProduit().start();

            }

        });

        btnAjouterPhoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    MultipartRequest cr = new MultipartRequest();
                    String filePath = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
                    cr.setUrl("http://localhost/pidev2017/produit/addimage.php");
                    cr.setPost(true);
                    String mime = "image/jpeg";
                    if (filePath != null) {
                        cr.addData("file", filePath, mime);
                        System.out.println(filePath);
                        cr.setFilename("file", filePath);//any unique name you want
                        imageName = filePath;
                        int index = imageName.lastIndexOf("/");
                        imageName = imageName.substring(index + 1);
                        InfiniteProgress prog = new InfiniteProgress();
                        Dialog dlg = prog.showInifiniteBlocking();
                        cr.setDisposeOnCompletion(dlg);
                        NetworkManager.getInstance().addToQueueAndWait(cr);
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        });

    }

}
