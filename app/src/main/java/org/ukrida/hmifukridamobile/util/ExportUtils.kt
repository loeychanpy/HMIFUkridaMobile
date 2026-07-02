package org.ukrida.hmifukridamobile.util

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.ukrida.hmifukridamobile.data.model.EventRegistrant
import java.io.IOException

object ExportUtils {

    suspend fun exportRegistrantsToXlsx(
        context: Context,
        registrants: List<EventRegistrant>,
        eventTitle: String
    ) {
        val fileName = "participants_${eventTitle.replace(" ", "_")}.xlsx"
        val mimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"

        val result = withContext(Dispatchers.IO) {
            runCatching {
                val workbook = XSSFWorkbook()
                val sheet = workbook.createSheet("Participants")

                val boldFont = workbook.createFont().apply { bold = true }
                val headerStyle = workbook.createCellStyle().apply { setFont(boldFont) }

                val headers = listOf("No", "Name", "NIM", "Email", "Registered At", "Attendance")
                val columnWidths = intArrayOf(8, 30, 18, 36, 26, 14)

                val headerRow = sheet.createRow(0)
                headers.forEachIndexed { i, title ->
                    headerRow.createCell(i).apply {
                        setCellValue(title)
                        cellStyle = headerStyle
                    }
                    sheet.setColumnWidth(i, columnWidths[i] * 256)
                }

                registrants.forEachIndexed { index, r ->
                    val row = sheet.createRow(index + 1)
                    row.createCell(0).setCellValue((index + 1).toDouble())
                    row.createCell(1).setCellValue(r.name)
                    row.createCell(2).setCellValue(r.nim)
                    row.createCell(3).setCellValue(r.email)
                    row.createCell(4).setCellValue(r.registeredAt)
                    row.createCell(5).setCellValue(if (r.attended) "Hadir" else "Tidak Hadir")
                }

                val values = ContentValues().apply {
                    put(MediaStore.Downloads.DISPLAY_NAME, fileName)
                    put(MediaStore.Downloads.MIME_TYPE, mimeType)
                    put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                }
                val uri = context.contentResolver.insert(
                    MediaStore.Downloads.EXTERNAL_CONTENT_URI, values
                ) ?: throw IOException("Gagal membuat file di Downloads.")

                context.contentResolver.openOutputStream(uri)?.use { workbook.write(it) }
                workbook.close()
            }
        }

        withContext(Dispatchers.Main) {
            result.fold(
                onSuccess = {
                    Toast.makeText(context, "Berhasil diekspor ke Downloads/$fileName", Toast.LENGTH_LONG).show()
                },
                onFailure = { e ->
                    Toast.makeText(context, "Gagal ekspor: ${e.message}", Toast.LENGTH_LONG).show()
                }
            )
        }
    }
}