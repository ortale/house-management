import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HouseUI extends JFrame {
    private JTextField searchField;
    private JButton searchButton, addButton;
    private JPanel houseListPanel;
    private HouseService houseService;
    private List<House> allHouses; // Store all houses here

    public HouseUI() {
        houseService = new HouseService();
        setupUi();
        loadHouses();
    }

    private void setupUi() {
        setTitle("House Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Search panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());
        searchField = new JTextField(20);
        searchButton = new JButton("Search");

        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);

        // House list panel
        houseListPanel = new JPanel();
        houseListPanel.setLayout(new BoxLayout(houseListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(houseListPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        addButton = new JButton("Add House");
        buttonPanel.add(addButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        searchButton.addActionListener(e -> {
            String searchQuery = searchField.getText();
            filterHouses(searchQuery);
        });

        addButton.addActionListener(e -> {
            HouseForm houseForm = new HouseForm(houseService, null, this);
            houseForm.setVisible(true);
        });
    }

    private void loadHouses() {
        try {
            allHouses = houseService.getAllHouses();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        displayHouses(allHouses);
    }

    private void displayHouses(List<House> houses) {
        houseListPanel.removeAll();
        for (House house : houses) {
            JPanel housePanel = new JPanel(new BorderLayout());
            housePanel.setBorder(BorderFactory.createTitledBorder(house.getName()));
            JLabel addressLabel = new JLabel("Address: " + house.getName());
            JButton viewButton = new JButton("View");

            viewButton.addActionListener(e -> {
                HouseForm houseForm = new HouseForm(houseService, house, this);
                houseForm.setVisible(true);
            });

            housePanel.add(addressLabel, BorderLayout.CENTER);
            housePanel.add(viewButton, BorderLayout.EAST);
            houseListPanel.add(housePanel);
        }
        houseListPanel.revalidate();
        houseListPanel.repaint();
    }

    private void filterHouses(String name) {
        List<House> filteredHouses = new ArrayList<>();
        for (House house : allHouses) {
            if (house.getName().toLowerCase().contains(name.toLowerCase())) {
                filteredHouses.add(house);
            }
        }
        displayHouses(filteredHouses);
    }

    public void refreshHouseList() {
        loadHouses();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HouseUI houseUI = new HouseUI();
            houseUI.setVisible(true);
        });
    }
}
