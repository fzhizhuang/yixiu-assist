package cn.fuzhizhuang.types.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

/**
 * xml工具类
 *
 * @author Fu.zhizhuang
 */
public class XmlUtil {

    /**
     * xml转成bean泛型方法
     */
    @SuppressWarnings("unchecked")
    public static <T> T xmlToBean(String resultXml, Class<?> clazz) {
        // XStream对象设置默认安全防护，同时设置允许的类
        XStream stream = new XStream(new DomDriver());
        stream.addPermission(AnyTypePermission.ANY);
        stream.allowTypes(new Class[]{clazz});

        stream.processAnnotations(new Class[]{clazz});
        stream.setMode(XStream.NO_REFERENCES);
        stream.alias("xml", clazz);

        return (T) stream.fromXML(resultXml);
    }

}
