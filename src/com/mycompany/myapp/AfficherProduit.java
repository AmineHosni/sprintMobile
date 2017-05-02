/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import Entities.Produit;
import com.codename1.components.ImageViewer;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import service.ProduitService;
import service.habchkleu;

/**
 *
 * @author MBM info
 */
public class AfficherProduit {

    private Resources theme;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");
        Toolbar.setGlobalToolbar(true);

    }

    public void start(Integer id, boolean isconnceted) {

        Form formAffiche = new Form(new BoxLayout(2));
        new habchkleu().insertHabchkleu(formAffiche, false);

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev2017/select.php");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Produit produit = new ProduitService().getProduit(new String(con.getResponseData()), id);
                
                Container detailssup = BoxLayout.encloseY(
                        
                        new Label(produit.getMarque()),
                        new Label(produit.getLibelle()+" : "+produit.getDescription())
                
                
                
                
                );
                Container containerImage = new Container();
                ImageViewer img = new ImageViewer();
                img.setImageList(new ProduitService().listerImages(produit));
                containerImage.add(img);
                
                Container container2 = new Container();
                    
                container2.setLayout(new BoxLayout(BoxLayout.Y_AXIS));  
               
                Container container6 = BoxLayout.encloseY(
                
                BoxLayout.encloseX(
                new Label("Prix : "),
                        new Label(produit.getPrixProduit().toString())
                ),
                BoxLayout.encloseX(
                new Label("Etat : "),
                        new Label(produit.getEtat())
                ),
                BoxLayout.encloseX(
                new Label("Categorie : "),
                        new Label(String.valueOf(produit.getProduitCategorie()))
                )        
                
                );
                if (produit.getQuantiteStock()!=0) {
                    container6.add(BoxLayout.encloseX(
                    new Label("Stock : Disponible")
                    ));
                }else container6.add(BoxLayout.encloseX(
                    new Label("Stock : Indisponible","WelcomeRed")
                    ));
                
                formAffiche.add(detailssup);
                formAffiche.add(containerImage);
                formAffiche.add(container6);
                System.out.println(produit.getQuantiteStock());
                if (isconnceted == true) {

                    Button btnSupprimer = new Button("Supprimer");

                    btnSupprimer.addActionListener(e -> {
                        new ProduitService().delete(produit.getId());
                        new ListProduit().start();

                    });
                    Button btnModifier = new Button("Modifier");
                    btnModifier.addActionListener(e -> new modifierProduit().start(produit));

                    Container boutons = BoxLayout.encloseX(btnModifier, btnSupprimer);
                    formAffiche.add(boutons);
                }
                formAffiche.show();
            }
        });
        NetworkManager.getInstance().addToQueue(con);
    }

}
