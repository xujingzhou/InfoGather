package com.dten.punchinghole.controller;

// import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dten.punchinghole.Mapper.PunchingHoleMapper;
import com.dten.punchinghole.bean.PunchingHoleVO;
import com.dten.punchinghole.singleton.SingletonMybatis;
import com.dten.punchinghole.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.util.List;

@ApiResponses(value = {
        @ApiResponse(code = 200, message = "请求正常完成"),
        @ApiResponse(code = 400, message = "请求中有语法问题"),
        @ApiResponse(code = 500, message = "服务器出现异常")}
)
@RestController
@RequestMapping("/v1")
public class PunchingHoleController {
    private static Logger logger = Logger.getLogger(PunchingHoleController.class);
    private static SqlSessionFactory sqlSessionFactory;
    static {
        sqlSessionFactory =  SingletonMybatis.getSqlSessionFactory();
    }

    @Resource
    PunchingHoleMapper punchingHoleMapper;

    @CrossOrigin
    @ApiIgnore()
    @RequestMapping
    public String index(){
        return "hello, punching hole";
    }

    @CrossOrigin
    @Transactional
    @ApiOperation(value = "获取指定分页的信息(如果未指定分页参数，则返回所有信息)", notes = "获取指定分页的信息列表", httpMethod = "GET")
    @RequestMapping(method = RequestMethod.GET, value = "/punchinghole") // , produces = "application/json;charset=UTF-8")
    public JSONResult getList(@RequestParam(/*defaultValue = "1", */ value = "pageNum", required = false) Integer pageNum,
                              @RequestParam(/*defaultValue = "30", */ value = "pageSize", required = false)Integer pageSize){
        List<PunchingHoleVO>  listPunchingHole;
        if (pageNum != null && pageSize != null) {
            System.out.printf("\n getList - pageNum: %s, pageSize: %s \n", Integer.toString(pageNum), Integer.toString(pageSize));

            Page<PunchingHoleVO> page = new Page<>(pageNum, pageSize);
            // 不进行count sql优化
            page.setOptimizeCountSql(false);

            IPage<PunchingHoleVO> pageReturn;
            try {
                pageReturn = punchingHoleMapper.getListByPagination(page);
                listPunchingHole = pageReturn.getRecords();

            } finally {

            }

            return new JSONResult<List<PunchingHoleVO>>().ok(listPunchingHole, pageReturn.getTotal());

        } else {
            System.out.printf("\n get all list. \n");

            SqlSession sqlSession = sqlSessionFactory.openSession();
            try {
                PunchingHoleMapper punchingHoleMapper = sqlSession.getMapper(PunchingHoleMapper.class);
                listPunchingHole = punchingHoleMapper.getList();
                sqlSession.commit();
            } finally {
                sqlSession.close();
            }

            return new JSONResult<List<PunchingHoleVO>>().ok(listPunchingHole, listPunchingHole.size());
        }
    }

    @CrossOrigin
    @Transactional
    @ApiOperation(value = "获取指定条件的信息", notes = "多条件查询后的信息列表", httpMethod = "GET")
    @RequestMapping(method = RequestMethod.GET, value = "/punchinghole/search")
    public JSONResult getBySearch(@RequestParam(value = "id", required = false) Long id,
                                  @RequestParam(value = "name", defaultValue = "", required = false) String name,
                                  @RequestParam(value = "type", defaultValue = "", required = false) String type,
                                  @RequestParam(value = "state", defaultValue = "", required = false) String state,
                                  @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                  @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<PunchingHoleVO> listPunchingHole;
        if (pageNum != null && pageSize != null) {
            System.out.printf("\n getBySearch - pageNum: %s, pageSize: %s \n", Integer.toString(pageNum), Integer.toString(pageSize));

            Page<PunchingHoleVO> page = new Page<>(pageNum, pageSize);
            // 不进行count sql优化
            page.setOptimizeCountSql(false);

            IPage<PunchingHoleVO> pageReturn;
            try {
//                LambdaQueryWrapper<PunchingHoleVO> queryWrapper = new LambdaQueryWrapper<>();
                pageReturn = punchingHoleMapper.getBySearchPagination(id, name, type, state, page);
                listPunchingHole = pageReturn.getRecords();

            } finally {

            }

            return new JSONResult<List<PunchingHoleVO>>().ok(listPunchingHole, pageReturn.getTotal());

        } else {
            System.out.printf("\n get search list. \n");

            SqlSession sqlSession = sqlSessionFactory.openSession();
            try {
                PunchingHoleMapper punchingHoleMapper = sqlSession.getMapper(PunchingHoleMapper.class);
                listPunchingHole = punchingHoleMapper.getBySearch(id, name, type, state);
                sqlSession.commit();
            } finally {
                sqlSession.close();
            }

            return new JSONResult<List<PunchingHoleVO>>().ok(listPunchingHole, listPunchingHole.size());
        }
    }

