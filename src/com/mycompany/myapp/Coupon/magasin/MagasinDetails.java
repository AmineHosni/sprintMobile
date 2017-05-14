/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.Coupon.magasin;

import Entities.Magasin;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import service.ToolbarSideMenu;

/**
 *
 * @author AmineHosni
 */
public class MagasinDetails {

//    Container infoContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
    Container infoContainer = new Container(new GridLayout(2));
    Container magasinContainer = new Container(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
    Container imageContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
    Container container;
    Container detailsContainer;

    Form f;

    public MagasinDetails( Magasin magasin) {
        System.out.println("Magasin details");
        String name = magasin.getName();
        int id = magasin.getId();

        f = new Form(name, new BoxLayout(BoxLayout.X_AXIS));
        new ToolbarSideMenu().insertSetting(f, false);

        System.out.println(name);
        
        f.getToolbar().addCommandToLeftBar("", FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, UIManager.getInstance().getComponentStyle("TitleCommand")), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new MagasinList().getF().show();
            }
        });

        f.show();

        //UsefulFunctions.setBackCommand(this.getF(),new MyApplication().getF());
        //Add Edit, Add and Delete Commands to the home Form context Menu
        Image im = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, UIManager.getInstance().getComponentStyle("Command"));
        Command edit = new Command("Edit", im) {

            @Override
            public void actionPerformed(ActionEvent evt) {
                System.out.println("Editing");
            }
        };
//        f.getToolbar().addCommandToOverflowMenu(edit);

        im = FontImage.createMaterial(FontImage.MATERIAL_LIBRARY_ADD, UIManager.getInstance().getComponentStyle("Command"));
        Command add = new Command("Add", im) {

            @Override
            public void actionPerformed(ActionEvent evt) {
                System.out.println("Adding");
                AddMagasin addMagasin = new AddMagasin();
                addMagasin.getF().show();
            }
        };
        f.getToolbar().addCommandToOverflowMenu(add);

        im = FontImage.createMaterial(FontImage.MATERIAL_DELETE, UIManager.getInstance().getComponentStyle("Command"));
        Command delete = new Command("Delete", im) {

            @Override
            public void actionPerformed(ActionEvent evt) {
                if (Dialog.show("Veuillez confirmer la suppression", "Êtes-vous sûr ?", "Oui,Supprimer", "annuler")) {
                    ConnectionRequest con = new ConnectionRequest();
                    con.setUrl("http://localhost/pidev2017/magasin/DeleteMagasin.php?id=" + magasin.getId());
                    con.addResponseListener(new ActionListener<NetworkEvent>() {
                        @Override
                        public void actionPerformed(NetworkEvent evt) {
                            byte[] data = (byte[]) evt.getMetaData();
                            String s = new String(data);

                            if (s.equals("success")) {
                                if (Dialog.show("Succès !", "Magasin supprimé avec succès", "Ok :-)", null)) {
                                    new MagasinList().getF().show();
                                };
                            } else {
                                Dialog.show("Erreure !", "Suppression Impossible", "Ok :-)", null);
                            }
                        }
                    });

                    NetworkManager.getInstance().addToQueue(con);
                }
            }

        };
        f.getToolbar().addCommandToOverflowMenu(delete);

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev2017/magasin/selectMagasin.php");
        con.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    Magasin magasin = getMagasin(new String(con.getResponseData()), id);
                    System.out.println(id + "" + magasin);
                    EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage("/icon-magasin.png"), false);
                    Image image = URLImage.createToStorage(placeholder, "magAZEAZEasin+" + magasin.getImage_name(), "http://localhost/CodeNameOne/" + magasin.getImage_name());
                    Label imageLabel = new Label(image.scaled(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight() / 3));
                    Label name = new Label(magasin.getName());

                    //name.setVerticalAlignment(Paint.Align.CENTER);
                    //name.getStyle().setFgColor(ColorUtil.GREEN);
                    name.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
                    imageContainer.add(imageLabel);
                    imageContainer.add(name);
                    magasinContainer.add(BorderLayout.NORTH, imageContainer);
//                    magasinContainer.add(BorderLayout.CENTER, infoContainer);
                    f.add(magasinContainer);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                f.refreshTheme();
            }

        });

        NetworkManager.getInstance().addToQueue(con);

        f.getContentPane().addPullToRefresh(new Runnable() {
            @Override
            public void run() {
//                infoContainer.removeAll();
                magasinContainer.removeAll();
                imageContainer.removeAll();
//                container.removeAll();
//                detailsContainer.removeAll();

                f.removeAll();
                NetworkManager.getInstance().addToQueue(con);
//                NetworkManager.getInstance().addToQueue(req);
                f.refreshTheme();
                f.revalidate();
            }
        });
    }

    public Magasin getMagasin(String json, Integer id) {
        Magasin m = new Magasin();
        try {

            JSONParser j = new JSONParser();

            Map<String, Object> etudiants = j.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) etudiants.get("magasin");
            for (Map<String, Object> obj : list) {

                if (Integer.parseInt(obj.get("id").toString()) == id) {
                    m.setId(id);
                    m.setName(obj.get("name").toString());
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

    public Form getF() {
        return f;
    }

    public void show() {

        f.show();
    }
}
