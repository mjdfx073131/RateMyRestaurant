package OracleManager;

import Models.*;

import java.sql.*;
import java.util.ArrayList;

import static java.sql.Types.NULL;

public class OracleManager {
    //instance fields
    private Connection con;

    //constructors
    public OracleManager() {}


    //return true if connecting succeeds, false if fails
    public boolean connect(String url, String ora_username, String password) {
        try {
            con = DriverManager.getConnection(url, ora_username, password);
            con.setAutoCommit(false);
            System.out.println("\nConnected to Oracle!");
        } catch (SQLException e) {
            System.out.println("Message: " + e.getMessage());
            return false;
        }
        return true;
    }

    //return true if disconnecting succeeds, false if fails
    public boolean disconnect(){
        try {
            con.close();
            System.out.println("\nDisconnected to Oracle!");
        } catch (SQLException e) {
            System.out.println("Message: " + e.getMessage());
            return false;
        }
        return true;
    }

    //return true if customer register succeeds, false if fails
    public boolean customerRegister(String cid, String password, String name){
        PreparedStatement ps;

        try{
            ps = con.prepareStatement("INSERT INTO customer VALUES(?,?,?,?)");
            ps.setString(1,cid);
            ps.setString(2,password);
            ps.setString(3,name);
            ps.setNull(4,NULL);
            ps.executeUpdate();
            con.commit();
            ps.close();
        }
        catch (SQLException ex){
            System.out.println("Message: " + ex.getMessage());
            try {
                con.rollback();
                return false;
            }
            catch (SQLException ex2) {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
        }
        return true;
    } //test done

    //return customer info if login succeeds, null if fails
    public Customer customerLogin(String cid, String password){
        PreparedStatement ps;
        ResultSet rs;
        Customer customer = new Customer();

        try{
            ps = con.prepareStatement("SELECT * FROM customer WHERE cid = ? and password = ?");
            ps.setString(1,cid);
            ps.setString(2,password);
            rs = ps.executeQuery();
            con.commit();

            if(rs.next()) {
                customer.setCid(rs.getString(1));
                customer.setPassword(rs.getString(2));
                customer.setName(rs.getString(3));
                customer.setPhone_num(rs.getString(4));
                ps.close();
                return customer;
            }
            else {
                ps.close();
                return null;
            }
        }
        catch (SQLException ex){
            System.out.println("Message: " + ex.getMessage());
            return null;
        }
    }//test done

    //return administrator info if login succeeds, null if fails
    public Administrator adminLogin(String cid, String password){
        PreparedStatement ps;
        ResultSet rs;
        Administrator administrator = new Administrator();

        try{
            ps = con.prepareStatement("SELECT * FROM ADMINISTRATOR WHERE cid = ? and password = ?");
            ps.setString(1,cid);
            ps.setString(2,password);
            rs = ps.executeQuery();
            con.commit();

            if(rs.next()) {
                administrator.setCid(rs.getString(1));
                administrator.setPassword(rs.getString(2));
                administrator.setAdmin_level(rs.getInt(3));
                ps.close();
                return administrator;
            }
            else {
                ps.close();
                return null;
            }
        }
        catch (SQLException ex){
            System.out.println("Message: " + ex.getMessage());
            return null;
        }
    }//test done

    //return restaurants info if searching restaurants succeeds, null if fails
    public ArrayList<Restaurant> searchRestaurants(String keyword){
        ArrayList<Restaurant> result = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        try{
            ps = con.prepareStatement("SELECT * FROM RESTAURANT where lower(name) like ? order by DISTANCE");
            ps.setString(1,"%" + keyword.toLowerCase() + "%");
            rs = ps.executeQuery();
            con.commit();
            addToRestaurantArray(result, ps, rs);
            ps.close();


        }catch (SQLException ex2)
        {
            System.out.println("Message: " + ex2.getMessage());
            System.exit(-1);
        }
        return result;
    }//test done

    //return restaurants info if filtering restaurants succeeds, null if fails
    public ArrayList<Restaurant> filterRestaurants(int star, int price , String type){
        ArrayList<Restaurant> result = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        try{
            if(type.equals("ALL")){
                ps = con.prepareStatement("SELECT * FROM Restaurant where star_level >=? and price_level <= ? order by DISTANCE");
                ps.setInt(1,star);
                ps.setInt(2,price);

            }else{
                ps = con.prepareStatement("SELECT * FROM Restaurant where star_level >=? and price_level <= ? and type_name = ? order by DISTANCE");
                ps.setInt(1,star);
                ps.setInt(2,price);
                ps.setString(3,type);
            }

            rs = ps.executeQuery();
            con.commit();
            addToRestaurantArray(result, ps, rs);
            ps.close();


        }catch (SQLException ex2)
        {
            System.out.println("Message: " + ex2.getMessage());
            System.exit(-1);
        }
        return result;
    }//test done

    //return restaurants info if getting favorite restaurants succeeds, null if fails
    public ArrayList<Restaurant> getFavorites(String cid){
        ArrayList<Restaurant> result = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        try{
            ps = con.prepareStatement("SELECT r.rid, r.name, r.star_level, r.price_level, r.type_name, r.distance, r.phone_num, r.website, r.Address from favorite f, restaurant r WHERE f.cid = ? and f.rid =r.rid");
            ps.setString(1, cid);
            rs= ps.executeQuery();
            con.commit();

            addToRestaurantArray(result,ps,rs);
            ps.close();

        }
        catch (SQLException ex){
            System.out.println("Message: " + ex.getMessage());
            try
            {
                // undo the insert
                con.rollback();

            }
            catch (SQLException ex2)
            {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
        }
        return result;
    }//test done

    //return true if add favorite restaurant succeeds, false if fails
    public boolean addFavarite(String cid, int rid){
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO FAVORITE VALUES (?,?)");
            ps.setString(1, cid);
            ps.setInt(2, rid);

            ps.executeUpdate();
            con.commit();
            ps.close();
        } catch (SQLException e)
        {
            System.out.println("Message: " + e.getMessage());
            try {
                con.rollback();
            }
            catch (SQLException e2) {
                System.out.println("Message: " + e2.getMessage());
                System.exit(-1);
            }
            return false;
        }
        return true;
    }//test done

    //return vouchers if getting restaurant vouchers succeeds, null if fails
    public ArrayList<Voucher> getVouchers(int rid){
        ArrayList<Voucher> result = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT v.Vid, v.Description, v.Start_date, v.Expire_date FROM PROMOTE p, VOUCHER v WHERE p.vid = v.vid AND p.rid = ?");
            ps.setInt(1, rid);

            ResultSet rs = ps.executeQuery();
            con.commit();

            while(rs.next()){
                Voucher voucher = new Voucher();
                voucher.setVid(rs.getInt("Vid"));
                voucher.setDescription(rs.getString("Description"));
                voucher.setStart_date(rs.getDate("Start_date"));
                voucher.setExpire_date(rs.getDate("Expire_date"));
                result.add(voucher);
            }

            ps.close();

        } catch (SQLException e) {
            System.out.println("Message: " + e.getMessage());
            return null;
        }
        return result;
    }//test done

