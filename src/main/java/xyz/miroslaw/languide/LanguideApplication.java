package xyz.miroslaw.languide;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import xyz.miroslaw.languide.model.Role;
import xyz.miroslaw.languide.model.User;
import xyz.miroslaw.languide.repository.ArticleRepository;
import xyz.miroslaw.languide.repository.UserRepository;
import xyz.miroslaw.languide.service.UserService;

import java.util.Arrays;
import java.util.Collection;

@SpringBootApplication
public class LanguideApplication {

    public static void main(String[] args) {
        SpringApplication.run(LanguideApplication.class, args);
    }

    @Component
    public class DataLoader implements ApplicationRunner {
        private UserRepository userRepository;
        private ArticleRepository articleRepository;
        private UserService userService;

        @Autowired
        public DataLoader(UserService userService, ArticleRepository articleRepository, UserRepository userRepository) {
            this.userRepository = userRepository;
            this.articleRepository = articleRepository;
            this.userService = userService;
        }

        @Override
        public void run(ApplicationArguments args) {
            Collection<Role> roleUser = Arrays.asList(new Role("ROLE_USER"));
            User user = new User("arek", "gracz", roleUser);
            userService.createUser(user);

        }

    }
}

