<template>
    <div class="navigator-div">
      <el-menu
        mode="horizontal"
        router
        width="100%"
        background-color="rgba(12, 100, 182,0.1)"
        text-color="white"
        style="font-size:18px;height: 50px;"
      >
      <el-menu-item index="/" >
        <i class="el-icon-location"></i>
        <span slot="title">Home</span>
      </el-menu-item>
      <el-submenu index="2"  >
        <template slot="title"><i class="el-icon-menu"></i>Browse</template>
        <el-menu-item index="/gene" class="submenu">Homologous Gene</el-menu-item>
        <!-- <el-menu-item index="/gene-detail">Gene</el-menu-item> -->
        <el-menu-item index="/traits" class="submenu">Traits</el-menu-item>
        <el-menu-item index="/variants" class="submenu">Variants</el-menu-item>
        <el-menu-item index="/expression" class="submenu">Expression</el-menu-item>
        <el-menu-item index="/go" class="submenu">Gene Ontology</el-menu-item>
        <el-menu-item index="/species" class="submenu">Species</el-menu-item>
      </el-submenu>
      <el-menu-item index="/documentation">
        <i class="el-icon-document"></i>
        <span slot="title">Documentation</span>
      </el-menu-item>
      <el-menu-item index="/downloads">
        <i class="el-icon-download"></i>
        <span slot="title">Downloads</span>
      </el-menu-item>
      <el-menu-item index="/statistics">
        <i class="el-icon-setting"></i>
        <span slot="title">Statistics</span>
      </el-menu-item>
      <el-menu-item index="/contact">
        <i class="el-icon-document"></i>
        <span slot="title">Contact</span>
      </el-menu-item>
      </el-menu>
  </div>
</template>
<style>


/* .navigator-div{
  height: 50px;
  background-color: rgb(0, 23, 55,1) !important;
} */
.navigator-div{
  padding-bottom: 0;
  position: absolute !important;
  bottom: 0;
  width: 100%;
  height: 50px;
}
.el-menu--horizontal>.el-submenu .el-submenu__title{
  height: 50px;
  line-height: 50px;
  font-size: 16px !important;
  background-color: rgba(12, 100, 182,0) !important;
}
.el-menu{
  display: block !important;
}
.el-menu-item{
  font-size: 16px !important;
  background-color: rgba(12, 100, 182,0) !important;
  height: 50px !important;
  line-height: 50px !important;
}
.el-menu-item > i{
  color: white !important;
}


.el-submenu__title{
  font-size: 16px !important;
  background-color: rgb(121,169,215,0) !important;
  height: 50px !important;
  line-height: 50px !important;
}

.submenu{
  font-size: 16px !important;
  background-color: rgb(99,176,233) !important;
  height: 36px !important;
  line-height: 36px !important;
}
.el-submenu__title > i{
 color: white !important;
}

.el-menu-item:hover{
  color: white !important;
  background-color: rgba(121,169,215) !important;
}

.el-submenu__title:hover{
  color: white !important;
  background-color: rgba(121,169,215) !important;
}


</style>
<script lang="ts">
export default {
  name: 'Navigator'
}

</script>