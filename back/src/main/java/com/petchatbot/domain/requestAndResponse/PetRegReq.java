package com.petchatbot.domain.requestAndResponse;

import com.petchatbot.domain.model.Breed;
import com.petchatbot.domain.model.Neutralization;
import com.petchatbot.domain.model.PetSex;
import lombok.Data;

@Data
public class PetRegReq {
    private String memberEmail;
    private Breed petBreed;
    private String petName;
    private String petAge;
    private PetSex petSex;
    private Neutralization petNeutralization;

}
