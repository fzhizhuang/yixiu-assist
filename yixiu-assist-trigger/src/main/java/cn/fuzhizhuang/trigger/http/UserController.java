package cn.fuzhizhuang.trigger.http;

import cn.dev33.satoken.stp.StpUtil;
import cn.fuzhizhuang.cases.dto.ModifyEmailDTO;
import cn.fuzhizhuang.cases.dto.ModifyUserNameDTO;
import cn.fuzhizhuang.cases.dto.SetPasswordDTO;
import cn.fuzhizhuang.cases.dto.UserInfoDTO;
import cn.fuzhizhuang.cases.service.UserService;
import cn.fuzhizhuang.types.base.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/uploadAvatar")
    @Operation(summary = "上传用户头像", description = "上传用户头像")
    public Result<String> uploadAvatar(@RequestParam("avatar") MultipartFile avatar) {
        // 获取用户ID
        String uid = StpUtil.getLoginIdAsString();
        // 上传文件
        String url = userService.uploadAvatar(uid, avatar);
        return Result.ok(url);
    }

    @PostMapping("/setPassword")
    @Operation(summary = "设置密码", description = "设置密码")
    public Result<Void> setPassword(@RequestBody @Valid SetPasswordDTO dto) {
        // 获取用户ID
        String uid = StpUtil.getLoginIdAsString();
        // 设置密码
        userService.setPassword(uid, dto);
        return Result.ok();
    }

    @PostMapping("/modifyEmail")
    @Operation(summary = "修改邮箱", description = "修改邮箱")
    public Result<Void> modifyEmail(@RequestBody @Valid ModifyEmailDTO dto) {
        // 获取用户ID
        String uid = StpUtil.getLoginIdAsString();
        // 修改邮箱
        userService.modifyEmail(uid, dto);
        return Result.ok();
    }

}
