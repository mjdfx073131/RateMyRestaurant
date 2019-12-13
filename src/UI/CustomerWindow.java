package UI;

import Models.Customer;
import Models.Restaurant;
import OracleManager.OracleManager;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CustomerWindow {
    //data fields
    private OracleManager oracleManager;
    private Customer customer;
    private ArrayList<Restaurant> resultRestaurants;
    private ArrayList<Restaurant> favoriteRestaurants;

    //GUI
    private JList resultList;
    private JList favoriteList;
    private JButton filterButton;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JTextField textField1;
    private JButton searchButton;
    private JPanel customerWindow;
    private JLabel welcomeLabel;
    private JButton refreshButton;
    BufferedImage bg = null;


    public CustomerWindow(OracleManager oracleManager_PassedIn, Customer customer_PassedIn) {
        oracleManager = oracleManager_PassedIn;
        customer = customer_PassedIn;

        JFrame frame = new JFrame("CustomerWindow");
        frame.setContentPane(customerWindow);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(600, 800);

        ImageIcon img = new ImageIcon("src/UI/1.jpg");
        JLabel jl_bg = new JLabel(img);
        jl_bg.setBounds(0, 0, 600, 800);

        frame.getLayeredPane().add(jl_bg, new Integer(Integer.MIN_VALUE));
        ((JPanel) frame.getContentPane()).setOpaque(false);

        try {
            bg = ImageIO.read(new File("src/UI/1.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame.setBounds(0, 0, 600, 800);
        frame.setResizable(false);

        //set up layout
        setupLayout();
      //  System.out.println(customerWindow.getSize());

        //ActionListener
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = textField1.getText().trim();
                resultRestaurants = oracleManager.searchRestaurants(keyword);
                displayResultList();
                textField1.setText("");
                System.out.println(customerWindow.getSize());

            }
        });

        textField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = textField1.getText().trim();
                resultRestaurants = oracleManager.searchRestaurants(keyword);
                displayResultList();
                textField1.setText("");
            }
        });

        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultRestaurants = oracleManager.filterRestaurants((int) comboBox1.getSelectedItem(), (int) comboBox2.getSelectedItem(), (String) comboBox3.getSelectedItem());
                displayResultList();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                favoriteRestaurants = oracleManager.getFavorites(customer.getCid());
                displayFavoriteList();
            }
        });

        resultList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                if (evt.getClickCount() >= 2) {
                    int i = list.locationToIndex(evt.getPoint());
                    Restaurant r = resultRestaurants.get(i);
                    new RestaurantInfo(oracleManager, customer, r);
                }
            }
        });

        favoriteList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                if (evt.getClickCount() >= 2) {
                    int i = list.locationToIndex(evt.getPoint());
                    Restaurant r = favoriteRestaurants.get(i);
                    new RestaurantInfo(oracleManager, customer, r);
                }
            }
        });
    }


    //helper method
    private void setupLayout() {
        //initialize comboBoxs
        comboBox1.addItem(1);
        comboBox1.addItem(2);
        comboBox1.addItem(3);
        comboBox1.addItem(4);
        comboBox1.addItem(5);

        comboBox2.addItem(1);
        comboBox2.addItem(2);
        comboBox2.addItem(3);
        comboBox2.setSelectedIndex(2);

        comboBox3.addItem("ALL");
        comboBox3.addItem("Japanese cuisine");
        comboBox3.addItem("Korean cuisine");
        comboBox3.addItem("Chinese cuisine");
        comboBox3.addItem("Western cuisine");
        comboBox3.addItem("Mexican cuisine");

        //initialize welcomeLabel
        welcomeLabel.setText("Hello, " + customer.getName() + "~");
        //initialize result list
        resultRestaurants = oracleManager.searchRestaurants("");
        displayResultList();
        //initialize favorite list
        favoriteRestaurants = oracleManager.getFavorites(customer.getCid());
        displayFavoriteList();
    }

    private void displayResultList() {
        String[] listData = new String[resultRestaurants.size()];
        for (int i = 0; i < resultRestaurants.size(); i++) {
            Restaurant r = resultRestaurants.get(i);
            listData[i] = String.format("%-4.4s", " " + r.getRid()) +
                    String.format("%-20.15s", "" + r.getName()) +
                    String.format("%-10.10s", "" + convertTo('*', r.getStar_level())) +
                    String.format("%-10.10s", "" + convertTo('$', r.getPrice_level())) +
                    String.format("%-20.20s", "" + r.getType_name()) +
                    String.format("%-5.5s", "<" + r.getDistance());
        }
        resultList.setListData(listData);
    }

    private void displayFavoriteList() {
        String[] listData = new String[favoriteRestaurants.size()];
        for (int i = 0; i < favoriteRestaurants.size(); i++) {
            Restaurant r = favoriteRestaurants.get(i);
            listData[i] = String.format("%-4.4s", " " + r.getRid()) +
                    String.format("%-20.15s", "" + r.getName()) +
                    String.format("%-10.10s", "" + convertTo('*', r.getStar_level()));
        }
        favoriteList.setListData(listData);
    }

    public static String convertTo(char symbol, int n) {
        String result = "";
        for (int i = 0; i < n; i++)
            result += symbol;
        return result;
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
        customerWindow = new JPanel();
        customerWindow.setLayout(new GridLayoutManager(10, 5, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Arial", Font.BOLD, 14, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Results");
        customerWindow.add(label1, new GridConstraints(4, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Arial", Font.BOLD, 14, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("Favourites");
        customerWindow.add(label2, new GridConstraints(7, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        customerWindow.add(scrollPane1, new GridConstraints(6, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        resultList = new JList();
        Font resultListFont = this.$$$getFont$$$("Andale Mono", Font.PLAIN, 14, resultList.getFont());
        if (resultListFont != null) resultList.setFont(resultListFont);
        scrollPane1.setViewportView(resultList);
        final JScrollPane scrollPane2 = new JScrollPane();
        customerWindow.add(scrollPane2, new GridConstraints(9, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        favoriteList = new JList();
        Font favoriteListFont = this.$$$getFont$$$("Andale Mono", Font.PLAIN, 14, favoriteList.getFont());
        if (favoriteListFont != null) favoriteList.setFont(favoriteListFont);
        scrollPane2.setViewportView(favoriteList);
        welcomeLabel = new JLabel();
        Font welcomeLabelFont = this.$$$getFont$$$(null, -1, 20, welcomeLabel.getFont());
        if (welcomeLabelFont != null) welcomeLabel.setFont(welcomeLabelFont);
        welcomeLabel.setText("Customer Window");
        customerWindow.add(welcomeLabel, new GridConstraints(0, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 2, false));
        filterButton = new JButton();
        filterButton.setText("Filter");
        customerWindow.add(filterButton, new GridConstraints(3, 3, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox1 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        comboBox1.setModel(defaultComboBoxModel1);
        customerWindow.add(comboBox1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox2 = new JComboBox();
        customerWindow.add(comboBox2, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox3 = new JComboBox();
        customerWindow.add(comboBox3, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Star Level >=");
        customerWindow.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        final JLabel label4 = new JLabel();
        label4.setText("Price Level <=");
        customerWindow.add(label4, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        final JLabel label5 = new JLabel();
        label5.setText("Food Type");
        customerWindow.add(label5, new GridConstraints(2, 2, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        final JLabel label6 = new JLabel();
        label6.setText("  Search by Keyword:");
        customerWindow.add(label6, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField1 = new JTextField();
        customerWindow.add(textField1, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        searchButton = new JButton();
        searchButton.setText("Search");
        customerWindow.add(searchButton, new GridConstraints(1, 3, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("ID   Name                               Star             Price              Type                            Distance (km)");
        customerWindow.add(label7, new GridConstraints(5, 0, 1, 5, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        final JLabel label8 = new JLabel();
        label8.setText("ID   Name                               Star");
        customerWindow.add(label8, new GridConstraints(8, 0, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        refreshButton = new JButton();
        refreshButton.setText("Refresh");
        customerWindow.add(refreshButton, new GridConstraints(8, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        return customerWindow;
    }

}
