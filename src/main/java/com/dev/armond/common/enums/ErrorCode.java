package com.dev.armond.common.enums;

import lombok.Getter;

/**
 * 시스템 전체에서 사용되는 에러 코드를 관리하는 enum 클래스
 * 
 * 에러 코드 네이밍 규칙:
 * - 카테고리_구체적상황_타입 형태
 * - 예: AUTH_USER_NOT_FOUND, AUTH_INVALID_CREDENTIALS
 * 
 * HTTP 상태 코드 가이드라인:
 * - 400: 클라이언트 요청 오류 (잘못된 입력, 검증 실패)
 * - 401: 인증 실패 (로그인 필요, 잘못된 인증 정보)
 * - 403: 권한 부족 (인증됐지만 접근 권한 없음)
 * - 404: 리소스 없음
 * - 500: 서버 내부 오류
 */
@Getter
public enum ErrorCode {
    
    // ========== 인증 관련 에러 (AUTH) ==========
    AUTH_USER_NOT_FOUND("AUTH_001", "사용자를 찾을 수 없습니다", 401),
    AUTH_INVALID_CREDENTIALS("AUTH_002", "아이디 또는 비밀번호가 올바르지 않습니다", 401),
    AUTH_TOKEN_GENERATION_FAILED("AUTH_003", "토큰 생성에 실패했습니다", 500),
    AUTH_TOKEN_INVALID("AUTH_004", "유효하지 않은 토큰입니다", 401),
    AUTH_TOKEN_EXPIRED("AUTH_005", "만료된 토큰입니다", 401),
    AUTH_REFRESH_TOKEN_NOT_FOUND("AUTH_006", "리프레시 토큰을 찾을 수 없습니다", 401),
    AUTH_REFRESH_TOKEN_MISMATCH("AUTH_007", "리프레시 토큰이 일치하지 않습니다", 401),
    
    // ========== 입력 검증 관련 에러 (VALIDATION) ==========
    VALIDATION_FAILED("VALID_001", "입력 검증에 실패했습니다", 400),
    VALIDATION_REQUIRED_FIELD("VALID_002", "필수 입력값이 누락되었습니다", 400),
    VALIDATION_INVALID_FORMAT("VALID_003", "입력값 형식이 올바르지 않습니다", 400),
    VALIDATION_PHONE_NUMBER_INVALID("VALID_004", "전화번호 형식이 올바르지 않습니다", 400),
    VALIDATION_PASSWORD_WEAK("VALID_005", "비밀번호가 보안 요구사항을 만족하지 않습니다", 400),
    
    // ========== 회원 관리 관련 에러 (MEMBER) ==========
    MEMBER_ALREADY_EXISTS("MEMBER_001", "이미 등록된 회원입니다", 409),
    MEMBER_NICKNAME_DUPLICATE("MEMBER_002", "이미 사용 중인 닉네임입니다", 409),
    MEMBER_EMAIL_DUPLICATE("MEMBER_003", "이미 사용 중인 이메일입니다", 409),
    MEMBER_PHONE_DUPLICATE("MEMBER_004", "이미 사용 중인 전화번호입니다", 409),
    MEMBER_NOT_FOUND("MEMBER_005", "회원 정보를 찾을 수 없습니다", 404),
    MEMBER_INACTIVE("MEMBER_006", "비활성화된 회원입니다", 403),
    
    // ========== 운동 관련 에러 (WORKOUT) ==========
    WORKOUT_NOT_FOUND("WORKOUT_001", "운동 정보를 찾을 수 없습니다", 404),
    WORKOUT_ALREADY_COMPLETED("WORKOUT_002", "이미 완료된 운동입니다", 400),
    WORKOUT_INVALID_SET("WORKOUT_003", "유효하지 않은 운동 세트입니다", 400),
    
    // ========== 루틴 관련 에러 (ROUTINE) ==========
    ROUTINE_NOT_FOUND("ROUTINE_001", "루틴을 찾을 수 없습니다", 404),
    ROUTINE_ACCESS_DENIED("ROUTINE_002", "루틴에 접근할 권한이 없습니다", 403),
    ROUTINE_NAME_DUPLICATE("ROUTINE_003", "이미 사용 중인 루틴 이름입니다", 409),
    
    // ========== 운동 종목 관련 에러 (EXERCISE) ==========
    EXERCISE_NOT_FOUND("EXERCISE_001", "운동 종목을 찾을 수 없습니다", 404),
    EXERCISE_MUSCLE_CATEGORY_NOT_FOUND("EXERCISE_002", "운동 부위 정보를 찾을 수 없습니다", 404),
    
    // ========== 시스템 에러 (SYSTEM) ==========
    SYSTEM_INTERNAL_ERROR("SYS_001", "서버 내부 오류가 발생했습니다", 500),
    SYSTEM_DATABASE_ERROR("SYS_002", "데이터베이스 오류가 발생했습니다", 500),
    SYSTEM_EXTERNAL_API_ERROR("SYS_003", "외부 API 호출에 실패했습니다", 500),
    SYSTEM_FILE_UPLOAD_ERROR("SYS_004", "파일 업로드에 실패했습니다", 500),
    SYSTEM_MAINTENANCE("SYS_005", "시스템 점검 중입니다", 503),
    
    // ========== 일반적인 에러 (COMMON) ==========
    COMMON_RUNTIME_ERROR("COMMON_001", "예기치 못한 오류가 발생했습니다", 500),
    COMMON_BAD_REQUEST("COMMON_002", "잘못된 요청입니다", 400),
    COMMON_FORBIDDEN("COMMON_003", "접근이 거부되었습니다", 403),
    COMMON_NOT_FOUND("COMMON_004", "요청한 리소스를 찾을 수 없습니다", 404);
    
    private final String code;
    private final String message;
    private final int httpStatus;
    
    ErrorCode(String code, String message, int httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
    
    /**
     * 에러 코드로 ErrorCode enum을 찾습니다
     * @param code 찾을 에러 코드
     * @return 해당하는 ErrorCode, 없으면 COMMON_RUNTIME_ERROR
     */
    public static ErrorCode findByCode(String code) {
        for (ErrorCode errorCode : values()) {
            if (errorCode.getCode().equals(code)) {
                return errorCode;
            }
        }
        return COMMON_RUNTIME_ERROR;
    }
    
    /**
     * 카테고리별 에러 코드 목록을 반환합니다
     * @param category 카테고리 (예: "AUTH", "MEMBER")
     * @return 해당 카테고리의 ErrorCode 배열
     */
    public static ErrorCode[] findByCategory(String category) {
        return java.util.Arrays.stream(values())
                .filter(errorCode -> errorCode.getCode().startsWith(category))
                .toArray(ErrorCode[]::new);
    }
} 