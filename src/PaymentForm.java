import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class PaymentForm extends JFrame {
    private JTextField descriptionField;
    private JSpinner paymentDateField, dueDateField;
    private JButton saveButton, cancelButton;
    private HouseForm parent;

    public PaymentForm(HouseForm parent) {
        this.parent = parent;
        setupUi();
    }

    private void setupUi() {
        setTitle("Add Payment");
        setSize(300, 200);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(3, 2));
        add(mainPanel, BorderLayout.CENTER);

        mainPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField(20);
        mainPanel.add(descriptionField);

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
        String description = descriptionField.getText();
        Date paymentDate = (Date) paymentDateField.getValue();
        Date dueDate = (Date) dueDateField.getValue();

        Payment payment = new Payment(description, paymentDate, dueDate);
        parent.addPayment(payment);
        dispose();
    }
}
