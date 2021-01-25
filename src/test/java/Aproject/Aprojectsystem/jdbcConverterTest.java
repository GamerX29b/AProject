package Aproject.Aprojectsystem;

import Aproject.Aprojectsystem.jaxbComponent.Client;
import Aproject.Aprojectsystem.jaxbComponent.JaxbConverterImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Самый обычный спринговый тест.
 */

@SpringBootTest(properties = "Ivan=Ivan")
@ActiveProfiles("test")
class jdbcConverterTest {
        @Autowired
       JaxbConverterImpl jaxbConverter;

        @Autowired
        jdbcConverterTest(JaxbConverterImpl jaxbConverter){
            this.jaxbConverter = jaxbConverter;
        }

    /**
     * Проверяем работу джаксби конвертера. Туда и обратно. Он должен создать XML с именем клиента Ivan
     * Потом превратить его обратно в объект Client получить из него имя и там тоже должно быть имя Ivan
     * Это означает что не сбилась кодировка, данные не просрались, при желании такими тестами можно накрыть все
     * методы.
     */
        @Test
        void testJaxbConverter(){
            Client client = new Client();
            client.setClientName("Ivan");
            String clientXml = jaxbConverter.clientToXml(client);
            Client xmltoClient = jaxbConverter.xmlToClient(clientXml);
            assertEquals(xmltoClient.getClientName(), ("Ivan")); //Специальный метод который сравнивает данные.
    }
}
