package com.app.group15.QuestionManager;

import com.app.group15.UserManagement.User;
import com.app.group15.Utility.ServiceUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class QuestionManagerService implements IQuestionManagerServiceInjector, IQuestionManagerService {

    private QuestionManagerAbstractDao questionManagerDao;

    @Override
    public void injectQuestionManagerInjectorService(QuestionManagerDao questionManagerDao) {
        this.questionManagerDao = questionManagerDao;
    }

    @Override
    public List<QuestionType> getQuestionType() {
        List<QuestionType> listOfQuestion = questionManagerDao.getAllQuestionTypes();
        listOfQuestion.forEach((QuestionType questionType) -> {
            questionType.setQuestionType(
                    QuestionTypeEnum.valueOf(questionType.getQuestionType()).getQuestionType());
        });
        return listOfQuestion;
    }


    @Override
    public Question formQuestion(String questionTitle, String questionText, int selectedOption) {
        Question question = new Question();
        question.setQuestionTitle(questionTitle);
        question.setQuestionText(questionText);
        question.setQuestionTypeId(selectedOption);
        if (selectedOption == 2 || selectedOption == 3) {
            question.addOptions(new Options());
        }
        return question;
    }

    @Override
    public List<String> formPreview(Question question) {
        List<String> options = null;
        if ((question.getQuestionTypeId() == 2) || (question.getQuestionTypeId() == 3)) {
            options = new ArrayList<>();
            List<String> temp = options;
            question.getOptions().forEach((Options opt) -> {
                temp.add(opt.getOption());
            });
        }
        return options;
    }

    @Override
    public boolean insertQuestion(Question question, User user) {

        question.setQuestionInstructorId(user.getId());
        Date currentDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        question.setQuestionAddedDate(simpleDateFormat.format(currentDate));

        int insertedQuestionId = questionManagerDao.saveQuestion(question);
        if (question.getQuestionTypeId() == 2 || question.getQuestionTypeId() == 3) {
            if (insertedQuestionId != -1) {
                List<Options> options = question.getOptions();
                List<Integer> optionsIdList = new ArrayList<>();
                for (Options option : options) {
                    int optionID = questionManagerDao.saveOption(option);
                    optionsIdList.add(optionID);
                }

                optionsIdList.forEach((Integer insertedChoiceId) -> {
                    questionManagerDao.saveQuestionOptionMapping(insertedQuestionId, insertedChoiceId);
                });

            }
        }

        return false;
    }

    @Override
    public List<Question> getAllQuestionsOfInstructor(int instructorId, String sortColumn) {
        ArrayList<Question> questionArrayList = (ArrayList<Question>) questionManagerDao.getAllQuestionsOfInstructor(instructorId);
        switch (sortColumn) {
            case "questionId":
                questionArrayList.sort(Comparator.comparingInt(Question::getQuestionId));
                break;
            case "questionTitle":
                questionArrayList.sort(Comparator.comparing(Question::getQuestionTitle));
                break;
            case "questionType":
                questionArrayList.sort(Comparator.comparingInt(Question::getQuestionTypeId));
                break;
            case "questionText":
                questionArrayList.sort(Comparator.comparing(Question::getQuestionText));
                break;
            case "questionDate":
                questionArrayList.sort(Comparator.comparing(Question::getQuestionAddedDate));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + sortColumn);
        }
        return questionArrayList;
    }

    @Override
    public Question getQuestion(int questionId) {
        if (ServiceUtility.isValidInt(questionId)) {
            return (Question) questionManagerDao.get(questionId);
        }
        return null;
    }

    @Override
    public List<String> getOptions(int questionId) {
        if (ServiceUtility.isValidInt(questionId)) {
            List<Options> optionsList = questionManagerDao.getOptions(questionId);
            List<String> optionsValueList = new ArrayList<>();
            optionsList.forEach(options -> optionsValueList.add(options.getValue()));
            return optionsValueList;
        } else {
            return null;
        }
    }


}