package Models;

public class RestaurantStar {

    private int rid;
    private int count;
    private double avgscore;


    public RestaurantStar() {}

    public RestaurantStar(int rid, int count, double avgscore) {
        this.rid = rid;
        this.count = count;
        this.avgscore = avgscore;
    }

    @Override
    public String toString() {
        return "RestaurantStar{" +
                "rid=" + rid +
                ", count=" + count +
                ", avgscore=" + avgscore +
                '}';
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getAvgscore() {
        return avgscore;
    }

    public void setAvgscore(double avgscore) {
        this.avgscore = avgscore;
    }
}

