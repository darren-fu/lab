package testYml;

import java.math.BigDecimal;

/**
 * version config
 * Created by darrenfu on 17-5-5.
 */
public interface VersionConfig extends Config {

    /**
     * Gets version.
     *
     * @return the version
     */
    BigDecimal getVersion();

    /**
     * Auto refresh boolean.
     *
     * @return the boolean
     */
    boolean autoRefresh();
}
