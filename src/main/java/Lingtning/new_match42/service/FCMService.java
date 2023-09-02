package Lingtning.new_match42.service;

import Lingtning.new_match42.entity.User;
import Lingtning.new_match42.repository.UserRepository;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FCMService {
    private final FirebaseMessaging firebaseMessaging;
    private final UserRepository userRepository;

    // user에게 message를 보내는 함수
    public ResponseEntity<?> sendChatMessage(User user, String message) {
        String token = user.getFcmToken();
        if (token == null) {
            throw new ResponseStatusException(BAD_REQUEST, "알림을 받을 대상에게 FCM 토큰이 없습니다.");
        }
        Notification notification = Notification.builder()
                .setTitle("매치42")
                .setBody(message)
                .build();

        Message fcmMessage = Message.builder()
                .setNotification(notification)
                .setAndroidConfig(AndroidConfig.builder()
                        .setTtl(3600 * 1000)
                        .setPriority(AndroidConfig.Priority.HIGH)
                        .build())
                .setApnsConfig(ApnsConfig.builder()
                        .setAps(Aps.builder()
                                .setContentAvailable(true)
                                .build())
                        .putHeader("apns-push-type", "background")
                        .putHeader("apns-priority", "5")
                        .putHeader("apns-topic", "com.lingtning.match42")
                        .build())
                .setToken(token)
                .build();

        try {
            firebaseMessaging.send(fcmMessage);
        } catch (FirebaseMessagingException e) {
            throw new ResponseStatusException(BAD_REQUEST, "FCM 메시지 전송에 실패했습니다. " + e.getMessage());
        }
        return ResponseEntity.ok("FCM 메시지 전송에 성공했습니다.");
    }

    public ResponseEntity<?> subscribeToken(User user, String token) {
        user.setFcmToken(token);
        try {
            sendChatMessage(user, "FCM 토큰 등록에 성공했습니다.");
            userRepository.save(user);
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "FCM 토큰 등록에 실패했습니다.");
        }
        return ResponseEntity.ok("FCM 토큰 등록에 성공했습니다.");
    }
}