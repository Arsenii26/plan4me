package ca.arsenii.plan4me.web.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ca.arsenii.plan4me.HasEmail;
import ca.arsenii.plan4me.model.User;
import ca.arsenii.plan4me.repository.UserRepository;
import ca.arsenii.plan4me.web.ExceptionInfoHandler;


@Component
public class UniqueMailValidator implements org.springframework.validation.Validator {

    @Autowired
    private UserRepository repository;

    @Override
    public boolean supports(Class<?> clazz) {
        return HasEmail.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        HasEmail user = ((HasEmail) target);
        User dbUser = repository.getByEmail(user.getEmail().toLowerCase());
        if (dbUser != null && !dbUser.getId().equals(user.getId())) {
            errors.rejectValue("email", ExceptionInfoHandler.EXCEPTION_DUPLICATE_EMAIL);
        }
    }
}
