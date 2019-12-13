package Models;

public class Customer {
    private String cid;
    private String password;
    private String name;
    private String phone_num;

    public Customer() {}

    public Customer(String cid, String password, String name, String phone_num) {
        this.cid = cid;
        this.password = password;
        this.name = name;
        this.phone_num = phone_num;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }
}
