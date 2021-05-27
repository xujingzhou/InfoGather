package com.dten.punchinghole.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dten.punchinghole.utils.ConcurrentDateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Data
@Component
@TableName(value = "punching_hole")
public class PunchingHoleVO {

//    @TableId(value = "id", type = IdType.AUTO)
//    private Long id;

    /**
     * 自己标识
     */
    @TableField(value = "self_id")
    private Long self_id;
    @TableField(value = "self_name")
    private String self_name;
    @TableField(value = "self_nat_type")
    private String self_nat_type;

    /**
     * 对方标识
     */
    @TableField("peer_id")
    private Long peer_id;
    @TableField(value = "peer_name")
    private String peer_name;
    @TableField(value = "peer_nat_type")
    private String peer_nat_type;

    // 打洞状态
    @TableField(value = "ice_connection_state")
    private String ice_connection_state;

    @TableField(value = "gmt_create")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private java.sql.Timestamp gmt_create;
    @TableField(value = "gmt_modified")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private java.sql.Timestamp gmt_modified;
    @TableField(value = "del_flag")
    private boolean del_flag; // 1:逻辑已删除, 0:逻辑未删除

    @Override
    public String toString() {
        return "punchingHole{" +
                "id=" + String.valueOf(self_id) +
                ", name='" + self_name + '\'' +
                ", nat_type=" + self_nat_type +
                ", peer_id=" + String.valueOf(peer_id) +
                ", peer_name=" + peer_name +
                ", peer_nat_type=" + peer_nat_type +
                ", ice_connection_state=" + ice_connection_state +
                ", gmt_create=" + ConcurrentDateUtil.format(gmt_create) +
                ", gmt_modified=" + ConcurrentDateUtil.format(gmt_modified) +
                ", del_flag=" + String.valueOf(del_flag) +
                '}';
    }
}
