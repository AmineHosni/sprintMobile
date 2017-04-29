/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.Home;
import com.mycompany.myapp.ListProduit;
import com.mycompany.myapp.Login;
import com.mycompany.myapp.MonProfile;
import java.io.IOException;

/**
 *
 * @author MBM info
 */
public class habchkleu {

    private Resources theme;

    Image maskedImage = null;

    public void insertHabchkleu(Form form, boolean connecter) {
        try {

            Image profilePic = Image.createImage("/lana.jpg");
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

            Object mask = maskImage.createMask();

            maskedImage = profilePic.applyMask(mask);

        } catch (IOException ex) {
        }
        if (connecter) {
            Container c = new Container(new BoxLayout(BoxLayout.X_AXIS));
            
            c.add(maskedImage);
            Container d = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            c.add(new Label("Lana del rey", "Title"));
            form.getToolbar().addComponentToSideMenu(c);
            form.getToolbar().addCommandToSideMenu("Mon Profile", null, new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent evt) {
                    new MonProfile().start();
                }

            });
            
        } else {
            Container c = new Container();
            c.add(new Label("Sign in", "Title"));
            Button b = new Button();
            b.addActionListener(e -> {
                new Login().start();
            });
            c.setLeadComponent(
                    b);
            b.setHidden(true);
            form.getToolbar().addComponentToSideMenu(c);
            form.getToolbar().addCommandToSideMenu("Login", null, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent evt) {
                    new Login().start();
                }

            });
            

        }
        //connecter bech twalli variable fil base (sql) w kol mayconnecti tetbadel
        form.getToolbar().addCommandToSideMenu("Home", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new Home().start();
            }
        });

        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        form.getToolbar().addCommandToSideMenu("Les Magasins", null, new ActionListener() {

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

    }
}
