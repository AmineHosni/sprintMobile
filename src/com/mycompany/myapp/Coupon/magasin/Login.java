/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.Coupon.magasin;

import Entities.Utilisateur;
import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.db.Row;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
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
    public static Utilisateur user;

    Label loginLabel;
    Label passwordLabel;
    TextField tfLogin;
    TextField tfPassword;
    Button ok;

    Database db;
    boolean created = false;

    String cacheLogin;
    boolean fromCache = false;
    CheckBox chxMemorise;

    public Login() {
        try {
            created = Database.exists("0");
            db = Database.openOrCreate("0");

            if (created == false) {
                db.execute("create table user (id INTEGER PRIMARY KEY autoincrement,idUser INTEGER, login TEXT,email TEXT,image TEXT)");
            }

            Label switchAcount = new Label("Switch Acount");
            switchAcount.setHidden(true);

            chxMemorise = new CheckBox("Rester connecté(e)");

            hi = new Form("Login", new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER));
            new ToolbarSideMenu().insertSetting(hi, false);

            ok = new Button("OK");
//            cancel = new Button("Cancel");

            loginLabel = new Label("Login", "Container");
            loginLabel.getAllStyles().setAlignment(Component.CENTER);

            passwordLabel = new Label("Password", "Container");
            passwordLabel.getAllStyles().setAlignment(Component.CENTER);

            tfLogin = new TextField("", "Login", 20, TextArea.ANY);
            tfPassword = new TextField("", "Password", 20, TextArea.PASSWORD);
            Style loginStyle = tfLogin.getAllStyles();
            Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
            loginStyle.setBorder(RoundBorder.create().
                    rectangle(true).
                    color(0xffffff).
                    strokeColor(0).
                    strokeOpacity(120).
                    stroke(borderStroke));
            loginStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
            loginStyle.setMargin(Component.BOTTOM, 3);
            Style passwordStyle = tfPassword.getAllStyles();
            passwordStyle.setBorder(RoundBorder.create().
                    rectangle(true).
                    color(0xffffff).
                    strokeColor(0).
                    strokeOpacity(120).
                    stroke(borderStroke));

            Container box = BoxLayout.encloseY(
                    loginLabel,
                    tfLogin,
                    passwordLabel,
                    tfPassword,
                    chxMemorise,
                    GridLayout.encloseIn(1, ok),
                    switchAcount
            );

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

            try {
                Cursor cCount = db.executeQuery("select count(*) from user");
                Row row = cCount.getRow();
                System.out.println("n: " + row.getInteger(0));
                cCount.close();

                Cursor c = db.executeQuery("SELECT idUser,login,email,image FROM user order BY id DESC LIMIT 1;");
                Row r = c.getRow();
                int cacheUserId = r.getInteger(0);
                String cacheLogin = r.getString(1);
                String cacheEmail = r.getString(2);
                String cacheImage = r.getString(3);

                Utilisateur cacheUser = new Utilisateur();
                cacheUser.setId(cacheUserId);
                cacheUser.setUsername(cacheLogin);
                cacheUser.setEmail(cacheEmail);
                cacheUser.setImage(cacheImage);

//                cacheLogin = r.getString(0);
//                String cachePassword = r.getString(1);
                c.close();
                if (cacheUser.getUsername() != null) {
//                    tfLogin.setText(cacheLogin);

                    tfLogin.setHidden(true);
                    tfPassword.setHidden(true);
//                    cancel.setHidden(true);
                    chxMemorise.setHidden(true);
                    ok.setText("Continue as " + cacheLogin);
                    user = cacheUser;
                    System.out.println(user);

                    fromCache = true;
                    switchAcount.setHidden(false);
                }
            } catch (IOException ex) {

            }

            switchAcount.addPointerPressedListener((ActionListener) (ActionEvent evt) -> {
                tfLogin.setText("");
                tfLogin.setHidden(false);
                tfPassword.setHidden(false);
                chxMemorise.setHidden(false);
                chxMemorise.setSelected(true);
                ok.setText("Ok");
                switchAcount.setHidden(true);
                hi.refreshTheme();

            });

            hi.add(BorderLayout.CENTER, layers);

//            cancel.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent evt) {
//                    new Home().start();
//                }
//            });
            ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {

                    if (fromCache) {
                        isLogged = true;
                        new Home().start();

                    } else {
                        ConnectionRequest req = new ConnectionRequest();
                        req.setUrl("http://localhost/pidev2017/magasin/login.php?username=" + tfLogin.getText() + "&password=" + tfPassword.getText());
                        req.addResponseListener(new ActionListener<NetworkEvent>() {

                            @Override
                            public void actionPerformed(NetworkEvent evt) {
                                byte[] data = (byte[]) evt.getMetaData();
                                String s = new String(data);
//                        user = getUser(new String(req.getResponseData()), tflogin.getText(), tfpassword.getText());
                                id = new String(req.getResponseData());
                                if (!id.equals("")) {

                                    ConnectionRequest con = new ConnectionRequest();
                                    con.setUrl("http://localhost/pidev2017/magasin/loginGetUser.php?userid=" + id);
                                    con.addResponseListener(new ActionListener<NetworkEvent>() {
                                        @Override
                                        public void actionPerformed(NetworkEvent evt) {

                                            ArrayList<Utilisateur> users = getUtilisateurs(new String(con.getResponseData()));
                                            user = users.get(0);

                                            if (chxMemorise.isSelected()) {
                                                try {

                                                    String[] values = new String[]{String.valueOf(user.getId()), user.getUsername(),
                                                        user.getEmail(), user.getImage()};
                                                    db.execute("delete from user ");
                                                    db.execute("VACCUM ");
                                                    db.execute("insert into user (idUser,login,email,image) values(?,?,?,?)", values);
                                                    System.out.println("insertion dans sqlLite OK !");
                                                } catch (IOException ex) {

                                                }
                                            }

                                        }
                                    });
                                    NetworkManager.getInstance().addToQueue(con);

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

                }
            });

            hi.show();

        } catch (IOException ex) {
        }

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
    public ArrayList<Utilisateur> getUtilisateurs(String json) {

        ArrayList<Utilisateur> listMagasins = new ArrayList<>();
        try {

            JSONParser j = new JSONParser();
            Map<String, Object> magasins = j.parseJSON(new CharArrayReader(json.toCharArray()));
            System.out.println(magasins.getClass());
            Map<String, Object> list = (Map<String, Object>) magasins.get("user");
            System.out.println("list: " + list);

            Utilisateur m = new Utilisateur();
            for (Map.Entry<String, Object> obj : list.entrySet()) {
                if (obj.getKey().toString().equals("id")) {
                    m.setId(Integer.parseInt(obj.getValue().toString()));
                }
                if (obj.getKey().toString().equals("username")) {
                    m.setUsername(obj.getValue().toString());
                }
                if (obj.getKey().toString().equals("email")) {
                    m.setEmail(obj.getValue().toString());
                }
                if (obj.getKey().toString().equals("image")) {
                    m.setImage(obj.getValue().toString());
                }

            }
            listMagasins.add(m);
        } catch (IOException ex) {
        }
        return listMagasins;
    }

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
