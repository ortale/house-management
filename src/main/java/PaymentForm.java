import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentForm extends JFrame {
    private JTextField feeInvoiceField;  // Declare email field
    private JTextField rentAmountField;  // Declare email field
    private JTextField statusField;  // Declare email field
    private JSpinner paymentDateField, dueDateField;
    private JButton saveButton, cancelButton;
    private HouseForm parent;

    public PaymentForm(HouseForm parent) {
        this.parent = parent;
        setupUi();
    }

    private void setupUi() {
        setTitle("Add Payment");
        setSize(300, 800);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(8, 1));
        add(mainPanel, BorderLayout.CENTER);

        // Rent field
        JPanel rentAmountPanel = new JPanel(new FlowLayout());
        rentAmountPanel.add(new JLabel("Rent Amount:"));
        rentAmountField = new JTextField(20);  // Initialize email field
        rentAmountPanel.add(rentAmountField);
        mainPanel.add(rentAmountPanel);

        // Invoice field
        JPanel feeInvoicePanel = new JPanel(new FlowLayout());
        feeInvoicePanel.add(new JLabel("Fee Amount:"));
        feeInvoiceField = new JTextField(20);  // Initialize email field
        feeInvoicePanel.add(feeInvoiceField);
        mainPanel.add(feeInvoicePanel);

        // Status field
        JPanel statusPanel = new JPanel(new FlowLayout());
        statusPanel.add(new JLabel("Status:"));
        statusField = new JTextField(20);  // Initialize status field
        statusPanel.add(statusField);
        mainPanel.add(statusPanel);

        mainPanel.add(new JLabel("Payment Date:"));
        paymentDateField = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor paymentDateEditor = new JSpinner.DateEditor(paymentDateField, "yyyy-MM-dd");
        paymentDateField.setEditor(paymentDateEditor);
        mainPanel.add(paymentDateField);

        mainPanel.add(new JLabel("Due Date:"));
        dueDateField = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dueDateEditor = new JSpinner.DateEditor(dueDateField, "yyyy-MM-dd");
        dueDateField.setEditor(dueDateEditor);
        mainPanel.add(dueDateField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> savePayment());
        cancelButton.addActionListener(e -> dispose());
    }

    private void savePayment() {
        Date paymentDate = (Date) paymentDateField.getValue();
        Date dueDate = (Date) dueDateField.getValue();
        Double rentAmount = Double.valueOf(rentAmountField.getText());
        Double feeAmount = Double.valueOf(feeInvoiceField.getText());
        String status = statusField.getText();

        // Define the date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Format the dates
        String formattedPaymentDate = dateFormat.format(paymentDate);
        String formattedDueDate = dateFormat.format(dueDate);

        Payment payment = new Payment(formattedPaymentDate, formattedDueDate, rentAmount, feeAmount, status);
        parent.addPayment(payment);
        dispose();
    }
}
