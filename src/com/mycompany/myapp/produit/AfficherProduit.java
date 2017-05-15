/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.produit;

import Entities.Produit;
import com.codename1.components.ImageViewer;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import static com.codename1.io.Log.p;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.Coupon.magasin.AddMagasin;
import com.mycompany.myapp.Coupon.magasin.AddProductToMagasin;
import com.mycompany.myapp.Coupon.magasin.Lien_id_magasin;
import com.mycompany.myapp.Coupon.magasin.Login;
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

    private Resources theme;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");
        Toolbar.setGlobalToolbar(true);

    }

    public void start(Integer id, boolean isconnceted) {

        Form formAffiche = new Form(new BoxLayout(2));
        new ToolbarSideMenu().insertSetting(formAffiche, false);

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev2017/produit/select.php");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Produit produit = new ProduitService().getProduit(new String(con.getResponseData()), id);

                Container detailssup = BoxLayout.encloseY(
                        new Label(produit.getMarque()),
                        new Label(produit.getLibelle() + " : " + produit.getDescription())
                );
                Container containerImage = new Container();
                ImageViewer img = new ImageViewer();
                img.setImageList(new ProduitService().listerImages(produit));
                containerImage.add(img);

                Container container2 = new Container();

                container2.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

                Container container6 = BoxLayout.encloseY(
                        BoxLayout.encloseX(
                                new Label("Prix : "),
                                new Label(produit.getPrixProduit().toString())
                        ),
                        BoxLayout.encloseX(
                                new Label("Etat : "),
                                new Label(produit.getEtat())
                        ),
                        BoxLayout.encloseX(
                                new Label("Categorie : "),
                                new Label(String.valueOf(produit.getProduitCategorie()))
                        )
                );
                Toolbar tb = formAffiche.getToolbar();
                formAffiche.getToolbar().addCommandToLeftBar("", FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, UIManager.getInstance().getComponentStyle("TitleCommand")), new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        new Home().start();
                    }

                });
                formAffiche.add(detailssup);
                formAffiche.add(containerImage);
                formAffiche.add(container6);
                if (produit.getQuantiteStock() != 0) {
                    container6.add(BoxLayout.encloseX(
                            new Label("Stock : Disponible")
                    )
                    );
                    Button Acheter = new Button("Acheter");
                    Button Panier = new Button("Magasin");

                    ConnectionRequest con = new ConnectionRequest();
                    con.setUrl("http://localhost/pidev2017/produit/checkProduitIsDansUnMagasin.php?id_produit=" + produit.getId());
                    con.addResponseListener(new ActionListener<NetworkEvent>() {
                        @Override
                        public void actionPerformed(NetworkEvent evt) {

                            String idMagasin = new String(con.getResponseData());
                            if (!idMagasin.equals("")) {
                                
                                
                                Button btnMagasin = new Button("add to magasin");
                    btnMagasin.addActionListener(new ActionListener() {
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
                                    } else if (nombreMagasins == 1) {
                                        ConnectionRequest con = new ConnectionRequest();
                                        con.setUrl("http://localhost/pidev2017/magasin/getListeMagasinsParUserId.php?userid=" + Login.id);
                                        con.addResponseListener(new ActionListener<NetworkEvent>() {
                                            @Override
                                            public void actionPerformed(NetworkEvent evt) {
                                                ArrayList<Lien_id_magasin> listeIdMagasins = getListLienIdMagasin(new String(con.getResponseData()));
                                                System.out.println("bofff:" + listeIdMagasins);
//                                    for(Lien_id_magasin eltIdMagasain : listeIdMagasins)
//                                    {
//                                        
//                                    }
                                                AddProductToMagasin a = new AddProductToMagasin(theme, listeIdMagasins, produit);
                                                a.getF().show();
                                            }
                                        });

                                        NetworkManager.getInstance().addToQueue(con);
                                    } else {
                                        ConnectionRequest con = new ConnectionRequest();
                                        con.setUrl("http://localhost/pidev2017/magasin/getListeMagasinsParUserId.php?userid=" + Login.id);
                                        con.addResponseListener(new ActionListener<NetworkEvent>() {
                                            @Override
                                            public void actionPerformed(NetworkEvent evt) {
                                                ArrayList<Lien_id_magasin> listeIdMagasins = getListLienIdMagasinMultiples(new String(con.getResponseData()));
                                                System.out.println("bofff:" + listeIdMagasins);
//                                    for(Lien_id_magasin eltIdMagasain : listeIdMagasins)
//                                    {
//                                        
//                                    }
                                                AddProductToMagasin a = new AddProductToMagasin(theme, listeIdMagasins, produit);
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
                                formAffiche.add(BoxLayout.encloseX(
                            Acheter, Panier, btnMagasin
                    ));
                                
                            } else{formAffiche.add(BoxLayout.encloseX(
                            Acheter, Panier
                    ));
                                
                            }

                        }
                    });

                    NetworkManager.getInstance().addToQueue(con);

                    

                    
                } else {
                    container6.add(BoxLayout.encloseX(
                            new Label("Stock : Indisponible", "WelcomeRed")
                    ));
                }

                if (isconnceted == true) {

                    Button btnSupprimer = new Button("Supprimer");

                    btnSupprimer.addActionListener(e -> {
                        new ProduitService().delete(produit.getId());
                        new ListProduit().start();

                    });
                    Button btnModifier = new Button("Modifier");
                    btnModifier.addActionListener(e -> new modifierProduit().start(produit));

                    Container boutons = BoxLayout.encloseX(btnModifier, btnSupprimer);
                    formAffiche.add(boutons);
                }
                formAffiche.show();
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

}
