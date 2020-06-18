package es.gob.covidradar.common.viewmodel

import es.gob.covidradar.models.domain.Answer
import es.gob.covidradar.models.domain.Question
import javax.inject.Inject

class QuestionViewModelFactory @Inject constructor() {

    fun createQuestionsListViewModel(questions: List<Question>): List<QuestionViewModel> =
        questions.map { createQuestionViewModel(it) }

    private fun createQuestionViewModel(question: Question): QuestionViewModel =
        when (question.type) {
            Question.Type.RATE -> QuestionViewModel.RateQuestion(
                question.id, question.question,
                createAnswerListViewModel(question.minValue, question.maxValue)
            )
            Question.Type.SINGLE_SELECTION -> QuestionViewModel.MultipleChoiceQuestion(
                question.id,
                question.question,
                createAnswerListViewModel(question.answers),
                false
            )
            Question.Type.MULTIPLE_SELECTION -> QuestionViewModel.MultipleChoiceQuestion(
                question.id,
                question.question,
                createAnswerListViewModel(question.answers),
                true
            )
        }

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