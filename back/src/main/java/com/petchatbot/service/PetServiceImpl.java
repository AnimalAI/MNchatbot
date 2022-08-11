package com.petchatbot.service;

import com.petchatbot.domain.model.*;
import com.petchatbot.domain.requestAndResponse.PetRegReq;
import com.petchatbot.repository.MemberRepository;
import com.petchatbot.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PetServiceImpl implements PetService{

    private final MemberRepository memberRepository;
    private final PetRepository petRepository;



    @Override
    public void registerPet(PetRegReq petRegReq) {
        Member findMember = getFindMember(petRegReq);
        Pet pet = createPetEntity(petRegReq);
        petRepository.save(pet);
        findMember.addPet(pet);
    }

    private Member getFindMember(PetRegReq petRegReq) {
        String memberEmail = petRegReq.getMemberEmail();
        Member findMember = memberRepository.findByMemberEmail(memberEmail);
        return findMember;
    }

    private Pet createPetEntity(PetRegReq petRegReq) {
        Breed petBreed = petRegReq.getPetBreed();
        log.info("petBreed={}", petBreed);
        String petName = petRegReq.getPetName();
        String petAge = petRegReq.getPetAge();
        PetSex petSex = petRegReq.getPetSex();
        Neutralization petNeutralization = petRegReq.getPetNeutralization();

        Pet pet = new Pet(petBreed, petName, petAge, petSex, petNeutralization);
        return pet;
    }

    @Override
    public void changePetInfo(Pet pet) {

    }
}
