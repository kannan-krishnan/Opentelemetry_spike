package com.orane.opentelemetryspike

import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter
import io.opentelemetry.api.GlobalOpenTelemetry
import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.trace.SdkTracerProvider
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor
import io.opentelemetry.sdk.trace.samplers.Sampler

object TelemetryConfig {

    fun initializeOpenTelemetry() {
        // Create an exporter
        val exporter = OtlpGrpcSpanExporter.builder()
            .setEndpoint("your-otlp-exporter-endpoint")
            .build()

        // Create a tracer provider with the exporter
        val tracerProvider = SdkTracerProvider.builder()
            .addSpanProcessor(SimpleSpanProcessor.create(exporter))
            .setSampler(Sampler.alwaysOn())
            .build()

        // Set the tracer provider for your OpenTelemetry instance
        OpenTelemetrySdk.builder()
            .setTracerProvider(tracerProvider)
            .buildAndRegisterGlobal()
    }
}
