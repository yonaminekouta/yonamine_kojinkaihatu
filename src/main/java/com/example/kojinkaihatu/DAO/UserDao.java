package com.example.kojinkaihatu.DAO;

import com.example.kojinkaihatu.entity.User;
import com.example.kojinkaihatu.entity.avg_data;
import com.example.kojinkaihatu.entity.training;

import java.util.List;

public interface UserDao {
    List<User> findAllUser();
    int        insertNewUser(User user);

    List<User>       findByIdUser(int id);
    List<avg_data> findAllManData();
    List<avg_data> findAllWomanData();

    List<training> findAllTraining();
    int           updateUser(int id,int height,int weight);
    int           deleteUser(int id);
}
