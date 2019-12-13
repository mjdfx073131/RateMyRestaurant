package UI;
import Protocol.Protocol;
import OracleManager.OracleManager;

public class RateMyRestaurant {
    private OracleManager oracleManager;

    public RateMyRestaurant() {
        oracleManager = new OracleManager();
        oracleManager.connect(Protocol.URL, Protocol.ORA_USERNAME, Protocol.PASSWORD);

        new LoginPage(oracleManager);
    }

    public static void main(String[] args) {
        new RateMyRestaurant();
    }
}