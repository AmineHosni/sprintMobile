/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.Coupon.magasin;

import com.codename1.capture.Capture;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ToastBar;
import com.codename1.googlemaps.MapContainer;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.maps.Coord;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.produit.Home;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 *
 * @author AmineHosni
 */
public class AddMagasin {

    private Resources theme;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature, uncomment if you have a pro subscription
        // Log.bindCrashProtection(true);
    }
    private MapContainer.MapObject marker;

    Form f;
    TextField tfLogin;
    String lat, lng;
    Label fileNameLabel;

    String nomMagasin, ownerMagasin, descriptionMagasin;
    TextField tfNomMagasin, tfOwnerMagasin, tfDescriptionMagasin;
    String imageName, adresseMagasin;

    public AddMagasin() {
        System.out.println("Add Magasin");
        f = new Form("Ajouter Magasin");
        Tabs t = new Tabs();

        final MapContainer cnt = new MapContainer();
        cnt.setSize(new Dimension(Display.getInstance().getDisplayWidth() / 2, Display.getInstance().getDisplayHeight() / 2));
        Button btnMoveCamera = new Button("Move Camera");
        btnMoveCamera.addActionListener(e -> {
            cnt.setCameraPosition(new Coord(-33.867, 151.206));
        });
        Style s = new Style();
        s.setFgColor(0xff0000);
        s.setBgTransparency(0);
        FontImage markerImg = FontImage.createMaterial(FontImage.MATERIAL_PLACE, s, Display.getInstance().convertToPixels(3));

        Button btnAddMarker = new Button("Add Marker");
        btnAddMarker.addActionListener(e -> {

            cnt.setCameraPosition(new Coord(36.7948008, 10.0031931));
            cnt.addMarker(
                    EncodedImage.createFromImage(markerImg, false),
                    cnt.getCameraPosition(),
                    "Hi marker",
                    "Optional long description",
                    evt -> {
                        ToastBar.showMessage("You clicked the marker", FontImage.MATERIAL_PLACE);
                    }
            );

        });

        cnt.addTapListener(e -> {
            if (marker != null) {
                lat = String.valueOf(cnt.getCoordAtPosition(e.getX(), e.getY()).getLatitude());
                lng = String.valueOf(cnt.getCoordAtPosition(e.getX(), e.getY()).getLongitude());

//                System.out.println("lat : " + cnt.getCoordAtPosition(e.getX(), e.getY()).getLatitude());
//                System.out.println("lng : " + cnt.getCoordAtPosition(e.getX(), e.getY()).getLongitude());
                ConnectionRequest con = new ConnectionRequest();
                con.setPost(true);
                con.setUrl("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat
                        + "," + lng + "&key=AIzaSyDUiPfabJtrpjjj1Ix1vBsl1fKG_kzmeMw");

                //  System.out.println(con.getUrl());
//            System.out.println(con.getContentLength());
                con.addResponseListener(new ActionListener<NetworkEvent>() {

                    @Override
                    public void actionPerformed(NetworkEvent evt) {
//                        System.out.println(new String(con.getResponseData()));
//                        System.out.println("zefkjnezfkjn");
                        adresseMagasin = getAddress(new String(con.getResponseData()));
                        System.out.println(adresseMagasin);
                        if (adresseMagasin != "") {
                            ToastBar.showMessage("You clicked " + adresseMagasin, FontImage.MATERIAL_PLACE);
                        }
//                    System.out.println(s);
//                    Label l = new Label(s);
//                    f.add(l);
                    }
                });
                NetworkManager.getInstance().addToQueue(con);
                //System.out.println("LAT LNG : " + cnt.getCoordAtPosition(e.getX(), e.getY()).toString());
                cnt.removeMapObject(marker);
            }

            //String txt = enterName.getText();
            marker = cnt.addMarker(
                    EncodedImage.createFromImage(markerImg, false),
                    cnt.getCoordAtPosition(e.getX(), e.getY()),
                    "",
                    "",
                    e3 -> {
                        ToastBar.showMessage("You clicked " + "", FontImage.MATERIAL_PLACE);
                    }
            );
        });

//        Container root = LayeredLayout.encloseIn(
//                BorderLayout.center(cnt)
//        );
        Container addressContainer = new Container(new BorderLayout());
        Button ajouterMagasinButton = new Button("ajouter magasin");
        addressContainer.add(BorderLayout.NORTH, ajouterMagasinButton);
        addressContainer.add(BorderLayout.SOUTH, cnt);

        Container infoContainer = new Container(BoxLayout.y());

        tfNomMagasin = new TextField();
        tfNomMagasin.setHint("nom du magasin");
//        tfName.setHintIcon(FontImage.createMaterial(FontImage.MATERIAL_ACCOUNT_CIRCLE, s));
        infoContainer.add(tfNomMagasin);

        tfOwnerMagasin = new TextField();
        tfOwnerMagasin.setHint("propriétaire");
//        tfPassword.setHintIcon(FontImage.createMaterial(FontImage.MATERIAL_LOCK, s));
        infoContainer.add(tfOwnerMagasin);

        tfDescriptionMagasin = new TextField();
        tfDescriptionMagasin.setHint("description");
//        tfDescription.setHintIcon(FontImage.createMaterial(FontImage.MATERIAL_LOCK, s));
        infoContainer.add(tfDescriptionMagasin);

        //UPLOAD IMAGE
        Button uploadImageButton = new Button("parcourir ..");

        infoContainer.add(uploadImageButton);

        t.addTab("Infos", infoContainer);
        t.addTab("Adresse", addressContainer);
//        try {
//            t.getStyle().setBgImage(Image.createImage("/face.JPG"));
//        } catch (IOException ex) {
//        }
        f.getToolbar().addCommandToLeftBar("", FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, UIManager.getInstance().getComponentStyle("TitleCommand")), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new Home().start();
            }
        });

        t.getStyle().setBgColor(0xFFFFFF);
        t.hideTabs();
        f.add(t);
        f.show();

        uploadImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    MultipartRequest cr = new MultipartRequest();
                    String filePath = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
                    cr.setUrl("http://localhost/pidev2017/magasin/addMagasinPhoto.php");
                    cr.setPost(true);
                    String mime = "image/jpeg";
                    if (filePath != null) {
                        cr.addData("file", filePath, mime);
                        System.out.println(filePath);
                        cr.setFilename("file", filePath);//any unique name you want
                        imageName = filePath;
                        int index = imageName.lastIndexOf("/");
                        imageName = imageName.substring(index + 1);
                        InfiniteProgress prog = new InfiniteProgress();
                        Dialog dlg = prog.showInifiniteBlocking();
                        cr.setDisposeOnCompletion(dlg);
                        NetworkManager.getInstance().addToQueueAndWait(cr);
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        ajouterMagasinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                nomMagasin = tfNomMagasin.getText();
                ownerMagasin = tfOwnerMagasin.getText();
                descriptionMagasin = tfDescriptionMagasin.getText();
                if (nomMagasin != null && ownerMagasin != null && descriptionMagasin != null
                        && imageName != null && adresseMagasin != null) {

                    System.out.println(nomMagasin);
                    System.out.println(ownerMagasin);
                    System.out.println(descriptionMagasin);
                    System.out.println(imageName);
                    System.out.println(adresseMagasin);
//                    Date currentDate = new Date();
//                    currentDate.getTime();
                    System.out.println(getCurrentTimeStamp());

                    ConnectionRequest req = new ConnectionRequest();
                    req.setUrl("http://localhost/pidev2017/magasin/insertMagasin.php?nom=" + nomMagasin + "&own="
                            + ownerMagasin + "&add=" + adresseMagasin + "&img=" + imageName + "&upd="
                            + getCurrentTimeStamp() + "&lat=" + lat + "&lng=" + lng
                            + "&des=" + descriptionMagasin + "&ownId=" + Login.id
                    );
                    System.out.println("http://localhost/pidev2017/magasin/insertMagasin.php?nom=" + nomMagasin + "&own="
                            + ownerMagasin + "&add=" + adresseMagasin + "&img=" + imageName + "&upd="
                            + getCurrentTimeStamp() + "&lat=" + lat + "&lng=" + lng
                            + "&des=" + descriptionMagasin + "&ownId=" + Login.id);

                    req.addResponseListener(new ActionListener<NetworkEvent>() {

                        @Override
                        public void actionPerformed(NetworkEvent evt) {
                            byte[] data = (byte[]) evt.getMetaData();
                            String s = new String(data);

                            if (s.equals("success")) {
                                if (Dialog.show("Bien joué !", "Magasin ajouté avec succès", "Ok :-)", null)) {
                                    new Home().start();
                                };
                            }
                            else{
                                System.out.println("s: "+s);
                            }
                        }
                    });

                    NetworkManager.getInstance().addToQueue(req);

                } else {
//                    Dialog.show("Dialog Title", new Label("Some informations are missing"), null);
                    Dialog.show("Careful !", "Some informations are missing", "ok", null);

                }
            }
        });
    }

    public static String getCurrentTimeStamp() {

        Calendar cal = Calendar.getInstance();
        //plus 1 for month cos java starts January at 0 for Gregorian calendar...see doc for Calendar.MONTH
        String currentDate = String.valueOf(cal.get(Calendar.YEAR)) + "-"
                + String.valueOf(cal.get(Calendar.MONTH) + 1) + "-"
                + String.valueOf(cal.get(Calendar.DAY_OF_MONTH));

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        int sec = cal.get(Calendar.SECOND);
        String currentTime;

        currentTime = String.valueOf(hour) + ":" + String.valueOf(min) + ":" + String.valueOf(sec);

        return currentDate + " " + currentTime;

    }

    public String getAddress(String json) {
        String s = "";
        ArrayList<String> allAddresses = new ArrayList<String>();
        if (marker != null) {
//            System.out.println(json.getBytes());
            String result = json.getBytes().toString();
            try {
                // System.out.println(result);

                JSONParser j = new JSONParser();

                Map<String, Object> etudiants = j.parseJSON(new CharArrayReader(json.toCharArray()));
                List<Map<String, Object>> list = (List<Map<String, Object>>) etudiants.get("results");
                for (Map<String, Object> obj : list) {

//                    System.out.println(obj.get("address_components"));
                    List<Map<String, String>> aze = (List<Map<String, String>>) obj.get("address_components");
                    for (Map<String, String> m : aze) {
//                        System.out.println(obj.get("formatted_address"));
                        s = obj.get("formatted_address").toString();
                        allAddresses.add(s);
//                        System.out.println(s);
//                        return s;
                    }

//                    System.out.println(obj);
//                    System.out.println();
                }
                if (allAddresses.size() > 1) {
                    return allAddresses.get(allAddresses.size() - 2);
                }
                // System.out.println(etudiants);
            } catch (IOException ex) {
            }

        }

        return "";
    }

    public TextField getTfLogin() {
        return tfLogin;
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

    public void show() {

        f.show();
    }

}
