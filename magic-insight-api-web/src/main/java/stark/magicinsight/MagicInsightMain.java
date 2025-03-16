package stark.magicinsight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"stark.magicinsight", "stark.dataworks.boot.autoconfig"})
@EnableTransactionManagement
public class MagicInsightMain
{
    public static void main(String[] args)
    {
        SpringApplication.run(MagicInsightMain.class);
    }
}
