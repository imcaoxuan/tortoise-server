package top.caoxuan.tortoiseserver.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.caoxuan.tortoiseserver.utils.QRCodeGenerator;
import top.caoxuan.tortoiseserver.utils.RuntimeUtil;

import java.util.UUID;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static UUID uuid;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http

                .authorizeRequests()

                .antMatchers( "/test", "/static/**").permitAll()

                .anyRequest().authenticated()

                .and()

                .formLogin()

                .loginPage("/signIn")

                .loginProcessingUrl("/login")

                .successForwardUrl("/chat")

                .failureUrl("/signIn?isFailed=true")

                //.defaultSuccessUrl("/success")

                .permitAll()

                .and()

                .logout()

                .permitAll()

                .and().csrf().disable();

    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        uuid = UUID.randomUUID();
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(new BCryptPasswordEncoder().encode("admin"))
                .roles("admin")
                .and()
                .withUser("user@caoxuan.top")
                .password(new BCryptPasswordEncoder().encode(uuid.toString()))
                .roles("user");
        System.out.println("uuid:" + uuid);
        QRCodeGenerator qrCodeGenerator = new QRCodeGenerator();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("url", "http://" + RuntimeUtil.getIpv4Address() + ":8080/");
        jsonObject.put("username", "user@caoxuan.top");
        jsonObject.put("password", uuid.toString());
        String data = JSON.toJSONString(jsonObject);
        qrCodeGenerator.generateQRCodeImage(data, 350, 350, +System.currentTimeMillis() + ".png");
    }


}