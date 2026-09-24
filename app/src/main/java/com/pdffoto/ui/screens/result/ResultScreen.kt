package com.pdffoto.ui.screens.result

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pdffoto.R
import com.pdffoto.domain.pdf.formatFileSize
import com.pdffoto.ui.util.openPdf
import com.pdffoto.ui.util.sharePdf

/**
 * Pantalla de resultado: muestra el PDF generado y permite abrirlo.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    onBackHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ResultViewModel = hiltViewModel(),
) {
    val result by viewModel.result.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text(text = stringResource(R.string.result_title)) }) },
        bottomBar = {
            Surface(tonalElevation = 3.dp) {
                Button(
                    onClick = onBackHome,
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(16.dp),
                ) {
                    Text(text = stringResource(R.string.action_back_home))
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        ) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(72.dp),
            )
            Text(
                text = stringResource(R.string.result_success),
                style = MaterialTheme.typography.headlineSmall,
            )

            val pdf = result
            if (pdf != null) {
                Text(
                    text = pdf.fileName,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = stringResource(
                        R.string.result_details,
                        pluralStringResource(R.plurals.result_pages, pdf.pageCount, pdf.pageCount),
                        formatFileSize(pdf.sizeBytes),
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Button(
                    onClick = {
                        if (!openPdf(context, pdf.uri)) {
                            Toast.makeText(context, R.string.result_no_viewer, Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                ) {
                    Text(text = stringResource(R.string.result_open))
                }

                OutlinedButton(
                    onClick = { sharePdf(context, pdf.uri) },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(text = stringResource(R.string.result_share))
                }
            }
        }
    }
}
