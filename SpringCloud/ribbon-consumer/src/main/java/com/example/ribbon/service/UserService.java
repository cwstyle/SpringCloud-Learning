package com.example.ribbon.service;

import com.example.ribbon.model.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

import java.util.List;
import java.util.concurrent.Future;

public class UserService {
    @Autowired
    private RestTemplate restTemplate;

    //同步执行-----------------------------------------------------------------
    //HystrixBadRequestException异常不会降级处理
    @HystrixCommand(ignoreExceptions = {HystrixBadRequestException.class})
    public User getUserById(Long id) {
        return restTemplate.getForObject("http://HELLO-SERVICE/users/{1}", User.class, id);

    }

    //异步执行
    public Future<User> getUserByIdAsync(final String id) {
        return new AsyncResult<User>() {
            @Override
            public User invoke() {
                return restTemplate.getForObject("http://HELLO-SERVICE/users/{1}", User.class, id);
            }
        };
    }
//    EAGER是该参数的默认值，表示使用observe()执行
//    @HystrixCommand(observableExecutionMode = ObservableExecutionMode.EAGER)
//      LAZY,表示使用toOBservable()执行
//    @HystrixCommand(observableExecutionMode = ObservableExecutionMode.LAZY)

    @HystrixCommand(fallbackMethod = "fallback1")
    public Observable<User> getUserById(final String id) {
        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> observer) {
                try {
                    if (!observer.isUnsubscribed()) {
                        User user = restTemplate.getForObject("http://HELLO-SERVICE/users/{1}", User.class, id);
                        observer.onNext(user);
                        observer.onCompleted();
                    }
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        });
    }

    //获取异常
    User fallback1(String id, Throwable e) {
        assert "gerUserById commmad faild".equals(e.getMessage());
        return null;
    }

    //------------------------------------缓存------------------------------
//    @CacheResult  //开启缓存，此时，缓存的key值会使用所有的参数
    //定义缓存key,定义生成key的方法
//    @CacheResult(cacheKeyMethod="getUserByIdCacheKey")
    @HystrixCommand
    public User getUserById2(@CacheKey("id") Long id) {//使用@CacheKey优先级别cacheKeyMethod低
        return restTemplate.getForObject("http://HELLO-SERVICE/users/{1}", User.class, id);

    }

    @HystrixCommand
    public User getUserById3(@CacheKey("id") User user) {//使用User内部属性做作为缓存key@
        return restTemplate.getForObject("http://HELLO-SERVICE/users/{1}", User.class, user.getId());

    }

    private Long getUserByIdCacheKey(Long id) {
        return id;
    }

    //缓存清理
    @CacheRemove(commandKey = "getUserById2")
    @HystrixCommand
    public void update(@CacheKey("id") User user) {
        restTemplate.postForObject("http://HELLO-SERVICE/users/{1}", user, User.class);

    }

    //---------------------------请求合并器--------------------------------------
    //batchMethod指定了批量请求的实现方法，collapserProperties为合并请求器设置相关属性
    //@HystrixProperty设置合并时间为100毫秒
    @HystrixCollapser(batchMethod = "findAll", collapserProperties =
            {@HystrixProperty(name = "timerDelayInMilliseconds", value = "100")})
    public User find(Long id) {
        return null;
    }

    @HystrixCommand
    public List<User> findAll(List<Long> ids) {
        return restTemplate.getForObject("http://HELLO-SERVICE/users?ids={1}", List.class, StringUtils.join(ids, ","));
    }
}
