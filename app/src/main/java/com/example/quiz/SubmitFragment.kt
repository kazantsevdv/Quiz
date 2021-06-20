package com.example.quiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.quiz.databinding.FragmentResultBinding
import java.lang.StringBuilder


class SubmitFragment : Fragment() {
    private var _viewBinding: FragmentResultBinding? = null
    private val viewBinding get() = checkNotNull(_viewBinding)

    private val quizRepo = QuizRepo.getQuiz()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,  // LifecycleOwner
            callback
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = FragmentResultBinding.inflate(inflater, container, false)
        val view = _viewBinding?.root


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = "Submit"
        viewBinding.tvRez.text = getRez()

        viewBinding.btShare.setOnClickListener {

            doShare()
        }

        viewBinding.btReset.setOnClickListener {
            requireActivity().finish()
        }
        viewBinding.btBack.setOnClickListener {

            doReset()
        }


    }

    private fun doShare() {
        val sb = StringBuilder().append("Резултат  ${calcRez()} из ${quizRepo.size}")
        sb.append(System.getProperty("line.separator"))
        quizRepo.forEach {
            sb.append(
                "${it.quiz} ваш ответ ${
                    when (it.userAnswer) {
                        0 -> it.answer.a1
                        1 -> it.answer.a2
                        2 -> it.answer.a3
                        3 -> it.answer.a4
                        4 -> it.answer.a5
                        else -> ""
                    }
                } "
            )
            sb.append(System.getProperty("line.separator"))
            sb.append(System.getProperty("line.separator"))
        }

        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        val shareBody = sb.toString()
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Результат теста")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        if (sharingIntent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(Intent.createChooser(sharingIntent, "Share via"))
        } else {
            Log.d("Intent", "Не получается обработать намерение!");
        }

    }

    private fun doReset() {
        quizRepo.forEach { it.userAnswer = -1 }
        val bundle = Bundle()
        bundle.putInt(QuizFragment.ID_QUIZ, 0)

        findNavController().navigate(R.id.action_submitFragment_to_quizFragment2)

    }

    private fun getRez(): String {

        return "Резултат  ${calcRez()} из ${quizRepo.size}"
    }

    private fun calcRez(): Int {
        var rez = 0
        quizRepo.forEach { if (it.userAnswer == it.correctAnswer) rez++ }
        return rez
    }


    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }

    companion object {
        fun newInstance() = SubmitFragment()
        const val ID_QUIZ = "ID_QUIZ"
    }
}