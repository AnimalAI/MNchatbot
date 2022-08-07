package com.petchatbot.domain.model;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_serial;
    private String memberEmail;
    private String memberPassword;
    private String roles; // USER, ADMIN

    public Member() {
    }

    public Member(String memberEmail, String memberPassword) {
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
    }


    public void changePassword(String password){
        this.memberPassword = password;
    }

}
