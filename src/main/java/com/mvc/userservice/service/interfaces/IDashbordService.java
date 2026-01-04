package com.mvc.userservice.service.interfaces;

import com.mvc.userservice.dto.DashbordAdmin;
import com.mvc.userservice.dto.DashbordAgent;

public interface IDashbordService {
    DashbordAdmin getDashbordAdmin();
    DashbordAgent getDashbordAgent(String username);
}
