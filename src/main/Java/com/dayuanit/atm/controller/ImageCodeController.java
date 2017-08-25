package com.dayuanit.atm.controller;

import com.dayuanit.atm.util.PicCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

/**
 * Created by YOUNG on 2017/8/24.
 */

@Controller
@RequestMapping("/code")
public class ImageCodeController extends BaseController{

    @RequestMapping("/create")
    public void createImge(HttpServletRequest req, HttpServletResponse resp) {
        String code = PicCodeUtil.createCode(4);//生成4位随机码
        req.getSession().setAttribute("code", code);//每请求一次生成新的code
        BufferedImage bimg = PicCodeUtil.creatImage(code);//将生成的验证码转成图片
        OutputStream os = null;
        try {
            resp.setContentType("image/jpeg");//设置响应头文件，使浏览器知道文件类型
            resp.setHeader("Pragma", "no-cache");//不缓存
            resp.setHeader("Cache-Control", "no-cache");//不缓存
            resp.setDateHeader("Expires", 0);//使用服务器端控制AJAX页面缓存,三个配合使用

            os = resp.getOutputStream();
            ImageIO.write(bimg, "png", os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
