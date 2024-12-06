package cn.fuzhizhuang.types.constant;

/**
 * 正则常量定义
 *
 * @author Fu.zhizhuang
 */
public class RegexConstant {

    // 密码正则，密码必须包含大小写字母和数字，且长度在8-16之间
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,16}$";
    // 密码正则提示
    public static final String PASSWORD_REGEX_TIP = "密码必须包含大小写字母和数字，且长度在8-16之间";

    // 验证码正则
    public static final String VERIFY_CODE_REGEX = "^[0-9]{6}$";
    // 验证码正则提示
    public static final String VERIFY_CODE_REGEX_TIP = "验证码必须为6位数字";

    // 邮箱正则
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    // 邮箱正则提示
    public static final String EMAIL_REGEX_TIP = "请输入正确的邮箱地址";

}
