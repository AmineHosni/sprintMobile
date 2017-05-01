/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Display;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author MBM info
 */
public class sqlDatabase {
    Database db;
    Cursor cur;
    public void start(){
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            
            System.out.println(new String(dateFormat.format(date)));
//            db = Display.getInstance().openOrCreate("sprintMobile.db");
//            
//             db.executeQuery("insert into aa (`LastName`) VALUES (\"nouri ya zebi\")");
//           cur = db.executeQuery("select * from aa");
//            if (cur.next()) {
//                 System.out.println(cur.getColumnIndex("LastName"));
//            }
         
    }
    public void connecter(){
        try {
            Cursor cur = db.executeQuery("select * from sprintMobile");
            
        } catch (IOException ex) {
        }
         }
}
