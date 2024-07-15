
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.spring.security.Entities.AppRole;
import com.example.spring.security.Entities.AppUser;
import com.example.spring.security.Services.AccountUser;


@SpringBootApplication
public class SpringSecurityApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}
//	@Bean
//	CommandLineRunner start(AccountUser accountUser) {
//		
//		return args -> {
//			accountUser.addNewRole(new AppRole(null, "USER"));
//			accountUser.addNewRole(new AppRole(null, "ADMIN"));
//			
//			accountUser.addNewUser(new AppUser(null, "imen","123",new ArrayList()));
//			accountUser.addNewUser(new AppUser(null, "amouna","123",new ArrayList()));
//
//			accountUser.addRoleToUser("imen", "USER");
//			accountUser.addRoleToUser("imen", "ADMIN");
//			accountUser.addRoleToUser("amouna", "USER");
//
//
//		};
//	}
	   @Bean
	   BCryptPasswordEncoder passwordEncoder(){ 
	        return new BCryptPasswordEncoder();
	    }

}
