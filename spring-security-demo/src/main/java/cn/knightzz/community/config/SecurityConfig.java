package cn.knightzz.community.config;

import cn.knightzz.community.entity.User;
import cn.knightzz.community.service.UserService;
import cn.knightzz.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 王天赐
 * @title: SecurityConfig
 * @projectName technical-tutorials
 * @description:
 * @website <a href="https://knightzz.cn/">https://knightzz.cn/</a>
 * @github <a href="https://github.com/knightzz1998">https://github.com/knightzz1998</a>
 * @create: 2023-05-19 15:49
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 忽略静态资源的访问
        web.ignoring().antMatchers("/resources/**");
    }

    /**
     * 认证核心接口 : 校验用户是否合法(检测密码是否正确)
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // 内置的认证规则
        // auth.userDetailsService(userService).passwordEncoder(new Pbkdf2PasswordEncoder("12345"));

        // 自定义认证规则
        // AuthenticationProvider: ProviderManager持有一组AuthenticationProvider,每个AuthenticationProvider负责一种认证.
        // 委托模式: ProviderManager将认证委托给AuthenticationProvider.
        auth.authenticationProvider(new AuthenticationProvider() {
            /**
             * 认证 : 校验密码是否正确
             * @param authentication User
             * @return
             * @throws AuthenticationException
             */
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String username = authentication.getName();
                String password = (String) authentication.getCredentials();

                User user = userService.findUserByName(username);
                if (user == null) {
                    throw new UsernameNotFoundException("账号不存在!");
                }
                password = CommunityUtil.md5(password + user.getSalt());
                if (!user.getPassword().equals(password)) {
                    throw new BadCredentialsException("密码不正确!");
                }

                // principal: 主要信息; credentials: 证书; authorities: 权限;
                return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
            }

            /**
             * 当前的AuthenticationProvider支持哪种类型的认证.
             * @param aClass
             * @return
             */
            @Override
            public boolean supports(Class<?> aClass) {
                // UsernamePasswordAuthenticationToken: Authentication接口的常用的实现类.
                return UsernamePasswordAuthenticationToken.class.equals(aClass);
            }
        });
    }

    /**
     * 授权 : 给予访问权限
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 登录相关配置
        // 这是配置登录表单的方法，表明使用表单进行登录认证
        http.formLogin()
                // 这是指定登录页面的URL路径为"/loginpage"，当用户未登录时访问受保护的资源时将会跳转到该页面。
                .loginPage("/loginpage")
                // 这是指定登录表单提交的URL路径为"/login"，当用户在登录页面提交表单时，会向该路径发送POST请求进行登录认证
                .loginProcessingUrl("/login")
                // 指定登录成功的处理器
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        // 登录成功以后, 重定向到 /index 页面
                        response.sendRedirect(request.getContextPath() + "/index");
                    }
                })
                // 指定登录失败的处理器
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                        // 设置异常数据
                        request.setAttribute("error", e.getMessage());
                        // 重定向到登陆页面
                        request.getRequestDispatcher("/loginpage").forward(request, response);
                    }
                });

        // 授权配置
        http.authorizeRequests()
                // 针对"/letter"路径的授权规则。它表示只有具有"USER"或"ADMIN"权限的用户才能访问该路径
                .antMatchers("/letter").hasAnyAuthority("USER", "ADMIN")
                // 这是针对"/admin"路径的授权规则。它表示只有具有"ADMIN"权限的用户才能访问该路径。
                .antMatchers("/admin").hasAnyAuthority("ADMIN")
                // 这是配置异常处理的部分。
                // 这是设置访问被拒绝时跳转的页面。当用户没有足够的权限访问某个资源时，将会跳转到"/denied"页面进行提示。
                .and().exceptionHandling().accessDeniedPage("/denied");
        // 增加Filter,处理验证码
        http.addFilterBefore(new Filter() {
            @Override
            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
                HttpServletRequest request = (HttpServletRequest) servletRequest;
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                // 判断当前请求的Servlet路径是否为"/login"，即判断是否为登录请求。
                if (request.getServletPath().equals("/login")) {
                    // 获取请求参数中名为"verifyCode"的值，用于验证码校验。
                    String verifyCode = request.getParameter("verifyCode");

                    // 如果验证码为空或不等于"1234"，表示验证码错误。
                    if (verifyCode == null || !verifyCode.equalsIgnoreCase("1234")) {
                        request.setAttribute("error", "验证码错误!");
                        // 如果验证码出现错误直接重定向
                        request.getRequestDispatcher("/loginpage").forward(request, response);
                        // 终止过滤器链的执行，即停止后续过滤器的执行。
                        return;
                    }
                }
                // 让请求继续向下执行.
                // 如果验证码正确，允许请求继续向下执行，传递给下一个过滤器或目标资源
                filterChain.doFilter(request, response);
            }
        }, UsernamePasswordAuthenticationFilter.class);

        // 记住我
        http.rememberMe()
                // 指定使用内存存储来保存"Remember Me"令牌的配置。
                // 在这里，使用了InMemoryTokenRepositoryImpl作为令牌的存储实现，它将令牌保存在内存中。
                .tokenRepository(new InMemoryTokenRepositoryImpl())
                // 这是设置"Remember Me"令牌的有效期时间(s)
                .tokenValiditySeconds(3600 * 24)
                .userDetailsService(userService);
    }
}
