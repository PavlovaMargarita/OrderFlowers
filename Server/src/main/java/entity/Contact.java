package entity;

import javax.persistence.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String patronymic;

    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(nullable = true)
    private String email;

    @Column(nullable = true)
    private String city;

    @Column(nullable = true)
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
        if (surname == null){
            throw new NullPointerException("surname is null");
        }
        if ((surname.trim()).equals("")){
            throw new IllegalArgumentException("surname is empty");
        }
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null){
            throw new NullPointerException("name is null");
        }
        if ((name.trim()).equals("")){
            throw new IllegalArgumentException("name is empty");
        }
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        if (patronymic == null){
            throw new NullPointerException("patronymic is null");
        }
        if ((patronymic.trim()).equals("")){
            throw new IllegalArgumentException("patronymic is empty");
        }
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
        if (email == null){
            throw new NullPointerException("email is null");
        }
        if ((email.trim()).equals("")){
            throw new IllegalArgumentException("email is empty");
        }
        Pattern emailPattern = Pattern.compile("^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*\n" +
                "      @[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$");
        Matcher matcher = emailPattern.matcher(email);
        if (!matcher.matches()){
            throw new IllegalArgumentException("email is incorrect");
        }
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if (city == null){
            throw new NullPointerException("city is null");
        }
        if ((city.trim()).equals("")){
            throw new IllegalArgumentException("city is empty");
        }
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        if (street == null){
            throw new NullPointerException("street is null");
        }
        if ((street.trim()).equals("")){
            throw new IllegalArgumentException("street is empty");
        }
        this.street = street;
    }

    public Integer getHome() {
        return home;
    }

    public void setHome(Integer home) {
        if (home == null){
            throw new NullPointerException("home is null");
        }
        if (home <= 0){
            throw new IllegalArgumentException("home is not positive value");
        }
        this.home = home;
    }

    public Integer getFlat() {
        return flat;
    }

    public void setFlat(Integer flat) {
        if (flat == null){
            throw new NullPointerException("flat is null");
        }
        if (flat <= 0){
            throw new IllegalArgumentException("flat is not positive value");
        }
        this.flat = flat;
    }
}
