package es.gob.covidradar.common.view

import es.gob.covidradar.common.viewmodel.QuestionViewModel

interface AnswerView {

    fun getSelectedAnswers(): QuestionViewModel

}