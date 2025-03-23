package stark.magicinsight.service.startup;

import com.volcengine.ark.runtime.service.ArkService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import stark.magicinsight.service.doubao.DoubaoMultiRoundChatSessionFactory;

/**
 * Initializer of the application, run only once, just after startup of the application.
 */
@Component
public class StartupInitializer implements ApplicationContextAware, ApplicationListener<WebServerInitializedEvent>
{
    @Value("${doubao.api-key-evn-variable-name}")
    private String doubaoApiKeyEnvironmentVariableName;

    @Autowired
    private DoubaoMultiRoundChatSessionFactory doubaoMultiRoundChatSessionFactory;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event)
    {
        String doubaoApiKey = System.getenv(doubaoApiKeyEnvironmentVariableName);
        doubaoMultiRoundChatSessionFactory.setDoubaoApiKey(doubaoApiKey);
        doubaoMultiRoundChatSessionFactory.setArkService(ArkService.builder().apiKey(doubaoApiKey).build());

//        try
//        {
//            // Initialize doubao configurations.
//            String doubaoApiKey = System.getenv(doubaoApiKeyEnvironmentVariableName);
//            doubaoMultiRoundChatSessionFactory.setDoubaoApiKey(doubaoApiKey);
//            doubaoMultiRoundChatSessionFactory.setArkService(ArkService.builder().apiKey(doubaoApiKey).build());
//
//            // Initialize video uploading options.
//            videoUploadingOptionHolder.setVideoUploadingOptions();
//        }
//        catch (IllegalAccessException e)
//        {
//            throw new RuntimeException(e);
//        }
    }
}
