package entity;

import bl.enums.RoleEnum;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "contact_id", nullable = false)
    private Integer contact;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum  role;

    @Column(nullable = false, length = 30)
    private String login;

    @Column(nullable = false, length = 30)
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderHistory> orderHistory;

    @OneToMany(mappedBy = "handlerManager", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> listHandlerManager;

    @OneToMany(mappedBy = "receiveManager", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> listReceiveManager;

    @OneToMany(mappedBy = "deliveryManager", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> listDeliveryManager;

    public User(){}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public Integer getContact() {
        return contact;
    }

    public void setContact(Integer contact) {
        this.contact = contact;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<OrderHistory> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<OrderHistory> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public List<Order> getListHandlerManager() {
        return listHandlerManager;
    }

    public void setListHandlerManager(List<Order> listHandlerManager) {
        this.listHandlerManager = listHandlerManager;
    }

    public List<Order> getListReceiveManager() {
        return listReceiveManager;
    }

    public void setListReceiveManager(List<Order> listReceiveManager) {
        this.listReceiveManager = listReceiveManager;
    }

    public List<Order> getListDeliveryManager() {
        return listDeliveryManager;
    }

    public void setListDeliveryManager(List<Order> listDeliveryManager) {
        this.listDeliveryManager = listDeliveryManager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (contact != null ? !contact.equals(user.contact) : user.contact != null) return false;
        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (role != user.role) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
