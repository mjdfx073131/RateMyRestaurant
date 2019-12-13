package OracleManager;

import Models.*;
import Protocol.Protocol;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OracleManagerTest {
    private OracleManager oracleManager;

    @BeforeEach
    void setUp() {
        oracleManager = new OracleManager();
        oracleManager.connect(Protocol.URL, Protocol.ORA_USERNAME, Protocol.PASSWORD);
    }

    @AfterEach
    void tearDown() {
        oracleManager.disconnect();
    }

    @Test
    void customerRegister() {
        boolean flag;
        flag = oracleManager.customerRegister("frankyf2", "asdf", "Franky Fan");
        System.out.println(flag);
        flag = oracleManager.customerRegister("frankyf2", "asdf", "Franky Fan");
        System.out.println(flag);
    } // test done

    @Test
    void customerLogin() {
        Customer customer;
        customer = oracleManager.customerLogin("risaw", "asdf");
        assertTrue(customer.getCid().equals("risaw"));
        assertTrue(customer.getName().equals("Risa Watanabe"));
        Customer customer1;
        customer1 = oracleManager.customerLogin("risaw", "asdfasdfsa");
        assertTrue(customer1 == null);
        Customer customer2;
        customer2 = oracleManager.customerLogin("adfasdf", "asdfasdfasf");
        assertTrue(customer2 == null);
    } //test done
    @Test
    void adminLogin() {
        Administrator administrator;
        administrator=oracleManager.adminLogin("kerry","kerry");
        assertTrue(administrator.getCid().equals("kerry"));
        assertTrue(administrator.getPassword().equals("kerry"));

        Administrator administrator1;
        administrator1=oracleManager.adminLogin("kerry","jepson");
        assertTrue(administrator1==null);

        Administrator administrator2;
        administrator2=oracleManager.adminLogin("adfad","adfadfa");
        assertTrue(administrator2 == null);
    } //test done

    @Test
    void searchRestaurants() {
        ArrayList<Restaurant> restaurants;
        restaurants = oracleManager.searchRestaurants("m");
        assertEquals(restaurants.size(),3);
        assertTrue(restaurants.get(0).getName().equals("My Home cuisine"));
        ArrayList<Restaurant> restaurants1;
        restaurants1=oracleManager.searchRestaurants("1");
        assertEquals(restaurants1.size(),0);
    } //test done

    @Test
    void filterRestaurants() {
        ArrayList<Restaurant> restaurants;
        restaurants=oracleManager.filterRestaurants(3,2,"Chinese cuisine");
//        for (Restaurant r: restaurants){
//            System.out.println(r.getName());
//        }
        assertEquals(restaurants.size(),3);

        ArrayList<Restaurant> restaurants1;
        restaurants1=oracleManager.filterRestaurants(5,1,"ALL");
        assertEquals(restaurants1.size(),0);

    }//test done

    @Test
    void getFavorites() {
        ArrayList<Restaurant> restaurants;
        restaurants=oracleManager.getFavorites("frankyf");
        assertEquals(restaurants.size(),3);
        assertEquals(restaurants.get(0).getName(),"Sushi Mura");
    }//test done

    @Test
    void addFavarite() {
        oracleManager.addFavarite("risaw", 2);
    } // test done

    @Test
    void getVouchers() {
        ArrayList<Voucher> vouchers = oracleManager.getVouchers(2);
        for(Voucher v: vouchers) {
            System.out.println(v.getVid());
            System.out.println(v.getDescription());
            System.out.println(v.getStart_date());
            System.out.println(v.getExpire_date());
            System.out.println();
        }
    }   // test done

    @Test
    void getComments() {
        ArrayList<Rate> rates;
        rates=oracleManager.getComments(1);
        assertEquals(rates.size(),4);

        ArrayList<Rate> rates1;
        rates1=oracleManager.getComments(4);
        assertEquals(rates1.size(),0);
    }   // test done

    @Test
    void rateRestaurant() {
        java.util.Date localDate = new java.util.Date();
        Date sqlDate = new Date(localDate.getTime());
        oracleManager.rateRestaurant("frankyf", 1, 3, "Execellent", sqlDate);
    }   // test done


    ///Jepson
    @Test
    void getNumOfCustomer() {
        int count = oracleManager.getNumOfCustomer();
        assertEquals(count, 5);
    }

    @Test
    void ComputeAllRestaurantStars() {
        ArrayList<RestaurantStar> restaurantStars;
        restaurantStars = oracleManager.ComputeAllRestaurantStars();
        assertEquals(restaurantStars.size(), 3);
        for(RestaurantStar rs: restaurantStars) {
//            System.out.println(rs.getRid());
//            System.out.println(rs.getAvgscore() + "\n");
            oracleManager.updateRestaurantStar(rs.getRid(), (int)Math.round(rs.getAvgscore()));
        }
        ArrayList<Restaurant> restaurants = oracleManager.findRestaurantsSupportAllVouchers();
        assertEquals(restaurants.get(0).getStar_level(), 4);
    }

    @Test
    void updateRestaurantStar() {
        //oracleManager.updateRestaurantStar();
    }

    @Test
    void findRestaurantsSupportAllVouchers() {
        ArrayList<Restaurant> restaurants;
        restaurants = oracleManager.findRestaurantsSupportAllVouchers();
        assertEquals(restaurants.size(), 1);
    }

    @Test
    void deleteExpiredVoucher() {
        assertTrue(oracleManager.deleteExpiredVoucher(Date.valueOf("2019-06-01")));
        ArrayList<Voucher>vouchers = oracleManager.getVouchers(1);
        for(Voucher v: vouchers) {
            System.out.println(v.getVid());
        }
    }

    @Test
    void getExpiredVouchers(){
        ArrayList<Voucher> expiredVouchers = oracleManager.getExpiredVouchers(Date.valueOf("2019-06-15"));
        if(expiredVouchers != null) {
            for (Voucher v : expiredVouchers) {
                System.out.println(v.getVid());
                System.out.println(v.getExpire_date());
            }
        }

        assertEquals(1, expiredVouchers.size());
    }
}