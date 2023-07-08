package com.zerobase.commerce.configuration;

import com.zerobase.commerce.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.zerobase.commerce.type.Role.ADMIN;
import static com.zerobase.commerce.type.Role.USER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private final TokenService tokenService;

    // BCryptPasswordEncoder를 통해서 비밀번호를 암호화하고 해당 빈을 통해서 암호화 및 비교작업 수행
    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // UserAuthenticationFailureHandler를 빈으로 등록하는 메서드이다.
    // 이 핸들러는 사용자의 로그인 실패 시에 호출되는 동작을 정의하는 것으로
    // 로그인 실패에 대한 추가적인 로직이 필요한 경우에 사용하는 것이다.
    @Bean
    UserAuthenticationFailureHandler getFailureHandler() {
        return new UserAuthenticationFailureHandler();
    }

    // 이렇게 하면 auth.userDetailsService(memberService).passwordEncoder(getPasswordEncoder());
    // 이거 적용한 거랑 같은 효과가 나온다는것 같은데 왜 그런지는 모르겠음..
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/h2/**");
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // http.csrf().disable()은 deprecated 되었다. 이렇게 메서드 체이닝 쓰지말고 함수형으로 잘 수납해서 쓰라는 것 같다.
        // (이전에도 지원하는 방식이었지만 사람들이 잘 안 썼다고 한다.)
        http.csrf(AbstractHttpConfigurer::disable);

        // h2로 확인하려고 만든것임
        http.headers(AbstractHttpConfigurer::disable);

        // jwt를 사용할 것이므로 세션을 stateless, 즉, 서버는 세션을 생성하지 않고, 모든 요청은 세션에 의존하지 않게 만든다.
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // 여러 접근 제어(프론트 페이지 구현해야 의미있는 부분인듯?)
        http.authorizeHttpRequests((auth) ->
                auth
                        .requestMatchers("/").permitAll() // 이것들 해도 안먹힘(왜지)
                        .requestMatchers("/h2/**").permitAll()
                        .requestMatchers("/user/signup").permitAll()
                        .requestMatchers("/user/signin").permitAll()
                        .requestMatchers("/user/**").hasAnyAuthority(USER.name(), ADMIN.name())
                        .requestMatchers("/admin/**").hasAuthority(ADMIN.name())
                        .requestMatchers("/cart/**").hasAnyAuthority(USER.name(), ADMIN.name())
                        .anyRequest().permitAll()
        );

        // 로그인 시 jwt 필터링
        http.addFilterBefore(new JwtAuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter.class);

        // 프론트엔드 페이지 구현시 사용
//        // 로그인 페이지 엔드포인트 설정
//        http.formLogin((login) ->
//                login
//                        .loginPage("/user/login")
//                        .failureHandler(getFailureHandler())
////                        .failureUrl("/user/login?error") // 로그인 실패 시 이동할 URL을 지정합니다.
//                        .permitAll()
//        );
//
//        // 로그아웃시 루트로 이동
//        http.logout((logout) ->
//                logout
//                        .logoutRequestMatcher(new AntPathRequestMatcher("/"))
//                        .logoutSuccessUrl("/")
//                        .permitAll()
//        );
//
//        // 접근한게 권한이 부족하다면 이쪽으로 넘긴다.
//        http.exceptionHandling((ex) ->
//                        ex.accessDeniedPage("/user/login"));

        return http.build();
    }
}
