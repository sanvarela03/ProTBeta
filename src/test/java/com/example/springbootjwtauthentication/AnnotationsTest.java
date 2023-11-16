package com.example.springbootjwtauthentication;

import lombok.Data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;

public class AnnotationsTest {

    private void checkIfSerializable(Object o){
        if(Objects.isNull(o)){
            //TODO
        }
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface JsonSerializable {

}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface JsonElement {
    public String key() default "";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Init {

}


@Data
@JsonSerializable
class Person {
    @JsonElement
    private String firstName;
    @JsonElement
    private String lastName;
    @JsonElement(key = "personAge")
    private String age;

    private String address;

    @Init
    private void initNames(){
        this.firstName = firstCapital(this.firstName);
        this.lastName = firstCapital(this.lastName);
    }

    /**
     * Para poner en mayusculas la primera letra
     * @param s cual se le aplicara el formato
     * @return el string con el formato de primera letra en mayuscula
     * */
    private String firstCapital(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
