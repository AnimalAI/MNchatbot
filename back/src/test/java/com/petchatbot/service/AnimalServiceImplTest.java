package com.petchatbot.service;

import com.petchatbot.domain.model.Member;
import com.petchatbot.domain.model.Pet;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Slf4j
class AnimalServiceImplTest {

    @Autowired
    PetService petServiceImpl;

    @Test
    void 반려동물추가(){
        Member member = new Member("abc@naver.com", "password");
        Pet pet = new Pet();

        petServiceImpl.registerPet(member, pet);
        assertThat(pet.getMember()).isEqualTo(member);
        assertThat(member.getPetList()).contains(pet);
        log.info(pet.getMember().getMemberEmail());
    }




}