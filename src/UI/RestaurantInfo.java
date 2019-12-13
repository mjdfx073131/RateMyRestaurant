package UI;

import Models.Customer;
import Models.Rate;
import Models.Restaurant;
import Models.Voucher;
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

public class RestaurantInfo {
    //data fields
    private OracleManager oracleManager;
    private Customer customer;
    private Restaurant selectedRestaurant;

    //GUI
    private JList commentList;
    private JComboBox scoreComboBox;
    private JTextField textField1;
    private JList voucherList;
    private JButton addFavouriteButton;
    private JTextField starField;
    private JTextField idField;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField websiteField;
    private JTextField addressField;
    private JTextField distanceField;
    private JTextField typeField;
    private JTextField priceField;
    private JButton rateButton;
    private JPanel restaurantInfo;
    BufferedImage bg = null;


    public RestaurantInfo(OracleManager oracleManager_PassedIn, Customer customer_PassedIn, Restaurant selectedRestaurant_PassedIn) {
        oracleManager = oracleManager_PassedIn;
        customer = customer_PassedIn;
        selectedRestaurant = selectedRestaurant_PassedIn;

        JFrame frame = new JFrame("RestaurantInfo");
        frame.setContentPane(restaurantInfo);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(750, 465);
        frame.setLocation(600, 0);


        ImageIcon img = new ImageIcon("src/UI/4.png");
        JLabel jl_bg = new JLabel(img);
        jl_bg.setBounds(0, 0, 800, 500);

        frame.getLayeredPane().add(jl_bg, new Integer(Integer.MIN_VALUE));
        ((JPanel) frame.getContentPane()).setOpaque(false);

        try {
            bg = ImageIO.read(new File("src/UI/4.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame.setBounds(0, 0, 800, 500);
        frame.setResizable(false);

        //set up layout
        setupLayout();


        //ActionListener
        addFavouriteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (oracleManager.addFavarite(customer.getCid(), selectedRestaurant.getRid()))
                    JOptionPane.showMessageDialog(null, "The restaurant has been successfully added to your favourite list!");
                else
                    JOptionPane.showMessageDialog(null, "It is already in your favourite list!");
            }
        });

        rateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int score = (int) scoreComboBox.getSelectedItem();
                String comment = textField1.getText();
                java.util.Date localDate = new java.util.Date();
                Date sqlDate = new Date(localDate.getTime());

                if (oracleManager.rateRestaurant(customer.getCid(), selectedRestaurant.getRid(), score, comment, sqlDate))
                    JOptionPane.showMessageDialog(null, "Rate successfully!");

                displayComments();
            }
        });

        System.out.println(restaurantInfo.getSize());
    }


    //helper method
    private void setupLayout() {
        idField.setText("" + selectedRestaurant.getRid());
        nameField.setText(selectedRestaurant.getName());
        phoneField.setText(selectedRestaurant.getPhone_num());
        websiteField.setText(selectedRestaurant.getWebsite());
        addressField.setText(selectedRestaurant.getAddress());
        starField.setText(CustomerWindow.convertTo('*', selectedRestaurant.getStar_level()));
        priceField.setText(CustomerWindow.convertTo('$', selectedRestaurant.getPrice_level()));
        typeField.setText(selectedRestaurant.getType_name());
        distanceField.setText("< " + selectedRestaurant.getDistance() + "km");

        //initialize comboBoxs
        scoreComboBox.addItem(1);
        scoreComboBox.addItem(2);
        scoreComboBox.addItem(3);
        scoreComboBox.addItem(4);
        scoreComboBox.addItem(5);
        scoreComboBox.setSelectedIndex(4);

        //initialize voucherList
        displayPromotedVouchers();

        //initialize commentList
        displayComments();
    }

    private void displayPromotedVouchers() {
        ArrayList<Voucher> vouchers = oracleManager.getVouchers(selectedRestaurant.getRid());

        String[] listData = new String[vouchers.size()];
        for (int i = 0; i < vouchers.size(); i++) {
            Voucher v = vouchers.get(i);
            listData[i] = String.format("%-6.6s", " " + v.getVid()) +
                    String.format("%-12.7s", "" + v.getDescription()) +
                    String.format("%-10.10s", "" + v.getExpire_date());
        }
        voucherList.setListData(listData);
    }

    private void displayComments() {
        ArrayList<Rate> rates = oracleManager.getComments(selectedRestaurant.getRid());

        String[] listData = new String[rates.size()];
        for (int i = 0; i < rates.size(); i++) {
            Rate rate = rates.get(i);
            listData[i] = String.format("%-16.16s", " " + rate.getCid()) +
                    String.format("%-8.8s", "" + rate.getScore()) +
                    String.format("%-65.65s", "" + rate.getRate_comment()) +
                    String.format("%-10.10s", "" + rate.getRate_date());
        }
        commentList.setListData(listData);
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
        restaurantInfo = new JPanel();
        restaurantInfo.setLayout(new GridLayoutManager(11, 7, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, 20, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Restaurant Information");
        restaurantInfo.add(label1, new GridConstraints(0, 0, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$(null, -1, 15, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("  Comments");
        restaurantInfo.add(label2, new GridConstraints(7, 0, 1, 7, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        restaurantInfo.add(scrollPane1, new GridConstraints(9, 0, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        commentList = new JList();
        Font commentListFont = this.$$$getFont$$$("Andale Mono", Font.PLAIN, 14, commentList.getFont());
        if (commentListFont != null) commentList.setFont(commentListFont);
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        commentList.setModel(defaultListModel1);
        scrollPane1.setViewportView(commentList);
        scoreComboBox = new JComboBox();
        restaurantInfo.add(scoreComboBox, new GridConstraints(10, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Comment");
        restaurantInfo.add(label3, new GridConstraints(10, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField1 = new JTextField();
        restaurantInfo.add(textField1, new GridConstraints(10, 3, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("  I rate (Score)");
        restaurantInfo.add(label4, new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Vouchers Promoted");
        restaurantInfo.add(label5, new GridConstraints(1, 4, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Restaurant");
        restaurantInfo.add(label6, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("VID    Description       Expired Date");
        restaurantInfo.add(label7, new GridConstraints(2, 4, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        restaurantInfo.add(scrollPane2, new GridConstraints(3, 4, 4, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        voucherList = new JList();
        Font voucherListFont = this.$$$getFont$$$("Andale Mono", Font.PLAIN, 14, voucherList.getFont());
        if (voucherListFont != null) voucherList.setFont(voucherListFont);
        scrollPane2.setViewportView(voucherList);
        final JLabel label8 = new JLabel();
        label8.setText("  ID:");
        restaurantInfo.add(label8, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("  Name:");
        restaurantInfo.add(label9, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("  Phone:");
        restaurantInfo.add(label10, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setText("  Website:");
        restaurantInfo.add(label11, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setText("  Address:");
        restaurantInfo.add(label12, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label13 = new JLabel();
        label13.setText("Star:");
        restaurantInfo.add(label13, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label14 = new JLabel();
        label14.setText("Price:");
        restaurantInfo.add(label14, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label15 = new JLabel();
        label15.setText("Type:");
        restaurantInfo.add(label15, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label16 = new JLabel();
        label16.setText("Distance:");
        restaurantInfo.add(label16, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addFavouriteButton = new JButton();
        addFavouriteButton.setText("Add Favourite");
        restaurantInfo.add(addFavouriteButton, new GridConstraints(6, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        starField = new JTextField();
        starField.setEditable(false);
        restaurantInfo.add(starField, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        idField = new JTextField();
        idField.setEditable(false);
        restaurantInfo.add(idField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nameField = new JTextField();
        nameField.setEditable(false);
        restaurantInfo.add(nameField, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        phoneField = new JTextField();
        phoneField.setEditable(false);
        restaurantInfo.add(phoneField, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        websiteField = new JTextField();
        websiteField.setEditable(false);
        restaurantInfo.add(websiteField, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        addressField = new JTextField();
        addressField.setEditable(false);
        restaurantInfo.add(addressField, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        distanceField = new JTextField();
        distanceField.setEditable(false);
        restaurantInfo.add(distanceField, new GridConstraints(5, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        typeField = new JTextField();
        typeField.setEditable(false);
        restaurantInfo.add(typeField, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        priceField = new JTextField();
        priceField.setEditable(false);
        restaurantInfo.add(priceField, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label17 = new JLabel();
        label17.setText("Username");
        restaurantInfo.add(label17, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label18 = new JLabel();
        label18.setText("");
        restaurantInfo.add(label18, new GridConstraints(8, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rateButton = new JButton();
        rateButton.setText("Rate");
        restaurantInfo.add(rateButton, new GridConstraints(10, 5, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label19 = new JLabel();
        label19.setText("Date");
        restaurantInfo.add(label19, new GridConstraints(8, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        final JLabel label20 = new JLabel();
        label20.setText("Score        Comment          ");
        restaurantInfo.add(label20, new GridConstraints(8, 1, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 3, false));
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
        return restaurantInfo;
    }

}
