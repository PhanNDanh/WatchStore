package com.example.identityservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvalidatedToken {

     String token; // Token làm khóa chính trong Redis

     Date expiryTime; // Thời gian hết hạn của token

     String username; // usename người dùng (nếu cần thiết)


}
