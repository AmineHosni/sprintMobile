/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.codename1.io.CharArrayReader;
import com.codename1.io.JSONParser;
import Entities.Produit;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Image;
import com.codename1.ui.URLImage;
import com.codename1.ui.list.DefaultListModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author MBM info
 */
public class ProduitService {

    public ArrayList<Produit> getListProduits(String json)  {
        ArrayList<Produit> listEtudiants = new ArrayList<>();

        try {

            JSONParser j = new JSONParser();

            Map<String, Object> etudiants = j.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) etudiants.get("produit");
            for (Map<String, Object> obj : list) {
                Produit produit = new Produit();
                produit.setId(Integer.parseInt(obj.get("id").toString()));
                produit.setMarque(obj.get("marque").toString());
                produit.setEtat(obj.get("etat").toString());
                produit.setImageName(obj.get("image_name").toString());
                produit.setPrixProduit(Double.parseDouble(obj.get("prixProduit").toString()));
                produit.setLibelle(obj.get("libelle").toString());
                produit.setQuantiteStock(Integer.parseInt(obj.get("quantiteStock").toString()));
                String dateStr = obj.get("created_date").toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date createdDate = sdf.parse(dateStr);
                  produit.setCreatedDate(createdDate);
                
                produit.setDescription(obj.get("description").toString());
                produit.setSeller(Integer.parseInt(obj.get("seller").toString()));
                listEtudiants.add(produit);

            }

        } catch (IOException ex) {
        } catch (ParseException ex) { }
        return listEtudiants;

    }

    public Produit getProduit(String json, Integer id) {
        Produit produit = new Produit();
        try {

            JSONParser j = new JSONParser();

            Map<String, Object> etudiants = j.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) etudiants.get("produit");
            for (Map<String, Object> obj : list) {

                if (Integer.parseInt(obj.get("id").toString()) == id) {
                    produit.setId(Integer.parseInt(obj.get("id").toString()));
                    produit.setMarque(obj.get("marque").toString());
                    produit.setEtat(obj.get("etat").toString());
                    produit.setImageName(obj.get("image_name").toString());
//                    produit.setImageName2(obj.get("image_name2").toString());
//                    produit.setImageName3(obj.get("image_name3").toString());
                    produit.setPrixProduit(Double.parseDouble(obj.get("prixProduit").toString()));
                    produit.setLibelle(obj.get("libelle").toString());
                    produit.setDescription(obj.get("description").toString());
                    produit.setQuantiteStock(Integer.parseInt(obj.get("quantiteStock").toString()));
                    System.out.println(produit.getProduitCategorie());
                    produit.setProduitCategorie(Integer.parseInt(obj.get("produitCategorie").toString()));
                }

            }

        } catch (IOException ex) {
        }
        return produit;

    }

    public ArrayList<Produit> getListProduitsRecherche(String json, String search) {
        ArrayList<Produit> listEtudiants = new ArrayList<>();

        try {

            JSONParser j = new JSONParser();

            Map<String, Object> etudiants = j.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) etudiants.get("produit");

            for (Map<String, Object> obj : list) {
                if (obj.get("libelle").toString().startsWith(search)) {
                    Produit produit = new Produit();
                    produit.setId(Integer.parseInt(obj.get("id").toString()));
                    produit.setMarque(obj.get("marque").toString());
                    produit.setEtat(obj.get("etat").toString());
                    produit.setPrixProduit(Double.parseDouble(obj.get("prixProduit").toString()));
                    produit.setLibelle(obj.get("libelle").toString());
                    produit.setDescription(obj.get("description").toString());
                    listEtudiants.add(produit);
                }

            }

        } catch (IOException ex) {
        }
        return listEtudiants;

    }

    public void delete(Integer id) {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev2017/delete.php?id=" + id);
        NetworkManager.getInstance().addToQueue(con);

    }
    public DefaultListModel listerImages(Produit produit) {
            DefaultListModel listeImage= new DefaultListModel<>();

        try {
            EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage("/error.jpg"), false);
            Image image = URLImage.createToStorage(placeholder, "magasin+" + "/" + produit.getImageName(), "http://localhost/pidev2017/image/" + "/" + produit.getImageName());
            Image image2 = URLImage.createToStorage(placeholder, "magasin+" + "/" + produit.getImageName2(), "http://localhost/pidev2017/image/" + "/" + produit.getImageName2());
            Image image3 = URLImage.createToStorage(placeholder, "magasin+" + "/" + produit.getImageName3(), "http://localhost/pidev2017/image/" + "/" + produit.getImageName3());
            listeImage.addItem(image.scaled(Display.getInstance().getDisplayWidth(),Display.getInstance().getDisplayHeight()/2));
            listeImage.addItem(image2.scaled(Display.getInstance().getDisplayWidth(),Display.getInstance().getDisplayHeight()/2));
            listeImage.addItem(image3.scaled(Display.getInstance().getDisplayWidth(),Display.getInstance().getDisplayHeight()/2));


        } catch (IOException ex) {
        }
                    return listeImage;


    }
}
