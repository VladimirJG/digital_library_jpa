package org.spring.data_jpa.util;

import org.spring.data_jpa.models.Reader;
import org.spring.data_jpa.services.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ReaderValidator implements Validator {
    private final ReaderService readerService;

    @Autowired
    public ReaderValidator(ReaderService readerService) {
        this.readerService = readerService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Reader.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Reader reader = (Reader) target;

        if (readerService.findByName(reader.getName()).isPresent())
            errors.rejectValue("name", "", "Читатель с таким именем уже существует");
    }
}
