package com.orane.opentelemetryspike

import android.app.Application
import io.opentelemetry.api.common.AttributeKey
import io.opentelemetry.api.common.Attributes
import io.opentelemetry.exporter.zipkin.ZipkinSpanExporter
import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.resources.Resource
import io.opentelemetry.sdk.trace.SdkTracerProvider
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor
import io.opentelemetry.sdk.trace.samplers.Sampler


class App:Application() {
    override fun onCreate() {
        super.onCreate()

        val zipkinExporter: ZipkinSpanExporter = ZipkinSpanExporter.builder()
            .setEndpoint("http://192.168.0.89:9411/api/v2/spans") // replace with your Zipkin endpoint
            .build()

        val tracerProvider = SdkTracerProvider.builder()
            .addSpanProcessor(SimpleSpanProcessor.create(zipkinExporter))
            .setSampler(Sampler.alwaysOn())
            .build()

        val sdkTracerProvider = SdkTracerProvider.builder()
            .addSpanProcessor(BatchSpanProcessor.builder(zipkinExporter).build())
            .setResource(Resource.create(Attributes.of(AttributeKey.stringKey("service.name"), "sample open telemetry app")))
            .setSampler(Sampler.alwaysOn())
            .build()

        OpenTelemetrySdk.builder()
            .setTracerProvider(sdkTracerProvider)
            .buildAndRegisterGlobal()

    }

}