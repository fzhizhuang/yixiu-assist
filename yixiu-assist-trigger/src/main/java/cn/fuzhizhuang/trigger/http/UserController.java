package cn.fuzhizhuang.trigger.http;

import cn.dev33.satoken.stp.StpUtil;
import cn.fuzhizhuang.cases.dto.ModifyUserNameDTO;
import cn.fuzhizhuang.cases.dto.UserInfoDTO;
import cn.fuzhizhuang.cases.service.UserService;
import cn.fuzhizhuang.types.base.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 *
 * @author Fu.zhizhuang
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "用户api", description = "用户api")
public class UserController {

    private final UserService userService;

    @GetMapping("/info")
    @Operation(summary = "获取用户信息", description = "获取用户信息")
    public Result<UserInfoDTO> getUserInfo() {
        // 获取用户ID
        String userId = StpUtil.getLoginIdAsString();
        // 获取用户信息
        UserInfoDTO userInfo = userService.getUserInfo(userId);
        return Result.ok(userInfo);
    }

    @PostMapping("/modifyUsername")
    @Operation(summary = "修改用户昵称", description = "修改用户昵称")
    public Result<Void> modifyUsername(@RequestBody @Valid ModifyUserNameDTO dto) {
        // 获取用户ID
        String uid = StpUtil.getLoginIdAsString();
        // 修改用户信息
        userService.modifyUsername(uid, dto.getUsername());
        return Result.ok();
    }
}
