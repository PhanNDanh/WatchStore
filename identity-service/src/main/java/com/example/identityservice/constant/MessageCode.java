package com.example.identityservice.constant;

public class MessageCode {

    private MessageCode() {}

    public static final class Account {
        private Account() {
        }

        public static final String ACCOUNT_NOT_FOUND = "Không tìm thấy người dùng";
        public static final String PERMISSION_NOT_FOUND = "Người dùng không có quyền truy cập";
    }


    public static final class Commom {

        private Commom(){
        }

        public static final String TOKEN_EXPIRED = "Token đã hết hạn";
        public static final String TOKEN_INVALIDATED = "Token không hợp lệ";
        public static final String TOKEN_UN_SUPPORT = "Không hỗ trợ token";
        public static final String TOKEN_MALFORMED = "Token không đúng định dạng";
        public static final String SIGNATURE_INVALID = "Chữ ký không hợp lệ";
        public static final String TOKEN_ERROR = "Lỗi token";

    }
}
