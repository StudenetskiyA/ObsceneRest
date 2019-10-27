import com.dayre.obscenerest.Application;
import com.dayre.obscenerest.controller.AdminController;
import com.dayre.obscenerest.controller.SearchByIdController;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UtilsTests {

    @Test
    void splitTagsTest(){
        String test = "tag1, tag2,tag3, ghhgjhdgjhgmm";
        List<String> result = Arrays.asList("tag1","tag2","tag3","ghhgjhdgjhgmm");
        assertEquals(result, Application.getListFromString(test));
    }
}
