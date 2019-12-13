package Models;

import java.sql.Date;

public class Voucher {
    private int vid;
    private String description;
    private Date start_date;
    private Date expire_date;

    public Voucher() {}

    public Voucher(int vid, String description, Date start_date, Date expire_date) {
        this.vid = vid;
        this.description = description;
        this.start_date = start_date;
        this.expire_date = expire_date;
    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(Date expire_date) {
        this.expire_date = expire_date;
    }
}
