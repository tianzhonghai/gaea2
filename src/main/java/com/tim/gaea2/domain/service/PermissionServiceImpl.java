package com.tim.gaea2.domain.service;

import com.tim.gaea2.domain.entity.generated.PermissionEntity;
import com.tim.gaea2.domain.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Tim on 2017/7/7.
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private RoleRepository roleRepository;

    public List<PermissionEntity> getAllUserPermission(int userId) {
        return roleRepository.getAllUserPermission(userId);
    }
}
