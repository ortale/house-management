import java.util.Date;

public class Payment {
    private Integer id;
    private String description;
    private Date paymentDate;
    private Date dueDate;

    // Constructors, getters, setters
    public Payment(String description, Date paymentDate, Date dueDate) {
        this.description = description;
        this.paymentDate = paymentDate;
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
