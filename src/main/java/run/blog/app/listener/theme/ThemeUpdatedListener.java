package run.blog.app.listener.theme;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import run.blog.app.cache.AbstractStringCacheStore;
import run.blog.app.event.options.OptionUpdatedEvent;
import run.blog.app.event.theme.ThemeUpdatedEvent;
import run.blog.app.service.ThemeService;

/**
 * Theme updated listener.
 *
 * @author johnniang
 * @date 19-4-29
 */
@Component
public class ThemeUpdatedListener {

    private final AbstractStringCacheStore cacheStore;

    public ThemeUpdatedListener(AbstractStringCacheStore cacheStore) {
        this.cacheStore = cacheStore;
    }

    @EventListener
    public void onApplicationEvent(ThemeUpdatedEvent event) {
        cacheStore.delete(ThemeService.THEMES_CACHE_KEY);
    }

    @EventListener
    public void onOptionUpdatedEvent(OptionUpdatedEvent optionUpdatedEvent) {
        cacheStore.delete(ThemeService.THEMES_CACHE_KEY);
    }
}
