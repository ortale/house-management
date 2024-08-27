import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CertificateForm extends JFrame {
    private JTextField nameField;
    private JSpinner dateField, expireDateField;
    private JButton saveButton, cancelButton;
    private HouseForm parent;

    public CertificateForm(HouseForm parent) {
        this.parent = parent;
        setupUi();
    }

    private void setupUi() {
        setTitle("Add Certificate");
        setSize(300, 200);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(3, 2));
        add(mainPanel, BorderLayout.CENTER);

        mainPanel.add(new JLabel("Certificate Name:"));
        nameField = new JTextField(20);
        mainPanel.add(nameField);

        mainPanel.add(new JLabel("Date:"));
        dateField = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateField, "yyyy-MM-dd");
        dateField.setEditor(dateEditor);
        mainPanel.add(dateField);

        mainPanel.add(new JLabel("Expire Date:"));
        expireDateField = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor expireDateEditor = new JSpinner.DateEditor(expireDateField, "yyyy-MM-dd");
        expireDateField.setEditor(expireDateEditor);
        mainPanel.add(expireDateField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> saveCertificate());
        cancelButton.addActionListener(e -> dispose());
    }

    private void saveCertificate() {
        String name = nameField.getText();
        Date date = (Date) dateField.getValue();
        Date expireDate = (Date) expireDateField.getValue();

        // Define the date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Format the dates
        String formattedDate = dateFormat.format(date);
        String formattedExpireDate = dateFormat.format(expireDate);

        Certificate certificate = new Certificate(name, formattedDate, formattedExpireDate);
        parent.addCertificate(certificate);
        dispose();
    }
}
