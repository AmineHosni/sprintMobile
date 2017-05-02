/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import Entities.Produit;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
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
public class modifierProduit {

    String imageName;
    Form current;

    private Resources theme;
    String libelle, description, etat, duree, created_date, marque;
    Double prixProduit;
    Integer quantiteStock, pourcentagePromotion, seller;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");
        Toolbar.setGlobalToolbar(true);

    }

    public void start(Produit produit) {
        if (current != null) {
            current.show();
            return;
        }
        Form formModif = new Form(new BorderLayout());
        new habchkleu().insertHabchkleu(formModif, false);
        TextField txtlibelle = new TextField(produit.getLibelle());
        TextArea txtDescription = new TextArea(produit.getDescription());
        TextField txtmarque = new TextField(produit.getMarque());
        Button btnModifier = new Button("Ajouter");
        Container n = new Container();
        n.add(txtlibelle);
        n.add(txtDescription);
        Container containery = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container containery2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container containerx = new Container(new BoxLayout(BoxLayout.X_AXIS));
        NumericSpinner prix = new NumericSpinner();
        prix.setValue(produit.getPrixProduit());
        NumericSpinner stock = new NumericSpinner();
        stock.setValue(produit.getQuantiteStock());
        containery.add(new Label("Prix"));
        containery.add(prix);
        containery2.add(new Label("Stock"));
        containery2.add(stock);
        containerx.add(containery);
        containerx.add(containery2);

        n.add(containerx);
        n.add(txtmarque);
        
        n.setScrollableY(true);
        formModif.add(BorderLayout.CENTER, n);
        formModif.add(BorderLayout.SOUTH, btnModifier);
        formModif.show();
        ConnectionRequest con = new ConnectionRequest();

        btnModifier.addActionListener(e -> {

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
            }else {
                String duree;
                con.setUrl("http://localhost/pidev2017/modif.php?"
                        + "libelle=" + txtlibelle.getText()
                        + "&description=" + txtDescription.getText()
                        + "&marque=" + txtmarque.getText()
                        + "&prixProduit=" + prix.getValue()
                        + "&quantiteStock=" + stock.getValue()
                        + "&updated_at=" + datenow +"&id="+produit.getId()
                      );
                
                NetworkManager.getInstance().addToQueue(con);
                new MonProfile().start();

            }

        });
        formModif.getToolbar().addCommandToLeftBar("", FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, UIManager.getInstance().getComponentStyle("TitleCommand")), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               new MonProfile().start();
            }

         
        });
    }

}

//        prix.setValue(produit.getPrixProduit());
//        NumericSpinner stock = new NumericSpinner();
//        stock.setValue(produit.getQuantiteStock());
//        Container d = new Container();
        