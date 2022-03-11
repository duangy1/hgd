<template>
<div class="table-container">
    <HeaderBar />
    <Banner />
    <Navigator />   
    <div class="left-tab">
        <h3 style="clear:left">Gene Detail</h3>
        <el-tabs :tab-position="tabPosition" style="height: 200px;">
            <el-tab-pane label="General">
                <div class="detail-info" style="margin-left:20%;display:table">
                    <h4 style="display:inline;">Organism    &nbsp;&nbsp;&nbsp;&nbsp;:</h4> Gallus <br>
                    <h4 style="display:inline;">Gene symbol :</h4> STXBP5 <br>
                    <h4 style="display:inline;">Gene Entrez id :</h4> 421620 <br>
                    <h4 style="display:inline;">Description &nbsp;&nbsp;&nbsp;:</h4> Gallus <br>
                    <h4 style="display:inline;">Other name :</h4> ENSGALG00000012336.6 <br>
                </div>
            </el-tab-pane>
            <el-tab-pane label="Orthology">Orthology</el-tab-pane>
            <el-tab-pane label="Traits">Traits<br><br>
            <el-checkbox label="Gallus gallus" >Compare Ortholog Genes</el-checkbox><br><br>

                Choose species
                <el-checkbox label="Gallus gallus"></el-checkbox>
                <el-checkbox label="Bovin"></el-checkbox>
                <el-checkbox label="Sheep"></el-checkbox><br><br>
            </el-tab-pane>
            <el-tab-pane label="Variants">Variants</el-tab-pane>
            <el-tab-pane label="Go">Go</el-tab-pane>
        </el-tabs>   
    </div>
    <div class="tables">
        <el-table
            :data="orthoData"
            style="width: 100%">
            <el-table-column
                type="index"
                :index="indexMethod">
            </el-table-column>
            <el-table-column
                prop="organism"
                label="organism"
                width="180">
            </el-table-column>
            <el-table-column
                prop="protein"
                label="protein"
                width="180">
            </el-table-column>
            <el-table-column
                prop="gene"
                label="gene">
            </el-table-column>
            <el-table-column prop='db_evidence' label="Db Evidence">
                    <template slot-scope="scope">
                        <div v-for="(value,key,index) in scope.row.db_evidence" :key="index">
                                {{key}}:{{value}}</div>
                    </template>
                </el-table-column>
        </el-table>
    </div>
    <FooterBar/>
</div>

</template>
<style  scoped>
.left-tab{
    margin-top: 3%;
    font-size: 27px;
}
.tables{
    margin-left: 15%;
    margin-top: 3%;
}
/* .is-left{
    margin: 10px;
} */

</style>
<script>
// @ is an alias to /src
import HeaderBar from '@/components/HeaderBar.vue';
import FooterBar from '@/components/FooterBar.vue';
import Navigator from '@/components/navigator.vue';
import Banner from "@/components/banner.vue";
// import "@/assets/css/traits.css";
export default {
  name: 'GeneDetail',
  components: {
    Navigator,
    Banner,
    HeaderBar,
    FooterBar
    
  },
  data(){
      return {
        tableData: [{
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }],
        tabPosition: 'left',
        geneData:[{
            organism: 'Gallus gallus',
            geneSymbol: 'STXBP5',
            geneName: '上海市普陀区金沙江路 1518 弄'
        }],
        orthoData:[{
            organism:"Sheep",
            protein:"F1PWE5",
            gene:"ZSWIM6",
            db_evidence:{'hieranoid': 1, 'Panther': 'PTHR22619'}
        },
        {
            organism:"Sheep",
            protein:"F1PWE5",
            gene:"ZSWIM6",
            db_evidence:{'hieranoid': 1, 'Panther': 'PTHR22619'}
        }]
    
      }
    },

    methods: {
      indexMethod(index) {
        return index * 1;
      }
  },
  
}
</script>
