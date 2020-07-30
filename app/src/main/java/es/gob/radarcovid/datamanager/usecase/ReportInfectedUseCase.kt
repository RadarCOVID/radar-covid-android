package es.gob.radarcovid.datamanager.usecase

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
import java.util.*
import javax.inject.Inject

class ReportInfectedUseCase @Inject constructor(
    private val contactTracingRepository: ContactTracingRepository,
    private val preferencesRepository: PreferencesRepository,
    private val rawRepository: RawRepository,
    private val apiRepository: ApiRepository
) {

    private val publicKey = "LS0tLS1CRUdJTiBQVUJMSUMgS0VZLS0tLS0KTUlHYk1CQUdCeXFHU000OUFnRUdCU3VCQkFBakE0R0dBQVFCbUlXU0ptdGVGNkh2VnI0M1V5SzliZStlNkpPQgpDRjlVaXpMeis4a3padkVEc25nMGl3VEF3UVB0QzdBMDlzQjVMM3EwSUl1N250Yzd4U1VqSUdTakZvd0JXL0xPCnFtMTBYQ1NkUWNZT3BMTi85dUI1emZKVUZOY3B6Ynk4dDAzSlg3TUZiYi9vQm1pcFNNNHptSm1UajR3Qm9XZ2sKRlF6ZEJHcnAwR2laUU9WVXRtUT0KLS0tLS1FTkQgUFVCTElDIEtFWS0tLS0tCg=="


    fun reportInfected(reportCode: String): Completable {

        return getVerifyToken(reportCode).flatMapCompletable {
            val onset = Calendar.getInstance()
            onset.add(Calendar.DATE, -14)
            val jwt = parseToken(it.token)
            var stringOnset = jwt.body.get("onset")
            contactTracingRepository.notifyInfected(it.token, onset.time)
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
            .setSigningKey(publicKey) // <---- publicKey, not privateKey
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