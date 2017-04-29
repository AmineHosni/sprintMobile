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
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
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

    public void start(Integer id,boolean isconnceted) {
        
       Form formAffiche = new Form(new BoxLayout(2));
             new habchkleu().insertHabchkleu(formAffiche,false);


        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev2017/select.php");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Produit produit = new ProduitService().getProduit(new String(con.getResponseData()), id);
                Container container = new Container();
                Container container2 = new Container();
                Container container4 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                Container container5 = new Container(new BoxLayout(BoxLayout.X_AXIS));
                ImageViewer img = new ImageViewer();
                img.setImageList(new ProduitService().listerImages(produit));
                container.add(img);
                
                container2.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
                container4.add(new Label("Libelle"));
                container4.add(new Label(produit.getLibelle()));
                container5.add(new Label("Description"));
                container5.add(new Label(produit.getDescription()));
                container2.add(container4);
                container2.add(container5);
                Container container6 = new Container(new BorderLayout());
                container6.add(BorderLayout.CENTER,container2);
              
                
                formAffiche.add(container);
                formAffiche.add(container6);
                   if (isconnceted==true) {

                    Button btnSupprimer = new Button("Supprimer");
                    formAffiche.add(btnSupprimer);

                    btnSupprimer.addActionListener(e->{
                    new ProduitService().delete(produit.getId());
                    new ListProduit().start();
                    System.out.println("connecter");

                });
                }
                           formAffiche.show();
            }
        });
        NetworkManager.getInstance().addToQueue(con);
        }

}
