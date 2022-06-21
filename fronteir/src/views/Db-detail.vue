<template>
<div class="table-container">
    <Subnav />    
    <h4 style="clear-left;float:left">Organism/InParanoid</h4>  
    <h1 style="margin-top:80px">11360 clusters for Gallus gallus and Bos taurus in InParanoid</h1>  
     
    <el-table
        :data="tableData"
        border
        style="width: 100%;margin-top:30px">
        <el-table-column
            prop="date"
            label="Cluster name"
            width="180">
        </el-table-column>
        <el-table-column
            prop="name"
            label="Organism 1"
            width="180">
        </el-table-column>
        <el-table-column
            prop="address"
            label="Protein name">
        </el-table-column>
        <el-table-column
            prop="pname1"
            label="Organism2">
        </el-table-column>
        <el-table-column
            prop="pname2"
            label="Protein name">
        </el-table-column>
  </el-table>
</div>

</template>

<script>
// @ is an alias to /src
import Subnav from '@/components/sub-nav.vue'
// import "@/assets/css/traits.css";
export default {
  name: 'dbDetail',
  components: {
    Subnav
  },
  data(){
      return {
        tableData: [{
          date: 'Cluster142',
          name: 'Bovin',
          pname1: 'E1C4D8',
          pname2: 'Q8IL19'
        }],
        
      }
    },

    methods: {
      
  },
  
}
</script>
