package com.mycompany.myapp.Coupon;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;

import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Rectangle;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridBagLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import Entities.Coupon;
import service.ToolbarSideMenu;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename
 * One</a> for the purpose of building native mobile applications using Java.
 */
public class MainCoupon {

    private Form current;
    private Resources theme;
    private int id_user = 2;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme2");
        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);
        // Pro only feature, uncomment if you have a pro subscription
        // Log.bindCrashProtection(true);
    }

    public void start() {
        if (current != null) {
            current.show();
            return;
        }
          Toolbar.setGlobalToolbar(true);
        Form hi = new Form(new GridLayout(2,2));
    
        
        
        
        hi.show();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev2017/coupon/select.php");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ArrayList<Coupon> coupons = getListEtudiant(new String(con.getResponseData()));
                for (Coupon coupon : coupons) {
                    System.out.println("hhh"+coupon.getQte());
                     if(coupon.getQte()>0 ){
                    Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                    EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(100, 100), true);
                    Random as = new Random();
                    String s = as.nextInt(50) + "s";
                    
                    URLImage image = URLImage.createToStorage(placeholder, coupon.getImage(), "http://localhost/eshop-master/web/images/products/" + coupon.getImage());
                    System.out.println("dzd " + coupon.getImage());
                    ImageViewer i = new ImageViewer(image);
                    Label lt = new Label((coupon.getTitre()));
                    lt.setUIID("titrecoupon"); 
                    Button bab = new Button("Voir");
                    bab.addActionListener(a -> {
                        buyCoupon af = new buyCoupon();
                        af.setTexte(coupon.getId());
                        af.start();
                        System.out.println("dsdssdsdsdsq");
                    });
                    C1.add(image.scaled(300, 100));
                    
                    C1.add(lt);
                    bab.setVisible(false);
                    C1.setLeadComponent(bab);
                   
                    hi.add(C1);
                }}
                hi.refreshTheme();
            }
        });
 

        NetworkManager.getInstance().addToQueue(con);
   new ToolbarSideMenu().insertSetting(hi, false);
        
          
            if(id_user != 0){
               hi.getToolbar().addCommandToSideMenu("Mes Coupons", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
           new MesCoupon().start();
            }
        });   
            }
      
    }

    public ArrayList<Coupon> getListEtudiant(String json) {
        ArrayList<Coupon> listEtudiants = new ArrayList<>();

        try {

            JSONParser j = new JSONParser();

            Map<String, Object> etudiants = j.parseJSON(new CharArrayReader(json.toCharArray()));

            System.out.println();
            List<Map<String, Object>> list = (List<Map<String, Object>>) etudiants.get("etudiant");

            for (Map<String, Object> obj : list) {
                Coupon e = new Coupon();
                e.setTitre(obj.get("titre").toString());
                e.setDescription(obj.get("description").toString());
                e.setImage(obj.get("image").toString());
                e.setId(Integer.parseInt(obj.get("id_coupon").toString()));   
                e.setQte(Integer.parseInt(obj.get("qte").toString()));

                System.out.println(obj.get("id_coupon").toString());
                listEtudiants.add(e);

            }

        } catch (IOException ex) {
        }
        return listEtudiants;

    }

    public void stop() {
        current = Display.getInstance().getCurrent();
        if (current instanceof Dialog) {
            ((Dialog) current).dispose();
            current = Display.getInstance().getCurrent();
        }
    }

    public void destroy() {
    }

}