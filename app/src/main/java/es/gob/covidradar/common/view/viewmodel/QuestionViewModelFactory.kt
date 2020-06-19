package es.gob.covidradar.common.view.viewmodel

import es.gob.covidradar.models.domain.Answer
import es.gob.covidradar.models.domain.Question
import javax.inject.Inject

class QuestionViewModelFactory @Inject constructor() {

    fun createQuestionsListViewModel(questions: List<Question>): List<QuestionViewModel> =
        questions.map { createQuestionViewModel(it) }

    private fun createQuestionViewModel(question: Question): QuestionViewModel = QuestionViewModel(
        id = question.id,
        type = when (question.type) {
            Question.Type.RATE -> QuestionViewModel.Type.RATE
            Question.Type.SINGLE_SELECTION -> QuestionViewModel.Type.SINGLE_SELECTION
            Question.Type.MULTIPLE_SELECTION -> QuestionViewModel.Type.MULTIPLE_SELECTION
            Question.Type.FIELD -> QuestionViewModel.Type.FIELD
        },
        text = question.question,
        answers = when (question.type) {
            Question.Type.RATE -> createAnswerListViewModel(
                question.minValue,
                question.maxValue
            )
            else -> createAnswerListViewModel(question.answers)
        },
        parentQuestionId = question.parentQuestionId ?: -1,
        parentAnswerId = question.parentAnswerId ?: -1,
        minValue = question.minValue,
        maxValue = question.maxValue
    )

    private fun createAnswerListViewModel(minValue: Int, maxValue: Int): List<AnswerViewModel> {
        val res: MutableList<AnswerViewModel> = ArrayList()
        for (x in minValue..maxValue)
            res.add(AnswerViewModel(x, x.toString()))
        return res
    }

    private fun createAnswerListViewModel(answers: List<Answer>): List<AnswerViewModel> =
        answers.map { createAnswerViewModel(it) }

    private fun createAnswerViewModel(answer: Answer): AnswerViewModel =
        AnswerViewModel(answer.id, answer.answer)


}