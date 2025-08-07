package cn.escort.frameworkConfig.web.securityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/*import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;*/

@Configuration
public class SecurityConfig {

    //用户认证
   /* @Bean(name = "userDetailsService")
    UserDetailsService getUserService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
                return new User("admin",new BCryptPasswordEncoder().encode("123"),auths);
            }
        };

    }

    //过滤器链配置
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(resp -> {
                    resp.requestMatchers("/", "/index").permitAll();
                    resp.requestMatchers("/hello").hasRole("admin");
                })
                .formLogin(form -> {
                    form.loginPage("/login").defaultSuccessUrl("/index").permitAll();
                })
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    //密码加密
    @Bean(name = "passwordEncoder")
    public PasswordEncoder encoder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/hello");
    }

*/



}
