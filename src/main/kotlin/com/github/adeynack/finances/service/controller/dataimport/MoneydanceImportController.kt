package com.github.adeynack.finances.service.controller.dataimport

import com.github.adeynack.finances.service.repository.import.moneydance.MoneydanceImport
import org.apache.log4j.LogManager
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("import/moneydance")
class MoneydanceImportController(
    private val moneydanceImport: MoneydanceImport
) {

    private val log = LogManager.getLogger(MoneydanceImportController::class.java)

    data class ImportRequest(val filename: String, val forceReset: Boolean)
    data class ImportResult(val request: ImportRequest, val succeeded: Boolean)

    @PostMapping("json")
    fun importFromMoneydanceJson(
        @RequestParam file: MultipartFile,
        @RequestParam filename: String,
        @RequestParam(required = false, defaultValue = "false") forceReset: Boolean
    ): ResponseEntity<ImportResult> {
        val request = ImportRequest(filename, forceReset)
        log.info("Importing from Moneydance JSON into file named `$filename`. Forcing reset if file exists: $forceReset.")
        moneydanceImport.import(file.inputStream, filename, forceReset)
        return ResponseEntity.ok(ImportResult(request, succeeded = true))
    }

}
