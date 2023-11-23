package com.orane.opentelemetryspike

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.orane.opentelemetryspike.ui.theme.OpentelemetrySpikeTheme
import io.opentelemetry.api.trace.Tracer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import io.opentelemetry.api.GlobalOpenTelemetry
import io.opentelemetry.api.common.AttributeKey
import io.opentelemetry.api.common.Attributes
import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.StatusCode

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        TelemetryInstrumentation.startTelemetry()
        setContent {
            OpentelemetrySpikeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen()
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OpentelemetrySpikeTheme {
        LoginScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val tracer: Tracer = GlobalOpenTelemetry
        .getTracer("login-tracer")


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Button(
            onClick = {
                val span: Span = tracer.spanBuilder("performLogin").startSpan()
                span.setStatus(StatusCode.UNSET)

                try {
                    span.setAttribute("username", username)
                    span.setAttribute("Password", password)
                    span.setStatus(StatusCode.OK)
                    span.updateName("Login attempt successful")
                } catch (e: Exception) {
                    span.recordException(e)
                } finally {
                    span.end()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Login")
        }
        Button(
            onClick = {
                val span: Span = tracer.spanBuilder("performLogin").startSpan()
                span.setStatus(StatusCode.UNSET)

                try {
                    span.setAttribute("username", username)
                    span.setAttribute("Password", password)
                    span.setStatus(StatusCode.ERROR)
                    span.updateName("Login attempt Fail")
                    span.addEvent(
                        "login Event",
                        Attributes.of(
                            AttributeKey.stringKey("username"),
                            username,
                            AttributeKey.stringKey("Password"),
                            password
                        )
                    )
                    span.recordException(Exception("Login Fail"))
                } catch (e: Exception) {
                    span.recordException(e)
                } finally {
                    span.end()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Login Fail")
        }
    }
}