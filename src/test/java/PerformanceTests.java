import com.dayre.obscenerest.Application;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

//Перед запуском тестов на производительность нужно стартовать сервис /start
class PerformanceTests {

    private String restServiceUrl = "http://localhost:8080/";
    private HttpClient httpClient = new HttpClient();
    private byte[] buffer = new byte[1024 * 10];//Предполагаем, что ответ будет не больше 10 кб.
    private String result = null;
    private int length;

    @Test
    void ByFiveNonPopularQueryTest() throws IOException {
        long time = System.currentTimeMillis();

        for (int i=Application.popularListSize;i<1001+Application.popularListSize;i++) {
            GetMethod getMethod = new GetMethod(restServiceUrl);
            getMethod.setQueryString("?id="+i+","+(i+12)+","+(i+5)+","+(i+1)+","+(i+10));
            httpClient.executeMethod(getMethod);

            InputStream output = getMethod.getResponseBodyAsStream();
            length = output.read(buffer);
            result = new String(buffer);
            System.out.println(result.trim());
        }
        System.out.println("By 5 test time " + (System.currentTimeMillis()-time));
    }

    @Test
    void OneByOneNonPopularQueryTest() throws IOException {
        long time = System.currentTimeMillis();

        for (int i = Application.popularListSize; i<1001+Application.popularListSize; i++) {
            GetMethod getMethod = new GetMethod(restServiceUrl);
            getMethod.setQueryString("?id="+i);
            httpClient.executeMethod(getMethod);

            InputStream output = getMethod.getResponseBodyAsStream();
            length = output.read(buffer);
            result = new String(buffer);
            System.out.println(result.trim());
        }
        System.out.println("By 1 test time " + (System.currentTimeMillis()-time));
    }

    @Test
    void ByFivePopularQueryTest() throws IOException {
        long time = System.currentTimeMillis();

        for (int i=0;i<Application.popularListSize+1;i++) {
            GetMethod getMethod = new GetMethod(restServiceUrl);
            getMethod.setQueryString("?id="+i+","+(i+12)+","+(i+5)+","+(i+1)+","+(i+10));
            httpClient.executeMethod(getMethod);

            InputStream output = getMethod.getResponseBodyAsStream();
            length = output.read(buffer);
            result = new String(buffer);
            System.out.println(result.trim());
        }
        System.out.println("By 5 test time " + (System.currentTimeMillis()-time));
    }

    @Test
    void OneByOnePopularQueryTest() throws IOException {
        long time = System.currentTimeMillis();

        for (int i = 0; i<Application.popularListSize+1; i++) {
            GetMethod getMethod = new GetMethod(restServiceUrl);
            getMethod.setQueryString("?id="+i);
            httpClient.executeMethod(getMethod);

            InputStream output = getMethod.getResponseBodyAsStream();
            length = output.read(buffer);
            result = new String(buffer);
            System.out.println(result.trim());
        }
        System.out.println("By 1 test time " + (System.currentTimeMillis()-time));
    }


}
