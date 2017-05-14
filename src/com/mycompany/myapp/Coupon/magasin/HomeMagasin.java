/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.Coupon.magasin;

import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.DefaultLookAndFeel;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import service.ToolbarSideMenu;

/**
 *
 * @author AmineHosni
 */
public class HomeMagasin {

    Form hi;
private Resources theme;
public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature, uncomment if you have a pro subscription
        // Log.bindCrashProtection(true);
    }
    public HomeMagasin() {
        
        
        System.out.println("Home");
        hi = new Form("Home", new BorderLayout());
//        if (Login.user!=null){
//            hi.setTitle(Login.user.getNom());
//        }
        hi.setScrollableY(false);

        new ToolbarSideMenu().insertSetting(hi, false);

        hi.getToolbar().addCommandToLeftBar("", null, null);
        hi.getToolbar().addCommandToRightBar("", null, null);
        
//        Label x = new Label("xD");
//        try {
//            hi.getToolbar().addComponentToSideMenu(new Label(Image.createImage("/face.JPG").scaled(45, 45)));
//        } catch (IOException ex) {
//        }
//        hi.getToolbar().getStyle().setBgColor(0xFFFFFF);

        Tabs t = new Tabs();
        t.getStyle().setBgColor(0xFFFFFF);
        t.hideTabs();

        Style s = UIManager.getInstance().getComponentStyle("Button");
        FontImage radioEmptyImage = FontImage.createMaterial(FontImage.MATERIAL_RADIO_BUTTON_UNCHECKED, s);
        FontImage radioFullImage = FontImage.createMaterial(FontImage.MATERIAL_RADIO_BUTTON_CHECKED, s);
        ((DefaultLookAndFeel) UIManager.getInstance().getLookAndFeel()).setRadioButtonImages(radioFullImage, radioEmptyImage, radioFullImage, radioEmptyImage);

        Container container1;
        try {
            container1 = BoxLayout.encloseY(
                    new Label(Image.createImage("/pc1.png").scaled(Display.getInstance().getDisplayWidth(),
                            Display.getInstance().getDisplayHeight() / 3)));

            t.addTab("Tab1", container1);
            Container container2 = BoxLayout.encloseY(new Label(Image.createImage("/promo_banner.jpg").scaled(Display.getInstance().getDisplayWidth(),
                    Display.getInstance().getDisplayHeight() / 3)));
            t.addTab("Tab2", container2);
            Container container3 = BoxLayout.encloseY(new Label(Image.createImage("/promotional-products1.png").scaled(Display.getInstance().getDisplayWidth(),
                    Display.getInstance().getDisplayHeight() / 3)));
            t.addTab("Tab3", container3);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
//        t.addTab("Tab2", new SpanLabel("Some text directly in the tab"));

        RadioButton firstTab = new RadioButton("");
        RadioButton secondTab = new RadioButton("");
        RadioButton thirdTab = new RadioButton("");
        firstTab.setUIID("Container");
        secondTab.setUIID("Container");
        thirdTab.setUIID("Container");
        new ButtonGroup(firstTab, secondTab, thirdTab);
        firstTab.setSelected(true);
        Container tabsFlow = FlowLayout.encloseCenter(firstTab, secondTab, thirdTab);
        Container images = new Container(new LayeredLayout());
        images.add(BorderLayout.north(t));
        images.add(BorderLayout.north(tabsFlow));

//        hi.getLayeredPane().add(tabsFlow);
        Container southContainer = FlowLayout.encloseCenterMiddle(new TextField("1"), new TextField("2"),
                new TextField("3"), new TextField("4"), new TextField("5"),
                new TextField("6"), new TextField("7"), new TextField("8"),
                new TextField("6"), new TextField("7"), new TextField("8"),
                new TextField("9"), new TextField("10"), new TextField("11"));
        southContainer.setScrollableY(true);
//        southContainer.add(new TextField("some text"));
//        southContainer.add(new TextField("some text"));
//        southContainer.add(new TextField("some text"));
//        southContainer.add(new TextField("some text"));
//        southContainer.add(new TextField("some text"));
//        southContainer.add(new TextField("some text"));
        hi.add(BorderLayout.NORTH, images);
        hi.add(BorderLayout.SOUTH, southContainer);

        t.addSelectionListener((i1, i2) -> {
            switch (i2) {
                case 0:
                    if (!firstTab.isSelected()) {
                        firstTab.setSelected(true);
                        System.out.println(t.getSelectedIndex());
                    }
                    break;
                case 1:
                    if (!secondTab.isSelected()) {
                        secondTab.setSelected(true);
                        System.out.println(t.getSelectedIndex());
                    }
                    break;
                case 2:
                    if (!thirdTab.isSelected()) {
                        thirdTab.setSelected(true);
                        System.out.println(t.getSelectedIndex());
                    }
                    break;
            }
        });

    }

    public Form getF() {
        return hi;
    }

    public void setF(Form f) {
        this.hi = f;
    }

    public void show() {

        hi.show();
    }

}
