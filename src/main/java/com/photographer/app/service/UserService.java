package com.photographer.app.service;

import com.photographer.app.modelsNew.Role;
import com.photographer.app.modelsNew.User;
import com.photographer.app.repo.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    Repository repository;
    //@Autowired
    //UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
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

        //if (userFromDB != null) {
          //  return false;
        //}

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

   /* public List<User> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }*/
}
