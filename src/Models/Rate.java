package Models;

import java.sql.Date;

public class Rate {
    int rateid;
    String cid;
    int rid;
    int score;
    String rate_comment;
    Date rate_date;

    public Rate() {}

    public Rate(int rateid, String cid, int rid, int score, String rate_comment, Date rate_date) {
        this.rateid = rateid;
        this.cid = cid;
        this.rid = rid;
        this.score = score;
        this.rate_comment = rate_comment;
        this.rate_date = rate_date;
    }

    public int getRateid() {
        return rateid;
    }

    public void setRateid(int rateid) {
        this.rateid = rateid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getRate_comment() {
        return rate_comment;
    }

    public void setRate_comment(String rate_comment) {
        this.rate_comment = rate_comment;
    }

    public Date getRate_date() {
        return rate_date;
    }

    public void setRate_date(Date rate_date) {
        this.rate_date = rate_date;
    }
}
