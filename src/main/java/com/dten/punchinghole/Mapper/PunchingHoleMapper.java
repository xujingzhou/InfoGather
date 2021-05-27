package com.dten.punchinghole.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dten.punchinghole.bean.PunchingHoleVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PunchingHoleMapper extends BaseMapper<PunchingHoleVO> {
    public IPage<PunchingHoleVO> getBySearchPagination(Long self_id, String self_name,
                                            String self_nat_type, String ice_connection_state,
                                             Page<?> page);
    public List<PunchingHoleVO> getBySearch(Long self_id, String self_name,
                                             String self_nat_type, String ice_connection_state);
    public List<PunchingHoleVO> getById(long id);
    public List<PunchingHoleVO> getList();
    public IPage<PunchingHoleVO> getListByPagination(Page<?> page);
    public boolean insertPunchingHole(PunchingHoleVO punchingHole);
    public boolean updatePunchingHole(long id, PunchingHoleVO punchingHole);
    public boolean deletePunchingHole(long id);
    public boolean deleteAllPunchingHole();
}
