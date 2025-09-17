package core.basesyntax.service;

import core.basesyntax.dao.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getId() == null || user.getAge() == null || user.getLogin() == null
                || user.getPassword() == null) {
            throw new NullPointerException("Any of fields cannot be empty");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login is already registered");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new RegistrationException("Login must contain at least 6 characters");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RegistrationException("Password must contain at least 6 characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User cannot be under 18 years old");
        }
        storageDao.add(user);
        return user;
    }
}
