package com.pdffoto.ui.screens.generating

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pdffoto.R

/**
 * Pantalla de progreso de la generación del PDF.
 *
 * Empieza a generar al entrar y avisa con [onFinished] cuando termina.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneratingScreen(
    onCancel: () -> Unit,
    onFinished: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GenerationViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.generate()
    }

    LaunchedEffect(state) {
        if (state is GenerationUiState.Success) onFinished()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text(text = stringResource(R.string.generating_title)) }) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        ) {
            when (val current = state) {
                is GenerationUiState.Error -> ErrorContent(
                    message = current.message,
                    onRetry = viewModel::generate,
                    onCancel = {
                        viewModel.cancel()
                        onCancel()
                    },
                )

                else -> InProgressContent(
                    state = current,
                    onCancel = {
                        viewModel.cancel()
                        onCancel()
                    },
                )
            }
        }
    }
}

@Composable
private fun InProgressContent(
    state: GenerationUiState,
    onCancel: () -> Unit,
) {
    CircularProgressIndicator()

    Text(
        text = (state as? GenerationUiState.InProgress)
            ?.let { stringResource(R.string.generating_progress, it.current, it.total) }
            ?: stringResource(R.string.generating_preparing),
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center,
    )

    OutlinedButton(onClick = onCancel) {
        Text(text = stringResource(R.string.action_cancel))
    }
}

@Composable
private fun ErrorContent(
    message: String?,
    onRetry: () -> Unit,
    onCancel: () -> Unit,
) {
    Text(
        text = message ?: stringResource(R.string.generating_error),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.error,
        textAlign = TextAlign.Center,
    )

    Button(onClick = onRetry) {
        Text(text = stringResource(R.string.action_retry))
    }

    OutlinedButton(onClick = onCancel) {
        Text(text = stringResource(R.string.action_cancel))
    }
}
