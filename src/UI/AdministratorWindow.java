package UI;

import Models.*;
import OracleManager.OracleManager;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

public class AdministratorWindow {
    //data fields
    private OracleManager oracleManager;
    private Administrator administrator;

    //GUI
    private JButton showTotalNumberOfButton;
    private JButton computeAllRestaurantStarsButton;
    private JButton findRestaurantsSupportingAllButton;
    private JList list1;
    private JButton deleteAllExpiredVouchersButton;
    private JPanel administratorWindow;
    private JLabel welcomeLabel;
    private JButton displayAllVouchersButton;
    BufferedImage bg = null;


    public AdministratorWindow(OracleManager oracleManager_PassedIn, Administrator administrator_PassedIn) {
        oracleManager = oracleManager_PassedIn;
        administrator = administrator_PassedIn;


        JFrame frame = new JFrame("AdministratorWindow");
        frame.setContentPane(administratorWindow);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1100, 500);


        ImageIcon img = new ImageIcon("src/UI/3.jpg");
        JLabel jl_bg = new JLabel(img);
        jl_bg.setBounds(0, 0, 1100, 500);

        frame.getLayeredPane().add(jl_bg, new Integer(Integer.MIN_VALUE));
        ((JPanel) frame.getContentPane()).setOpaque(false);

        try {
            bg = ImageIO.read(new File("src/UI/3.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame.setBounds(0, 0, 1100, 500);
        frame.setResizable(false);

        welcomeLabel.setText("Administrator Window for " + administrator.getCid());

        //ActionListener
        showTotalNumberOfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int num = oracleManager.getNumOfCustomer();
                String[] listData = new String[1];
                listData[0] = "   We currently have registered customers: " + num;
                list1.setListData(listData);
            }
        });

        computeAllRestaurantStarsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //compute all
                ArrayList<RestaurantStar> restaurantStars = oracleManager.ComputeAllRestaurantStars();
                String[] listData = new String[restaurantStars.size() + 2];
                listData[0] = "   The restaurant's average score based on customer comments:";
                listData[1] = "   Restaurant ID    Number of Comments   Average Score";
                for (int i = 0; i < restaurantStars.size(); i++) {
                    RestaurantStar rs = restaurantStars.get(i);
                    listData[i + 2] = "   " + rs.getRid() + "                 " + rs.getCount() + "                   " + Math.round(rs.getAvgscore() * 10) / 10.0;
                }
                list1.setListData(listData);

                //update all
                for (RestaurantStar rs : restaurantStars)
                    oracleManager.updateRestaurantStar(rs.getRid(), (int) Math.round(rs.getAvgscore()));
            }
        });

        findRestaurantsSupportingAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Restaurant> restaurants = oracleManager.findRestaurantsSupportAllVouchers();
                String[] listData = new String[restaurants.size() + 2];
                listData[0] = "   The restaurants that supports all available vouchers:";
                listData[1] = "   Restaurant ID    Name";
                for (int i = 0; i < restaurants.size(); i++) {
                    Restaurant r = restaurants.get(i);
                    listData[i + 2] = "   " + r.getRid() + "                " + r.getName();
                }
                list1.setListData(listData);
            }
        });

        deleteAllExpiredVouchersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.Date localDate = new java.util.Date();
                Date sqlDate = new Date(localDate.getTime());
                ArrayList<Voucher> expiredVouchers = oracleManager.getExpiredVouchers(sqlDate);
                oracleManager.deleteExpiredVoucher(sqlDate);

                String[] listData = new String[expiredVouchers.size() + 2];
                listData[0] = "   The expired vouchers being deleted:";
                listData[1] = "   Voucher_ID        Description             Expire_Date";
                for (int i = 0; i < expiredVouchers.size(); i++) {
                    Voucher v = expiredVouchers.get(i);
                    listData[i + 2] = "   " + v.getVid() + "                 " + v.getDescription() + "    " + v.getExpire_date();
                }
                list1.setListData(listData);
            }
        });
        displayAllVouchersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Voucher> vouchers = oracleManager.displayVouchers();
                String[] listData = new String[vouchers.size() + 2];
                listData[0] = "   All vouchers are displayed:";
                listData[1] = "   Voucher_ID        Description             Expire_Date";

                for (int i = 0; i < vouchers.size(); i++) {
                    Voucher v = vouchers.get(i);
                    listData[i + 2] = "   " + v.getVid() + "                 " + v.getDescription() + "    " + v.getExpire_date();
                }
                list1.setListData(listData);


            }
        });

        System.out.println(administratorWindow.getSize());
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        administratorWindow = new JPanel();
        administratorWindow.setLayout(new GridLayoutManager(6, 2, new Insets(0, 0, 0, 0), -1, -1));
        welcomeLabel = new JLabel();
        Font welcomeLabelFont = this.$$$getFont$$$(null, -1, 20, welcomeLabel.getFont());
        if (welcomeLabelFont != null) welcomeLabel.setFont(welcomeLabelFont);
        welcomeLabel.setText("Administrator Window");
        administratorWindow.add(welcomeLabel, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        showTotalNumberOfButton = new JButton();
        showTotalNumberOfButton.setText("Show Total Number of Customer");
        administratorWindow.add(showTotalNumberOfButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(278, 30), null, 0, false));
        computeAllRestaurantStarsButton = new JButton();
        computeAllRestaurantStarsButton.setText("Compute All Restaurant Stars and Update");
        administratorWindow.add(computeAllRestaurantStarsButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(278, 30), null, 0, false));
        findRestaurantsSupportingAllButton = new JButton();
        findRestaurantsSupportingAllButton.setText("Find Restaurants Supporting All Vouchers");
        administratorWindow.add(findRestaurantsSupportingAllButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(278, 30), null, 0, false));
        list1 = new JList();
        Font list1Font = this.$$$getFont$$$("Andale Mono", Font.BOLD, 20, list1.getFont());
        if (list1Font != null) list1.setFont(list1Font);
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        list1.setModel(defaultListModel1);
        list1.setSelectionMode(2);
        administratorWindow.add(list1, new GridConstraints(1, 1, 5, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        deleteAllExpiredVouchersButton = new JButton();
        deleteAllExpiredVouchersButton.setText("Delete All Expired Vouchers");
        administratorWindow.add(deleteAllExpiredVouchersButton, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(278, 30), null, 0, false));
        displayAllVouchersButton = new JButton();
        displayAllVouchersButton.setText("Display All Vouchers");
        administratorWindow.add(displayAllVouchersButton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return administratorWindow;
    }

}
