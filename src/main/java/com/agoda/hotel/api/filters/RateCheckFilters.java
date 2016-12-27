package com.agoda.hotel.api.filters;

import com.agoda.hotel.api.rate.limit.services.IAPIRateLimitServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by sgupt13 on 24/12/16.
 */
@Component
public class RateCheckFilters implements Filter {

    @Autowired
    private IAPIRateLimitServices apiRateLimitServices;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String apiKey = httpRequest.getHeader("API_KEY");

        if (!StringUtils.hasText(apiKey))
        {
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.reset();

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("API_KEY is missing from header.");
            return;
        }
        else
        {
            try {
                apiRateLimitServices.requestToken(apiKey);
            } catch (Exception e) {
                HttpServletResponse resp = (HttpServletResponse) response;
                resp.reset();

                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                resp.getWriter().write(e.getMessage());
                return;
            }
        }



        //doFilter
        chain.doFilter(httpRequest, response);
    }

    @Override
    public void destroy() {

    }
}
