package es.gob.radarcovid.datamanager.usecase

import com.google.android.gms.common.util.IOUtils
import es.gob.radarcovid.R
import es.gob.radarcovid.datamanager.repository.ContactTracingRepository
import es.gob.radarcovid.datamanager.repository.RawRepository
import io.jsonwebtoken.Jwts
import java.io.DataInputStream
import java.io.File
import java.io.FileInputStream
import java.security.Key
import java.security.KeyFactory
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

        val token = buildToken(reportCode)

        repository.notifyInfected(token, onSuccess, onError)
    }

    private fun buildToken(reportCode: String) : String {

        try {


            val keyBytes = rawRepository.getRawFileBytes(R.raw.sedia_rsa_private_key)

            val id = UUID.randomUUID().toString()

            val issuedDate = Date()
            val expirationDate = getDatePlus(issuedDate, 30)

            val pkc = PKCS8EncodedKeySpec(keyBytes)
            val keyFactory = KeyFactory.getInstance("RSA")

            val key : Key = keyFactory.generatePrivate( pkc )

            return Jwts.builder()
                .setId(id)
                .setIssuer("http://es.gob.radarcovid.android")
                .setAudience("http://es.gob.radarcovid.android")
                .setSubject("androidApp")
                .setIssuedAt(issuedDate)
                .setExpiration(expirationDate)
                .claim("TAN", reportCode)
                .signWith(key)
                .compact()


        } catch (ex: Throwable ) {
            ex.printStackTrace()
        }

        return ""

    }

    private fun getDatePlus(date: Date, minutes: Int) : Date {
        val cal = Calendar.getInstance() // creates calendar
        cal.time = date // sets calendar time/date
        cal.add(Calendar.MINUTE, minutes) // adds one hour
        return cal.time
    }

}