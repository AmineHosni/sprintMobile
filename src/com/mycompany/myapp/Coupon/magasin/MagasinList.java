/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.Coupon.magasin;

import com.codename1.charts.compat.Paint;
import com.codename1.components.SpanLabel;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Entities.Magasin;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import service.ToolbarSideMenu;

/**
 *
 * @author AmineHosni
 */
public class MagasinList {

    Form hi;
    Container magasinContainer = new Container(new GridLayout(2, 2));
    Container ligneContainer;
    Button button;
    Container infoContainer = new Container(new GridLayout(2));
    Container magasinContainers = new Container(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
    Container imageContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
    Container container;
    Container detailsContainer;

    public MagasinList() {
        System.out.println("Magasin list");
        hi = new Form("Liste des magasins", new BoxLayout(2));
        new ToolbarSideMenu().insertSetting(hi, false);
        Style s = UIManager.getInstance().getComponentStyle("Title");
        TextField searchField = new TextField("", "Chercher un magasin"); // <1>
        searchField.getHintLabel().setUIID("Title");
        searchField.setUIID("Title");
        searchField.getAllStyles().setAlignment(Component.LEFT);
        hi.getToolbar().setTitleComponent(searchField);
        FontImage searchIcon = FontImage.createMaterial(FontImage.MATERIAL_SEARCH, s);
        SpanLabel sp = new SpanLabel();

        HashMap<String, Magasin> data = new HashMap<String, Magasin>();

        //hi.add(sp);
        hi.show();

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev2017/magasin/selectMagasin.php");
        con.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                ArrayList<Magasin> magasins = getListMagasin(new String(con.getResponseData()));

                for (Magasin magasin : magasins) {
                    try {
                        data.put(magasin.getName(), magasin);
                        //System.out.println(magasin.toString());
                        ligneContainer = new Container(new BorderLayout());
                        //Image image = Image.createImage("/face.jpg").scaled(150, 150);
                        //System.out.println(magasin);
                        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage("/icon-magasin.png"), false);
                        Image image = URLImage.createToStorage(placeholder, "magasin+" + magasin.getImage_name(), "http://localhost/CodeNameOne/" + magasin.getImage_name());
                        Label imageLabel = new Label(image.scaled(150, 150));
                        Label name = new Label(magasin.getName());
                        name.setAlignment(Paint.Align.CENTER);

                        button = new Button();
                        button.setHidden(true);

                        //name.setVerticalAlignment(Paint.Align.CENTER);
                        ligneContainer.add(BorderLayout.CENTER, imageLabel);
                        ligneContainer.add(BorderLayout.SOUTH, name);
                        ligneContainer.add(BorderLayout.NORTH, button);
                        //ligneContainer.add(BorderLayout.SOUTH, new Label(magasin.getAddress()));
                        ligneContainer.setSize(new Dimension(500, 500));

                        button.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent evt) {
                                System.out.println("clicked");
                                MagasinDetails magasinDetails;
                                magasinDetails = new MagasinDetails(magasin);
                                System.out.println(magasin.getName());
                                magasinDetails.getF().show();

                            }
                        });
                        ligneContainer.setLeadComponent(button);
                        //ligneContainer.setPreferredSize(new Dimension(100, 100));
                        magasinContainer.add(ligneContainer);

                    } catch (IOException ex) {
                    }
                }
                hi.add(magasinContainer);
                //System.out.println(getListMagasin(new String(con.getResponseData())));

            }
        });

//        searchField.addDataChangeListener((i1, i2) -> { // <2>
//            String t = searchField.getText();
//            if (t.length() < 1) {
//                for (Component cmp : hi.getContentPane()) {
//                    cmp.setHidden(false);
//                    cmp.setVisible(true);
//                }
//            } else {
//                t = t.toLowerCase();
//                for (Component cmp : hi.getContentPane()) {
//                    String val = null;
//                    if (cmp instanceof Label) {
//                        val = ((Label) cmp).getText();
//                    } else if (cmp instanceof TextArea) {
//                        val = ((TextArea) cmp).getText();
//                    } else {
//                        val = (String) cmp.getPropertyValue("text");
//                    }
//                    boolean show = val != null && val.toLowerCase().indexOf(t) > -1;
//                    cmp.setHidden(!show); // <3>
//                    cmp.setVisible(show);
//                }
//            }
//            hi.getContentPane().animateLayout(250);
//        });
//
//        hi.add("A Game of Thrones").
//        add("A Clash Of Kings").
//        add("A Storm Of Swords").
//        add("A Feast For Crows").
//        add("A Dance With Dragons").
//        add("The Winds of Winter").
//        add("A Dream of Spring");
        hi.getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_SEARCH, e -> {
//            new Search().start(txtSearch.getText());
            String t = searchField.getText();
            System.out.println(t);

            magasinContainer.removeAll();
            ligneContainer.removeAll();

            if (imageContainer != null) {
                imageContainer.removeAll();
            }
            hi.refreshTheme();
            hi.revalidate();

            ConnectionRequest req = new ConnectionRequest();
            req.setUrl("http://localhost/pidev2017/magasin/selectMagasin.php");
            req.addResponseListener(new ActionListener<NetworkEvent>() {

                @Override
                public void actionPerformed(NetworkEvent evt) {
                    try {
                        Magasin magasin = getMagasin(new String(req.getResponseData()), t);
//                        System.out.println(id + "" + magasin);
                        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage("/icon-magasin.png"), false);
                        Image image = URLImage.createToStorage(placeholder, "magAZEAZEasin+" + magasin.getImage_name(), "http://localhost/CodeNameOne/" + magasin.getImage_name());
                        Label imageLabel = new Label(image.scaled(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight() / 3));
                        Label name = new Label(magasin.getName());

                        //name.setVerticalAlignment(Paint.Align.CENTER);
                        //name.getStyle().setFgColor(ColorUtil.GREEN);
                        name.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
                        imageContainer.add(imageLabel);
                        imageContainer.add(name);
                        if (magasinContainers != null) {
                            magasinContainers.removeAll();
                            magasinContainers.add(BorderLayout.NORTH, imageContainer);
                        }
//                    magasinContainer.add(BorderLayout.CENTER, infoContainer);
                        hi.add(magasinContainers);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    hi.refreshTheme();
                }

            });

            NetworkManager.getInstance().addToQueue(req);

        });
