import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class HouseForm extends JFrame {
    private JTextField nameField;
    private JTextField emailField;  // Declare email field
    private JSpinner astExpirationField; // Declare AST expiration field
    private JButton saveButton;
    private JButton cancelButton;
    private JButton addCertificateButton;
    private JButton addPaymentButton;
    private JList<Certificate> certificateList;
    private DefaultListModel<Certificate> certificateListModel;
    private DefaultTableModel tableModel;
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
        setSize(400, 1000);  // Adjusted size to fit the new email field
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(8, 1));  // Increased rows from 4 to 5
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

        // Invoice AST Expiration field
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
//            JPanel paymentPanel = new JPanel(new BorderLayout());
//            paymentPanel.setBorder(BorderFactory.createTitledBorder("Payments"));
//            paymentPanel.setSize(200,500);
//            paymentListModel = new DefaultListModel<>();
//            paymentList = new JList<>(paymentListModel);
//            JScrollPane paymentScrollPane = new JScrollPane(paymentList);
//            paymentPanel.add(paymentScrollPane, BorderLayout.CENTER);
//            addPaymentButton = new JButton("Add Payment");
//            paymentPanel.add(addPaymentButton, BorderLayout.SOUTH);
//            mainPanel.add(paymentPanel);

            // Payment list
            JPanel paymentPanel = new JPanel(new BorderLayout());
            paymentPanel.setBorder(BorderFactory.createTitledBorder("Payments"));
            paymentPanel.setSize(200, 500);

            // Define table headers
            String[] columnNames = {"Rent Amount", "Fee", "Status"};

            // Create the table model
            tableModel = new DefaultTableModel(columnNames, 0); // Initially empty

            // Create the JTable with the populated model
            JTable paymentTable = new JTable(tableModel);

            // Add the table to a scroll pane
            JScrollPane tableScrollPane = new JScrollPane(paymentTable);
            paymentPanel.add(tableScrollPane, BorderLayout.CENTER);

            // Add button for adding payments
            addPaymentButton = new JButton("Add Payment");
            paymentPanel.add(addPaymentButton, BorderLayout.SOUTH);

            // Add the payment panel to the main panel
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
        }

        if (house != null && house.getPayments() != null) {
            paymentListModel = new DefaultListModel<>();

            // Load payments
            paymentListModel.clear();
            for (Payment payment : house.getPayments()) {
                paymentListModel.addElement(payment);
            }

            // Populate the table model from paymentListModel
            for (int i = 0; i < paymentListModel.size(); i++) {
                Payment payment = paymentListModel.getElementAt(i);
                Object[] row = {payment.getRentAmount(), payment.getFeeAmount(), payment.getStatus()};
                tableModel.addRow(row);
            }

            // Add a TableModelListener to capture changes
            tableModel.addTableModelListener(event -> {
                int row = event.getFirstRow();  // Row where the change occurred
                int column = event.getColumn(); // Column where the change occurred

                if (column >= 0) { // Ensure it's not a structural change
                    Object newValue = tableModel.getValueAt(row, column); // Get the updated value
                    String columnName = tableModel.getColumnName(column); // Get column name

                    System.out.println("Value changed at row: " + row + ", column: " + columnName + " (index " + column + ")");
                    System.out.println("New value: " + newValue);

                    Payment payment = paymentListModel.getElementAt(row);

                    switch (column) {
                        case 0: // Rent Amount
                            payment.setRentAmount(Double.valueOf(newValue.toString())); // Adjust type as needed
                            break;
                        case 1: // Fee
                            payment.setFeeAmount(Double.valueOf(newValue.toString())); // Adjust type as needed
                            break;
                        case 2: // Status
                            payment.setStatus((String) newValue); // Adjust type as needed
                            break;
                    }

                    updatePayment(payment);
                }
            });
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

    private void updatePayment(Payment payment) {
        try {
            // Parse the ISO 8601 string
            ZonedDateTime zonedPaymentDateTime = ZonedDateTime.parse(payment.getPaymentDate());
            ZonedDateTime zonedDueDateTime = ZonedDateTime.parse(payment.getDueDate());

            // Convert ZonedDateTime to Date
            Date paymentDate = Date.from(zonedPaymentDateTime.toInstant());
            Date dueDate = Date.from(zonedDueDateTime.toInstant());

            // Define the date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // Format the dates
            String formattedPaymentDate = dateFormat.format(paymentDate);
            String formattedDueDate = dateFormat.format(dueDate);
            payment.setPaymentDate(formattedPaymentDate);
            payment.setDueDate(formattedDueDate);
            houseService.updatePayment(house.getId(), payment);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
