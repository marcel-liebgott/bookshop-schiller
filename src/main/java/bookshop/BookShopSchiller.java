package bookshop;

import org.salespointframework.Salespoint;
import org.salespointframework.SalespointSecurityConfiguration;
import org.salespointframework.SalespointWebConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackageClasses = { Salespoint.class, BookShopSchiller.class })
@EnableJpaRepositories(basePackageClasses = { Salespoint.class, BookShopSchiller.class })
@ComponentScan
public class BookShopSchiller {

	/**
	 * Run the BookShopSchiller.
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(BookShopSchiller.class, args);
	}
	
	@Configuration
	static class BookShopSchillerWebConfiguration extends SalespointWebConfiguration {
	
		@Override
		public void addViewControllers(ViewControllerRegistry registry) {
			registry.addViewController("/user/login").setViewName("login");
		}
	}
	
	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	static class WebSecurityConfiguration extends SalespointSecurityConfiguration {
		
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			
			http.csrf().disable();
			
			http.authorizeRequests().antMatchers("/**").permitAll().and().
				formLogin().loginPage("/user/login").loginProcessingUrl("/user/login").and().
				logout().logoutUrl("/logout").logoutSuccessUrl("/");
		}
		
	}

}
