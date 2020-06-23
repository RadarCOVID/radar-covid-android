package es.gob.radarcovid.common.view

import es.gob.radarcovid.common.view.viewmodel.QuestionViewModel

interface AnswerView {

    fun getSelectedAnswers(): QuestionViewModel

}