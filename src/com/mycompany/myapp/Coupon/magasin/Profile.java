/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.Coupon.magasin;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import service.ToolbarSideMenu;

/**
 *
 * @author AmineHosni
 */
public class Profile {

    Form f;

    public Profile(Resources theme) {
        System.out.println("Profile");
        f = new Form(new BorderLayout());
        new ToolbarSideMenu().insertSetting(f, false);

        Container buttons = new Container(BoxLayout.y());
        Button ajoutMagasin = new Button("Ajouter Magasin");
        buttons.add(ajoutMagasin);

        f.add(BorderLayout.CENTER, buttons);

        ajoutMagasin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                AddMagasin addMagasin = new AddMagasin();
                addMagasin.getF().show();
            }
        });
        f.getToolbar().addCommandToLeftBar("", FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, UIManager.getInstance().getComponentStyle("TitleCommand")), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                HomeMagasin home = new HomeMagasin();
                home.getF().show();
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
