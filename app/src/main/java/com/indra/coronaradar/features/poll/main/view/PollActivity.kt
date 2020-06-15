package com.indra.coronaradar.features.poll.main.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.indra.coronaradar.R
import com.indra.coronaradar.common.base.BaseActivity
import com.indra.coronaradar.common.view.CMDialog
import com.indra.coronaradar.common.viewmodel.QuestionViewModel
import com.indra.coronaradar.features.poll.main.protocols.PollPresenter
import com.indra.coronaradar.features.poll.main.protocols.PollView
import com.indra.coronaradar.features.poll.question.view.QuestionFragment
import kotlinx.android.synthetic.main.activity_poll.*
import javax.inject.Inject

class PollActivity : BaseActivity(), PollView, QuestionFragment.Callback {

    companion object {

        fun open(context: Context) =
            context.startActivity(Intent(context, PollActivity::class.java))

    }

    @Inject
    lateinit var presenter: PollPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poll)

        initViews()
        presenter.viewReady()
    }

    private fun initViews() {
        imageButtonBack.setOnClickListener { onBackPressed() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        presenter.onBackButtonPressed()
    }

    override fun showPollProgress(currentQuestion: Int, totalQuestions: Int) {
        stepIndicator.setProgress(currentQuestion, totalQuestions)
    }

    override fun showQuestion(
        isLastQuestion: Boolean,
        question: QuestionViewModel
    ) {
        supportFragmentManager.beginTransaction()
            .add(R.id.wrapperQuestion, QuestionFragment.newInstance(isLastQuestion, question))
            .apply {
                if (wrapperQuestion.childCount != 0)
                    addToBackStack(QuestionFragment.TAG)
            }.commit()
    }

    override fun showSkipQuestionDialog() {
        CMDialog.createCancelableDialog(this,
            title = "",
            description = getString(R.string.question_dialog_message),
            okButtonText = getString(R.string.question_dialog_ok),
            cancelButtonText = getString(R.string.question_dialog_cancel),
            onOkButtonClick = { presenter.onContinueWithoutAnswer() },
            onCancelButtonClick = {}
        ).show()
    }

    override fun onContinueButtonClick(answers: QuestionViewModel) {
        presenter.onNextButtonClick(answers)
    }

}
