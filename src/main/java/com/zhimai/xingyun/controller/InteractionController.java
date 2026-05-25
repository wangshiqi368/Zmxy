package com.zhimai.xingyun.controller;

import com.zhimai.xingyun.dto.InteractionCreateDTO;
import com.zhimai.xingyun.dto.InteractionDTO;
import com.zhimai.xingyun.service.IInteractionService;
import com.zhimai.xingyun.util.UserContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 互动记录与待办模块 API
 */
@RestController
@RequestMapping("/api/interactions")
public class InteractionController {

    private final IInteractionService interactionService;

    public InteractionController(IInteractionService interactionService) {
        this.interactionService = interactionService;
    }

    /**
     * 追加沟通纪要
     */
    @PostMapping
    public ResponseEntity<Void> addInteraction(@RequestBody InteractionCreateDTO dto) {
        interactionService.addInteraction(dto, UserContext.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 标记/取消标记待办完成
     */
    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<Void> toggleStatus(@PathVariable("id") Long interactionId) {
        interactionService.toggleFollowUpStatus(interactionId, UserContext.getUserId());
        return ResponseEntity.ok().build();
    }

    /**
     * 获取近期待办事项列表 (用于首页)
     */
    @GetMapping("/upcoming")
    public ResponseEntity<List<InteractionDTO>> getUpcomingFollowUps() {
        List<InteractionDTO> upcoming = interactionService.getUpcomingFollowUps(UserContext.getUserId());
        return ResponseEntity.ok(upcoming);
    }
}
