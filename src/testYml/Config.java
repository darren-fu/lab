package testYml;

/**
 * 配置接口
 * Created by darrenfu on 17-5-8.
 */
public interface Config {
    /**
     * Auto store boolean.
     *
     * @return the boolean
     */
    boolean autoStore();

    /**
     * Gets config path.
     *
     * @return the config path
     */
    String getConfigPath();

    /**
     * Sets config path.
     *
     * @param path the path
     */
    void setConfigPath(String path);
}
