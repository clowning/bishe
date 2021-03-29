package com.shuanghan.community.service;


import com.shuanghan.community.dto.PaginationDTO;
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


//当一个请求既要使用User也要使用Question的时候可以用Service来组装
@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        Integer totalPage;

        if(totalCount%size == 0){
            totalPage= totalCount/size;
        }else {
            totalPage = totalCount/size +1;
        }
        if(page <1){
            page=1;
        }
        if(page>totalPage){
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage,page);
        //size *(page-1)
        Integer offset = size *(page-1);
        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for(Question question :questions){
            User user =userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        Integer totalCount = questionMapper.countByUserId(userId);

        if(totalCount%size == 0){
            totalPage= totalCount/size;
        }else {
            totalPage = totalCount/size +1;
        }
        if(page <1){
            page=1;
        }
        if(page>totalPage){
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);
        //size *(page-1)
        Integer offset = size *(page-1);
        List<Question> questions = questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for(Question question :questions){
            User user =userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }


    public QuestionDTO getById(Integer id) {
        Question question= questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId()==null){
            //如果再问题表中没有该id的问题,则创建问题
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else {
            //有该id的问题就修改
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }
}
