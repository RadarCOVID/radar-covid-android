package es.gob.radarcovid.datamanager.usecase

import es.gob.radarcovid.R
import es.gob.radarcovid.datamanager.repository.ContactTracingRepository
import es.gob.radarcovid.datamanager.repository.RawRepository
import io.jsonwebtoken.Jwts
import org.bouncycastle.util.io.pem.PemReader
import java.io.StringReader
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.*
import javax.inject.Inject

class ReportInfectedUseCase @Inject constructor(
    private val repository: ContactTracingRepository,
    private val rawRepository: RawRepository
) {


    fun reportInfected(
        reportCode: String,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {

        try {
            val token = buildToken(reportCode)

            repository.notifyInfected(token, onSuccess, onError)
        } catch (e: Exception) {
            onError(e)
        }

    }

    private fun buildToken(reportCode: String) : String {

        val id = UUID.randomUUID().toString()

        val issuedDate = Date()
        val expirationDate = getDatePlus(issuedDate, 30)

        val key = loadPrivateKey(rawRepository.getRawFileString(R.raw.sedia_rsa_private_key))

        return Jwts.builder()
            .setId(id)
            .setIssuer("http://es.gob.radarcovid.android")
            .setAudience("http://es.gob.radarcovid.android")
            .setSubject("androidApp")
            .setIssuedAt(issuedDate)
            .setExpiration(expirationDate)
            .claim("tan", reportCode)
            .signWith(key)
            .compact()

    }


    private fun loadPrivateKey(key: String): PrivateKey? {
        var readerPem: PemReader? = null
        var result: PrivateKey? = null
        try {
            val reader = StringReader(key)
            readerPem = PemReader(reader)
            readerPem.use {
                val obj = it.readPemObject()
                val content = obj.getContent()
                result = KeyFactory.getInstance("RSA").generatePrivate(PKCS8EncodedKeySpec(content))
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    private fun getDatePlus(date: Date, minutes: Int) : Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.MINUTE, minutes)
        return cal.time
    }

}