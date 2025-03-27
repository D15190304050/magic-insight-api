package stark.magicinsight.service.beans;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfiguration
{
    /*
    这段代码配置了一个Kaptcha验证码生成器，设置了验证码图片的宽度、高度、字符来源和长度等属性，
    并将其注册为一个Spring Bean。这样，在应用程序的其他部分可以通过注入这个Bean来生成验证码图片。
    Properties对象，用于设置Kaptcha生成验证码时的各种属性。
    Config是Kaptcha库中的一个类，用于存储验证码生成的配置。
     */
    @Bean
    public Producer kaptcha()
    {
        Properties properties = new Properties();
        properties.setProperty("kaptcha.image.width", "150");
        properties.setProperty("kaptcha.image.height", "50");
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
