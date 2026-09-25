package com.pdfoto.ui.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.pdfoto.R
import java.io.File

/** Abre el PDF con la app visora del sistema. Devuelve `false` si no hay ninguna. */
fun openPdf(context: Context, uri: String): Boolean = try {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri.toUri(), "application/pdf")
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    context.startActivity(intent)
    true
} catch (_: ActivityNotFoundException) {
    false
}

/** Abre una URL en el navegador. Devuelve `false` si no hay ninguna app que la maneje. */
fun openUrl(context: Context, url: String): Boolean = try {
    context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
    true
} catch (_: ActivityNotFoundException) {
    false
}

/** Comparte el PDF con el selector de apps del sistema. */
fun sharePdf(context: Context, uri: String) {
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        type = "application/pdf"
        putExtra(Intent.EXTRA_STREAM, uri.toUri())
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    val chooser = Intent.createChooser(sendIntent, context.getString(R.string.share_pdf_title))
    context.startActivity(chooser)
}

/**
 * Abre un correo prerellenado con los diagnósticos y, si existe, adjunta el log local.
 * Lo envía el usuario con su app de correo: la app no hace ninguna conexión.
 */
fun sendFeedback(
    context: Context,
    email: String,
    subject: String,
    body: String,
    attachment: File?,
) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)

        if (attachment != null && attachment.exists()) {
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                attachment,
            )
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }
    context.startActivity(Intent.createChooser(intent, subject))
}
