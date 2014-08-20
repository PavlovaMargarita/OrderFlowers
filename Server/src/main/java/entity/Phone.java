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
    @JoinColumn(name = "owner_id", nullable = false)
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

        if (!comment.equals(phone.comment)) return false;
        if (!countryCode.equals(phone.countryCode)) return false;
        if (!id.equals(phone.id)) return false;
        if (!operatorCode.equals(phone.operatorCode)) return false;
        if (!owner.equals(phone.owner)) return false;
        if (!phoneNumber.equals(phone.phoneNumber)) return false;
        if (phoneType != phone.phoneType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + owner.hashCode();
        result = 31 * result + countryCode.hashCode();
        result = 31 * result + operatorCode.hashCode();
        result = 31 * result + phoneNumber.hashCode();
        result = 31 * result + phoneType.hashCode();
        result = 31 * result + comment.hashCode();
        return result;
    }
}
