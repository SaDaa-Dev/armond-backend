package com.dev.armond.repository;

import com.dev.armond.domain.entity.Member;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Test
    void test() throws Exception {
        //given
        Member member = new Member();
        member.setUsername("hi");
        member.setAge(10);

        memberRepository.save(member);

        List<Member> hello =
                memberRepository.findByUsernameAndAgeGreaterThan("hi", 8);
        //when

        //then
    }
}