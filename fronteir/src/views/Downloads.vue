<template>
  <div>
    <HeaderBar/>
    <Banner/>
    <!-- <Navigator/> -->
    <el-breadcrumb class="arrow-title" separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">Home</el-breadcrumb-item>
      <el-breadcrumb-item>Downloads</el-breadcrumb-item>
    </el-breadcrumb>
    <el-container style="border: 1px solid #eee;overflow: hidden;">
      <div style="width:92%;margin: auto;">
        <!-- <div class="container"> -->
        <div class="wrapper-box">
          <el-card style="width:100%;border-top: 2px solid rgb(64, 158, 255)!important;">


            <el-container>

              <el-aside style="overflow: hidden;" width="250px">
                <!-- <img src="../assets/img/arrow1.png" style="width:100%;vertical-align:top;margin:10px 0;"> -->
                <el-menu
                    :class="navBarFixed == true ? 'navBarWrap' :''"
                    default-active="2"
                    style="text-align: left;"
                    width="100%"
                    @select="selectMenu">
                  <el-menu-item index="1"  @click="tableShow(1)">
                    <i class="el-icon-chat-line-square"></i>
                    <span slot="title">Homologous Protein</span>
                  </el-menu-item>
                  <el-menu-item index="2"  @click="tableShow(6)">
                    <i class="el-icon-chat-line-square"></i>
                    <span slot="title">Homologous Gene</span>
                  </el-menu-item>
                  <el-menu-item index="3"  @click="tableShow(2)">
                    <i class="el-icon-chat-line-square"></i>
                    <span slot="title">Trait Files</span>
                  </el-menu-item>
                  <el-menu-item index="4"  @click="tableShow(3)">
                    <i class="el-icon-chat-line-square"></i>
                    <span slot="title">Variation Gene</span>
                  </el-menu-item>
                  <el-menu-item index="5"  @click="tableShow(4)">
                    <i class="el-icon-chat-line-square"></i>
                    <span slot="title">Gene Ontology</span>
                  </el-menu-item>
                  <el-menu-item index="6"  @click="tableShow(5)">
                    <i class="el-icon-chat-line-square"></i>
                    <span slot="title">Expression</span>
                  </el-menu-item>

                </el-menu>

              </el-aside>
              <el-main>
                <div v-for="item of tableData" v-show="show.a" id="section-1" :key="item.id"
                     class="div-speciesfile" >
                  <el-descriptions :column="3" :size="size" :title=item.name border class="margin-top">
                    <!-- <template slot="extra">
                       <el-button type="primary" size="small">操作</el-button>
                     </template> -->
                    <el-descriptions-item v-for="item of item.data" :key="item.subid"
                                          style="text-align:center !important;">
                      <a class="filename download" v-bind:href="item.address"> {{ item.name }}</a>
                    </el-descriptions-item>

                  </el-descriptions>
                </div>

                <div v-for="item of geneData" v-show="show.f" id="section-2" :key="item.id"
                     class="div-speciesfile" >
                  <el-descriptions :column="3" :size="size" :title=item.name border class="margin-top">
                    <!-- <template slot="extra">
                       <el-button type="primary" size="small">操作</el-button>
                     </template> -->
                    <el-descriptions-item v-for="item of item.data" :key="item.subid"
                                          style="text-align:center !important;">
                      <a class="filename download" v-bind:href="item.address"> {{ item.name }}</a>
                    </el-descriptions-item>

                  </el-descriptions>
                </div>


                <div v-for="item of traitData" v-show="show.b" id="section-3" :key="item.id"
                     class="div-speciesfile Filelayout" >
                  <el-descriptions :column="3" :size="size"  border class="margin-top">
                    <!-- <template slot="extra">
                        <el-button type="primary" size="small">操作</el-button>
                      </template> -->
                    <el-descriptions-item v-for="item of item.data" :key="item.subid"
                                          style="text-align:center !important;">
                      <a class="filename download" v-bind:href="item.address"> {{ item.name }}</a>
                    </el-descriptions-item>

                  </el-descriptions>
                </div>
                <div v-for="item of varData" v-show="show.c" id="section-4" :key="item.id"
                     class="div-speciesfile Filelayout">
                  <el-descriptions :column="3" :size="size" border class="margin-top">
                    <!-- <template slot="extra">
                        <el-button type="primary" size="small">操作</el-button>
                      </template> -->
                    <el-descriptions-item v-for="item of item.data" :key="item.subid"
                                          style="text-align:center !important;">
                      <a class="filename download" v-bind:href="item.address"> {{ item.name }}</a>
                    </el-descriptions-item>

                  </el-descriptions>
                </div>
                <div v-for="item of goData" v-show="show.d" id="section-5" :key="item.id"
                     class="div-speciesfile Filelayout">
                  <el-descriptions :size="size"  :column="3"  border >
                    <!-- <template slot="extra">
                        <el-button type="primary" size="small">操作</el-button>
                      </template> -->
                    <el-descriptions-item v-for="item of item.data" :key="item.subid"
                                          style="text-align:center !important;">
                      <a class="filename download" v-bind:href="item.address"> {{ item.name }}</a>
                    </el-descriptions-item>

                  </el-descriptions>
                </div>
                <div v-for="item of expData" v-show="show.e" id="section-6" :key="item.id"
                     class="div-speciesfile Filelayout">
                  <el-descriptions :column="3" :size="size" border class="margin-top">
                    <!-- <template slot="extra">
                        <el-button type="primary" size="small">操作</el-button>
                      </template> -->
                    <el-descriptions-item v-for="item of item.data" :key="item.subid"
                                          style="text-align:center !important;">
                      <a class="filename download" v-bind:href="item.address"> {{ item.name }}</a>
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
          class="backTop"
          style="z-index: 999;"
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
    <FooterBar/>
  </div>
