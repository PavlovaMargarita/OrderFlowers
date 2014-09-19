package com.itechart.courses.service.role;

import com.itechart.courses.dto.RoleDTO;
import com.itechart.courses.enums.RoleEnum;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Override
    public List getRoles() {
        List roleEnum = new ArrayList();
        for(int i = 0; i < RoleEnum.values().length; i++) {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setRole(RoleEnum.values()[i]);
            if(roleDTO.getRole().equals(RoleEnum.ROLE_ADMIN)){
                roleDTO.setRoleRussian("Администратор");
            }
            if(roleDTO.getRole().equals(RoleEnum.ROLE_PROCESSING_ORDERS_SPECIALIST)){
                roleDTO.setRoleRussian("Специалист по обработке заказов");
            }
            if(roleDTO.getRole().equals(RoleEnum.ROLE_RECEIVING_ORDERS_MANAGER)){
                roleDTO.setRoleRussian("Менеджер по приему заказа");
            }
            if(roleDTO.getRole().equals(RoleEnum.ROLE_SERVICE_DELIVERY_MANAGER)){
                roleDTO.setRoleRussian("Менеджер по доставке заказа");
            }
            if(roleDTO.getRole().equals(RoleEnum.ROLE_SUPERVISOR)){
                roleDTO.setRoleRussian("Супервизор");
            }
            roleEnum.add(roleDTO);
        }
        return roleEnum;
    }
}