    @CrossOrigin
    @Transactional
    @ApiOperation(value = "获取指定ID的信息", notes = "获取指定ID的信息列表", httpMethod = "GET")
    @RequestMapping(method = RequestMethod.GET, value = "/punchinghole/{id}") // , produces = "application/json;charset=UTF-8")
    public JSONResult getById(@PathVariable("id") long id){
        List<PunchingHoleVO> listPunchingHole;
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            PunchingHoleMapper punchingHoleMapper = sqlSession.getMapper(PunchingHoleMapper.class);
            listPunchingHole = punchingHoleMapper.getById(id);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }

        return new JSONResult<List<PunchingHoleVO>>().ok(listPunchingHole, listPunchingHole.size());
    }

    @CrossOrigin
    @Transactional
    @ApiOperation(value = "新增信息", notes = "新增一条信息", httpMethod = "POST")
    @RequestMapping(method = RequestMethod.POST, value = "/punchinghole") // , produces = "application/json;charset=UTF-8")
    public JSONResult add(@RequestBody PunchingHoleVO punchingHole){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            PunchingHoleMapper punchingHoleMapper = sqlSession.getMapper(PunchingHoleMapper.class);
            punchingHoleMapper.insertPunchingHole(punchingHole);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }

        return new JSONResult<>().ok();
    }

    @CrossOrigin
    @Transactional
    @ApiOperation(value = "更新指定ID的信息", notes = "更新指定ID的信息", httpMethod = "PUT")
    @RequestMapping(method = RequestMethod.PUT, value = "/punchinghole/{id}") // , produces = "application/json;charset=UTF-8")
    public JSONResult update(@PathVariable("id") long id, @RequestBody PunchingHoleVO punchingHole){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            PunchingHoleMapper punchingHoleMapper = sqlSession.getMapper(PunchingHoleMapper.class);
            punchingHoleMapper.updatePunchingHole(id, punchingHole);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }

        return new JSONResult<>().ok();
    }

    @CrossOrigin
    @Transactional
    @ApiOperation(value = "删除全部信息", notes = "删除全部信息", httpMethod = "DELETE")
    @RequestMapping(method = RequestMethod.DELETE, value = "/punchinghole") // , produces = "application/json;charset=UTF-8")
    public JSONResult deleteAll(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            PunchingHoleMapper punchingHoleMapper = sqlSession.getMapper(PunchingHoleMapper.class);
            punchingHoleMapper.deleteAllPunchingHole();
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }

        return new JSONResult<>().ok();
    }

    @CrossOrigin
    @Transactional
    @ApiOperation(value = "删除指定ID的信息", notes = "删除指定ID的信息", httpMethod = "DELETE")
    @RequestMapping(method = RequestMethod.DELETE, value = "/punchinghole/{id}") //, produces = "application/json;charset=UTF-8")
    public JSONResult delete(@PathVariable("id") long id){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            PunchingHoleMapper punchingHoleMapper = sqlSession.getMapper(PunchingHoleMapper.class);
            punchingHoleMapper.deletePunchingHole(id);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }

        return new JSONResult<>().ok();
    }
}