//        
        hi.getToolbar().addCommandToLeftBar("", FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, UIManager.getInstance().getComponentStyle("TitleCommand")), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                HomeMagasin home = new HomeMagasin();
                home.getF().show();
            }
        });
//        hi.getToolbar().addCommandToSideMenu("List Check", null, new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent evt) {
//                //f2.show();
//                ListCheck login = new ListCheck(theme);
//                login.getF().showBack();
//            }
//        });
//        hi.getToolbar().addCommandToSideMenu("Select items", null, new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent evt) {
//                //f2.show();
//                SelectableItems login = new SelectableItems(theme);
//                login.getF().showBack();
//            }
//        });
        hi.refreshTheme();

        NetworkManager.getInstance().addToQueue(con);

//        hi.getContentPane().addPullToRefresh(new Runnable() {
//            @Override
//            public void run() {
////                infoContainer.removeAll();
//                magasinContainer.removeAll();
//                imageContainer.removeAll();
////                container.removeAll();
////                detailsContainer.removeAll();
//
//                infoContainer.removeAll();
//                magasinContainers.removeAll();
//                imageContainer.removeAll();
//                container.removeAll();
//                detailsContainer.removeAll();
//
//                hi.removeAll();
//                NetworkManager.getInstance().addToQueue(con);
////                NetworkManager.getInstance().addToQueue(req);
//                hi.refreshTheme();
//                hi.revalidate();
//            }
//        });
    }

    public Form getF() {
        return hi;
    }

    public ArrayList<Magasin> getListMagasin(String json) {
        ArrayList<Magasin> listMagasins = new ArrayList<>();

        try {

            JSONParser j = new JSONParser();

            Map<String, Object> magasins = j.parseJSON(new CharArrayReader(json.toCharArray()));
            System.out.println(magasins.getClass());

            List<Map<String, Object>> list = (List<Map<String, Object>>) magasins.get("magasin");

            for (Map<String, Object> obj : list) {
                Magasin m = new Magasin();
                m.setId(Integer.parseInt(obj.get("id").toString()));
                m.setName(obj.get("name").toString());
                m.setOwner(obj.get("owner").toString());
                m.setAddress(obj.get("address").toString());
                m.setImage_name(obj.get("image_name").toString());

                String x = obj.get("updated_at").toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date birthDate = sdf.parse(x);
                m.setUpdated_at(birthDate);

                m.setAltitude(obj.get("altitude").toString());
                m.setLongitude(obj.get("longitude").toString());
                m.setDescription(obj.get("description").toString());
                m.setImage_name(obj.get("image_name").toString());
                m.setOwner_id(Integer.parseInt(obj.get("owner_id").toString()));
                listMagasins.add(m);

            }

        } catch (IOException ex) {
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return listMagasins;

    }

    public Magasin getMagasin(String json, String t) {
        Magasin m = new Magasin();
        try {

            JSONParser j = new JSONParser();

            Map<String, Object> etudiants = j.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) etudiants.get("magasin");
            for (Map<String, Object> obj : list) {

                if (obj.get("name").toString().equals(t)) {
                    m.setName(t);
                    m.setId(Integer.parseInt(obj.get("id").toString()));
                    m.setOwner(obj.get("owner").toString());
                    m.setAddress(obj.get("address").toString());
                    m.setImage_name(obj.get("image_name").toString());

                    String x = obj.get("updated_at").toString();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date birthDate;
                    try {
                        birthDate = sdf.parse(x);
                        m.setUpdated_at(birthDate);
                    } catch (ParseException ex) {
                    }

                    m.setAltitude(obj.get("altitude").toString());
                    m.setLongitude(obj.get("longitude").toString());
                    m.setDescription(obj.get("description").toString());
                    m.setImage_name(obj.get("image_name").toString());
                    m.setOwner_id(Integer.parseInt(obj.get("owner_id").toString()));
                }

            }

        } catch (IOException ex) {
        }
        return m;

    }

    public void show() {

        hi.show();
    }
}
