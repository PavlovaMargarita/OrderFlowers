package entity;

import javax.persistence.*;
import java.util.Date;

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

    public Contact(){}

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
}