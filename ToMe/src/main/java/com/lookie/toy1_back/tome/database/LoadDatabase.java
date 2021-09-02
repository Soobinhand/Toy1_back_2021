package com.lookie.toy1_back.tome.database;

import com.lookie.toy1_back.tome.domain.User;
import com.lookie.toy1_back.tome.repository.UserRepository;
import com.lookie.toy1_back.tome.role.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    @Bean
    CommandLineRunner initDatabase(UserRepository repository){
        return args -> {
            log.info("ex" + repository.save(new User("a","a","a","a", UserRole.USER)));
            log.info("ex" + repository.save(new User("b","b","b","b", UserRole.ADMIN)));
        };
    }
}
