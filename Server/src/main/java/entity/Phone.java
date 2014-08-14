package entity;

import bl.enums.PhoneTypeEnum;

import javax.persistence.*;

@Entity
@Table(name = "phone")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private Contact owner;

    @Column(name = "country_code", nullable = false)
    private Short countryCode;

    @Column(name = "operator_code", nullable = false)
    private Short operatorCode;

    @Column(name = "phone_number", nullable = false)
    private Integer phoneNumber;

    @Column(name = "phone_type", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private PhoneTypeEnum phoneType;

    @Column(nullable = true, length = 255)
    private String comment;

    public Phone(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Contact getOwner() {
        return owner;
    }

    public void setOwner(Contact owner) {
        this.owner = owner;
    }

    public Short getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Short countryCode) {
        this.countryCode = countryCode;
    }

    public Short getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(Short operatorCode) {
        this.operatorCode = operatorCode;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PhoneTypeEnum getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(PhoneTypeEnum phoneType) {
        this.phoneType = phoneType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Phone phone = (Phone) o;

        if (comment != null ? !comment.equals(phone.comment) : phone.comment != null) return false;
        if (countryCode != null ? !countryCode.equals(phone.countryCode) : phone.countryCode != null) return false;
        if (id != null ? !id.equals(phone.id) : phone.id != null) return false;
        if (operatorCode != null ? !operatorCode.equals(phone.operatorCode) : phone.operatorCode != null) return false;
        if (owner != null ? !owner.equals(phone.owner) : phone.owner != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(phone.phoneNumber) : phone.phoneNumber != null) return false;
        if (phoneType != phone.phoneType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
        result = 31 * result + (operatorCode != null ? operatorCode.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (phoneType != null ? phoneType.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
