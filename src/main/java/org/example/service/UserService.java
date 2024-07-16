package org.example.service;

import org.example.entity.User;
import org.example.repository.BaseRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    @Autowired
    public void UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getAllUser() {
        System.out.println("Получаем всех клиентов");
        List<User> result = (List<User>) userRepository.findAll();
        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<User>();
        }
    }

    public User getUserById(int id) {
        System.out.println("Получаем нужного клиента");
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            System.out.println("Пользователь не найден");
            return null;
        }
    }

    public User createOrUpdateUser(User user) {
        System.out.println("createOrUpdateUser");
        if (user.getId() == 0) {
            user = userRepository.save(user);
            return user;
        } else {
            Optional<User> userOld = userRepository.findById(user.getId());
            if (userOld.isPresent()) {
                User newUser = userOld.get();
                newUser.setEmail(user.getEmail());
                newUser.setPassword(user.getPassword());

                userRepository.save(newUser);

                return newUser;
            }else{
                user = userRepository.save(user);
                return user;
            }
        }
    }

    public void deleteUserById(int id){
        System.out.println("Удаление задания");
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            userRepository.deleteById(id);
        }else{
            System.out.println("Такого задания нет");
        }
    }

}