    //return comments info if getting comments succeeds, null if fails
    public ArrayList<Rate> getComments(int rid){
        ArrayList<Rate> result = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        try{
            ps = con.prepareStatement("SELECT * from RATE WHERE rid = ? order by RATE_DATE DESC");
            ps.setInt(1, rid);
            rs= ps.executeQuery();
            con.commit();

            while(rs.next()){
                Rate rate = new Rate();
                rate.setRateid(rs.getInt("RATEID"));
                rate.setCid(rs.getString("CID"));
                rate.setRid(rs.getInt("RID"));
                rate.setScore(rs.getInt("SCORE"));
                rate.setRate_comment(rs.getString("RATE_COMMENT"));
                rate.setRate_date(rs.getDate("RATE_DATE"));
                result.add(rate);
            }
            ps.close();
        }
        catch (SQLException ex){
            System.out.println("Message: " + ex.getMessage());
        }
        return result;
    }

    //return true if rating restaurant succeeds, false if fails
    public boolean rateRestaurant(String cid, int rid, int score, String comment, Date date){
        PreparedStatement ps;

        try{
            ps = con.prepareStatement("INSERT INTO RATE values(RATEID_COUNTER.nextval,?,?,?,?,?)");
            ps.setString(1, cid);
            ps.setInt(2, rid);
            ps.setInt(3, score);
            ps.setString(4, comment);
            ps.setDate(5, date);
            ps.executeUpdate();
            con.commit();
            ps.close();
        }
        catch (SQLException ex){
            System.out.println("Message: " + ex.getMessage());
            try {
                con.rollback();
                return false;
            }
            catch (SQLException ex2) {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
        }
        return true;
    }


    private static void addToRestaurantArray(ArrayList<Restaurant> result, PreparedStatement ps, ResultSet rs) throws SQLException {
        while (rs.next()) {
            Restaurant restaurant = new Restaurant();
            restaurant.setRid(rs.getInt(1));
            restaurant.setName(rs.getString(2));
            restaurant.setStar_level(rs.getInt(3));
            restaurant.setPrice_level(rs.getInt(4));
            restaurant.setType_name(rs.getString(5));
            restaurant.setDistance(rs.getInt(6));
            restaurant.setPhone_num(rs.getString(7));
            restaurant.setWebsite(rs.getString(8));
            restaurant.setAddress(rs.getString(9));
            result.add(restaurant);
        }

    }


    //Jepson
    public int getNumOfCustomer() {
        ResultSet rs;
        PreparedStatement ps;
        int result = 0;
        try{
            ps = con.prepareStatement("SELECT count(*) FROM CUSTOMER");
            rs = ps.executeQuery();
            con.commit();
            if(rs.next()) {
                result = rs.getInt(1);
                System.out.println(result);
            }
            ps.close();

        }catch (SQLException ex2)
        {
            System.out.println("Message: " + ex2.getMessage());
            System.exit(-1);
        }

        return result;


    }

    public ArrayList<RestaurantStar> ComputeAllRestaurantStars() {
        PreparedStatement ps;
        ResultSet rs;
        ArrayList<RestaurantStar> restaurantStars = new ArrayList<RestaurantStar>();

        try {
            ps = con.prepareStatement("SELECT r.Rid, count(*) count, avg(r.SCORE) AS avgStar FROM Rate r, Restaurant res WHERE r.Rid = res.Rid GROUP BY r.Rid ORDER by r.Rid");
            rs = ps.executeQuery();
            con.commit();

            while (rs.next()) {
                RestaurantStar restaurantStar = new RestaurantStar();
                restaurantStar.setRid(rs.getInt(1));
                restaurantStar.setCount(rs.getInt(2));
                restaurantStar.setAvgscore(rs.getDouble(3));
                restaurantStars.add(restaurantStar);
            }
            ps.close();
        }
        catch (SQLException ex){
            System.out.println("Message: " + ex.getMessage());
            return null;
        }
        return restaurantStars;

    }

    public boolean updateRestaurantStar(int rid, int newstar) {
        PreparedStatement ps;
        ResultSet rs;

        try{
            ps = con.prepareStatement("update Restaurant set Star_level = ? where rid = ?");
            ps.setInt(1, newstar);
            ps.setInt(2,rid);
            rs = ps.executeQuery();
            con.commit();
            ps.close();

        }
        catch (SQLException e)
        {
            System.out.println("Message: " + e.getMessage());
            try {
                con.rollback();
            }
            catch (SQLException e2) {
                System.out.println("Message: " + e2.getMessage());
                System.exit(-1);
            }
            return false;
        }
        return true;
    }

    public ArrayList<Restaurant> findRestaurantsSupportAllVouchers() {
        PreparedStatement ps;
        ResultSet rs;
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();

        try {
            ps = con.prepareStatement("SELECT r.rid, r.name, r.star_level, r.price_level, r.type_name, r.distance, r.phone_num, r.website, r.Address FROM Restaurant r WHERE NOT EXISTS(SELECT * FROM Voucher v WHERE NOT EXISTS(SELECT p.rid FROM Promote p WHERE r.rid = p.rid AND v.vid = p.vid))");
            rs = ps.executeQuery();

            con.commit();

            addToRestaurantArray(restaurants,ps,rs);
            ps.close();
        }
        catch (SQLException ex){
            System.out.println("Message: " + ex.getMessage());
            return null;
        }
        return restaurants;



    }

    public boolean deleteExpiredVoucher(Date date){
        try{
            PreparedStatement ps =con.prepareStatement("DELETE FROM Voucher where EXPIRE_DATE < ?");
            ps.setDate(1, date);
            ps.executeUpdate();
            con.commit();

            ps.close();
        }catch (SQLException e)
        {
            System.out.println("Message: " + e.getMessage());
            try {
                con.rollback();
            }
            catch (SQLException e2) {
                System.out.println("Message: " + e2.getMessage());
                System.exit(-1);
            }
            return false;
        }
        return true;
    }

    public ArrayList<Voucher> displayVouchers(){


        PreparedStatement ps;
        ResultSet rs;
        ArrayList<Voucher> vouchers = new ArrayList<Voucher>();

        try {
            ps =con.prepareStatement("SELECT VID,DESCRIPTION,EXPIRE_DATE FROM Voucher");
            rs = ps.executeQuery();

            con.commit();
            while (rs.next()) {
                Voucher voucher = new Voucher();
                voucher.setVid(rs.getInt(1));
                voucher.setDescription(rs.getString(2));
                voucher.setExpire_date(rs.getDate(3));
                vouchers.add(voucher);
            }

            ps.close();
        }
        catch (SQLException ex){
            System.out.println("Message: " + ex.getMessage());
            return null;
        }
        return vouchers;

    }


    public ArrayList<Voucher> getExpiredVouchers(Date date){
        ArrayList<Voucher> result = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;

        try{
            ps =con.prepareStatement("SELECT * FROM Voucher where EXPIRE_DATE < ?");
            ps.setDate(1, date);
            rs = ps.executeQuery();
            con.commit();

            while(rs.next()){
                Voucher voucher = new Voucher();
                voucher.setVid(rs.getInt("VID"));
                voucher.setDescription(rs.getString("DESCRIPTION"));
                voucher.setStart_date(rs.getDate("START_DATE"));
                voucher.setExpire_date(rs.getDate("EXPIRE_DATE"));
                result.add(voucher);
            }
            ps.close();

        }catch (SQLException e){
            System.out.println("Message: " + e.getMessage());
            return null;
        }
        return result;
    }
}
