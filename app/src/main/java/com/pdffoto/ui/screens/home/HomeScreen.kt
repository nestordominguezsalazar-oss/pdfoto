package com.pdffoto.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdffoto.R
import com.pdffoto.ui.theme.PDFotoTheme
import com.pdffoto.ui.util.openUrl
import com.pdffoto.ui.util.sendFeedback

/**
 * Pantalla de inicio: crear un PDF, escanear, ver el historial, la política de privacidad o
 * enviar comentarios.
 */
@Composable
fun HomeScreen(
    onCreatePdf: () -> Unit,
    onOpenCamera: () -> Unit,
    onOpenHistory: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val privacyUrl = stringResource(R.string.privacy_policy_url)
    val supportEmail = stringResource(R.string.support_email)
    val feedbackSubject = stringResource(R.string.feedback_subject)

    HomeContent(
        onCreatePdf = {
            viewModel.startNewCreation()
            onCreatePdf()
        },
        onOpenCamera = {
            viewModel.startNewCreation()
            onOpenCamera()
        },
        onOpenHistory = onOpenHistory,
        onOpenPrivacy = { openUrl(context, privacyUrl) },
        onSendFeedback = {
            sendFeedback(
                context = context,
                email = supportEmail,
                subject = feedbackSubject,
                body = viewModel.feedbackDiagnostics(),
                attachment = viewModel.feedbackLogFile(),
            )
        },
        modifier = modifier,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun HomeContent(
    onCreatePdf: () -> Unit,
    onOpenCamera: () -> Unit,
    onOpenHistory: () -> Unit,
    onOpenPrivacy: () -> Unit,
    onSendFeedback: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineLarge,
            )
            Text(
                text = stringResource(R.string.home_tagline),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )

            FlowRow(
                modifier = Modifier.padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                FeatureBadge(stringResource(R.string.home_badge_no_ads))
                FeatureBadge(stringResource(R.string.home_badge_no_watermark))
                FeatureBadge(stringResource(R.string.home_badge_offline))
                FeatureBadge(stringResource(R.string.home_badge_private))
            }

            Button(
                onClick = onCreatePdf,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
            ) {
                Text(text = stringResource(R.string.home_create_pdf))
            }

            OutlinedButton(
                onClick = onOpenCamera,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = stringResource(R.string.home_scan_camera))
            }

            TextButton(onClick = onOpenHistory) {
                Text(text = stringResource(R.string.home_history))
            }

            TextButton(onClick = onOpenPrivacy) {
                Text(text = stringResource(R.string.home_privacy))
            }

            TextButton(onClick = onSendFeedback) {
                Text(text = stringResource(R.string.home_feedback))
            }
        }
    }
}

@Composable
private fun FeatureBadge(text: String) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeContentPreview() {
    PDFotoTheme {
        HomeContent(
            onCreatePdf = {},
            onOpenCamera = {},
            onOpenHistory = {},
            onOpenPrivacy = {},
            onSendFeedback = {},
        )
    }
}
