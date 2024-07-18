package org.example.crud_template.controller;

import org.example.crud_template.model.Role;
import org.example.crud_template.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role createdRole = roleService.createRole(role);
        return ResponseEntity.ok(createdRole);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        return roleService.findRoleById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public ResponseEntity<Iterable<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.findAllRoles());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role role) {
        Role updatedRole = roleService.updateRole(id, role);
        return ResponseEntity.ok(updatedRole);
    }

}