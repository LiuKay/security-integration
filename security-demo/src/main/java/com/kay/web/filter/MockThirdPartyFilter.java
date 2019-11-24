package com.kay.web.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Suppose this class is a third party filter and can not be modified
 *
 * @author LiuKay
 * @since 2019/11/24
 */
public class MockThirdPartyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("MockThirdPartyFilter start.");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("MockThirdPartyFilter end");
    }

    @Override
    public void destroy() {

    }
}
