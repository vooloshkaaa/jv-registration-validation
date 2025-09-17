package core.basesyntax.service;

import core.basesyntax.dao.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login is already registered");
        }
        if (user.getLogin().length() <= 6) {
            throw new RegistrationException("Login must contain at least 6 characters");
        }
        if (user.getPassword().length() <= 6) {
            throw new RegistrationException("Password must contain at least 6 characters");
        }
        if (user.getAge() < 18) {
            throw new RegistrationException("User cannot be under 18 years old");
        }
        storageDao.add(user);
        return user;
    }
}
