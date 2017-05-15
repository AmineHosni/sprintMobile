/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.Coupon.magasin;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.mycompany.myapp.produit.Home;
import service.ToolbarSideMenu;

/**
 *
 * @author AmineHosni
 */
public class Login {

    Form hi;
    public static Boolean isLogged = false;
//    String username;
//    static User user = new User();
    public static String id = "";

    public Login() {
        hi = new Form("Login", new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER));
        new ToolbarSideMenu().insertSetting(hi, false);

        Button ok = new Button("OK");
        Button cancel = new Button("Cancel");

        Label loginLabel = new Label("Login", "Container");
        loginLabel.getAllStyles().setAlignment(Component.CENTER);

        Label passwordLabel = new Label("Password", "Container");
        passwordLabel.getAllStyles().setAlignment(Component.CENTER);

        TextField tflogin = new TextField("", "Login", 20, TextArea.ANY);
        TextField tfpassword = new TextField("", "Password", 20, TextArea.PASSWORD);
        Style loginStyle = tflogin.getAllStyles();
        Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
        loginStyle.setBorder(RoundBorder.create().
                rectangle(true).
                color(0xffffff).
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        loginStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
        loginStyle.setMargin(Component.BOTTOM, 3);
        Style passwordStyle = tfpassword.getAllStyles();
        passwordStyle.setBorder(RoundBorder.create().
                rectangle(true).
                color(0xffffff).
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));

        Container box = BoxLayout.encloseY(
                loginLabel,
                tflogin,
                passwordLabel,
                tfpassword,
                GridLayout.encloseIn(2, cancel, ok));

        Button closeButton = new Button();
        Style closeStyle = closeButton.getAllStyles();
        closeStyle.setFgColor(0xffffff);
        closeStyle.setBgTransparency(0);
        closeStyle.setPaddingUnit(Style.UNIT_TYPE_DIPS);
        closeStyle.setPadding(3, 3, 3, 3);
        closeStyle.setBorder(RoundBorder.create().shadowOpacity(100));
        FontImage.setMaterialIcon(closeButton, FontImage.MATERIAL_CLOSE);

        Container layers = LayeredLayout.encloseIn(box, FlowLayout.encloseRight(closeButton));
        Style boxStyle = box.getUnselectedStyle();
        boxStyle.setBgTransparency(255);
        boxStyle.setBgColor(0xeeeeee);
        boxStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
        boxStyle.setPaddingUnit(Style.UNIT_TYPE_DIPS);
        boxStyle.setMargin(4, 3, 3, 3);
        boxStyle.setPadding(2, 2, 2, 2);

        closeButton.addActionListener((ActionListener) (ActionEvent evt) -> {
            new Home().start();
        });

        hi.add(BorderLayout.CENTER, layers);

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new Home().start();
            }
        });
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ConnectionRequest req = new ConnectionRequest();
                req.setUrl("http://localhost/pidev2017/magasin/login.php?username=" + tflogin.getText() + "&password=" + tfpassword.getText());
                req.addResponseListener(new ActionListener<NetworkEvent>() {

                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        byte[] data = (byte[]) evt.getMetaData();
                        String s = new String(data);
//                        user = getUser(new String(req.getResponseData()), tflogin.getText(), tfpassword.getText());
                        id = new String(req.getResponseData());
                        if (!id.equals("")) {

                            
                            
                            ConnectionRequest con = new ConnectionRequest();
                            con.setUrl("http://localhost/pidev2017/magasin/loginGetUser.php?username=" + tflogin.getText() + "&password=" + tfpassword.getText());
                            con.addResponseListener(new ActionListener<NetworkEvent>() {
                                @Override
                                public void actionPerformed(NetworkEvent evt) {
                                    
                                    
                                    
                                }
                            });
                            
                            

                            System.out.println("id: " + id);
                            isLogged = true;
                            new Home().start();
                        } else if (!Dialog.show("Erreur !", "login ou mot de passe incorrect", "Ok", "abandonner")) {

                            new Home().start();
                        }
                    }
                });

                NetworkManager.getInstance().addToQueue(req);
            }
        });

        hi.show();

    }

//    private User getUser(String json, String username, String password) {
//        User u = new User();
//        try {
//
//            JSONParser j = new JSONParser();
//
//            Map<String, Object> etudiants = j.parseJSON(new CharArrayReader(json.toCharArray()));
//            List<Map<String, Object>> list = (List<Map<String, Object>>) etudiants.get("user");
//            for (Map<String, Object> obj : list) {
//
//                u.setId(Integer.parseInt(obj.get("id").toString()));
//                u.setUsername(obj.get("username").toString());
//                u.setEmail(obj.get("email").toString());
//                u.setNom(obj.get("nom").toString());
//
//            }
//
//        } catch (IOException ex) {
//        }
//        return u;
//
//    }
    public Form getF() {
        return hi;
    }

    public void show() {
        hi.show();
    }

    public static Boolean getStatus() {
        return isLogged;
    }

    public static void setStatus(Boolean b) {
        isLogged = b;
    }
//
//    public static User getUser() {
//        return user;
//    }
}
