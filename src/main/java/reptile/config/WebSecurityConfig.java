package reptile.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import entity.User;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
	 
    @Override
    protected void configure(HttpSecurity http) throws Exception { //配置策略
        http.csrf().disable();
        http.authorizeRequests().
                antMatchers("/static/**").permitAll()
//                .antMatchers("/login").permitAll()
                .antMatchers("/pwd").permitAll().anyRequest().authenticated().
                and().formLogin().loginPage("/tologin")
                .loginProcessingUrl("/tologin")//处理登陆接口
                //.successForwardUrl(forwardUrl)
                .defaultSuccessUrl("/login",true)
//                .successForwardUrl("/tologin")
                .permitAll().
//                .successHandler(loginSuccessHandler()).
                and().logout().logoutUrl("/loginout").permitAll().invalidateHttpSession(true).
                deleteCookies("JSESSIONID").logoutSuccessHandler(logoutSuccessHandler()).
                and().sessionManagement().maximumSessions(10).expiredUrl("/tologin");
    }
 
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
      auth.eraseCredentials(false);
 
       //auth.inMemoryAuthentication().withUser("123").password(new BCryptPasswordEncoder(4).encode("123")).roles("user");
       // auth.inMemoryAuthentication().withUser("111").password(new BCryptPasswordEncoder(4).encode("111")).roles("admin");
    }
//    public PasswordEncoder passwordEncoder(){
//    	return new PasswordEncoder() {
//			
//			@Override
//			public boolean matches(CharSequence rawPassword, String encodedPassword) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//			
//			@Override
//			public String encode(CharSequence rawPassword) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//		};
//    }
 
    @Bean
    public BCryptPasswordEncoder passwordEncoder() { //密码加密
        return new BCryptPasswordEncoder(4);
    }
 
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() { //登出处理
        return new LogoutSuccessHandler()  {
            @Override
            public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                try {
                    User user = (User) authentication.getPrincipal();
                    logger.info("USER : " + user.getUsername()+ " LOGOUT SUCCESS !  ");
                } catch (Exception e) {
                    logger.info("LOGOUT EXCEPTION , e : " + e.getMessage());
                }
                httpServletResponse.sendRedirect("/tologin");
            }
        };
    }
 
//    @Bean
//    public SavedRequestAwareAuthenticationSuccessHandler loginSuccessHandler() { //登入处理
//        return new SavedRequestAwareAuthenticationSuccessHandler() {
//            @Override
//            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//                logger.info("USER : " + userDetails.getUsername() + " LOGIN SUCCESS !  ");
////                response.sendRedirect("/index");
//                super.onAuthenticationSuccess(request, response, authentication);
//            }
//        };
//    }
    @Bean
    public UserDetailsService userDetailsService() {    //用户登录实现
        return new UserDetailsService() {
//            @Autowired
//            private UserRepository userRepository;
 
            @Override
            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
                System.out.println("登陆人："+s);
            	User user =  new User();
                user.setUserName(s);
                user.setPassword("$2a$04$1RJs5s1PYFWEdtiWrb7Uj.VF0U92dFu38rswjDenCKWwvfuudVYVu");
//                if (user == null){
//                    throw new UsernameNotFoundException("Username " + s + " not found");
//                }
 
//                Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
//                authorities.add(new SimpleGrantedAuthority("user"));
                return user;
            }
        };
 
 
    }
}