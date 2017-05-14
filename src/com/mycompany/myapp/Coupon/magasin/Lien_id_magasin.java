/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.Coupon.magasin;

/**
 *
 * @author AmineHosni
 */
public class Lien_id_magasin {

    private int id;
    private String name;

    public Lien_id_magasin() {
    }

    public Lien_id_magasin(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Lien_id_magasin{" + "id=" + id + ", name=" + name + '}';
    }

}
