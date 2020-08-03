package es.gob.radarcovid.datamanager.usecase

import es.gob.radarcovid.BuildConfig
import es.gob.radarcovid.datamanager.repository.ApiRepository
import es.gob.radarcovid.datamanager.repository.ContactTracingRepository
import es.gob.radarcovid.datamanager.repository.PreferencesRepository
import es.gob.radarcovid.datamanager.repository.RawRepository
import es.gob.radarcovid.models.request.RequestVerifyCode
import es.gob.radarcovid.models.response.ResponseToken
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import org.dpppt.android.sdk.util.SignatureUtil
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class ReportInfectedUseCase @Inject constructor(
    private val contactTracingRepository: ContactTracingRepository,
    private val preferencesRepository: PreferencesRepository,
    private val rawRepository: RawRepository,
    private val apiRepository: ApiRepository
) {

    fun reportInfected(reportCode: String): Completable {

        return getVerifyToken(reportCode).flatMapCompletable {

            val jwt = parseToken(it.token)
            val formatter = SimpleDateFormat("yyyy-MM-dd");
            var onset = formatter.parse(jwt.body.get("onset") as? String)
            contactTracingRepository.notifyInfected(it.token, onset)
        }.concatWith {
            setInfectionReportDate(Date())
            Completable.complete()
        }

    }

    private fun setInfectionReportDate(date: Date) {
        preferencesRepository.setInfectionReportDate(date)
    }

    private fun getVerifyToken(reportCode: String): Observable<ResponseToken> {
        return Observable.create { emitter ->
            val result = apiRepository.verifyCode(RequestVerifyCode(null, reportCode))

            if (result.isLeft()) {
                emitter.onError(result.left().get())
            } else {
                emitter.onNext(result.right().get())
                emitter.onComplete()
            }

        }
    }

    private fun parseToken(signedJWT: String): Jws<Claims> {
        return Jwts.parserBuilder()
            .setSigningKey(SignatureUtil.getPublicKeyFromBase64(BuildConfig.PUBLIC_KEY_VERIFICATION))
            .build()
            .parseClaimsJws(signedJWT);
    }

    private fun getDatePlus(date: Date, minutes: Int): Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.MINUTE, minutes)
        return cal.time
    }

}