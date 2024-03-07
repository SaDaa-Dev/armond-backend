package com.chandev.armond.domain.service;


import com.chandev.armond.domain.Member;
import com.chandev.armond.repository.MemberRepository;
import com.chandev.armond.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("Jeong");

        //when
        Long savedId = memberService.join(member);

        //then
        assertThat(member.getId()).isEqualTo(savedId);
    }

    @Test
    void 회원_가입_예외() throws Exception {
        //given
        Member member = new Member();
        member.setName("Jeong");
        Member member1 = new Member();
        member1.setName("Jeong");
        //when
        memberService.join(member);
        org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class, () -> {
           memberService.join(member1);
        });


        //then
    }


}