package Models;

public class Restaurant {
    private int rid;
    private String name;
    private int star_level;
    private int price_level;
    private String type_name;
    private int distance;
    private String phone_num;
    private String website;
    private String Address;

    public Restaurant() {}

    public Restaurant(int rid, String name, int star_level, int price_level, String type_name, int distance, String phone_num, String website, String address) {
        this.rid = rid;
        this.name = name;
        this.star_level = star_level;
        this.price_level = price_level;
        this.type_name = type_name;
        this.distance = distance;
        this.phone_num = phone_num;
        this.website = website;
        Address = address;
    }


    @Override
    public String toString() {
        return "Restaurant{" +
                "rid=" + rid +
                ", name='" + name + '\'' +
                ", star_level=" + star_level +
                ", price_level=" + price_level +
                ", type_name='" + type_name + '\'' +
                ", distance=" + distance +
                ", phone_num='" + phone_num + '\'' +
                ", website='" + website + '\'' +
                ", Address='" + Address + '\'' +
                '}';
    }



    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStar_level() {
        return star_level;
    }

    public void setStar_level(int star_level) {
        this.star_level = star_level;
    }

    public int getPrice_level() {
        return price_level;
    }

    public void setPrice_level(int price_level) {
        this.price_level = price_level;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}