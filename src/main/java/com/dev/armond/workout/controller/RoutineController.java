package com.dev.armond.workout.controller;

import com.dev.armond.common.reponse.ApiResponse;
import com.dev.armond.member.dto.CustomMemberDetails;
import com.dev.armond.workout.dto.common.RoutineDto;
import com.dev.armond.workout.dto.workout.SaveRoutineDto;
import com.dev.armond.workout.service.RoutineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/routines")
@RequiredArgsConstructor
@Slf4j
public class RoutineController {
    private final RoutineService routineService;

    // 루틴 저장
    @PostMapping
    public ResponseEntity<ApiResponse<SaveRoutineDto>> createRoutine(@RequestBody SaveRoutineDto routineDto) {
        SaveRoutineDto createdRoutine = routineService.createRoutine(routineDto);
        return ResponseEntity
                .created(URI.create("/routines/" + createdRoutine.id()))
                .body(ApiResponse.success("Routine created successful.", createdRoutine));
    }

    // 루틴 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SaveRoutineDto>> getRoutine(@PathVariable Long id) {
        routineService.getRoutine(id);
        return ResponseEntity.ok(ApiResponse.success("Routine 목록 조회", null));
    }

    // 루틴 전체 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<RoutineDto>>> getRoutines(@AuthenticationPrincipal CustomMemberDetails member) {
        routineService.getRoutines(member.getMemberId());
        return ResponseEntity.ok(ApiResponse.success("Routine 목록 조회", null));
    }

    // 루틴 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SaveRoutineDto>> updateRoutine(@PathVariable Long id, @RequestBody SaveRoutineDto saveRoutineDto) {
        SaveRoutineDto updatedRoutine = routineService.updateRoutine(id, saveRoutineDto);
        return ResponseEntity.ok(ApiResponse.success("Routine updated successfully", updatedRoutine));
    }

    // 루틴 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteRoutine(@PathVariable Long id) {
        routineService.deleteRoutine(id);
        return ResponseEntity.ok(ApiResponse.success("Routine deleted successfully", null));
    }
}
