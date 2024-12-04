package cn.fuzhizhuang.cases.service;

import cn.fuzhizhuang.cases.dto.UserInfoDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户服务
 *
 * @author Fu.zhizhuang
 */
public interface UserService {

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserInfoDTO getUserInfo(String userId);

    /**
     * 修改用户昵称
     *
     * @param username 新昵称
     */
    void modifyUsername(String uid, String username);

    /**
     * 上传用户头像
     *
     * @param avatar 头像文件
     * @return 头像地址
     */
    String uploadAvatar(String uid, MultipartFile avatar);
}
