package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private User actual;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        actual = new User();
        actual.setId((long)1);
    }

    @Test
    void register_validData_Ok() {
        actual.setAge(20);
        actual.setLogin("abcdefg");
        actual.setPassword("abcdefg");
        assertEquals(registrationService.register(actual), actual);
    }

    @Test
    void register_ageUnder18_notOk() {
        actual.setAge(16);
        actual.setLogin("abcdeffg");
        actual.setPassword("abcdefg");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual));
    }

    @Test
    void register_loginAlreadyExistsInStorage_NotOk() {
        actual.setAge(20);
        actual.setLogin("abcdefgh");
        actual.setPassword("abcdefgh");
        registrationService.register(actual);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual));
    }

    @Test
    void register_loginHasUnder6Characters_NotOk() {
        actual.setAge(20);
        actual.setLogin("abc");
        actual.setPassword("1234567");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual));
    }

    @Test
    void register_passwordHasUnder6Characters_NotOk() {
        actual.setAge(20);
        actual.setLogin("1234567");
        actual.setPassword("abc");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual));
    }

}
