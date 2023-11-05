package de.hatoka.bubbles.bubble.capi;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import de.hatoka.bubbles.bubble.internal.business.BubbleBORepositoryImpl;
import de.hatoka.bubbles.bubble.internal.persistence.BubbleDao;
import de.hatoka.bubbles.bubble.internal.persistence.BubblePO;
import de.hatoka.bubbles.bubble.internal.remote.BubbleController;

@Configuration
@EntityScan(basePackageClasses = { BubblePO.class })
@EnableJpaRepositories(basePackageClasses = { BubbleDao.class })
@ComponentScan(basePackageClasses = { BubbleBORepositoryImpl.class, BubbleController.class })
public class BubbleConfiguration
{
    /**
     * Allow matrix parameter
     * @return adapted http fire wall
     */
    @Bean
    public HttpFirewall getHttpFirewall() {
        StrictHttpFirewall strictHttpFirewall = new StrictHttpFirewall();
        strictHttpFirewall.setAllowSemicolon(true);
        return strictHttpFirewall;
    }
}
