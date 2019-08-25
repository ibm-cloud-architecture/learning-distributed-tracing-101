package com.example.demo;

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
		ReporterConfiguration reporterConfig = new ReporterConfiguration().withLogSpans(true);
		return new Configuration("service-a").withSampler(samplerConfig).withReporter(reporterConfig).getTracer();
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
