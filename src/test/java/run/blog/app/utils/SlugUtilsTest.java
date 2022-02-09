package run.blog.app.utils;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * @author johnniang
 * @date 3/20/19
 */
public class SlugUtilsTest {

    @Test
    public void makeSlugTest() {
        String slugResult = SlugUtils.slugify("Hello World");

        Assert.assertThat(slugResult, equalTo("hello-world"));
    }
}
