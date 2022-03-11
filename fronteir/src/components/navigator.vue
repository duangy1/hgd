<template>
    <div class="navigator-div">
      <el-menu
        text-color="#fff"
        background-color="rgb(0, 23, 55) !important;"
        style="width:100%;height:56px"
      >
        <el-menu-item index="1" >
          <a href="http://localhost:9405/" class="navModule">
          <i class="el-icon-location"></i>
          <span slot="title">Home</span>
          </a>
        </el-menu-item>
        <el-submenu index="2" text-color="#FFFFFF">
          <template slot="title"><i class="el-icon-menu"></i>Browse</template>
          <div style="display:block;z-index:6;text-align:center">
            <a href="http://localhost:9405/gene" class="navModule"><el-menu-item index="2-3" >Ortholog Gene</el-menu-item></a>
            <a href="http://localhost:9405/traits" class="navModule"><el-menu-item index="2-1" >Traits</el-menu-item></a>
            <a href="http://localhost:9405/variants" class="navModule"><el-menu-item index="2-1" >Variants</el-menu-item></a>
            <a href="http://localhost:9405/species" class="navModule"><el-menu-item index="2-2" >Species</el-menu-item></a>
            
          </div>
        </el-submenu>
        <el-menu-item index="3">
          <i class="el-icon-document"></i>
          <span slot="title">Tools</span>
        </el-menu-item>
        <el-menu-item index="4">
          <i class="el-icon-download"></i>
          <span slot="title">Downloads</span>
        </el-menu-item>
        <el-menu-item index="5">
          <i class="el-icon-setting"></i>
          <span slot="title">Statistics</span>
        </el-menu-item>
        <el-menu-item index="6">
          <i class="el-icon-document"></i>
          <span slot="title">Contact Us</span>
        </el-menu-item>
      </el-menu>
    </div>
</template>
<style scoped>
.navigator-div{
  /* background-color: rgb(84, 92, 100); */
  background-color: rgb(0, 23, 55);
}
  .el-menu-item{
    color: white !important;
    font-size: 15px;
    background-color: rgb(0, 23, 55) !important;
    /* background-color: rgb(84, 92, 100); */
  }
  .el-menu-item i{
      color: white !important;
  }
  .el-submenu__title{
    color: white !important;
    background-color: rgb(0, 23, 55) !important;
  }
 .submenu-item{
  height: 20px;
  margin-top: 10px;
  width: 100%;
  text-align:left; 
  /* background-color: black; */
 }
 .browse div{
   color: white !important;
 }
.el-icon-menu{
  color: white !important;
}
.el-submenu__title{
  color: white;
}
.el-menu--horizontal>div>.el-submenu {
    float: left;
}
.el-menu--horizontal>div>.el-menu-item {
    float: left;
    height: 60px;
    line-height: 60px;
    margin: 0;
    border-bottom: 2px solid transparent;
    color: #909399;
    background-color: rgb(0, 23, 55) !important;
}
.navModule{
  font-size: 16px !important;
  color: white !important;
}
</style>
<script lang="ts">
export default {
  name: 'Navigator'
}

</script>