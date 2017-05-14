/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.Coupon.MesCoupon;
import com.mycompany.myapp.produit.Home;
import java.io.IOException;

/**
 *
 * @author MBM info
 */
public class ToolbarSideMenu {

    private Resources theme;

    Image maskedImage = null;

    public void insertSetting(Form form, boolean connecter) {

        Container utilisateur = new Container(new BoxLayout(BoxLayout.X_AXIS));

        try {
            Image profilePic = Image.createImage("/user.png");
            utilisateur.add(profilePic);
            Container utilisateurCommand = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Container seConnecter = new Container();
            seConnecter.add(new Label("Connecter", "Title"));
            Button btnSeConnecter = new Button();
            btnSeConnecter.addActionListener(e -> System.out.println("Connecter"));
            seConnecter.add(btnSeConnecter);
            seConnecter.setLeadComponent(btnSeConnecter);
            Container inscription = new Container();
            inscription.add(new Label("Inscription", "Title"));
            Button btnInscription = new Button();
            btnInscription.addActionListener(e -> System.out.println("Inscription"));
            seConnecter.add(btnInscription);
            inscription.setLeadComponent(btnInscription);

            utilisateurCommand.add(seConnecter);
            utilisateurCommand.add(inscription);

            utilisateur.add(utilisateurCommand);

            form.getToolbar().addComponentToSideMenu(utilisateur);
        } catch (IOException ex) {
        }

        form.getToolbar().addCommandToSideMenu("Home", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new Home().start();
            }
        });

        form.getToolbar().addCommandToSideMenu("Magasins", null, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                // 7ot win t7ebbo yemchi
            }
        });

        form.getToolbar().addCommandToSideMenu("Vente Flash", null, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                // 7ot win t7ebbo yemchi
            }
        });

        form.getToolbar().addCommandToSideMenu("Mes Coupons", null, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                new MesCoupon().start();
            }
        });

//        try {
//
//            Image profilePic = Image.createImage("/lana.jpg");
//            //form.addComponent(label1);
//            int w = profilePic.getWidth();
//            int h = profilePic.getHeight();
//            Image maskImage = Image.createImage(w, h);
//            Graphics g = maskImage.getGraphics();
//            g.setAntiAliased(true);
//            g.setColor(0x000000);
//            g.fillRect(0, 0, w, h);
//            g.setColor(0xffffff);
//            g.fillArc(0, 0, w, h, 0, 360);
//
//            Object mask = maskImage.createMask();
//
//            maskedImage = profilePic.applyMask(mask);
//
//        } catch (IOException ex) {
//        }
//        if (connecter) {
//            
//            
//            Container c = new Container(new BoxLayout(BoxLayout.X_AXIS));
//            c.add(maskedImage);
//            c.add(new Label("Lana del rey", "Title"));
//            
//            form.getToolbar().addComponentToSideMenu(c);
//            
//            form.getToolbar().addCommandToSideMenu("Mes Produits", null, new ActionListener() {
//                
//                @Override
//                public void actionPerformed(ActionEvent evt) {
//                    new MonProfile().start();
//                }
//
//            });
//            
//        } else {
//            Container c = new Container();
//            c.add(new Label("Sign in", "Title"));
//            Button b = new Button();
//            b.addActionListener(e -> {
//                new Login().start();
//            });
//            c.setLeadComponent(
//                    b);
//            b.setHidden(true);
//            form.getToolbar().addComponentToSideMenu(c);
//            form.getToolbar().addCommandToSideMenu("Login", null, new ActionListener() {
//
//                @Override
//                public void actionPerformed(ActionEvent evt) {
//                    new Login().start();
//                }
//
//            });
//            
//
//        }
//        //connecter bech twalli variable fil base (sql) w kol mayconnecti tetbadel
//        
//
//        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
//        form.getToolbar().addCommandToSideMenu("Les Magasins", null, new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent evt) {
//                // 7ot win t7ebbo yemchi
//            }
//        });
//
//        form.getToolbar().addCommandToSideMenu("Vente Flash", null, new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent evt) {
//                // 7ot win t7ebbo yemchi
//            }
//        });
    }
}
