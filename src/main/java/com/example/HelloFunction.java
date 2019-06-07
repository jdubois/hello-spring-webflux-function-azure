package com.example;

import com.example.model.Greeting;
import com.example.model.User;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.function.context.FunctionRegistration;
import org.springframework.cloud.function.context.FunctionType;
import org.springframework.cloud.function.context.FunctionalSpringApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;

import java.util.function.Function;

@SpringBootConfiguration
public class HelloFunction implements ApplicationContextInitializer<GenericApplicationContext> {

    public static void main(String[] args) throws Exception {
        FunctionalSpringApplication.run(HelloFunction.class, args);
    }

    public Function<User, Greeting> hello() {
        return user -> new Greeting("Welcome, " + user.getName());
    }

    @Override
    public void initialize(GenericApplicationContext context) {
        context.registerBean("hello", FunctionRegistration.class,
                () -> new FunctionRegistration<>(hello())
                        .type(FunctionType.from(User.class).to(Greeting.class)));
    }
}
