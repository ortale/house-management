import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class HouseForm extends JFrame {
    private JTextField nameField;
    private JButton saveButton, cancelButton, addCertificateButton, addPaymentButton;
    private JList<Certificate> certificateList;
    private DefaultListModel<Certificate> certificateListModel;
    private JList<Payment> paymentList;
    private DefaultListModel<Payment> paymentListModel;
    private HouseService houseService;
    private House house;
    private HouseUI parent;

    public HouseForm(HouseService houseService, House house, HouseUI parent) {
        this.houseService = houseService;
        this.house = house;
        this.parent = parent;
        setupUi();

        if (house != null) {
            loadHouseDetails();
        }
    }

    private void setupUi() {
        setTitle(house == null ? "Add House" : "Edit House");
        setSize(400, 400);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(4, 1));
        add(mainPanel, BorderLayout.CENTER);

        // Name field
        JPanel namePanel = new JPanel(new FlowLayout());
        namePanel.add(new JLabel("House Name:"));
        nameField = new JTextField(20);
        namePanel.add(nameField);
        mainPanel.add(namePanel);

        // Certificate list
        JPanel certificatePanel = new JPanel(new BorderLayout());
        certificatePanel.setBorder(BorderFactory.createTitledBorder("Certificates"));
        certificateListModel = new DefaultListModel<>();
        certificateList = new JList<>(certificateListModel);
        JScrollPane certificateScrollPane = new JScrollPane(certificateList);
        certificatePanel.add(certificateScrollPane, BorderLayout.CENTER);
        addCertificateButton = new JButton("Add Certificate");
        certificatePanel.add(addCertificateButton, BorderLayout.SOUTH);
        mainPanel.add(certificatePanel);

        // Payment list
        JPanel paymentPanel = new JPanel(new BorderLayout());
        paymentPanel.setBorder(BorderFactory.createTitledBorder("Payments"));
        paymentListModel = new DefaultListModel<>();
        paymentList = new JList<>(paymentListModel);
        JScrollPane paymentScrollPane = new JScrollPane(paymentList);
        paymentPanel.add(paymentScrollPane, BorderLayout.CENTER);
        addPaymentButton = new JButton("Add Payment");
        paymentPanel.add(addPaymentButton, BorderLayout.SOUTH);
        mainPanel.add(paymentPanel);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners
        saveButton.addActionListener(e -> saveHouse());
        cancelButton.addActionListener(e -> dispose());
        addCertificateButton.addActionListener(e -> {
            CertificateForm certificateForm = new CertificateForm(this);
            certificateForm.setVisible(true);
        });
        addPaymentButton.addActionListener(e -> {
            PaymentForm paymentForm = new PaymentForm(this);
            paymentForm.setVisible(true);
        });
    }

    private void loadHouseDetails() {
        nameField.setText(house.getName());

        // Load certificates
        certificateListModel.clear();
        for (Certificate certificate : house.getCertificates()) {
            certificateListModel.addElement(certificate);
        }

        // Load payments
        paymentListModel.clear();
        for (Payment payment : house.getPayments()) {
            paymentListModel.addElement(payment);
        }
    }

    private void saveHouse() {
        String name = nameField.getText();

        if (house == null) {
            house = new House();
            house.setName(name);
            house.setCertificates(new ArrayList<>());
            house.setPayments(new ArrayList<>());
            try {
                houseService.addHouse(house);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            house.setName(name);
            try {
                houseService.updateHouse(house);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        parent.refreshHouseList();
        dispose();
    }

    public void addCertificate(Certificate certificate) {
        certificateListModel.addElement(certificate);
        house.getCertificates().add(certificate);
        try {
            houseService.addCertificate(house.getId(), certificate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addPayment(Payment payment) {
        paymentListModel.addElement(payment);
        house.getPayments().add(payment);
        try {
            houseService.addPayment(house.getId(), payment);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
