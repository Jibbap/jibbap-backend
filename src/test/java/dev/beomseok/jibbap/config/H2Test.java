package dev.beomseok.jibbap.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

//@ContextConfiguration(classes = {TestDatasourceConfig.class})
@ActiveProfiles("test")
@SpringBootTest
public class H2Test {
}
