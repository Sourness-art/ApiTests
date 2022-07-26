package utils;

import pojos.CreateUserWithBuilderRequest;

public class UserGenerator {

    public static CreateUserWithBuilderRequest getUser(){

       return CreateUserWithBuilderRequest.builder()
                .name("morpheus")
                .job("leader")
                .build();
    }
}
