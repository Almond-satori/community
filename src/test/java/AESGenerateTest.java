import cn.hutool.core.bean.BeanUtil;
import com.community.utils.AESGenerator;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

public class AESGenerateTest {

    @Test
    public void testAES(){
        AESGenerator aesGenerator = new AESGenerator();
        String keyStr = aesGenerator.generateKey(65535);

        String cypherText = aesGenerator.generateCypherText("123456", keyStr);
        String decode = aesGenerator.decode(cypherText, keyStr);
        System.out.println(decode);
        System.out.println(keyStr.length());
    }
}
