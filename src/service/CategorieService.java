/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Entities.Categorie;
import Entities.Produit;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author MBM info
 */
public class CategorieService {
Categorie i = null;
    public void start() {
        returnId("Lunettes");
    }

    public ArrayList<Categorie> getListCategorie(String json) {
        ArrayList<Categorie> listCategories = new ArrayList<>();

        try {

            JSONParser j = new JSONParser();

            Map<String, Object> categries = j.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) categries.get("categorie");
            for (Map<String, Object> obj : list) {
                Categorie categorie = new Categorie();
                categorie.setId(Integer.parseInt(obj.get("id").toString()));
                categorie.setNomCategorie(obj.get("nomCategorie").toString());
                listCategories.add(categorie);

            }

        } catch (IOException ex) {
        }
        return listCategories;

    }

    public Categorie returnId(String s) {
     
        ConnectionRequest concategorie = new ConnectionRequest();
        concategorie.setUrl("http://localhost/pidev2017/selectcategorie.php");
        concategorie.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                ArrayList<Categorie> list = new ArrayList<>();
                list = new CategorieService().getListCategorie(new String(concategorie.getResponseData()));
                Categorie cr;
                for (Categorie categorie : list) {
                    System.out.println(categorie.getId());
                    if (categorie.getNomCategorie() == s) {
                        
                        cr = categorie;
                    
                    i=cr;
                    }
                }
                

            }

        });
        NetworkManager.getInstance().addToQueue(concategorie);
        System.out.println("aa"+i);
        return i;

    }

}
