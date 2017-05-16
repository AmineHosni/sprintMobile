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
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
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

        ButtonGroup b = new ButtonGroup();

        newListe = new ArrayList<>();

        for (Lien_id_magasin lien : liste) {
//            Button button = new Button(lien.getName());
//            choiceContainer.add(button);
            Label labelId = new Label(String.valueOf(lien.getId()));

            RadioButton rb = new RadioButton(lien.getName());

            //chkBx.isSelected();
            choiceContainer.add(rb);
            choiceContainer.setScrollableY(true);
            LabelIdContainer.add(labelId);
            LabelIdContainer.setVisible(false);
            b.add(rb);
        }

        Button addToSelectedMagasinsButton = new Button("Ajouter à ces magasins");

        f.add(BorderLayout.CENTER, choiceContainer);
        f.add(BorderLayout.SOUTH, addToSelectedMagasinsButton);
        f.refreshTheme();
        f.show();

        addToSelectedMagasinsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                RadioButton rb = b.getRadioButton(b.getSelectedIndex());

                if (rb != null) {
                    String nomMagasin = rb.getText();
                    

                    ConnectionRequest con = new ConnectionRequest();
                    con.setUrl("http://localhost/pidev2017/verifierDuplicationProduitDansMagasin.php?nom_magasin="
                            + nomMagasin+ "&id_produit=" + p.getId());

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
