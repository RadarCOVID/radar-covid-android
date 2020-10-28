/*
 * Copyright (c) 2020 Gobierno de España
 *
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 *  SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.covidreport.form.pages.step1.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.common.base.Constants
import es.gob.radarcovid.features.covidreport.form.pages.step1.protocols.Step1MyHealthPresenter
import es.gob.radarcovid.features.covidreport.form.pages.step1.protocols.Step1MyHealthView
import es.gob.radarcovid.features.covidreport.form.view.CovidReportCallback
import kotlinx.android.synthetic.main.fragment_step1_my_health.*
import kotlinx.android.synthetic.main.layout_back_navigation.*
import kotlinx.android.synthetic.main.layout_select_date_symptom.view.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class Step1MyHealthFragment : BaseFragment(), Step1MyHealthView {

    companion object {

        fun newInstance() = Step1MyHealthFragment()

    }

    @Inject
    lateinit var presenter: Step1MyHealthPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_step1_my_health, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onResume() {
        super.onResume()
        presenter.onCodeChanged(editTextCodeAccessibility.text.toString())
    }

    override fun onPause() {
        super.onPause()
        (activity as? CovidReportCallback)?.hideKeyboard()
    }

    private fun initViews() {

        imageButtonBack.contentDescription =
            "${labelManager.getText("MY_HEALTH_TITLE_PAGE", R.string.covid_report_title)} ${
            labelManager.getText(
                "ACC_BUTTON_BACK_TO",
                R.string.navigation_back_to
            )
            }"

        buttonContinue.setOnClickListener {
            (activity as? CovidReportCallback)?.hideKeyboard()
            presenter.onContinueButtonClick()
        }

        editTextCodeAccessibility.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                presenter.onCodeChanged(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        layoutSelectDate.setOnClickListener { presenter.onSelectDateClick() }
        layoutSelectDate.contentDescription = getSelectDateContentDescription()

        imageButtonBack.setOnClickListener { presenter.onBackButtonClick() }

        buttonCancel.setOnClickListener { presenter.onCancel() }

        stepsProgress.apply {
            visibility = View.VISIBLE
            setSteps(1, 2)
        }

    }


    override fun getReportCode(): String =
        editTextCodeAccessibility.text.toString()

    override fun setButtonContinueEnabled(enabled: Boolean) {
        buttonContinue.isEnabled = enabled
    }

    override fun showDatePickerDialog() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val realMonth = monthOfYear + 1
                layoutSelectDate.layoutInputDate.labelDay.text =
                    if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                layoutSelectDate.layoutInputDate.labelMonth.text =
                    if (realMonth < 10) "0$realMonth" else "$realMonth"
                layoutSelectDate.layoutInputDate.labelYear.text = year.toString()
                layoutSelectDate.contentDescription =
                    getDateSelectedContentDescription(dayOfMonth, realMonth, year)
            }

        val datePicker = DatePickerDialog(context!!, dateSetListener, year, month, day)

        val calendarDisableDates = Calendar.getInstance()
        datePicker.datePicker.maxDate = calendarDisableDates.timeInMillis
        calendarDisableDates.add(Calendar.DATE, -14)
        datePicker.datePicker.minDate = calendarDisableDates.timeInMillis

        datePicker.show()
    }

    override fun getDateSelected(): Date? {
        val labelDay = layoutSelectDate.layoutInputDate.labelDay.text
        val labelMonth = layoutSelectDate.layoutInputDate.labelMonth.text
        val labelYear = layoutSelectDate.layoutInputDate.labelYear.text

        return if (!labelDay.isNullOrEmpty() && !labelMonth.isNullOrEmpty() && !labelYear.isNullOrEmpty()) {
            val date = SimpleDateFormat(
                Constants.DATE_FORMAT,
                Locale.getDefault()
            ).parse("$labelDay/$labelMonth/$labelYear")
            date
        } else {
            null
        }
    }

    override fun performContinueButtonClick(reportCode: String, date: Date?) {
        (activity as? CovidReportCallback)?.onContinueButtonClick(1)
        (activity as? CovidReportCallback)?.setValuesFromStep1(reportCode, date)
    }

    override fun performBackButtonClick() {
        (activity as? CovidReportCallback)?.onContinueButtonClick(0)
    }

    override fun finish() {
        (activity as? CovidReportCallback)?.onFinishButtonClick()
    }

    private fun getSelectDateContentDescription() =
        "${labelManager.getText(
            "ACC_MY_HEALTH_DATE_PICKER_NO_SELECTED",
            R.string.covid_report_acc_date_not_selected
        )}, " +
                "${labelManager.getText(
                    "MY_HEALTH_DIAGNOSTIC_DATE_DAY",
                    R.string.covid_report_date_day
                )}, " +
                "${labelManager.getText(
                    "MY_HEALTH_DIAGNOSTIC_DATE_MONTH",
                    R.string.covid_report_date_month
                )}, " +
                "${labelManager.getText(
                    "MY_HEALTH_DIAGNOSTIC_DATE_YEAR",
                    R.string.covid_report_date_year
                )}"


    private fun getDateSelectedContentDescription(day: Int, month: Int, year: Int): String {
        val dayDescription = "${labelManager.getText(
            "MY_HEALTH_DIAGNOSTIC_DATE_DAY",
            R.string.covid_report_date_day
        )} $day"
        val monthDescription = "${labelManager.getText(
            "MY_HEALTH_DIAGNOSTIC_DATE_MONTH",
            R.string.covid_report_date_month
        )} $month"
        val yearDescription = "${labelManager.getText(
            "MY_HEALTH_DIAGNOSTIC_DATE_YEAR",
            R.string.covid_report_date_year
        )} $year"
        return labelManager.getText(
            "ACC_MY_HEALTH_DATE_PICKER_SELECTED",
            R.string.covid_report_acc_date_selected
        ).toString()
            .replace("$1", "$dayDescription $monthDescription $yearDescription")
    }

}