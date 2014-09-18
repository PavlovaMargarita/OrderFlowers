package com.itechart.courses.dto;

import java.sql.Date;

/**
 * Created by User on 18.09.14.
 */
public class OrderSearchDTO {
    private String customerSurname;
    private String recipientSurname;
    private Date lowerOrderDate;
    private Date upperOrderDate;

    public String getCustomerSurname() {
        return customerSurname;
    }

    public void setCustomerSurname(String customerSurname) {
        this.customerSurname = customerSurname;
    }

    public String getRecipientSurname() {
        return recipientSurname;
    }

    public void setRecipientSurname(String recipientSurname) {
        this.recipientSurname = recipientSurname;
    }

    public Date getLowerOrderDate() {
        return lowerOrderDate;
    }

    public void setLowerOrderDate(Date lowerOrderDate) {
        this.lowerOrderDate = lowerOrderDate;
    }

    public Date getUpperOrderDate() {
        return upperOrderDate;
    }

    public void setUpperOrderDate(Date upperOrderDate) {
        this.upperOrderDate = upperOrderDate;
    }
}
