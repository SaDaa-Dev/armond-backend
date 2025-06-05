package com.dev.armond.member.dto;

import com.dev.armond.common.enums.Gender;
import com.dev.armond.member.entity.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberInfo {
    private Long id;
    private String phoneNumber;
    private String name;
    private String nickName;
    private Gender gender;
    private Double height;
    private Double weight;
    private Integer goalCalories;

    public static MemberInfo from(Member member) {
        return MemberInfo.builder()
                .id(member.getId())
                .phoneNumber(member.getPhoneNumber())
                .name(member.getName())
                .nickName(member.getNickName())
                .gender(member.getGender())
                .height(member.getHeight())
                .weight(member.getWeight())
                .goalCalories(member.getGoalCalories())
                .build();
    }
}
