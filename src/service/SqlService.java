/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.db.Row;
import java.io.IOException;

/**
 *
 * @author MBM info
 */
public class SqlService {
Database db;
    public void start() {
        
        boolean created = false;
        try {
            created = Database.exists("sprintMobile");

            db = Database.openOrCreate("sprintMobile");

            if (created == false) {
                db.execute("create table sprintMobile (id INTEGER,username varchar(255));");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    public void adduser(int id,String username) {

        try {

            db.execute("insert into sprintMobile(id,username) values ('" + id + "','" + username + "');");
            System.out.println("OK");

        } catch (IOException ex) {
        }
    }
    public boolean verifUser() {
        boolean verif = false;
        try {

            Cursor c = db.executeQuery("select * from sprintMobile");
            while (c.next()) {
                Row r = c.getRow();
                if (r == null) {
                    verif = false;
                } else {
                    verif = true;
                }

            }
        } catch (IOException ex) {
        }
        return verif;

    }
    public void vider(){
    try {
        db.executeQuery("delete from sprintMobile");
    } catch (IOException ex) {
    }
    }
    
}