</template>

<style scoped src="../assets/css/download.css"></style>
<style scoped src="../assets/css/documentation.css"></style>
<style scoped>
.download {
  text-decoration: none;
  font-size:15px;
}
.Filelayout{
  width: 25%;
  float: left
}


</style>


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
      expData: fileInfo.expData,
      geneData: fileInfo.geneData,
      tabPosition: 'left',
      size:"",
      size2:"medium",
      show: {
        a: true,
        b: false,
        c: false,
        d: false,
        e: false,
        f:false
      }
    }

  },
  methods: {
    selectMenu(key) {
      console.log("key:", key);
      const target = document.querySelector("#section-" + key)
      target.scrollIntoView({behavior: 'smooth'})
      // scrollTo() 把内容滚动到指定的坐标。减去导航高度的目的：导航用定位固定在顶部，如果不减去，导航栏会遮挡一部分内容
      window.scrollTo(0, target.offsetTop)
    },
    tableShow(key) {
      console.log(key);
      console.log("hello")
      if (key == 1) {
        this.show.a = true;
        this.show.b = false;
        this.show.c = false;
        this.show.d = false;
        this.show.e = false;
        this.show.f = false;
      } else if (key == 2) {
        this.show.a = false;
        this.show.b = true;
        this.show.c = false;
        this.show.d = false;
        this.show.e = false;
        this.show.f = false;
      } else if (key == 3) {
        this.show.a = false;
        this.show.b = false;
        this.show.c = true;
        this.show.d = false;
        this.show.e = false;
        this.show.f = false;
      } else if (key == 4) {
        this.show.a = false;
        this.show.b = false;
        this.show.c = false;
        this.show.d = true;
        this.show.e = false;
        this.show.f = false;
      } else if (key == 5) {
        this.show.a = false;
        this.show.b = false;
        this.show.c = false;
        this.show.d = false;
        this.show.e = true;
        this.show.f = false;
      }
    else if (key == 6) {
          this.show.a = false;
          this.show.b = false;
          this.show.c = false;
          this.show.d = false;
          this.show.e = false;
          this.show.f = true;
        }
    }
  }

}
</script>