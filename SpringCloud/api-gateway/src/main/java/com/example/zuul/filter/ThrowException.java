package com.example.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Component
public class ThrowException extends ZuulFilter {
    private static Logger log= LoggerFactory.getLogger(ThrowException.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        log.info("This is a pre filter, it will throw a RuntimeException");
        RequestContext requestContext=RequestContext.getCurrentContext();
        try {
            doSomething();
        }catch (Exception e){
            //error.status_code:错误编码
            //error_exception:Exception异常duixiang
            //error.message:错误信息
            requestContext.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            requestContext.set("error_exception",e);
        }

        return null;
    }
    private  void doSomething(){
        throw  new RuntimeException("Exist some errors...");
    }
}
