package run.blog.app.security.service;

import org.springframework.lang.NonNull;

import java.util.Optional;

/**
 * One-time-token service interface.
 *
 * @author johnniang
 */
public interface OneTimeTokenService {

    /**
     * Get the corresponding uri.
     *
     * @param oneTimeToken one-time token must not be null
     * @return the corresponding uri
     */
    @NonNull
    Optional<String> get(@NonNull String oneTimeToken);

    /**
     * Create one time token.
     *
     * @param uri request uri.
     * @return one time token.
     */
    @NonNull
    String create(@NonNull String uri);

    /**
     * Revoke one time token.
     *
     * @param oneTimeToken one time token must not be null
     */
    void revoke(@NonNull String oneTimeToken);
}
