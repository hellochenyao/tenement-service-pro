package com.kanata.message.dao.impl;

import com.kanata.core.dto.page.Page;
import com.kanata.core.entity.PrivateMsgEntity;
import com.kanata.message.dao.PrivateMsgDao;
import com.kanata.message.dao.vo.privateMsg.PrivateMsgUserReceiveFilterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PrivateMsgDaoImpl implements PrivateMsgDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public Page<PrivateMsgEntity> findConnectMsg(PrivateMsgUserReceiveFilterVo privateFilter) {
        StringBuilder sql = new StringBuilder();
        sql.append("select SQL_CALC_FOUND_ROWS * from private_msg WHERE 1=1");
        List<Object> param = new ArrayList<>();
        if(privateFilter.getUserid()!=null&&!StringUtils.isEmpty(privateFilter.getUserid())){
            sql.append(" and userid = ? ");
            param.add(privateFilter.getUserid());
            sql.append("and receive_userid not in( SELECT userid from private_msg  WHERE receive_userid = ? ");
            param.add(privateFilter.getUserid());
            sql.append("GROUP BY userid) group by receive_userid  union SELECT * from private_msg  WHERE receive_userid = ?  ");
            param.add(privateFilter.getUserid());
            sql.append(" GROUP BY userid ORDER BY create_time desc");
        }
        if(privateFilter.getPageNo()!=0 && privateFilter.getPageSize()!=0){
            sql.append(" limit ?,? ");
            param.add((privateFilter.getPageNo()-1)*privateFilter.getPageSize());
            param.add(privateFilter.getPageSize());
        }
        RowMapper<PrivateMsgEntity> rowMapper = new BeanPropertyRowMapper<>(PrivateMsgEntity.class);
        List<PrivateMsgEntity> list = jdbcTemplate.query(sql.toString(),param.toArray(),rowMapper);
        final String totalSql = "SELECT FOUND_ROWS();";
        Integer total = jdbcTemplate.queryForObject(totalSql, Integer.class);
        Page<PrivateMsgEntity> page = new Page();
        page.setData(list);
        page.setTotal(total);
        return page;
    }

    @Override
    public void deleteMsg(int userid, int receiveUserid) {
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<>();
        sql.append("delete from private_msg where userid=? and receive_userid=? or receive_userid = ? and userid=?");
        params.add(userid);
        params.add(receiveUserid);
        params.add(userid);
        params.add(receiveUserid);
        jdbcTemplate.update(sql.toString(),params.toArray());
    }
}
