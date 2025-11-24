package opl.employee.config;

import java.util.UUID;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

	@Bean
	ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		// String to UUID
		Converter<String, UUID> toUUID = ctx -> ctx.getSource() == null ? null : UUID.fromString(ctx.getSource());
		modelMapper.addConverter(toUUID, String.class, UUID.class);

		// UUID to String
		Converter<UUID, String> toString = ctx -> ctx.getSource() == null ? null : ctx.getSource().toString();
		modelMapper.addConverter(toString, UUID.class, String.class);
		return modelMapper;
	}

}