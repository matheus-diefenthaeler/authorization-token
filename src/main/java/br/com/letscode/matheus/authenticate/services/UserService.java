package br.com.letscode.matheus.authenticate.services;

import br.com.letscode.matheus.authenticate.entities.User;
import br.com.letscode.matheus.authenticate.repositories.UserRepository;
import br.com.letscode.matheus.authenticate.service.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void verifyUser(String email, String pwd) {
        Optional<User> user = userRepository.findByEmailAndPassword(email,pwd);
        var entity = user.orElseThrow(()-> new UserNotFoundException("User not found!"));
    }

}
