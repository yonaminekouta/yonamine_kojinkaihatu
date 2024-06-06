package com.example.kojinkaihatu.service;

import com.example.kojinkaihatu.DAO.PgUserDao;
import com.example.kojinkaihatu.entity.User;
import com.example.kojinkaihatu.entity.avg_data;
import com.example.kojinkaihatu.entity.training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PgUserService implements UserService{
    @Autowired
    private PgUserDao pgUserDao;

    @Override
    public List<User> findAllUser(){return pgUserDao.findAllUser();}

    @Override
    public int        insertNewUser(User user){return pgUserDao.insertNewUser(user);};

    @Override
    public List<User> findByIdUser(int id){return pgUserDao.findByIdUser(id);}

    @Override
    public List<avg_data> findAllManData(){return pgUserDao.findAllManData();};
    @Override
    public List<avg_data> findAllWomanData(){return pgUserDao.findAllWomanData();};
    @Override
    public List<training> findAllTraining(){return pgUserDao.findAllTraining();};
    @Override
    public int            updateUser(int id,int height,int weight){return pgUserDao.updateUser(id,height,weight);};
    @Override
    public int            deleteUser(int id){return pgUserDao.deleteUser(id);};
}