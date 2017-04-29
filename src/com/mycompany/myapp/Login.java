/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;

/**
 *
 * @author MBM info
 */
public class Login {

    
    
//
//    public Login(Resources theme) {
//        UIBuilder ui = new UIBuilder();
//        formlogin = ui.createContainer(theme, "Login").getComponentForm();     
//        Useraname = (TextField) ui.findByName("username", formlogin);
//        Password = (TextField) ui.findByName("password", formlogin);
//        btnLogin = (Button) ui.findByName("btnlogin", formlogin);
//          btnLogin.addActionListener(e->{
//            new InsertProduit().start();
//              
//          });
//      
//    }
  
        public void start(){
                Form formlogin = new Form();
                TextField txtpassword = new TextField();
                txtpassword.setHint("Password");
                formlogin.add(new TextField("Username"));
                Button btnConnecter= new Button("Connecter") ;
                formlogin.add(txtpassword);
                formlogin.add(btnConnecter);
                
                formlogin.show();
        
                btnConnecter.addActionListener(e->{
                    //if
                    new ListProduit().start();
                });
        }
       
         
//                            Home home = new Home(theme, Useraname.getText());
//                            home.getFormHome().show();
                        

            
    
       
   
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    

}
