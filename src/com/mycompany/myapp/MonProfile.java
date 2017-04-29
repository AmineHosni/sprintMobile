/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import Entities.Produit;
import com.codename1.components.FloatingActionButton;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.BOTTOM;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Rectangle;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.ArrayList;
import service.ProduitService;

/**
 *
 * @author MBM info
 */
public class MonProfile {

    Form current;

    private Resources theme;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");
        Toolbar.setGlobalToolbar(true);

    }

    public void start() {
        if (current != null) {
            current.show();
            return;
        }
        Label label3 = new Label();
        Form MonProfile = new Form();
        MonProfile.setLayout(BoxLayout.y());
        Toolbar tb = MonProfile.getToolbar();
        tb.setTitleCentered(false);
        try {
            Image profilePic = Image.createImage("/jj.jpg");
            Label label1 = new Label(profilePic);
            //form.addComponent(label1);
            int w = profilePic.getWidth();
            int h = profilePic.getHeight();

            Image maskImage = Image.createImage(w, h);
            Graphics g = maskImage.getGraphics();
            g.setAntiAliased(true);
            g.setColor(0x000000);
            g.fillRect(0, 0, w, h);
            g.setColor(0xffffff);
            g.fillArc(0, 0, w, h, 0, 360);
            Label label2 = new Label(maskImage);
            //form.addComponent(label2);

            Object mask = maskImage.createMask();

            Image maskedImage = profilePic.applyMask(mask);
            label3 = new Label(maskedImage.scaled(100, 100));

        } catch (IOException ex) {
        }

        Container remainingTasks = BoxLayout.encloseY(
                new Label("12", "CenterTitle"),
                new Label("remaining tasks", "CenterSubTitle")
        );
       // backrouuuuuuuuund color  remainingTasks.getStyle().setBgColor();
        remainingTasks.setUIID("RemainingTasks");
        Container completedTasks = BoxLayout.encloseY(
                new Label("32", "CenterTitle"),
                new Label("completed tasks", "CenterSubTitle")
        );
        completedTasks.setUIID("CompletedTasks");
        Container titleCmp = BoxLayout.encloseY(
                //FlowLayout.encloseIn(menuButton),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY(
                                
                                new Label("Mon Profile", "SubTitle"),
                                 new Label("Jamel Mustapha", "Title")

                        )
                ).add(BorderLayout.EAST, label3)//,GridLayout.encloseIn(2, remainingTasks, completedTasks)
        );
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.bindFabToContainer(MonProfile.getContentPane());

        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);
        fab.addActionListener(e -> {
            try {
                Dialog popup = new Dialog();
                popup.setDialogUIID("Container");
                popup.setLayout(new LayeredLayout());
                Image img = Image.createImage("/ajouter.png");
                Button c1 = new Button(img);
                Button trans = new Button(" ");
                trans.setUIID("Container");
                c1.setUIID("Container");
                Style c1s = c1.getAllStyles();
                c1s.setMarginUnit(Style.UNIT_TYPE_DIPS);
                c1s.setMarginBottom(16);
                c1s.setMarginLeft(12);
                c1s.setMarginRight(3);
                popup.add(trans).add(FlowLayout.encloseIn(c1));
                ActionListener a = ee ->  new InsertProduit().start();
                trans.addActionListener(a);
                c1.addActionListener(a);
                popup.setTransitionInAnimator(CommonTransitions.createEmpty());
                popup.setTransitionOutAnimator(CommonTransitions.createEmpty());
                
                popup.setDisposeWhenPointerOutOfBounds(true);
            int t = MonProfile.getTintColor();
            MonProfile.setTintColor(0);
            popup.showPopupDialog(new Rectangle(MonProfile.getWidth() - 20, MonProfile.getHeight() - 15, 10, 10));
          
            MonProfile.setTintColor(t);
            } catch (IOException ex) {
            }
        });
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev2017/select.php");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ArrayList<Produit> produits = new ProduitService().getListProduits(new String(con.getResponseData()));
                for(Produit produit : produits) {
                    if (produit.getSeller()==9) {
                        try {
                        Container container = new Container(new FlowLayout());
                        container.add(new Label(Image.createImage("/frigi.png").scaled(46, 46)));
                        Container detailsContainer = new Container(new BoxLayout(2));
                        detailsContainer.add(new Label(produit.getLibelle()));
                        detailsContainer.add(produit.getPrixProduit().toString());
                        container.add(detailsContainer);
                        Button b = new Button(produit.getLibelle());
                        b.addActionListener(e -> {
                            new AfficherProduit().start(produit.getId(),true);
                        });
                        b.setHidden(true);
                        
                        container.add(b);
                        container.setLeadComponent(b);
                        MonProfile.add(container);
                    } catch (IOException ex) {
                    }
                    }
                    
                }
                MonProfile.refreshTheme();
            }
        });
        NetworkManager.getInstance().addToQueue(con);
        MonProfile.show();
    }

}
