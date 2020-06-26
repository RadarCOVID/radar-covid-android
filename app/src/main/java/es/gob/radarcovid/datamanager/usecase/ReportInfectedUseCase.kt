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


private const val key = "LS0tLS1CRUdJTiBQUklWQVRFIEtFWS0tLS0tCk1JSUV2QUlCQURBTkJna3Foa2lHOXcwQkFRRUZBQVNDQktZd2dnU2lBZ0VBQW9JQkFRREpOOWZGY2ZoRDdKSzYKeDFySDRhRkJEQnhIaU9VSXhxNmJ1L0Z2TkVob05BZExOL0FyVG5UTjNsZ1g5aUp5MWcwNythRnpXSzRIOHR1YgpGQ0ZxNFc2M2Z4UUN4MGw0Wnh0aUhKTG9TU09CeUllT3phOE5VN21tbmFLU2J2WDd3clZxSlFZS0NZcEEzZUx1Cjc5eFQ5b1JZeStHZ1RiTnFZRG5rTCtRRW5KMnQ3U0hIUWJ1Y014QnIzc2dnbXV4ZU8wbVJBYklaTmhmL296Y3UKNks0WlNNbThBMzRqNGxjZkhkTTNXeGtsYlZaUDJDTVV5clZWSkNNWWZYNFltdEdnWUY4VHBiRnFscUpWeW9kVgo1Z3RSL0VhZDVRdUovb1pYTE41Vi9xNmpZRlFjMlFWYk5KdWtFUjFWS2V0NFI3aWphYjVheVpoWUV4L3I5TmVhCmcyRXdaZGQ5QWdNQkFBRUNnZ0VBZFpiZnpyQVMwTWNBM0l4Qi9ndGFiQ1FDcHI5V2NYR0NldG96ZHJSTUdERkgKamVxTHZSTWxhV1dsZjZORWNHMnM0RDY2M2lpVjAybFdjdU1wd0VjcnA2cG83RkxBR2MvUGpkOTY3cXBIU01JQwpqaTZmUFIrUG45SUJQcWYvc25nUUFvN09YN0ZCalRUZU9Kb04zVGQwRWx4Ylo0M2c2cXAvU2wyK1YvK2FtaGV1Cm5ZN00xNzhFS0g3RjA0dVZkTjZGSThXbUsvUUVXbUNleDNBV3NReW9JVmxMei9zY0ZWNHRTNDJOemdiVVdVcS8KOEkrWTluN0cwRFI4RlhCQWN3dFpGSHBzbXVjK2RkYy9GckRTclk2N0h6dGwrQ3Exc3p2N2UrMkVmcjJWZEtQWQpmOEhjUkZ5eDNqSU1xdkpYUzF1VDh2dlROSjhGbFhUeUFGRW1EVWd4NFFLQmdRRDZKdDRRZjErZ2xTeUZ3TDlnCnNrUlo2S2JjL05Xa2lsY0NtbEtTVUpDVDNZMmJjcGJ0VnJwaVQ2bkl1MVFyVlpMUWdRRVlqUEZ2WEhoQnZjRmwKYmZrZVBucjdQcXhvUmVjTGpoSHovams5NnUyZlBIVU1DTW0wY0lXVjU5YXlEbnJiK1ZESGhVNW9WTzd6QkZ2SQo5a2hzMU1yeGtWZnkzSUcvNG4rT28zYTF5UUtCZ1FETjdCekZ5U2orOGVwQVJnSENhYVo4Q0JUU2NKY2dTSkVlCmNzL09YQWdJVW81c0hMWUJxTGQ3L1VVNGc2NXVhZ2ZmYTBPWXNNNFAzc2lCNTYxdk1qWDU1TmcvVzFxajJtUE4KTG9oVVV6bHczZHpPRnZMc014QjBaSlpkTmdHQnkrUVdHQ1BuVkVaZzVNeDdGdnZaamE1K2tKYzgrMUpBZzlhKwp1c3RwWUxCK0ZRS0JnRXduNlJNOHMvQS9lZjUrWDhVZ2dJNmV0YTR4aXhrOHhLQ1dPdTB3ZWRPS2I4SVRjTDl3CjErMTJNMHBaUFphenhxNlRsN0hSdHU4Z1I3dzcrTlRZVmk5TzlrSG5JclhjRWlzQ1paVFNvTHFvK3c2dmFUTHcKdWZlbENnWkpQcU9XcFBDelJsUjMwUmNTUkd6WHR0bkhvT1VCSTRmSHArN1lFdWhJSEcxZ2RwT0JBb0dBY0JHSApHekUrU1htNUpoK3poMjNlT0hSaGVLSGo3YUx3cjZTR2FlVjJUYWs0YjZnMGViU3JueVFZandoRFh6My9hTjFFCm5ZM2pwNGwwa0JaWk1rSWVWQkR5aTVDWlJNRnZQVlNNeDIrL3ZDaFNxaXFkTEdVaUdHWkIyeHF3T1VhUXJHR1kKOEtYUjQ1dkJtVi9KMHYyanNLZmFWMHJqMmM2bWlZaTVlWEV3dkgwQ2dZQlhabVpNc0tRcFRZRmZMcWNRWGlEbQpURERvYWdQUlBaWExUd3QrUGFmTXZWMkhhVnpBVDZHeWo0ajBaUGlTOUlhOVJQZVl6cjkwUWEzMElkTVlMekJVCm9uVDAxTmdqZFpVU0ZQbXM2eW1PKzV1dE80a2p6WEU1OXdtamZ5RWdEZ056WFRmRTdOK3FDVzlCMHh5eTJxNlcKQWhpRWlSZFBLOHFNOTU2QnJqVm1oUT09Ci0tLS0tRU5EIFBSSVZBVEUgS0VZLS0tLS0="

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

            val id = UUID.randomUUID().toString()

            val issuedDate = Date()
            val expirationDate = getDatePlus(issuedDate, 30)

            //val key = noPem(rawRepository.getRawFileBytes(R.raw.sedia_rsa_private_key))
            //val key = pem(key)
            val key = pem(rawRepository.getRawFileString(R.raw.sedia_rsa_private_key))

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

    private fun noPem(key: ByteArray) : PrivateKey {
        val pkc = PKCS8EncodedKeySpec(key)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePrivate( pkc )
    }

    private fun noPem(key: String) : PrivateKey {
        val pkc = PKCS8EncodedKeySpec(android.util.Base64.decode(key, android.util.Base64.DEFAULT))
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePrivate( pkc )
    }

    private fun pem(key: String): PrivateKey? {
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
        val cal = Calendar.getInstance() // creates calendar
        cal.time = date // sets calendar time/date
        cal.add(Calendar.MINUTE, minutes) // adds one hour
        return cal.time
    }

}