package com.itechart.cources.entity;

import com.itechart.cources.bl.enums.RoleEnum;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contact_id", nullable = false)
    private Contact contact;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum  role;

    @Column(nullable = false, length = 30)
    private String login;

    @Column(nullable = false, length = 30)
    private String password;

    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderHistory> orderHistory;

    @OneToMany(mappedBy = "handlerManager", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> listHandlerManager;

    @OneToMany(mappedBy = "receiveManager", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> listReceiveManager;

    @OneToMany(mappedBy = "deliveryManager", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> listDeliveryManager;


    public User(){
        orderHistory = new ArrayList<OrderHistory>();
        listHandlerManager = new ArrayList<Order>();
        listReceiveManager = new ArrayList<Order>();
        listDeliveryManager = new ArrayList<Order>();
        isDelete = false;
    }

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

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
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

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!contact.equals(user.contact)) return false;
        if (!id.equals(user.id)) return false;
        if (!isDelete.equals(user.isDelete)) return false;
        if (!listDeliveryManager.equals(user.listDeliveryManager)) return false;
        if (!listHandlerManager.equals(user.listHandlerManager)) return false;
        if (!listReceiveManager.equals(user.listReceiveManager)) return false;
        if (!login.equals(user.login)) return false;
        if (!orderHistory.equals(user.orderHistory)) return false;
        if (!password.equals(user.password)) return false;
        if (role != user.role) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + contact.hashCode();
        result = 31 * result + role.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + isDelete.hashCode();
        result = 31 * result + orderHistory.hashCode();
        result = 31 * result + listHandlerManager.hashCode();
        result = 31 * result + listReceiveManager.hashCode();
        result = 31 * result + listDeliveryManager.hashCode();
        return result;
    }
}
