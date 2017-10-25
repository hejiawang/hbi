package com.wang.hbi.admin.controller.login;

import com.wang.hbi.admin.controller.BaseController;
import com.wang.hbi.core.utils.web.KaptchaDefine;
import com.wang.hbi.admin.utils.HbiAdminUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * 验证码 Controller
 *
 * @Author HeJiawang
 * @Date 2017/10/19 22:00
 */
@Controller
@RequestMapping("/")
public class CaptchaController extends BaseController {

    /**
     * 验证码随机文字
     */
    private final String TEXT_WORD = "absdegkmnopwx123456789";

    /**
     * 生成验证码
     *
     * @param request
     * @param response
     * @return
     *
     * @throws Exception 应用：在登录、注册、找回密码时，显示验证码时调用。
     */
    @RequestMapping(value = "getKaptcha.png", method = RequestMethod.GET )
    public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) {
        setResponseHeaders(response);
        try {
            String sessionId = HbiAdminUserUtil.getOrCreateSessionId(request, response);

            String captchaText = getNextWord(TEXT_WORD, 4);

            BufferedImage bufferedImage = getKaptcha(captchaText);
            ImageIO.write(bufferedImage, "png", response.getOutputStream());

            logger.debug("生成验证码:{}, sessionId:{}", captchaText, sessionId);
            KaptchaDefine.setTextToMemcached(sessionId, captchaText);

        } catch (Exception e) {
            logger.error("生成验证码错误：", e);
        } finally {
            try {
                response.getOutputStream().flush();
                response.getOutputStream().close();
            } catch (IOException e) {
                logger.error("close ByteArrayInputStream error", e);
                ;
            }
        }
    }

    /**
     * set response headers
     * @param response
     */
    private void setResponseHeaders(HttpServletResponse response) {
        response.setContentType("image/png");

        response.setHeader("Cache-Control", "no-store, no-cache");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");

        long time = System.currentTimeMillis();
        response.setDateHeader("Last-Modified", time);
        response.setDateHeader("Date", time);
        response.setDateHeader("Expires", 0L);
    }

    /**
     * 取得Kapthca的验证码
     * @param text
     * @return
     */
    private BufferedImage getKaptcha(String text) {

        KaptchaDefine kaptchaDefine = new KaptchaDefine();
        kaptchaDefine.setWidth(70);
        kaptchaDefine.setHeight(32);
        kaptchaDefine.setFontSize(18);
        kaptchaDefine.setNoiseLine(0);
        kaptchaDefine.setNoisePoint(0);

        Font[] fonts = {new Font("华文细黑", Font.BOLD, 18)};
        kaptchaDefine.setFonts(fonts);

        return kaptchaDefine.createImage(text);
    }

    /**
     * 生成随机文字
     * @param characters
     * @param len
     * @return
     */
    private String getNextWord(String characters, int len) {
        Random rnd = new Random();
        StringBuffer sb  = new StringBuffer();
        // 如果有最小长度
        // int l = minLength + (maxLength > minLength ? rnd.nextInt(maxLength - minLength) : 0);
        for (int i = 0; i < len; i++) {
            int j = rnd.nextInt(characters.length());
            sb.append(characters.charAt(j));
        }
        return sb.toString();
    }
}
