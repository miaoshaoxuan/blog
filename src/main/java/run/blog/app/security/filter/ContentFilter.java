package run.blog.app.security.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import run.blog.app.cache.AbstractStringCacheStore;
import run.blog.app.config.properties.HaloProperties;
import run.blog.app.security.handler.ContentAuthenticationFailureHandler;
import run.blog.app.security.service.OneTimeTokenService;
import run.blog.app.service.OptionService;
import run.blog.app.utils.HaloUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Content filter
 *
 * @author johnniang
 * @date 19-5-6
 */
@Component
@Order(-1)
public class ContentFilter extends AbstractAuthenticationFilter {

    public ContentFilter(HaloProperties haloProperties,
            OptionService optionService,
            AbstractStringCacheStore cacheStore,
            OneTimeTokenService oneTimeTokenService) {
        super(haloProperties, optionService, cacheStore, oneTimeTokenService);

        addUrlPatterns("/**");

        String adminPattern = HaloUtils.ensureBoth(haloProperties.getAdminPath(), "/") + "**";
        addExcludeUrlPatterns(
                adminPattern,
                "/api/**",
                "/install",
                "/version",
                "/js/**",
                "/css/**");

        // set failure handler
        setFailureHandler(new ContentAuthenticationFailureHandler());
    }

    @Override
    protected String getTokenFromRequest(HttpServletRequest request) {
        return null;
    }

    @Override
    protected void doAuthenticate(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Do nothing
        filterChain.doFilter(request, response);
    }
}
