package de.hatoka.bubbles.human.capi;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import de.hatoka.bubbles.human.internal.business.HumanBORepositoryImpl;
import de.hatoka.bubbles.human.internal.persistence.HumanDao;
import de.hatoka.bubbles.human.internal.persistence.HumanPO;
import de.hatoka.bubbles.human.internal.remote.HumanController;

@Configuration
@EntityScan(basePackageClasses = { HumanPO.class })
@EnableJpaRepositories(basePackageClasses = { HumanDao.class })
@ComponentScan(basePackageClasses = { HumanBORepositoryImpl.class, HumanController.class })
public class HumanConfiguration
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
