package entity;

import javax.persistence.*;

@Entity
@Table(name = "phone")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "country_code", nullable = false)
    private Short countryCode;

    @Column(name = "operator_code", nullable = false)
    private Short operatorCode;

    @Column(name = "phone_number", nullable = false)
    private Integer phoneNumber;

    @Column(name = "phone_type", nullable = false, length = 15)
    private String phoneType;

    @Column(nullable = true, length = 255)
    private String comment;

    @Column(name = "contact_id", nullable = false)
    private Integer contactId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if (id == null){
            throw new NullPointerException("id is null");
        }
        if (id <= 0){
            throw new IllegalArgumentException("id is not positive value");
        }
        this.id = id;
    }

    public Short getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Short countryCode) {
        if (countryCode == null){
            throw new NullPointerException("countryCode is null");
        }
        if (countryCode <= 0){
            throw new IllegalArgumentException("countryCode is not positive value");
        }
        this.countryCode = countryCode;
    }

    public Short getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(Short operatorCode) {
        if (operatorCode == null){
            throw new NullPointerException("operatorCode is null");
        }
        if (operatorCode <= 0){
            throw new IllegalArgumentException("operatorCode is not positive value");
        }
        this.operatorCode = operatorCode;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        if (phoneNumber == null){
            throw new NullPointerException("phoneNumber is null");
        }
        if (phoneNumber <= 0){
            throw new IllegalArgumentException("phoneNumber is not positive value");
        }
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Phone phone = (Phone) o;

        if (countryCode != null ? !countryCode.equals(phone.countryCode) : phone.countryCode != null) return false;
        if (id != null ? !id.equals(phone.id) : phone.id != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(phone.phoneNumber) : phone.phoneNumber != null) return false;
        if (operatorCode != null ? !operatorCode.equals(phone.operatorCode) : phone.operatorCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
        result = 31 * result + (operatorCode != null ? operatorCode.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }
}
