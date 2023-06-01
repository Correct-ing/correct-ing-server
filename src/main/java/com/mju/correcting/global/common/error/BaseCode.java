package com.mju.correcting.global.common.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BaseCode {
    /**
     * Common
     */
    SUCCESS(200, "C001", "요청이 성공적으로 처리되었습니다."),
    CREATED(201, "C002", "요청이 성공적으로 생성되었습니다."),
    INVALID_INPUT_VALUE(400, "C003", "요청 정보가 올바르지 않습니다."),
    INVALID_TYPE_VALUE(400, "C004", "요청 타입이 올바르지 않습니다."),
    HANDLE_ACCESS_DENIED(403, "C005", "접근 권한이 존재하지 않습니다."),
    METHOD_NOT_ALLOWED(405, "C006", "요청 Method 가 올바르지 않습니다."),
    INTERNAL_SERVER_ERROR(500, "C007", "내부 서버 오류입니다."),

    /**
     * User
     */
    EXISTS_USERNAME(400, "U001", "이미 존재하는 아이디 입니다."),
    EXISTS_PHONE_NUMBER(400, "U002", "이미 존재하는 번호 입니다."),
    UNSIGN_USERNAME_OR_PHONE(400, "U003", "가입되지 않은 username 또는 번호입니다."),
    WRONG_PASSWORD(400, "U004", "잘못된 비밀번호 입니다."),
    WITHOUT_AUTHORIZATION_TOKEN(403,"U005", "권한 정보가 없는 토큰입니다."),
    UNREGISTERD_PHONE_NUMBER(400,"U006", "등록되지 않은 핸드폰 번호입니다."),

    INVALID_REFRESH_TOKEN(400,"U007", "검증되지 않은 토큰입니다."),
    NOT_FOUND_USER(400,"U008", "유저를 찾지 못했습니다."),

    /**
     * File
     */
    FAILED_FILE_UPLOAD(500, "F001", "파일 업로드에 실패하였습니다."),
    INVALID_FILE_TYPE(500, "F002", "잘못된 형식의 파일입니다."),
    NOT_FOUND_FILE(400, "F003", "파일을 찾을 수 없습니다."),

    /**
     * PostImage
     */
    NOT_FOUND_POST(400, "P001", "피드를 찾을 수 없습니다."),
    NOT_FOUND_POSTUSER(400, "P002", "피드를 받은 사람이 존재하지 않습니다."),

    /*
    * Comment
    * */
    NOT_FOUND_COMMENT(400, "C001", "댓글을 찾을 수 없습니다."),

    /*
     * Reply
     * */
    NOT_FOUND_REPLY(400, "R001", "답글를 찾을 수 없습니다."),

    /*
     * Reply
     * */
    NOT_FOUND_FOLLOW(400, "R001", "followId를 찾을 수 없습니다."),

    /*
     * Likes
     **/
    NOT_FOUND_LIKES(400, "L001", "해당 Like를 찾을 수 없습니다."),

    /*
     * Push
     **/
    FAILED_PUSH(500, "P001", "푸시알림 보내기 실패"),
    NOT_FOUND_CHATROOM(500, "CR001","채팅방이 존재하지 않습니다." ),
    NOT_FOUND_SCORE(500, "S001","점수가 존재하지 않습니다.");

    private final String code;
    private final String message;
    private int status;

    BaseCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
