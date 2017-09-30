package com.example.ribbon.command;

import com.example.ribbon.model.User;
import com.netflix.hystrix.HystrixObservableCommand;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

public class UserObservableCommand extends HystrixObservableCommand<User> {
    private RestTemplate restTemplate;
    private Long id;

    public UserObservableCommand(Setter setter, RestTemplate restTemplate, Long id) {
        super(setter);
        this.restTemplate=restTemplate;
        this.id=id;
    }
    @Override
    protected Observable<User> construct() {
        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> observer) {
                try {
                    if(!observer.isUnsubscribed()){
                        User user=restTemplate.getForObject("http://HELLO-SERVICE/users/{1}",User.class,id);
                        observer.onNext(user);
                        observer.onCompleted();
                    }
                }catch (Exception e){
                    observer.onError(e);
                }
            }
        });
    }
}
