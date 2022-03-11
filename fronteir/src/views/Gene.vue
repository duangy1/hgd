<template>
<div class="table-container">
    <HeaderBar/>
     <Banner />
    <Navigator />      
    <div class="decorate-bar">Search Gene</div>
    <el-card class="wrap-card" shadow="none">
        
    <div class="select-species">
        <el-select v-model="value" placeholder="Choose Species" >
            <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
            </el-option>
            
        </el-select>
        <div class="input-box">
        Accession Id <el-input class="input-filter"></el-input>
        Location<el-input class="input-filter"></el-input>
        </div>
        <div class="input-box">
        Gene Symbol<el-input class="input-filter"></el-input>
        Go Term<el-input class="input-filter"></el-input>
        </div>
        <!-- <div class="chooseSpe" >
            <el-button type="primary" >Gallus gallus(32190)</el-button>
            <el-button type="primary" >Bovin(21930)</el-button>
        </div> -->
    </div>
    </el-card>
    <div class="decorate-bar">Result Table</div>
    <el-card class="wrap-card" shadow="none">
        <el-table
            :data="tableData"
            border
            style="width: 100%"
            v-loading="loading">
       
        <el-table-column
            prop="ensemblGeneId"
            label="Gene Id"
        >
        </el-table-column>
        <el-table-column
            prop="geneSynonym"
            label="Gene Symbol"
        >
        </el-table-column>
        <el-table-column
            prop="geneDescription"
            label="Description"
        >
        </el-table-column>
        <el-table-column
            prop="orthoList"
            label="Ortholog Gene"
        >
        </el-table-column>
        <el-table-column
            label="Ortholog Gene">
            <template slot-scope="scope">
                <el-table border :data='scope.row.orthoList' class="sub-table" :show-header="false">
                    <el-table-column prop='geneid' label="Gene Id"></el-table-column>
                    <el-table-column prop='gene_name' label="Gene Name"></el-table-column>
                    <el-table-column prop='protein' label="Protein"></el-table-column>
                    <el-table-column prop='Organism' label="Organism"></el-table-column>
                    <el-table-column prop='db_evidence' label="Db Evidence">
                        <template slot-scope="scope">
                            <div v-for="(value,key,index) in scope.row.db_evidence" :key="index">
                                    {{key}}:{{value}}</div>
                        </template>
                    </el-table-column>
                </el-table>
            </template>
        </el-table-column>
        
        </el-table>
        <el-pagination
            class="pagination"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="10"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalSize"> 
        </el-pagination>
    </el-card>
    
    <FooterBar/>
</div>
</template>

<style src="../assets/css/gene.css" scoped></style>
<script>

import Navigator from '@/components/navigator.vue';
import FooterBar from '@/components/FooterBar.vue';
import HeaderBar from '@/components/HeaderBar.vue';
import Banner from "@/components/banner.vue";

export default {
  name: 'Gene',
  components: {
    Navigator,
    FooterBar,
    HeaderBar,
    Banner
  },
  data(){
      return {
        totalSize:0,       
        tableData:[],
        multipleSelection: [],
        tabPosition: 'left',
        options: [{
          value: '选项1',
          label: 'Gallus gallus'
        }, {
          value: '选项2',
          label: 'Bovin'
        }],
        value: '',
        loading:true
      }
    },

    methods: {
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
      },
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
      }
      
  },
  mounted () {
    const axiosInstance = this.$axios.create({
    headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'},// 设置传输内容的类型和编码
    withCredentials: true,// 指定某个请求应该发送凭据。允许客户端携带跨域cookie，也需要此配置
  });
    axiosInstance
      .get('http://localhost:9401/api/gene')
      .then(response => {
          console.log("response:",response.data.data);
          this.tableData = response.data.data
          this.totalSize = response.data.recordsTotal
        })
      .catch(error => {
        console.log(error)
        this.errored = true
      })
      .finally(() => this.loading = false)
  }
}
</script>
