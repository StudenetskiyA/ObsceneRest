import com.dayre.obscenerest.controller.AdminController;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReadAndWriteTests {
    private String restServiceUrl = "http://localhost:8080/";
    private HttpClient httpClient = new HttpClient();
    private byte[] buffer = new byte[1024 * 10];//Предполагаем, что ответ будет не больше 10 кб.
    private String result = null;
    private int length;

    @Test
    void StartTest()  {
       // AdminController.start();
    }

    //Без /start этот тест не запустить.
    @Test
    void ReadAndWriteSame() throws IOException {
        //Write
        GetMethod getMethod;
        PostMethod postMethod = new PostMethod(restServiceUrl);
        postMethod.setPath("/save");
        //Запишем другие данные, вдруг после предыдущего теста осталось.
        postMethod.setQueryString("?id=911&tags=test");
        httpClient.executeMethod(postMethod);

        postMethod.setQueryString("?id=911&tags=tag1,tag2");
        httpClient.executeMethod(postMethod);

        //Read
        getMethod = new GetMethod(restServiceUrl);
        getMethod.setQueryString("?id=911");
        httpClient.executeMethod(getMethod);

        InputStream output = getMethod.getResponseBodyAsStream();
        length = output.read(buffer);
        result = new String(buffer);
        assertEquals("[{\"id\":911,\"tags\":\"tag1,tag2\",\"popularity\":0}]", result.trim());
    }
}