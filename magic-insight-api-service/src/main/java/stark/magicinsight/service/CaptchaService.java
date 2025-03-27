package stark.magicinsight.service;

import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;
import stark.dataworks.basic.data.redis.RedisQuickOperation;
import stark.dataworks.boot.autoconfig.web.LogArgumentsAndResponse;
import stark.dataworks.boot.web.ServiceResponse;
import stark.magicinsight.dto.results.CaptchaResponse;

import javax.imageio.ImageIO;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@LogArgumentsAndResponse
public class CaptchaService
{
    @Autowired
    private Producer captchaProducer;

    @Autowired
    private RedisQuickOperation redisQuickOperation;

    /*
    Producer的实现体？
    response.setContentType方法 设置HTTP响应的内容类型为image/jpeg，表示返回的内容是一个JPEG格式的图片
    字符串captchaBytes的组成：将输出流中的字节数组转换为Base64编码的字符串，并添加data:image/jpeg;base64,前缀，以便可以直接在HTML中作为图片源使用。
    redisQuickOperation.set方法 将验证码文本存储到Redis中，键为验证码ID，值为验证码文本，并设置过期时间为5分钟
     */

    public ServiceResponse<CaptchaResponse> generateCaptcha(HttpServletResponse response) throws IOException
    {
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        String captchaText = captchaProducer.createText();
        BufferedImage captchaImage = captchaProducer.createImage(captchaText);
        FastByteArrayOutputStream outputStream = new FastByteArrayOutputStream();
        ImageIO.write(captchaImage, "jpg", outputStream);
        String captchaBytes = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(outputStream.toByteArray());

        String captchaId = "Captcha-" + UUID.randomUUID().toString();
        redisQuickOperation.set(captchaId, captchaText, 5, TimeUnit.MINUTES);

        log.info("Captcha text = {}", captchaText);

        CaptchaResponse captchaResponse = new CaptchaResponse(captchaId, captchaBytes);
        return ServiceResponse.buildSuccessResponse(captchaResponse);
    }
}
