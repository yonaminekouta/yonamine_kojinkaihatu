package com.example.kojinkaihatu.DAO;

import com.example.kojinkaihatu.entity.User;
import com.example.kojinkaihatu.entity.avg_data;
import com.example.kojinkaihatu.entity.training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PgUserDao implements UserDao{
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<User> findAllUser(){
        return jdbcTemplate.query("SELECT *" +
                                       "FROM users;",
                new DataClassRowMapper<>(User.class));
    }

    @Override
    public int insertNewUser(User user){
        System.out.println("新規ユーザー登録:"+user);
        var param=new MapSqlParameterSource();
        return jdbcTemplate.update("INSERT INTO" +
                                        " users(login_id,pass,name,height,weight,gender) " +
                                        "VALUES ('" +user.login_id()+"',"+
                                        "'"+user.pass()+"',"+
                                        "'"+user.name()+"',"+
                                        user.height()+","+
                                        user.weight()+","+
                                        user.gender()+");"
                                        ,param);
    }

    @Override
    public List<User> findByIdUser(int id){
        var param=new MapSqlParameterSource();
        return jdbcTemplate.query("SELECT * " +
                                    "FROM users " +
                                    "WHERE id="+id,
                new DataClassRowMapper<>(User.class));
    };

    @Override
    public List<avg_data> findAllManData(){
        return jdbcTemplate.query("SELECT *" +
                        "FROM avg_man;",
                new DataClassRowMapper<>(avg_data.class));
    };

    @Override
    public List<avg_data> findAllWomanData(){
        return jdbcTemplate.query("SELECT *" +
                        "FROM avg_man;",
                new DataClassRowMapper<>(avg_data.class));
    };

    @Override
    public List<training> findAllTraining(){
        return jdbcTemplate.query("SELECT *" +
                        "FROM training;",
                new DataClassRowMapper<>(training.class));
    };

    @Override
    public int updateUser(int id,int height,int weight){
        var param=new MapSqlParameterSource();
        return jdbcTemplate.update("UPDATE users " +
                                        "SET height="+height+",weight="+weight+
                                        " WHERE id="+id,param);
    }

    @Override
    public int deleteUser(int id){
        var param=new MapSqlParameterSource();
        return jdbcTemplate.update("DELETE FROM users" +
                                    " WHERE id="+id,param);
    }
}
