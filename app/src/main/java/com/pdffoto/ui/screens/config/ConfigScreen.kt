package com.pdffoto.ui.screens.config

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pdffoto.R
import com.pdffoto.domain.model.MarginSize
import com.pdffoto.domain.model.Orientation
import com.pdffoto.domain.model.PageSize
import com.pdffoto.domain.model.Quality
import com.pdffoto.ui.components.ConfigChipRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigScreen(
    onBack: () -> Unit,
    onGenerate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ConfigViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.config_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.action_back),
                        )
                    }
                },
            )
        },
        bottomBar = {
            Surface(tonalElevation = 3.dp) {
                Button(
                    onClick = onGenerate,
                    enabled = uiState.fileName.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(16.dp),
                ) {
                    Text(text = stringResource(R.string.action_generate_pdf))
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            OutlinedTextField(
                value = uiState.fileName,
                onValueChange = viewModel::onFileNameChange,
                label = { Text(text = stringResource(R.string.config_file_name)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )

            ConfigChipRow(
                title = stringResource(R.string.config_page_size),
                options = PageSize.entries,
                selected = uiState.pdfConfig.pageSize,
                label = { it.label() },
                onSelect = viewModel::onPageSizeSelect,
            )

            ConfigChipRow(
                title = stringResource(R.string.config_orientation),
                options = Orientation.entries,
                selected = uiState.pdfConfig.orientation,
                label = { it.label() },
                onSelect = viewModel::onOrientationSelect,
            )

            ConfigChipRow(
                title = stringResource(R.string.config_margin),
                options = MarginSize.entries,
                selected = uiState.pdfConfig.margin,
                label = { it.label() },
                onSelect = viewModel::onMarginSelect,
            )

            ConfigChipRow(
                title = stringResource(R.string.config_quality),
                options = Quality.entries,
                selected = uiState.pdfConfig.quality,
                label = { it.label() },
                onSelect = viewModel::onQualitySelect,
            )
        }
    }
}

@Composable
private fun PageSize.label(): String = stringResource(
    when (this) {
        PageSize.A4 -> R.string.page_size_a4
        PageSize.LETTER -> R.string.page_size_letter
        PageSize.AUTO -> R.string.page_size_auto
    },
)

@Composable
private fun Orientation.label(): String = stringResource(
    when (this) {
        Orientation.PORTRAIT -> R.string.orientation_portrait
        Orientation.LANDSCAPE -> R.string.orientation_landscape
        Orientation.AUTO -> R.string.orientation_auto
    },
)

@Composable
private fun MarginSize.label(): String = stringResource(
    when (this) {
        MarginSize.NONE -> R.string.margin_none
        MarginSize.SMALL -> R.string.margin_small
        MarginSize.LARGE -> R.string.margin_large
    },
)

@Composable
private fun Quality.label(): String = stringResource(
    when (this) {
        Quality.LOW -> R.string.quality_low
        Quality.MEDIUM -> R.string.quality_medium
        Quality.HIGH -> R.string.quality_high
    },
)
