import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class HouseForm extends JFrame {
    private JTextField nameField;
    private JTextField emailField;  // Declare email field
    private JSpinner astExpirationField; // Declare AST expiration field
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
        setSize(400, 450);  // Adjusted size to fit the new email field
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(5, 1));  // Increased rows from 4 to 5
        add(mainPanel, BorderLayout.CENTER);

        // Name field
        JPanel namePanel = new JPanel(new FlowLayout());
        namePanel.add(new JLabel("House Name:"));
        nameField = new JTextField(20);
        namePanel.add(nameField);
        mainPanel.add(namePanel);

        // Email field
        JPanel emailPanel = new JPanel(new FlowLayout());
        emailPanel.add(new JLabel("Email:"));
        emailField = new JTextField(20);  // Initialize email field
        emailPanel.add(emailField);
        mainPanel.add(emailPanel);

        // AST Expiration field
        JPanel astExpirationPanel = new JPanel(new FlowLayout());
        astExpirationPanel.add(new JLabel("AST Expiration Date:"));
        astExpirationField = new JSpinner(new SpinnerDateModel());  // Initialize AST expiration field
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(astExpirationField, "dd/MM/yyyy");
        astExpirationField.setEditor(dateEditor);
        astExpirationPanel.add(astExpirationField);
        mainPanel.add(astExpirationPanel);

        if (house != null) {
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

            addCertificateButton.addActionListener(e -> {
                CertificateForm certificateForm = new CertificateForm(this);
                certificateForm.setVisible(true);
            });
            addPaymentButton.addActionListener(e -> {
                PaymentForm paymentForm = new PaymentForm(this);
                paymentForm.setVisible(true);
            });
        }

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
    }

    private void loadHouseDetails() {
        nameField.setText(house.getName());
        emailField.setText(house.getEmail());  // Load the email address

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        if (house.getAstExpDate() != null) {
            LocalDate localDate = LocalDate.parse(house.getAstExpDate(), formatter);
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            astExpirationField.setValue(date);
        }

        if (house != null && house.getCertificates() != null) {
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
    }

    private void saveHouse() {
        String name = nameField.getText();
        String email = emailField.getText();  // Get the email address
        Date astExpirationDate = (Date) astExpirationField.getValue();  // Get the AST expiration date

        // Define the date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Format the dates
        String formattedDate = dateFormat.format(astExpirationDate);

        if (house == null) {
            house = new House();
            house.setName(name);
            house.setEmail(email);  // Set the email address

            house.setAstExpDate(formattedDate);  // Set the AST expiration date
            house.setCertificates(new ArrayList<>());
            house.setPayments(new ArrayList<>());
            try {
                houseService.addHouse(house);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            house.setName(name);
            house.setEmail(email);  // Update the email address
            house.setAstExpDate(formattedDate);  // Update the AST expiration date
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
