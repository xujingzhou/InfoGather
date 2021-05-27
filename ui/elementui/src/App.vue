<template>
  <div id="app">
      <el-form :model="queryForm" :ref="queryForm" label-width="110px" class="search" size="mini">
        <el-row>
            <el-col :span="5">
                <el-form-item label="本机ID:">
                    <el-input v-model="queryForm.id" placeholder="请输入本机ID" clearable prefix-icon="el-icon-search">></el-input>
                </el-form-item>
             </el-col>
            <el-col :span="5">
                <el-form-item label="本机名称:">
                    <el-input v-model="queryForm.name" placeholder="请输入本机名称" clearable prefix-icon="el-icon-search"></el-input>
                </el-form-item>
            </el-col>
            <el-col :span="6">
                <el-form-item label="本机NAT类型:">
                    <el-input v-model="queryForm.type" placeholder="请输入本机NAT类型" clearable prefix-icon="el-icon-search"></el-input>
                 </el-form-item>
             </el-col>
            <el-col :span="5">
                <el-form-item label="打洞状态:">
                    <el-input v-model="queryForm.state" placeholder="请输入打㓊状态" clearable prefix-icon="el-icon-search"></el-input>
                 </el-form-item>
            </el-col>
            <el-col :span="3">
                <el-button type="primary" @click="search()"  size="mini">查询</el-button>
                <el-button type="warning" @click="resetForm()"  size="mini">重置</el-button>
            </el-col>
        </el-row>
    </el-form>

    <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" border>
      <!-- <el-table-column fixed prop="id" label="序号" width="50" align="center" highlight-current-row="true"> -->
      <el-table-column prop="id" label="序号" width="50" align="center" highlight-current-row="true">
        <template slot-scope="scope">{{scope.$index+1}}</template>
      </el-table-column>
      <el-table-column prop="self_id" label="本机ID" width="120" align="center"><template slot-scope="scope">{{ scope.row.self_id }}</template></el-table-column>
      <el-table-column prop="self_name" label="本机名称" width="120" align="center"><template slot-scope="scope">{{ scope.row.self_name }}</template></el-table-column>
      <el-table-column prop="self_nat_type" label="本机NAT类型" width="180" align="center"><template slot-scope="scope">{{ scope.row.self_nat_type }}</template></el-table-column>
      <el-table-column prop="peer_id" label="对端ID" width="120" align="center"><template slot-scope="scope">{{ scope.row.peer_id }}</template></el-table-column>
      <el-table-column prop="peer_name" label="对端名称" width="120" align="center"><template slot-scope="scope">{{ scope.row.peer_name }}</template></el-table-column>
      <el-table-column prop="peer_nat_type" label="对端NAT类型" width="180" align="center"><template slot-scope="scope">{{ scope.row.peer_nat_type }}</template></el-table-column>
      <el-table-column prop="ice_connection_state" label="打洞状态" width="150" align="center"><template slot-scope="scope">{{ scope.row.ice_connection_state }}</template></el-table-column>
      <el-table-column prop="gmt_create" label="创建时间"  align="center"><template slot-scope="scope">{{ scope.row.gmt_create }}</template></el-table-column>
    </el-table>

    <!--分页组件-->
    <el-pagination background
       @size-change="handleSizeChange"
       @current-change="handleCurrentChange"
       :current-page="1"
       :page-sizes="[10, 20, 30, 40]"
       :page-size="10"
       layout="total, sizes, prev, pager, next, jumper"
       :total="total">
    </el-pagination>
  </div>
</template>

<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src=”https://unpkg.com/axios/dist/axios.min.js”></script>
<script>
  export default {
     name:'App',
    created(){
      this.queryInfo();

    },
    data(){
      return {
         queryForm: {
                    id:'',
                    name:'',
                    type:'',
                    state:''
                },
        tableData:[],
        multipleSelection: [],
        total:0,
        currentPage:1,
        pageSize:10
      }
    },
    mounted() {
    },
    methods:{
      queryInfo:function() {
        var url = '/punchinghole?pageNum=' + this.currentPage + '&pageSize=' + this.pageSize;
        console.log("url: ", this.axios.defaults.baseURL+url);
        this.getData(url);
      },

      getData:function(_url) {
        var access_token = this.getAccessToken();
        var _this = this;
        this.axios({
          method : 'GET',
          url : _url,
          async : true,
          dataType : 'json',
          headers:{
              "Accept": "application/json",
              'Authorization': 'Bearer ' + access_token
          },
          }).then((result) =>{ 
            console.log("result.total: ", result.data.total);
            console.log("result.data: ", result.data.data);

            _this.total = result.data.total;
            _this.tableData = result.data.data;
          }).catch((error) =>{
                console.log(error);       
          })
      },

      getAccessToken:function() {
        return Math.random().toString(36).substring(2);
      },

      search() {
          var url = '/punchinghole/search?id=' + this.queryForm.id.trim() + '&name=' + this.queryForm.name.trim() +
                    '&type=' + this.queryForm.type.trim() + '&state=' + this.queryForm.state.trim() +
                    '&pageNum=' + this.currentPage + '&pageSize=' + this.pageSize;
          console.log("url: ", this.axios.defaults.baseURL+url);

          this.getData(url, true);
      },

      resetForm() {
        this.queryForm.id = "";
        this.queryForm.name = "";
        this.queryForm.type = "";
        this.queryForm.state = "";
      },

      handleSizeChange(val){
        this.pageSize = val;
        this.search();
      },
    
      handleCurrentChange(val){
        this.currentPage = val;
        this.search();
      }
    }
  }
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 10px;
}

  /* 固定表头 */
 .el-table__body-wrapper.is-scrolling-none{
    overflow-y: auto;
    max-height: calc(100vh - 100px); 
  }
  /* 自定义-表格样式  */
  .tableClass .cell{   
    padding: 0!important;
    height: 40px;
    line-height: 30px!important;
    text-align: center;
  }
  

</style>
