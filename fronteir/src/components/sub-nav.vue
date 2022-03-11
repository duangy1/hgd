<template>
    <div class="navigator-div">
          <el-menu
            default-active="2"
            class="el-menu-vertical-demo"
            text-color="#000000">
            <el-menu-item index="1" @click.native="goHome">
              <i class="el-icon-location"></i>
              <span slot="title">Home</span>
            </el-menu-item>
            
            <el-submenu index="2" >
              
              <template slot="title">
                <i class="el-icon-menu"></i>Browse
              </template>
              <!-- <el-menu-item index="2-3">选项3</el-menu-item> -->
              <div style="width:100px;display:block;z-index:6">
                <el-menu-item index="2-1">Traits</el-menu-item>
                <el-menu-item index="2-2">Species</el-menu-item>
                <el-menu-item index="2-3">Ortholog Gene</el-menu-item>
              </div>
            </el-submenu>
            <el-menu-item index="3">
              <i class="el-icon-document"></i>
              <span slot="title">Tools</span>
            </el-menu-item>
            <el-menu-item index="3">
              <i class="el-icon-download"></i>
              <span slot="title">Downloads</span>
            </el-menu-item>
            <el-menu-item index="4">
              <i class="el-icon-setting"></i>
              <span slot="title">Statistics</span>
              
            </el-menu-item>
            <el-menu-item index="3">
              <i class="el-icon-document"></i>
              <span slot="title">Contact Us</span>
            </el-menu-item>
          </el-menu>
        </div>
</template>
<style scoped>
.el-menu-item{
    color: black !important;
    font-size: 15px;
}
.el-submenu__title {
  color: black !important;
}
.el-submenu{
  color: black !important;
}
.el-menu--inline{
  display: block !important;
}
.el-submenu .el-menu{
  display: block !important;
}
.submenu-item{
  height: 20px;
  margin-top: 10px;
  width: 110%;
  text-align:left; 
  background-color: white !important;
}
.navigator-div{
  background-color: white !important;
}


</style>
<script lang="ts">
export default {
  name: 'Sub-nav',
  methods:{
      goHome(){
      this.$router.replace('/') 
  }
  }
}
</script>