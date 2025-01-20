import java.util.ArrayList;
import java.util.List;

public class House {
    private int id;
    private String name;
    private String email;
    private String astExpDate;
    private Double invoiceAmount;
    private Double rentAmount;
    private String feeInvoiceDate;
    private List<Certificate> certificates = new ArrayList<>();
    private List<Payment> payments = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Certificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<Certificate> certificates) {
        this.certificates = certificates;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public String getAstExpDate() {
        return astExpDate;
    }

    public void setAstExpDate(String astExpDate) {
        this.astExpDate = astExpDate;
    }

    @Override
    public String toString() {
        return name;
    }

    public Double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(Double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public Double getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(Double rentAmount) {
        this.rentAmount = rentAmount;
    }

    public String getFeeInvoiceDate() {
        return feeInvoiceDate;
    }

    public void setFeeInvoiceDate(String feeInvoiceDate) {
        this.feeInvoiceDate = feeInvoiceDate;
    }
}
