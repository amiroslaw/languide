package xyz.miroslaw.languide.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/resources/**", "/webjars/**", "/assets/**").permitAll()
                .antMatchers("/index", "/home", "/", "/register").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login")
                    .defaultSuccessUrl("/index").failureUrl("/login?error").permitAll()
//                .and().rememberMe().rememberMeCookieName("my-remember-me-cookie").tokenRepository(persistentTokenRepository()).tokenValiditySeconds(24 * 60 * 60)
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").deleteCookies("my-remember-me-cookie").permitAll();
//                .and().logout().permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("pass").roles("USER");
    }
}