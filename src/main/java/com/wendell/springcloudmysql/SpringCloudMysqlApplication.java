package com.wendell.springcloudmysql;

import com.wendell.springcloudmysql.model.Contact;
import com.wendell.springcloudmysql.repository.ContactRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.LongStream;

@SpringBootApplication
public class SpringCloudMysqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudMysqlApplication.class, args);
	}

	@Bean
	CommandLineRunner init(ContactRepository contactRepository) {
		return args -> {
			contactRepository.deleteAll();
			LongStream.range(1, 11)
					.mapToObj(i -> {
						Contact c = new Contact();
						c.setName("Contact " + i);
						c.setEmail("contact" + i + "@email.com");
						c.setPhone("(111) 111-1111");
						return c;
					})
					.map(v -> contactRepository.save(v))
					.forEach(System.out::println);
		};
	}

}
