package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;
import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;


@Component
public class DBInitialize {

    private UserService userService;
    private RoleRepository roleRepository;

    @Autowired
    public DBInitialize(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @PostConstruct
    @Transactional
    public void initializeUsers() {
        Role roleadmin = new Role();
        roleadmin.setRoleName("ROLE_ADMIN");
        roleRepository.save(roleadmin);

        Role userRole = new Role();
        userRole.setRoleName("ROLE_USER");
        roleRepository.save(userRole);

        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(roleadmin);
        adminRoles.add(userRole);
        User adminuser = new User("admin", "lastname1", 33L,
                "user1@gmail.com", "100", adminRoles);
        userService.save(adminuser);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        User simpleUser = new User("user", "lastname2", 23L,
                "sb@gmail.com", "200", userRoles);
        userService.save(simpleUser);

    }
}
