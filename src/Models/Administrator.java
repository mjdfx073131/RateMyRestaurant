package Models;

public class Administrator{
    private String cid;
    private String password;
    private int admin_level;

    public Administrator() {}

    public Administrator(String cid, String password, int admin_level) {
        this.cid = cid;
        this.password = password;
        this.admin_level = admin_level;
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

    public int getAdmin_level() {
        return admin_level;
    }

    public void setAdmin_level(int admin_level) {
        this.admin_level = admin_level;
    }
}
