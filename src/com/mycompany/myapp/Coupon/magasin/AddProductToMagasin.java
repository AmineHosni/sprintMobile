/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.Coupon.magasin;

import Entities.Produit;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.produit.AfficherProduit;
import java.util.ArrayList;

/**
 *
 * @author AmineHosni
 */
public class AddProductToMagasin {

    Form f;
    Container choiceContainer;
    Container LabelIdContainer;
    ArrayList<Lien_id_magasin> newListe;
    private Resources theme;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme2");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature, uncomment if you have a pro subscription
        // Log.bindCrashProtection(true);
    }

    public AddProductToMagasin(Resources theme, ArrayList<Lien_id_magasin> liste, Produit p) {
        System.out.println("Add Product to magasin");
        f = new Form("ajouter aux ..", new BorderLayout());

        f.getToolbar().addCommandToLeftBar("", FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, UIManager.getInstance().getComponentStyle("TitleCommand")), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new AfficherProduit().start(p.getId(), false);
            }
        });
        choiceContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        LabelIdContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        newListe = new ArrayList<>();

        for (Lien_id_magasin lien : liste) {
//            Button button = new Button(lien.getName());
//            choiceContainer.add(button);
            Label labelId = new Label(String.valueOf(lien.getId()));
            
            CheckBox chkBx = new CheckBox(lien.getName());
            
            //chkBx.isSelected();
            choiceContainer.add(chkBx);
            choiceContainer.setScrollableY(true);
            LabelIdContainer.add(labelId);
            LabelIdContainer.setVisible(false);
        }

        Button addToSelectedMagasinsButton = new Button("Ajouter à ces magasins");

        f.add(BorderLayout.CENTER, choiceContainer);
        f.add(BorderLayout.SOUTH, addToSelectedMagasinsButton);
        f.refreshTheme();
        f.show();

        addToSelectedMagasinsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                //                System.out.println(choiceContainer.getComponentAt(0));
                for (int i = 1; i <= liste.size(); i++) {
                    CheckBox c = (CheckBox) choiceContainer.getComponentAt(i - 1);
                    Label l = (Label) LabelIdContainer.getComponentAt(i - 1);
                    if (c.isSelected()) {
                        System.out.println(c.getText() + " SELECTED ");
                        Lien_id_magasin lien = new Lien_id_magasin(Integer.parseInt(l.getText()), c.getText());
//                        System.out.println(lien);
                        newListe.add(lien);

                    } else {
                        System.out.println(c.getText() + " NOT SELECTED ");

                    }

                }
                if (newListe.size() != 0) {
                    System.out.println(newListe);
                    System.out.println("bof");
//                    System.out.println("taille: " + newListe.size());
                    for (Lien_id_magasin lien : newListe) {
                        ConnectionRequest con = new ConnectionRequest();
                        con.setUrl("http://localhost/pidev2017/magasin/verifierDuplicationProduitDansMagasin.php?id_magasin="
                                + lien.getId() + "&id_produit=" + p.getId());

                        con.addResponseListener(new ActionListener<NetworkEvent>() {

                            @Override
                            public void actionPerformed(NetworkEvent evt) {

                                int nombreProduitDansMagasin = Integer.valueOf(new String(con.getResponseData()));
                                System.out.println("produit Id: " + p.getId() + " magasin Name: " + lien.getName() + "nombre de produit : " + nombreProduitDansMagasin);
                                if (nombreProduitDansMagasin == 0) {

                                    ConnectionRequest req = new ConnectionRequest();
                                    req.setUrl("http://localhost/pidev2017/magasin/insertProductIntoMagasin.php?id_magasin="
                                            + lien.getId() + "&id_produit=" + p.getId() + "&id_user=" + Login.user.getId());

                                    req.addResponseListener(new ActionListener<NetworkEvent>() {
                                        @Override
                                        public void actionPerformed(NetworkEvent evt) {

                                            System.out.println("1");

                                            byte[] data = (byte[]) evt.getMetaData();
                                            String s = new String(data);

                                            if (s.equals("success")) {
                                                if (Dialog.show("Bien joué !", "Produit ajouté au(x) magasin(s) avec succès", "Ok :-)", null)) {
                                                    HomeMagasin home = new HomeMagasin();
                                                    home.getF().show();
                                                };
                                            } else {
                                                System.out.println("erreur magasin :" + lien.getName() + " " + s);
                                            }
                                        }
                                    });

                                    System.out.println("2");
                                    NetworkManager.getInstance().addToQueue(req);
                                }
                            }
                        });

                        NetworkManager.getInstance().addToQueue(con);

                    }
                } else if (!Dialog.show("Aucun magasin choisi", "Veuillez en hoisir au moins un", "Ok", "annuler")) {

                    new AfficherProduit().start(p.getId(), false);
                }

            }
        });

    }

    public Form getF() {
        return f;
    }

    public void show() {
        f.show();
    }

}
