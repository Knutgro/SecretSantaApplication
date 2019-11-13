package com.kg.secretsanta.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.kg.secretsanta.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.kg.secretsanta.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.kg.secretsanta.domain.User.class.getName());
            createCache(cm, com.kg.secretsanta.domain.Authority.class.getName());
            createCache(cm, com.kg.secretsanta.domain.User.class.getName() + ".authorities");
            createCache(cm, com.kg.secretsanta.domain.Wish.class.getName());
            createCache(cm, com.kg.secretsanta.domain.Member.class.getName());
            createCache(cm, com.kg.secretsanta.domain.Member.class.getName() + ".wishes");
            createCache(cm, com.kg.secretsanta.domain.Member.class.getName() + ".owners");
            createCache(cm, com.kg.secretsanta.domain.Member.class.getName() + ".gifters");
            createCache(cm, com.kg.secretsanta.domain.Member.class.getName() + ".receivers");
            createCache(cm, com.kg.secretsanta.domain.Member.class.getName() + ".events");
            createCache(cm, com.kg.secretsanta.domain.Event.class.getName());
            createCache(cm, com.kg.secretsanta.domain.Event.class.getName() + ".gifts");
            createCache(cm, com.kg.secretsanta.domain.Event.class.getName() + ".wishes");
            createCache(cm, com.kg.secretsanta.domain.Event.class.getName() + ".members");
            createCache(cm, com.kg.secretsanta.domain.Gift.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
