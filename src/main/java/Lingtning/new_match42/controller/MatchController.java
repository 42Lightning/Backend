package Lingtning.new_match42.controller;

import Lingtning.new_match42.dto.MatchRoomResponse;
import Lingtning.new_match42.dto.UserMatchInfoResponse;
import Lingtning.new_match42.service.MatchService;
import Lingtning.new_match42.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/match")
@Tag(name = "Match", description = "매칭 관련 API")
public class MatchController {
    private final UserService userService;
    private final MatchService matchService;

    @Autowired
    public MatchController(UserService userService, MatchService matchService) {
        this.userService = userService;
        this.matchService = matchService;
    }

    @GetMapping("/me")
    @Operation(summary = "매칭 정보 반환 API", description = "현재 매칭 정보를 반환하는 API", responses = {
            @ApiResponse(responseCode = "200", description = "매칭 정보 반환 완료")
    })
    public UserMatchInfoResponse getMatchInfo(Authentication authentication) {
        return matchService.getMatchInfo(authentication);
    }

    @PostMapping("/chat/start")
    @Operation(summary = "채팅 매칭 시작 API", description = "채팅 매칭을 시작하는 API", responses = {
            @ApiResponse(responseCode = "200", description = "채팅 매칭 시작 완료")
    })
    public MatchRoomResponse startChatMatch(Authentication authentication) {
        return matchService.startChatMatch(authentication);
    }

    @PostMapping("/chat/stop")
    @Operation(summary = "채팅 매칭 종료 API", description = "채팅 매칭을 종료하는 API", responses = {
            @ApiResponse(responseCode = "200", description = "채팅 매칭 종료 완료")
    })
    public void stopChatMatch(Authentication authentication) {
        matchService.stopChatMatch(authentication);
    }
}