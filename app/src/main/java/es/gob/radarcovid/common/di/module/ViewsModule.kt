/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.common.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import es.gob.radarcovid.common.view.*
import es.gob.radarcovid.common.view.adapter.SingleChoiceAdapter
import es.gob.radarcovid.features.home.view.ShareDialog
import es.gob.radarcovid.features.information.view.InformationDialog
import es.gob.radarcovid.features.main.view.ExposureHealedDialog
import es.gob.radarcovid.features.stats.view.StatsCountriesDialog

@Module
abstract class ViewsModule {

    @ContributesAndroidInjector
    abstract fun bindsLabelDotTextView(): LabelDotTextView

    @ContributesAndroidInjector
    abstract fun bindsLabelTextView(): LabelTextView

    @ContributesAndroidInjector
    abstract fun bindsLabelEditText(): LabelEditText

    @ContributesAndroidInjector
    abstract fun bindsLabelButton(): LabelButton

    @ContributesAndroidInjector
    abstract fun bindsLabelImageButton(): LabelImageButton

    @ContributesAndroidInjector
    abstract fun bindsLowRiskInfoDialog(): ExposureHealedDialog

    @ContributesAndroidInjector
    abstract fun bindShareDialog(): ShareDialog

    @ContributesAndroidInjector
    abstract fun bindsLabelCheckBox(): LabelCheckBox

    @ContributesAndroidInjector
    abstract fun bindsLabelConstraintLayout(): LabelConstraintLayout

    @ContributesAndroidInjector
    abstract fun bindsMoreInfoButton(): MoreInfoButton

    @ContributesAndroidInjector
    abstract fun bindsLabelRadioButton(): LabelRadioButton

    @ContributesAndroidInjector
    abstract fun bindsStepsProgress(): StepsProgress

    @ContributesAndroidInjector
    abstract fun bindsSingleChoiceAdapter(): SingleChoiceAdapter

    @ContributesAndroidInjector
    abstract fun bindsLegalTermsDialog(): LegalTermsDialog

    @ContributesAndroidInjector
    abstract fun bindsInformationDialog(): InformationDialog

    @ContributesAndroidInjector
    abstract fun bindsStatsCountriesDialog(): StatsCountriesDialog

}