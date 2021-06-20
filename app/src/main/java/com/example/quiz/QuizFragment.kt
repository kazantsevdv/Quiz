package com.example.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.quiz.databinding.FragmentQuizBinding


class QuizFragment : Fragment() {
    private var _viewBinding: FragmentQuizBinding? = null
    private val viewBinding get() = checkNotNull(_viewBinding)
    private var idQuiz: Int = -1
    private val quizRepo = QuizRepo.getQuiz()



    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {
                goPrev()
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
        _viewBinding = FragmentQuizBinding.inflate(inflater, container, false)
        val view = _viewBinding?.root

        idQuiz = arguments?.getInt(ID_QUIZ) ?: 0
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = "Quiz ${idQuiz+1}"
        if (idQuiz >= 0) {
            viewBinding.bPrev.isEnabled = idQuiz != 0
            viewBinding.bNext.isEnabled = quizRepo[idQuiz].userAnswer >= 0
            if (idQuiz == quizRepo.size -1) {
                viewBinding.bNext.text = getString(R.string.submit)
            }
            viewBinding.tvquiz.text = quizRepo[idQuiz].quiz
            viewBinding.radioButton.text = quizRepo[idQuiz].answer.a1
            viewBinding.radioButton2.text = quizRepo[idQuiz].answer.a2
            viewBinding.radioButton3.text = quizRepo[idQuiz].answer.a3
            viewBinding.radioButton4.text = quizRepo[idQuiz].answer.a4
            viewBinding.radioButton5.text = quizRepo[idQuiz].answer.a5
            viewBinding.bNext.setOnClickListener {

                goNext()
            }
            viewBinding.bPrev.setOnClickListener {

                goPrev()
            }
            if (quizRepo[idQuiz].userAnswer>=0)
            (viewBinding.radioGroup.getChildAt(quizRepo[idQuiz].userAnswer) as RadioButton).isChecked=true
            viewBinding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
                quizRepo[idQuiz].userAnswer= when(checkedId){
                    R.id.radioButton->0
                    R.id.radioButton2->1
                    R.id.radioButton3->2
                    R.id.radioButton4->3
                    R.id.radioButton5->4
                    else ->-1
                }

                viewBinding.bNext.isEnabled = quizRepo[idQuiz].userAnswer >= 0
            }
        }
    }

    private fun goNext() {
        if (idQuiz < quizRepo.size - 1) {
            val bundle = Bundle()
            bundle.putInt(ID_QUIZ, idQuiz + 1)
            findNavController().navigate(R.id.action_quizFragment_self, bundle)
        }
        if(idQuiz == quizRepo.size -1){
            findNavController().navigate(R.id.submitFragment)
        }
    }

    private fun goPrev() {
        if (idQuiz > 0) {
            val bundle = Bundle()
            bundle.putInt(ID_QUIZ, idQuiz - 1)
            findNavController().navigate(R.id.quizFragment, bundle)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }

    companion object {
        fun newInstance() = QuizFragment()
        const val ID_QUIZ = "ID_QUIZ"
    }
}