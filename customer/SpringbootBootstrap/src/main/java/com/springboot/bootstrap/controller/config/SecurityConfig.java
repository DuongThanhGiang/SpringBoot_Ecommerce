package com.springboot.bootstrap.controller.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);
        httpSecurity.csrf(csrf -> csrf.disable());
        httpSecurity.authorizeHttpRequests((authorize)->{
            authorize.requestMatchers(mvc.pattern("/registration**")).permitAll();
            authorize.requestMatchers(mvc.pattern("/js/**")).permitAll();
            authorize.requestMatchers(mvc.pattern("/fe/**")).permitAll();
            authorize.requestMatchers(mvc.pattern("/css/**")).permitAll();
            authorize.requestMatchers(mvc.pattern("/img/**")).permitAll();
            authorize.requestMatchers(mvc.pattern("/hoa_don/**")).hasRole("ADMIN");
            authorize.requestMatchers(mvc.pattern("/hoa_don/**")).hasRole("STAFF");
            authorize.requestMatchers(mvc.pattern("/hoa_don_chi_tiet/**")).hasRole("ADMIN");
            authorize.requestMatchers(mvc.pattern("/hoa_don_chi_tiet/**")).hasRole("STAFF");
            authorize.requestMatchers(mvc.pattern("/khach_hang/**")).hasRole("ADMIN");
            authorize.requestMatchers(mvc.pattern("/khach_hang/**")).hasRole("STAFF");
            authorize.requestMatchers(mvc.pattern("/dia_chi/**")).hasRole("ADMIN");
            authorize.requestMatchers(mvc.pattern("/dia_chi/**")).hasRole("STAFF");
            authorize.requestMatchers(mvc.pattern("/nhan_vien/**")).hasRole("ADMIN");
            authorize.requestMatchers(mvc.pattern("/chuc_vu/**")).hasRole("STAFF");
            authorize.requestMatchers(mvc.pattern("/voucher/**")).hasRole("ADMIN");
            authorize.requestMatchers(mvc.pattern("/danh_muc/**")).hasRole("ADMIN");
            authorize.requestMatchers(mvc.pattern("/kich_thuoc/**")).hasRole("ADMIN");
            authorize.requestMatchers(mvc.pattern("/mau_sac/**")).hasRole("ADMIN");
            authorize.requestMatchers(mvc.pattern("/san_pham/**")).hasRole("ADMIN");
            authorize.requestMatchers(mvc.pattern("/spct/**")).hasRole("ADMIN");
            authorize.requestMatchers(mvc.pattern("/them_sp/**")).hasRole("ADMIN");
            authorize.requestMatchers(mvc.pattern("/thuong_hieu/**")).hasRole("ADMIN");
            authorize.requestMatchers(mvc.pattern("/giao_dich/**")).hasRole("ADMIN");
            authorize.requestMatchers(mvc.pattern("/giao_dich/**")).hasRole("STAFF");
            authorize.requestMatchers(mvc.pattern("/home/**")).hasRole("ADMIN");
            authorize.anyRequest().authenticated();
        })
                .formLogin(form -> form.loginPage("/login").permitAll())
                .logout(form -> form.invalidateHttpSession(true).clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());
        return httpSecurity.build();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
