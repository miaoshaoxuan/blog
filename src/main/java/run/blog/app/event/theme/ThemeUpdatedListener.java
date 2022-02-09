package run.blog.app.event.theme;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import run.blog.app.service.ThemeService;
import run.blog.app.cache.StringCacheStore;
import run.blog.app.event.options.OptionUpdatedEvent;

/**
 * Theme updated listener.
 *
 * @author johnniang
 * @date 19-4-29
 */
@Component
public class ThemeUpdatedListener {

    private final StringCacheStore cacheStore;

    public ThemeUpdatedListener(StringCacheStore cacheStore) {
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
