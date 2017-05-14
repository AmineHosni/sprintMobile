package Entities;

public class Coupon {
    private int id,qte;
    private double  tauxred;
    private String titre,description,image,date_deb,date_fin;

    public Coupon(String titre, String description,String image) {
        this.titre = titre;
        this.description = description;        this.image = image;

    }

    public Coupon() {
        
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public double getTauxred() {
        return tauxred;
    }

    public void setTauxred(double tauxred) {
        this.tauxred = tauxred;
    }

    public String getDate_deb() {
        return date_deb;
    }

    public void setDate_deb(String date_deb) {
        this.date_deb = date_deb;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}