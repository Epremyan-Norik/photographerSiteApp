package com.photographer.app.service;

import com.photographer.app.model.Role;
import com.photographer.app.model.User;
import com.photographer.app.repo.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private Repository repository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private MailService mailService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }


    public boolean addEmail(String email, User user){
        if(!StringUtils.isEmpty(email)){
            String emailConfirmCode = String.valueOf((int)(Math.random()*9000000+1000000));
            String message = String.format("Привет, %s \n" +
                    "Для подтверждения почтового адреса перейдите по ссылке: http://localhost:8080/activate/%s", user.getUsername(),emailConfirmCode);
            mailService.send(email,"Код подтверждения", message);
            repository.setUserEmail(user, email ,emailConfirmCode);
        }
        else {
            return false;
        }

        return true;
    }

    public boolean confirmEmail(User user, String confirmCode){
        //Stringrepository.getUserEmail(user);
        String dbCode = repository.getUserEmailConfirmCode(user);
        if(!dbCode.equals(confirmCode)){
            return false;
        }
        repository.setConfirmedEmail(user);
        return true;
    }
    public String getUserEmail(User user){

        return repository.getUserEmail(user);
    }
    public boolean emailIsConfirmed(User user){

        return repository.emailIsConfirmed(user);
    }

    public User findUserById(Long userId) throws UsernameNotFoundException {
        User userFromDb = repository.findUserById(userId);
        if (userFromDb == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return userFromDb;
    }

    public List<User> allUsers() {
        return repository.findAllUsers();
    }

    public boolean saveUser(User user) {
        User userFromDB = repository.findUserByUsername(user.getUsername());
        user.setRoles(Collections.singleton(new Role("ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        repository.saveUser(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (repository.findUserById(userId)!=null) {
            repository.deleteUserById(userId.intValue());
            return true;
        }
        return false;
    }

}
