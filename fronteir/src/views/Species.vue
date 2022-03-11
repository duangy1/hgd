<template>
<div class="table-container">
    <HeaderBar />
    <Navigator />  
    <div class="filterDiv">
      <el-card>
        <h1 class="titleHs">Species Information</h1>  
        <h2 class="titleHs">Organism: Gallus gallus</h2>  
        <h2 class="titleHs">Description: </h2>  
        <!-- <el-table
          :data="tableData"
          border
          class="speInfoTable">
          <el-table-column
              prop="date"
              label="Organism"
              width="180">
          </el-table-column>
          <el-table-column
              prop="name"
              label="Gene Number"
              width="180">
              Ensembl:33459;
              Entrez:11203;
          </el-table-column>
          <el-table-column
              prop="address"
              label="Protein Number">
              Ensembl:33459;
              Entrez:11203;
              Uniprot:12303;
          </el-table-column>
          <el-table-column
              prop="address"
              label="Homolog Information">
              <el-table-column
                label="hieranoid record">
                <template>
                  <el-link href="/db-detail">2567</el-link>
                </template>
              </el-table-column>
              <el-table-column label="treefam group">
                <template>
                  <el-link href="/db-detail">1799</el-link>
                </template>
              </el-table-column>
              <el-table-column label="Panther group">
                <template>
                  <el-link href="/db-detail">937</el-link>
                </template>
              </el-table-column>
              <el-table-column label="OMA group">
                <template>
                  <el-link href="/db-detail">0</el-link>
                </template>
              </el-table-column>
              <el-table-column label="EggNog group">
                <template>
                  <el-link href="/db-detail">0</el-link>
                </template>
              </el-table-column>
              
          </el-table-column>
    </el-table> -->

      </el-card>
    
      <el-card class="orthoTable">
      <h1 class="titleHs" >Ortholog Information between organisms</h1>  
      <h3 class="titleHs" >Choose Species:</h3>
        <!-- <el-select v-model="value1" placeholder="请选择物种1" style="width:300px">
                <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
                </el-option>
            </el-select> -->
        <el-select v-model="value2" placeholder="Choose Species" style="margin:0 auto;width:400px" >
            <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
            </el-option>
        </el-select>
        <el-button type="primary" class="submitButton">Submit</el-button>
      <el-table
          :data="tableData"
          border
          class="speInfoTable">
          <el-table-column
              prop="date"
              label="Organism 1"
              width="180">
          </el-table-column>
          <el-table-column
              prop="name"
              label="Organism 2"
              width="180">
          </el-table-column>
          <el-table-column
              prop="address"
              label="Ortholog Gene">
          </el-table-column>
          <el-table-column
              prop="address"
              label="Homolog Information">
              <el-table-column
                label="hieranoid record">
                <template>
                  <el-link href="/db-detail">2567</el-link>
                </template>
              </el-table-column>
              <el-table-column label="treefam group">
                <template>
                  <el-link href="/db-detail">1799</el-link>
                </template>
              </el-table-column>
              <el-table-column label="Panther group">
                <template>
                  <el-link href="/db-detail">937</el-link>
                </template>
              </el-table-column>
              <el-table-column label="OMA group">
                <template>
                  <el-link href="/db-detail">0</el-link>
                </template>
              </el-table-column>
              <el-table-column label="EggNog group">
                <template>
                  <el-link href="/db-detail">0</el-link>
                </template>
              </el-table-column>
              
          </el-table-column>
    </el-table>
<!-- 给一个俩物种之间同源基因大列表 -->
    <el-table
          :data="tableData"
          border
          class="speInfoTable">
          <el-table-column
              prop="date"
              label="Organism 1"
              width="180">
          </el-table-column>
          <el-table-column
              prop="name"
              label="Organism 2"
              width="180">
          </el-table-column>
          <el-table-column
              prop="address"
              label="Ortholog GeneName">
          </el-table-column>
          <el-table-column
              prop="address"
              label="Ortholog GeneId">
          </el-table-column>
          <el-table-column
              prop="address"
              label="Map protein">
              <!-- <el-table-column
                label="hieranoid record">
                <template>
                  <el-link href="/db-detail">2567</el-link>
                </template>
              </el-table-column>
              <el-table-column label="treefam group">
                <template>
                  <el-link href="/db-detail">1799</el-link>
                </template>
              </el-table-column>
              <el-table-column label="Panther group">
                <template>
                  <el-link href="/db-detail">937</el-link>
                </template>
              </el-table-column>
              <el-table-column label="OMA group">
                <template>
                  <el-link href="/db-detail">0</el-link>
                </template>
              </el-table-column>
              <el-table-column label="EggNog group">
                <template>
                  <el-link href="/db-detail">0</el-link>
                </template>
              </el-table-column> -->
              
          </el-table-column>
          
    </el-table>
    </el-card>
  </div>
  <FooterBar />
</div>

</template>
<style scoped>
.table-container{
  width: 100%;
}
.speInfoTable{
  width: 90%;
  margin-top:30px;
  margin:2% 5%
}
.submitButton{
  margin-left: 5%;
  width: 8%;
}
.filterDiv{
  margin: 3% auto;
  width: 90%;
  /* height: 0; */
}
.el-card__body{
  padding: 0;
}
.titleHs{
  text-align:left;
  margin-left:5%;
}
.orthoTable{
  margin-top: 3%;
}
</style>
<script>
// @ is an alias to /src
// import Subnav from '@/components/sub-nav.vue'
import Navigator from '@/components/navigator.vue';
// import "@/assets/css/traits.css";
import FooterBar from '@/components/FooterBar.vue'
import HeaderBar from '../components/HeaderBar.vue'
export default {
  name: 'Species',
  components: {
    Navigator,
    FooterBar,
    HeaderBar
  },
  data(){
      return {
        tableData: [{
          date: 'Gallus gallus',
          name: 'Bovin',
          address: '3631'
        }],
        multipleSelection: [],
        options: [{
          value: '选项1',
          label: 'Gallus gallus'
        }, {
          value: '选项2',
          label: 'Bovin'
        }],
        value1: '',
        value2: ''
      }
    },

    methods: {
      
  },
  
}
</script>
