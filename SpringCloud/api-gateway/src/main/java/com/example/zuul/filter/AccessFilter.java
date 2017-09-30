package com.example.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class AccessFilter extends ZuulFilter {
    private  static Logger log= LoggerFactory.getLogger(AccessFilter.class);
    //过滤器类型，它决定过滤器在请求的哪个生命周期中执行，这里pre，代表会在请求被路由之前执行
    @Override
    public String filterType() {
        return "pre";
    }

    //执行顺序
    @Override
    public int filterOrder() {
        return 0;
    }

    //判断该过滤器是否需要被执行，这里返回true，代表所有的请求都会生效
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx= RequestContext.getCurrentContext();
        HttpServletRequest request=ctx.getRequest();

        log.info("send {} request to {}",request.getMethod(),request.getRequestURI().toString());

        Object token=request.getParameter("token");
        if(token==null){
            log.warn("access token is empty");
            //令zuul过滤该请求，不对其进行路由
            ctx.setSendZuulResponse(false);
            //设置其返回错误码
            ctx.setResponseStatusCode(401);
            return null;
        }
        log.info("access token ok");
        return null;
    }
}
