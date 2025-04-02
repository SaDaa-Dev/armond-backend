package com.dev.armond.member.service;

import com.dev.armond.member.dto.CustomMemberDetails;

public interface MemberDetailService {
    CustomMemberDetails loadMemberByPhoneNumber(String userEmail);
}
