/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Date;

/**
 *
 * @author AmineHosni
 */
public class Magasin {

    private Integer id;
    private String name;
    private String owner;
    private String address;
    private String image_name;
    private Date updated_at;
    private String altitude;
    private String longitude;
    private String description;
    private Integer owner_id;

    public Magasin() {
    }

    public Magasin(Integer id, String name, String owner, String address, String image_name, Date updated_at, String altitude, String longitude, String description, Integer owner_id) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.address = address;
        this.image_name = image_name;
        this.updated_at = updated_at;
        this.altitude = altitude;
        this.longitude = longitude;
        this.description = description;
        this.owner_id = owner_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Integer owner_id) {
        this.owner_id = owner_id;
    }

    @Override
    public String toString() {
        return "Magasin{" + "id=" + id + ", name=" + name + ", owner=" + owner + ", address=" + address + ", image_name=" + image_name + ", updated_at=" + updated_at + ", altitude=" + altitude + ", longitude=" + longitude + ", description=" + description + ", owner_id=" + owner_id + '}';
    }

}
