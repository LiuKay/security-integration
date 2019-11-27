package com.kay.security.demo.web.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author LiuKay
 * @since 2019/11/24
 */
//@Component
public class TimeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("TimeFilter start");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("TimeFilter end");
    }

    @Override
    public void destroy() {
    }
}
