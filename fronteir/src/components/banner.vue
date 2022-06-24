<template>
    <div class="banner-box">
      <div style="float:left;background-color: white;border-radius: 50%;width: 100px;height: 100px;margin-top: 30px;margin-right: 20px;margin-left: 10px;">
       <img :src="logo"  style="width:150px;height:150px;margin-top: -18px;margin-left: -18px;" />
      </div>
      
      <div class="wrapper">
        <div class="module-title">Homolog Gene Database</div>
        <div class="subtitle">A data portal of homolog genes across multiple species</div>
      </div>
      <Navigator />
    </div>
    
</template>

<script>
import logo from "@/assets/img/icon/logo.png";
import Navigator from '@/components/navigator.vue';
export default {
  name: 'Banner',
  components: {
    Navigator
  },
  data(){
    return {logo
    }
  }

}
</script>
<style scoped>
.banner-box{
  background-image: url("../assets/img/banner-2.png");
  color: white;
  height: 200px;
  width: 100%;
  background-repeat: no-repeat;
  background-position: right;
  background-size: cover;
  padding: 0 !important;
  /* background-attachment: fixed; */
  position: relative;
  margin-top: -35px;
  display: table;
  /* opacity: 0; */
}
.module-title{
  text-align: left;
  font-weight: 700;
  /* color: rgb(222, 222, 222); */
  /* color: rgb(232, 232, 232); */
  padding-top:18px;
  /* padding-left: 1.4%; */
  /* font-size: 50px; */
  color: #f2f2f2;
  font-size: 42px;
}
.subtitle{
  font-size: 23px;
  color: #fefefe;
  text-align: left;
}
.wrapper{
  margin-top: 25px;
  /* margin-left: 10px; */
}
</style>