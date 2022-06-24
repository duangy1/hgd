<template>
  <div>
    <HeaderBar />
    <Banner />
    <!-- <Navigator /> -->
    <el-breadcrumb separator-class="el-icon-arrow-right" class="arrow-title">
      <el-breadcrumb-item :to="{ path: '/' }">Browse</el-breadcrumb-item>
      <el-breadcrumb-item>Downloads</el-breadcrumb-item>
    </el-breadcrumb>
    <el-container style="border: 1px solid #eee;overflow: hidden;">
        <div style="width:92%;margin: auto;">
        <!-- <div class="container"> -->
          <div class="wrapper-box">
            <el-card style="width:100%;border-top: 2px solid rgb(64, 158, 255)!important;">
           
          
              <el-container>
                  <el-aside width="250px" style="overflow: hidden;">
                      <!-- <img src="../assets/img/arrow1.png" style="width:100%;vertical-align:top;margin:10px 0;"> -->
                      <el-menu
                          default-active="2"
                          style="text-align: left;"
                          width="100%"
                          :class="navBarFixed == true ? 'navBarWrap' :''"
                          @select="selectMenu">
                      <el-menu-item index="1">
                          <i class="el-icon-chat-line-square"></i>
                          <span slot="title">Homolog Protein</span>
                      </el-menu-item>
                      <el-menu-item index="2">
                          <i class="el-icon-chat-line-square"></i>
                          <span slot="title">Trait Files</span>
                      </el-menu-item>
                      <el-menu-item index="3">
                          <i class="el-icon-chat-line-square"></i>
                          <span slot="title">Variation Gene</span>
                      </el-menu-item>
                      <el-menu-item index="4">
                          <i class="el-icon-chat-line-square"></i>
                          <span slot="title">Gene Ontology</span>
                      </el-menu-item>
                      <el-menu-item index="5">
                          <i class="el-icon-chat-line-square"></i>
                          <span slot="title">Expression</span>
                      </el-menu-item>
                      
                    </el-menu>
                  </el-aside>
                    <el-main>
                      <div v-for="item of tableData" :key="item.id" class="div-speciesfile" id="section-1">
                       <el-descriptions class="margin-top" :title=item.name :column="3" :size="size" border >
                       <!-- <template slot="extra">
                          <el-button type="primary" size="small">操作</el-button>
                        </template> -->
                       <el-descriptions-item  style="text-align:center !important;" v-for="item of item.data" :key="item.subid">
                          <a class="filename"> {{item.name}}</a>
                        </el-descriptions-item>
                        
                      </el-descriptions>
                      </div>
                      <div v-for="item of traitData" :key="item.id" class="div-speciesfile" id="section-2">
                          <el-descriptions class="margin-top" :title=item.name :column="3" :size="size" border >
                        <!-- <template slot="extra">
                            <el-button type="primary" size="small">操作</el-button>
                          </template> -->
                        <el-descriptions-item  style="text-align:center !important;" v-for="item of item.data" :key="item.subid">
                            <a class="filename"> {{item.name}}</a>
                          </el-descriptions-item>
                          
                        </el-descriptions>
                     </div>
                     <div v-for="item of varData" :key="item.id" class="div-speciesfile" id="section-3">
                          <el-descriptions class="margin-top" :title=item.name :column="3" :size="size" border >
                        <!-- <template slot="extra">
                            <el-button type="primary" size="small">操作</el-button>
                          </template> -->
                        <el-descriptions-item  style="text-align:center !important;" v-for="item of item.data" :key="item.subid">
                            <a class="filename"> {{item.name}}</a>
                          </el-descriptions-item>
                          
                        </el-descriptions>
                     </div>
                     <div v-for="item of goData" :key="item.id" class="div-speciesfile" id="section-4">
                          <el-descriptions class="margin-top" :title=item.name :column="3" :size="size" border >
                        <!-- <template slot="extra">
                            <el-button type="primary" size="small">操作</el-button>
                          </template> -->
                        <el-descriptions-item  style="text-align:center !important;" v-for="item of item.data" :key="item.subid">
                            <a class="filename"> {{item.name}}</a>
                          </el-descriptions-item>
                          
                        </el-descriptions>
                     </div>
                     <div v-for="item of expData" :key="item.id" class="div-speciesfile" id="section-5">
                          <el-descriptions class="margin-top" :title=item.name :column="3" :size="size" border >
                        <!-- <template slot="extra">
                            <el-button type="primary" size="small">操作</el-button>
                          </template> -->
                        <el-descriptions-item  style="text-align:center !important;" v-for="item of item.data" :key="item.subid">
                            <a class="filename"> {{item.name}}</a>
                          </el-descriptions-item>
                          
                        </el-descriptions>
                     </div>
                  </el-main>
              </el-container>
            </el-card>
            </div>
        </div>
    </el-container>
    <template>
    <el-backtop
      :bottom="100"
      style="z-index: 999;"
      class="backTop"
    >
      <div
        style="
           {
            height: 100%;
            width: 100%;
            box-shadow: 0 0 6px rgba(0, 0, 0, 0.12);
            text-align: center;
            line-height: 40px;
            color: #1989fa;
            border-radius: 50%;
            background-color: white;
          }
        "
      >
      <i class="el-icon-caret-top"></i>
      </div>
    </el-backtop>
    </template>
    <FooterBar />
  </div>
</template>

<style src="../assets/css/download.css" scoped></style>
<style src="../assets/css/documentation.css" scoped></style>
<script>
// import Navigator from "@/components/navigator.vue";
import Banner from "@/components/banner.vue";
import FooterBar from "@/components/FooterBar.vue";
import HeaderBar from "@/components/HeaderBar.vue";
import fileInfo from "@/assets/static/download.json";

export default {
  name: 'Downloads',
  components: {
    // Navigator,
    FooterBar,
    HeaderBar,
    Banner,
    
  },
  data() {
      return {
        tableData: fileInfo.tableData,
        traitData: fileInfo.traitData,
        varData: fileInfo.varData,
        goData: fileInfo.goData,
        expData: fileInfo.expData
      }
    },
  methods:{
    selectMenu(key){
      console.log("key:",key);
      const target = document.querySelector("#section-"+key)
      target.scrollIntoView({ behavior: 'smooth' })
      // scrollTo() 把内容滚动到指定的坐标。减去导航高度的目的：导航用定位固定在顶部，如果不减去，导航栏会遮挡一部分内容
      window.scrollTo(0, target.offsetTop)
    },
  }

}
</script>