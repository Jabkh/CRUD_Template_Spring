package org.example.crud_template.service;

import org.example.crud_template.model.Role;
import org.example.crud_template.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public Optional<Role> findRoleById(Long id) {
        return roleRepository.findById(id);
    }

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    public Role updateRole(Long id, Role roleDetails) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found for this id :: " + id));
        role.setName(roleDetails.getName());
        return roleRepository.save(role);
    }

    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found for this id :: " + id));
        roleRepository.delete(role);
    }

//    @PostConstruct
//    public void initRoles() {
//        addRoleIfNotFound("ROLE_ADMIN");
//        addRoleIfNotFound("ROLE_USER");
//    }

    private void addRoleIfNotFound(String name) {
        Optional<Role> role = roleRepository.findByName(name);
        if (role.isEmpty()) {
            Role newRole = new Role();
            newRole.setName(name);
            roleRepository.save(newRole);
        }
    }
}