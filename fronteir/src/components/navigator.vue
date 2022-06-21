<template>
    <div class="navigator-div">
      <el-menu
        mode="horizontal"
        router
        background-color="rgba(0, 23, 55,1)"
        text-color="white"
      >
      <el-menu-item index="/" >
        <i class="el-icon-location"></i>
        <span slot="title">Home</span>
      </el-menu-item>
      <el-submenu index="2" text-color="#FFFFFF" >
        <template slot="title"><i class="el-icon-menu"></i>Browse</template>
        <el-menu-item index="/gene" >Homolog Gene</el-menu-item>
        <!-- <el-menu-item index="/gene-detail">Gene</el-menu-item> -->
        <el-menu-item index="/traits" >Traits</el-menu-item>
        <el-menu-item index="/variants" >Variants</el-menu-item>
        <el-menu-item index="/expression" >Expression</el-menu-item>
        <el-menu-item index="/go" >Gene Ontology</el-menu-item>
        <el-menu-item index="/species" >Species</el-menu-item>
      </el-submenu>
      <el-menu-item index="documentation">
        <i class="el-icon-document"></i>
        <span slot="title">Documentation</span>
      </el-menu-item>
      <el-menu-item index="downloads">
        <i class="el-icon-download"></i>
        <span slot="title">Downloads</span>
      </el-menu-item>
      <el-menu-item index="statistics">
        <i class="el-icon-setting"></i>
        <span slot="title">Statistics</span>
      </el-menu-item>
      <!-- <el-menu-item index="6">
        <i class="el-icon-document"></i>
        <span slot="title">Contact</span>
      </el-menu-item> -->
      </el-menu>
  </div>
</template>
<style>


.navigator-div{
  /* background-color: rgb(84, 92, 100); */
  height: 50px;
  background-color: rgb(0, 23, 55,1) !important;
}
.el-menu--horizontal>.el-submenu .el-submenu__title{
  height: 50px;
  line-height: 50px;
  
}
.el-menu{
  display: block !important;
}
.el-menu-item{
  color: white !important;
  background-color: rgba(0, 23, 55,1) !important;
}
.el-menu-item .is-active{
  color: white !important;
  background-color: rgba(0, 23, 55,0.7) !important;
}

.el-submenu__title{
  color: white !important;
  background-color: rgb(0, 23, 55) !important;
}
  .el-submenu__title .is-active{
  color: white !important;
  background-color: rgb(0, 23, 55) !important;
}
.el-submenu__title:hover{
  color: white !important;
  background-color: rgb(89, 116, 154) !important;
}
.el-menu-item:hover{
  background-color: rgb(89, 116, 154) !important;
}
.browse div{
   color: white !important;
 }
.el-icon-menu{
  color: white !important;
}
.el-menu--horizontal>.el-menu-item{
  height: 50px;
  line-height: 50px;
}

.navModule{
  color: white !important;
}
</style>
<script lang="ts">
export default {
  name: 'Navigator'
}

</script>