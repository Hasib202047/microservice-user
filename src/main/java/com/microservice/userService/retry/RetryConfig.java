package com.microservice.userService.retry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;

@Configuration
public class RetryConfig {
    /*
    * Retry logging configuration to track the re try
    * */
    @Bean
    public RetryListener loggingRetryListener() {
        return new RetryListener() {
            @Override
            public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
                return true;
            }

            @Override
            public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
            }

            @Override
            public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
                System.out.println("🔁 Retry attempt " + context.getRetryCount() +
                        " due to: " + throwable.getClass().getSimpleName() + " - " + throwable.getMessage());
            }
        };
    }
}
