package io.nology.todo_backend.config;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.nology.todo_backend.auth.RegisterDTO;
import io.nology.todo_backend.todo.CreateTodoDTO;
import io.nology.todo_backend.todo.Todo;
import io.nology.todo_backend.user.CreateUserDTO;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper(PasswordEncoder passwordEncoder) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.typeMap(String.class, String.class).setConverter(new StringTrimConverter());
        mapper.typeMap(RegisterDTO.class, CreateUserDTO.class).addMappings(
                mapping -> mapping.using(new PasswordEncodeConverter(passwordEncoder)).map(RegisterDTO::getPassword,
                        CreateUserDTO::setPassword));
        mapper.typeMap(CreateTodoDTO.class, Todo.class)
                .addMappings(m -> m.using(new DateConvertor()).map(CreateTodoDTO::getDueDate, Todo::setDueDate));
        return mapper;
    }

    private class DateConvertor implements Converter<String, Date> {

        @Override
        public Date convert(MappingContext<String, Date> context) {
            String dateString = context.getSource();
            if (dateString == null) {
                return null;
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return formatter.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }

    }

    private class StringTrimConverter implements Converter<String, String> {

        @Override
        public String convert(MappingContext<String, String> context) {
            if (context.getSource() == null) {
                return null;
            }
            return context.getSource().trim();
        }

    }

    private static class PasswordEncodeConverter implements Converter<String, String> {
        private final PasswordEncoder passwordEncoder;

        public PasswordEncodeConverter(PasswordEncoder passwordEncoder) {
            this.passwordEncoder = passwordEncoder;
        }

        @Override
        public String convert(MappingContext<String, String> context) {
            if (context.getSource() == null) {
                return null;
            }
            return passwordEncoder.encode(context.getSource().trim());
        }
    }

}
