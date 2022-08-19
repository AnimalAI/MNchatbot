package com.petchatbot.controller;

import com.petchatbot.config.ResponseMessage;
import com.petchatbot.config.StatusCode;
import com.petchatbot.domain.dto.MemberDto;
import com.petchatbot.domain.requestAndResponse.DefaultRes;
import com.petchatbot.domain.requestAndResponse.JoinReq;
import com.petchatbot.domain.requestAndResponse.PetRegReq;
import com.petchatbot.repository.MemberRepository;
import com.petchatbot.service.MemberService;
import com.petchatbot.service.PetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PetController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final PetService petService;

    @PostMapping("/addPet")
    public ResponseEntity<String> addPet(@RequestBody PetRegReq petRegReq) {

        petService.registerPet(petRegReq); // 펫 등록
        log.info("petRegReg={}",petRegReq.getPetName());
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.ADD_ANIMAL), HttpStatus.OK);
    }
}
