package com.github.adeynack.finances.service.controller.dataimport

import com.github.adeynack.finances.service.repository.import.moneydance.MoneydanceImport
import com.github.adeynack.finances.service.service.LogService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("import/moneydance")
class MoneydanceImportController(
    logService: LogService,
    private val moneydanceImport: MoneydanceImport
) {

    private val log = logService.getLogger(MoneydanceImportController::class)

    val userId = "asd" // todo: Replace usage of this by real user ID

    data class ImportRequest(val filename: String, val forceReset: Boolean)
    data class ImportResult(val request: ImportRequest, val succeeded: Boolean)

    @PostMapping("json")
    fun importFromMoneydanceJson(
        @RequestParam file: MultipartFile,
        @RequestParam book: String,
        @RequestParam(required = false, defaultValue = "false") forceReset: Boolean
    ): ResponseEntity<ImportResult> {
        val request = ImportRequest(book, forceReset)
        log.info("Importing from Moneydance JSON into book `$book`. Forcing reset if file exists: $forceReset.")
        moneydanceImport.import(userId, file.inputStream, book, forceReset)
        return ResponseEntity.ok(ImportResult(request, succeeded = true))
    }

}
