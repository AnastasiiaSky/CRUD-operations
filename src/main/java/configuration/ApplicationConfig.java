/**
 * @autor Анастасия Гапонова
 * @version 1.0
 */

package configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import repositories.implementation.LibraryOperationsImpl;

/**
 * Класс конфигурации приложения.
 *
 */
@Configuration
@PropertySource("classpath:db.properties")
public class ApplicationConfig {
    @Value("${db.driver.name}")
    private String driverName;
    @Value("${db.url}")
    private String url;
    @Value("${db.user}")
    private String username;
    @Value("${db.password}")
    private String password;

    /**
     * Конструктор по умолчанию.
     */
    public ApplicationConfig() {
    }

    /**
     * Создание бина JdbcTemplate с параметрами:
     * Имя драйвера базы данных
     * URL базы данных
     * Имя пользователя для доступа к базе данных
     * Пароль для доступа к базе данных
     *
     * @return новый экземпляр JdbcTemplate.
     */
    @Bean(name = "jdbcTemplate")
    public JdbcTemplate createJdbcTemplate() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return new JdbcTemplate(dataSource);
    }


    /**
     * Создание бина LibraryOperationsImpl.
     *
     * @param jdbcTemplate бин JdbcTemplate
     * @see ApplicationConfig#createJdbcTemplate()
     * @return новый экземпляр LibraryOperationsImpl.
     */
    @Bean
    public LibraryOperationsImpl libraryOperationsImpl(JdbcTemplate jdbcTemplate) {
        return new LibraryOperationsImpl(jdbcTemplate);
    }
}
