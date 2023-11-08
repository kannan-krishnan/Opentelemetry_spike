package com.orane.opentelemetryspike

import android.app.Application
import io.opentelemetry.api.GlobalOpenTelemetry
import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator
import io.opentelemetry.context.Scope
import io.opentelemetry.context.propagation.ContextPropagators
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter
import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.trace.SdkTracerProvider
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor


class App:Application() {
    override fun onCreate() {
        super.onCreate()
        TelemetryConfig.initializeOpenTelemetry()

        val exporter = OtlpGrpcSpanExporter.builder().build()
        val processor = BatchSpanProcessor.builder(exporter).build()

        OpenTelemetrySdk.builder()
            .setTracerProvider(SdkTracerProvider.builder().addSpanProcessor(processor).build())
            .setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance()))
            .buildAndRegisterGlobal()

    }


    fun doSomething() {
        val tracer: Tracer = GlobalOpenTelemetry.getTracerProvider().get("com.example.myapp")

        val span: Span = tracer.spanBuilder("my span").startSpan()
        try {
            val scope: Scope = span.makeCurrent()
            // your code here
        } finally {
            span.end()
        }
    }

}