package com.zw.rule.web.shiro.credentials;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.ByteSource;

import java.util.concurrent.atomic.AtomicInteger;


public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
    private Cache<String, AtomicInteger> passwordRetryCache;

    private static final String DEFAULT_CACHE_KEY = "passwordRetry_";

    private String defaultCacheKey = DEFAULT_CACHE_KEY;

    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token,
                                      AuthenticationInfo info) {
        String username = (String) token.getPrincipal();

        // retry count + 1  不能用 username 作为key, username 为key， 用来存储用户授权信息了
        AtomicInteger retryCount = passwordRetryCache.get(defaultCacheKey + username);

        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(defaultCacheKey + username, retryCount);
        }

        if (retryCount.incrementAndGet() > 5) {
            // if retry count > 5 throw
            throw new ExcessiveAttemptsException();
        }

        boolean matches = super.doCredentialsMatch(token, info);

        if (matches) {
            // clear retry count
            passwordRetryCache.remove(defaultCacheKey + username);
        }

        return matches;
    }

    /**
     * build user password
     */
    public String buildCredentials(String userName, String password, String credentialsSalt) {
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userName, password, ByteSource.Util.bytes(userName + credentialsSalt), userName);
        AuthenticationToken token = new UsernamePasswordToken(userName, password);
        return super.hashProvidedCredentials(token, authenticationInfo).toString();
    }

    public void setDefaultCacheKey(String defaultCacheKey) {
        this.defaultCacheKey = defaultCacheKey;
    }
}
