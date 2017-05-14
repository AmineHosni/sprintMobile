/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.Coupon.magasin;

import Entities.Produit;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import service.ProduitService;
import service.ToolbarSideMenu;

/**
 *
 * @author MBM info
 */
public class AfficherProduit {

    Form formAffiche;

    Resources theme;

    Container containerImageProduit, underImageContainer, descriptionProduitContainer;
    Button addToMagasinbutton;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");
        Toolbar.setGlobalToolbar(true);

    }

    public AfficherProduit(Resources theme, Produit p) {
        System.out.println("Afficher Produit");
        formAffiche = new Form(p.getLibelle(), new BoxLayout(2));
        new ToolbarSideMenu().insertSetting(formAffiche, false);

        formAffiche.getToolbar().addCommandToLeftBar("", FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, UIManager.getInstance().getComponentStyle("TitleCommand")), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new ListProduit(theme).getF().show();
            }
        });

        containerImageProduit = new Container();
        underImageContainer = new Container(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        descriptionProduitContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        addToMagasinbutton = new Button("ajouter au magasin..");

        formAffiche.show();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev2017/magasin/selectProduit.php");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Produit produit = new ProduitService().getProduit(new String(con.getResponseData()), p.getId());
//                underImageContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));

                Style styleImageProfile = UIManager.getInstance().getComponentStyle("BACKGROUND_IMAGE_ALIGNED_TOP_RIGHT");
                FontImage.setMaterialIcon(addToMagasinbutton, FontImage.MATERIAL_ADD);

                try {
                    containerImageProduit.add(new Label(Image.createImage("/frigi.png").scaled(300, 300)));
                } catch (IOException ex) {

                }
                descriptionProduitContainer.add(new Label(produit.getLibelle()));
                descriptionProduitContainer.add(new Label(produit.getDescription()));

                underImageContainer.add(BorderLayout.CENTER, descriptionProduitContainer);
                underImageContainer.add(BorderLayout.SOUTH, addToMagasinbutton);

                formAffiche.add(containerImageProduit);
                formAffiche.add(underImageContainer);

            }
        });

        addToMagasinbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ConnectionRequest con = new ConnectionRequest();
                con.setUrl("http://localhost/pidev2017/magasin/getNombreMagasinParUserId.php?id=" + Login.id);
                con.addResponseListener(new ActionListener<NetworkEvent>() {
                    @Override
                    public void actionPerformed(NetworkEvent evt) {

                        int nombreMagasins = Integer.valueOf(new String(con.getResponseData()));
                        System.out.println("nombre de magasins :" + nombreMagasins);
                        if (nombreMagasins == 0) {
                            if (Dialog.show("Pas de magasins ?", "Créez-en un dès maintenant !", "d'accord!", "meh..")) {
                                AddMagasin ajouterMagasin = new AddMagasin();
                                ajouterMagasin.getF().show();
                            }
                        } else if(nombreMagasins ==1) {
                            ConnectionRequest con = new ConnectionRequest();
                            con.setUrl("http://localhost/pidev2017/magasin/getListeMagasinsParUserId.php?userid=" + Login.id);
                            con.addResponseListener(new ActionListener<NetworkEvent>() {
                                @Override
                                public void actionPerformed(NetworkEvent evt) {
                                    ArrayList<Lien_id_magasin> listeIdMagasins = getListLienIdMagasin(new String(con.getResponseData()));
                                    System.out.println("bofff:" +listeIdMagasins);
//                                    for(Lien_id_magasin eltIdMagasain : listeIdMagasins)
//                                    {
//                                        
//                                    }
                                    AddProductToMagasin a = new AddProductToMagasin(theme, listeIdMagasins, p);
                                    a.getF().show();
                                }
                            });

                            NetworkManager.getInstance().addToQueue(con);
                        }
                        else{
                            ConnectionRequest con = new ConnectionRequest();
                            con.setUrl("http://localhost/pidev2017/magasin/getListeMagasinsParUserId.php?userid=" + Login.id);
                            con.addResponseListener(new ActionListener<NetworkEvent>() {
                                @Override
                                public void actionPerformed(NetworkEvent evt) {
                                    ArrayList<Lien_id_magasin> listeIdMagasins = getListLienIdMagasinMultiples(new String(con.getResponseData()));
                                    System.out.println("bofff:" +listeIdMagasins);
//                                    for(Lien_id_magasin eltIdMagasain : listeIdMagasins)
//                                    {
//                                        
//                                    }
                                    AddProductToMagasin a = new AddProductToMagasin(theme, listeIdMagasins, p);
                                    a.getF().show();
                                }
                            });

                            NetworkManager.getInstance().addToQueue(con);
                            
                        }
                    }
                });

                NetworkManager.getInstance().addToQueue(con);
            }
        });

        NetworkManager.getInstance().addToQueue(con);
    }

    public ArrayList<Lien_id_magasin> getListLienIdMagasin(String json) {

        ArrayList<Lien_id_magasin> listMagasins = new ArrayList<>();
        try {

            JSONParser j = new JSONParser();
            Map<String, Object> magasins = j.parseJSON(new CharArrayReader(json.toCharArray()));
            System.out.println(magasins.getClass());
            Map<String, Object> list = (Map<String, Object>) magasins.get("lien_id_magasin");
            System.out.println("list: " + list);

            Lien_id_magasin m = new Lien_id_magasin();
            for (Map.Entry<String, Object> obj : list.entrySet()) {
                if (obj.getKey().toString().equals("id")) {
                    m.setId(Integer.parseInt(obj.getValue().toString()));
                }
                if (obj.getKey().toString().equals("name")) {
                    m.setName(obj.getValue().toString());
                }

            }
            listMagasins.add(m);
        } catch (IOException ex) {
        }
        return listMagasins;
    }
    
    public ArrayList<Lien_id_magasin> getListLienIdMagasinMultiples(String json) {

        ArrayList<Lien_id_magasin> listMagasins = new ArrayList<>();
        try {

            JSONParser j = new JSONParser();
            Map<String, Object> magasins = j.parseJSON(new CharArrayReader(json.toCharArray()));
            System.out.println();
            List<Map<String, Object>> list = (List<Map<String, Object>>) magasins.get("lien_id_magasin");
            for (Map<String, Object> obj : list) {
                Lien_id_magasin m = new Lien_id_magasin();
                m.setId(Integer.parseInt(obj.get("id").toString()));
                m.setName(obj.get("name").toString());
                listMagasins.add(m);

            }
        } catch (IOException ex) {
        }
        return listMagasins;
    }

    public Form getF() {
        return formAffiche;
    }

    public void show() {

        formAffiche.show();
    }
}
