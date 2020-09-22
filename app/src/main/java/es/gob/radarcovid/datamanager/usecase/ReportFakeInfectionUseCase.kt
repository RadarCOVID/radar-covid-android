/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.datamanager.usecase

import es.gob.radarcovid.datamanager.repository.ApiRepository
import es.gob.radarcovid.datamanager.repository.FakeInfectionReportRepository
import es.gob.radarcovid.models.request.RequestVerifyCode
import es.gob.radarcovid.models.response.ResponseToken
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class ReportFakeInfectionUseCase @Inject constructor(
    private val fakeInfectionReportRepository: FakeInfectionReportRepository,
    private val apiRepository: ApiRepository
) {

    companion object {

        const val FAKE_REPORT_CODE: String = "112358132134"
        
    }


    fun reportFakeInfection(): Completable =
        getFakeVerifyToken().flatMapCompletable {
            fakeInfectionReportRepository.notifyFakeInfected(it.token)
        }.concatWith {
            it.onComplete()
        }

    private fun getFakeVerifyToken(): Observable<ResponseToken> {
        return Observable.create { emitter ->
            val result = apiRepository.verifyCode(RequestVerifyCode(null, FAKE_REPORT_CODE))

            if (result.isLeft()) {
                emitter.onError(result.left().get())
            } else {
                emitter.onNext(result.right().get())
                emitter.onComplete()
            }

        }
    }

}