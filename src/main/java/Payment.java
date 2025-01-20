import java.util.Date;

public class Payment {
    private Integer id;
    private Integer houseId;
    private String paymentDate;
    private String dueDate;
    private Double rentAmount;
    private Double feeAmount;
    private String status;
    private Integer emailSent;

    public Payment() {
    }

    // Constructors, getters, setters
    public Payment(String paymentDate, String dueDate, Double rentAmount, Double feeAmount, String status) {
        this.paymentDate = paymentDate;
        this.dueDate = dueDate;
        this.rentAmount = rentAmount;
        this.feeAmount = feeAmount;
        this.status = status;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return rentAmount.toString();
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public Double getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(Double rentAmount) {
        this.rentAmount = rentAmount;
    }

    public Double getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(Double feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getEmailSent() {
        return emailSent;
    }

    public void setEmailSent(Integer emailSent) {
        this.emailSent = emailSent;
    }
}
