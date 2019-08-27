package com.example.serviceb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.jaegertracing.Configuration;
import io.jaegertracing.Configuration.ReporterConfiguration;
import io.jaegertracing.Configuration.SamplerConfiguration;

@SpringBootApplication
public class DemoApplication {

	@Bean
	public io.opentracing.Tracer initTracer() {
		SamplerConfiguration samplerConfig = new SamplerConfiguration().withType("const").withParam(1);
		ReporterConfiguration reporterConfig = ReporterConfiguration.fromEnv().withLogSpans(true);
		return Configuration.fromEnv("service-b").withSampler(samplerConfig).withReporter(reporterConfig).getTracer();
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
