package entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

//Hello

@Entity
@Table(name = "contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = 30)
    private String surname;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = true, length = 30)
    private String patronymic;

    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(nullable = true, length = 50)
    private String email;

    @Column(nullable = true, length = 20)
    private String city;

    @Column(nullable = true, length = 20)
    private String street;

    @Column(nullable = true)
    private Integer home;

    @Column(nullable = true)
    private Integer flat;

    @OneToMany(mappedBy = "recipient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> listOrderRecipient;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner", targetEntity = Phone.class, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Phone> phones;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getHome() {
        return home;
    }

    public void setHome(Integer home) {
        this.home = home;
    }

    public Integer getFlat() {
        return flat;
    }

    public void setFlat(Integer flat) {
        this.flat = flat;
    }

    public List<Order> getListOrderRecipient() {
        return listOrderRecipient;
    }

    public void setListOrderRecipient(List<Order> listOrderRecipient) {
        this.listOrderRecipient = listOrderRecipient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (city != null ? !city.equals(contact.city) : contact.city != null) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(contact.dateOfBirth) : contact.dateOfBirth != null) return false;
        if (email != null ? !email.equals(contact.email) : contact.email != null) return false;
        if (flat != null ? !flat.equals(contact.flat) : contact.flat != null) return false;
        if (home != null ? !home.equals(contact.home) : contact.home != null) return false;
        if (id != null ? !id.equals(contact.id) : contact.id != null) return false;
        if (name != null ? !name.equals(contact.name) : contact.name != null) return false;
        if (patronymic != null ? !patronymic.equals(contact.patronymic) : contact.patronymic != null) return false;
        if (street != null ? !street.equals(contact.street) : contact.street != null) return false;
        if (surname != null ? !surname.equals(contact.surname) : contact.surname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (patronymic != null ? patronymic.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (home != null ? home.hashCode() : 0);
        result = 31 * result + (flat != null ? flat.hashCode() : 0);
        return result;
    }
}
