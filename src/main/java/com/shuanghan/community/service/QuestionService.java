package com.shuanghan.community.service;


import com.shuanghan.community.dto.QuestionDTO;
import com.shuanghan.community.mapper.QuestionMapper;
import com.shuanghan.community.mapper.UserMapper;
import com.shuanghan.community.model.Question;
import com.shuanghan.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public List<QuestionDTO> list() {
        List<Question> questions = questionMapper.list();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for(Question question :questions){
            User user =userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return  questionDTOList;
    }
    //当一个请求既要使用User也要使用Question的时候可以用Service来组装

}
