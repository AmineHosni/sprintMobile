package com.mycompany.myapp.Coupon;


import com.codename1.components.ImageViewer;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Image;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import java.io.IOException;
import java.util.Random;

public class AcheterCoupon {

    private Form current;
    private Resources theme;
int id_coupon =0 ;
    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature, uncomment if you have a pro subscription
        // Log.bindCrashProtection(true);
    }
    
    public void start() {
        System.out.println("Acheter Coupon");
     Toolbar.setGlobalToolbar(true);

Form hi = new Form("Votre Code ", new BoxLayout(BoxLayout.Y_AXIS));
            
EncodedImage placeholderr = EncodedImage.createFromImage(Image.createImage(100, 100), true);
Random as = new Random();
                    String s = as.nextInt(500) + "s";
URLImage qr = URLImage.createToStorage(placeholderr,String.valueOf(id_coupon), "https://chart.googleapis.com/chart?chs=300x300&cht=qr&chl=http%3A%2F%2Fhttp://192.168.43.145/eshop-master/web/app_dev.php/coupon/print/"+id_coupon);

    //    System.out.println(id_coupon+"dsqdqllqlqlqlqlqlql");
ImageViewer j = new ImageViewer(qr);
hi.add(qr.scaled(300, 300));
Image arrow = null;
       try {
    arrow = Image.createImage("/arrow.png");
} catch(IOException err) {
    Log.e(err);
}
hi.show();
     
hi.getToolbar().addCommandToLeftBar("", arrow.scaled(60, 20), e -> new HomeCoupon().start());
     
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
        if(current instanceof Dialog) {
            ((Dialog)current).dispose();
            current = Display.getInstance().getCurrent();
        }
    }
    
    public void destroy() {
    }

  public int setTexte(int k) {
        int id = k;
        id_coupon = k;
    //    System.out.println(id_coupon);
        return k;
    }

}
