package cn.fuzhizhuang.trigger.http;

import cn.dev33.satoken.stp.StpUtil;
import cn.fuzhizhuang.cases.dto.EmailAuthDTO;
import cn.fuzhizhuang.cases.dto.PasswordAuthDTO;
import cn.fuzhizhuang.cases.dto.SendCaptchaDTO;
import cn.fuzhizhuang.cases.dto.WxAuthDTO;
import cn.fuzhizhuang.cases.service.AuthService;
import cn.fuzhizhuang.domain.user.model.entity.QrCodeEntity;
import cn.fuzhizhuang.types.base.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 *
 * @author Fu.zhizhuang
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "认证api", description = "认证api")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/password")
    @Operation(summary = "密码验证")
    public Result<String> passwordAuth(@RequestBody @Valid PasswordAuthDTO dto) {
        String token = authService.passwordAuth(dto);
        return Result.ok(token);
    }

    @PostMapping("/email")
    @Operation(summary = "邮箱验证")
    public Result<String> emailAuth(@RequestBody @Valid EmailAuthDTO dto) {
        String token = authService.emailAuth(dto);
        return Result.ok(token);
    }

    @GetMapping("/qrcode")
    @Operation(summary = "获取微信二维码")
    public Result<QrCodeEntity> getQrCode() {
        QrCodeEntity qrCode = authService.getQrCode();
        return Result.ok(qrCode);
    }

    @PostMapping("/wx")
    @Operation(summary = "微信登录")
    public Result<String> wxAuth(@RequestBody @Valid WxAuthDTO dto) {
        String token = authService.wxAuth(dto);
        return Result.ok(token);
    }

    @PostMapping("/sendCaptcha")
    @Operation(summary = "发送验证码")
    public Result<Void> sendCaptcha(@RequestBody @Valid SendCaptchaDTO dto) {
        authService.sendCaptcha(dto);
        return Result.ok();
    }

    @GetMapping("/logout")
    @Operation(summary = "退出登录")
    public Result<Void> logout() {
        String userId = StpUtil.getLoginIdAsString();
        StpUtil.logout(userId);
        return Result.ok();
    }
}
