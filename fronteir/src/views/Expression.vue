<template>
  <div>
    <HeaderBar />
    <Banner />
    <!-- <Navigator /> -->
    
    <!-- 左侧树结构 -->
    <!-- 抽屉结构 -->
    <el-drawer
      :visible.sync="drawer"
      :direction="direction"
    >
      <div class="tree-div">
      <el-card shadow="none" class="tree-card" >
      <el-input placeholder="Enter keywords to filter" v-model="filterText" class="filterInput"> 
      </el-input>

      <el-tree
        style="margin-top: 15px"
        class="filter-tree"
        :data="treedata"
        default-expand-all
        :filter-node-method="filterNode"
        icon-class="el-icon-s-management"
        ref="tree"
        :expand-on-click-node="false"
        @node-click="getInfoByTrait"
      >
      </el-tree>
      </el-card>
    </div>
    </el-drawer>
    <el-breadcrumb separator-class="el-icon-arrow-right" class="arrow-title">
      <el-breadcrumb-item :to="{ path: '/gene' }">Browse</el-breadcrumb-item>
      <el-breadcrumb-item>Expression</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 侧边栏按钮 -->
  
  <el-container style="border: 1px solid #eee;overflow: hidden;" id="trait-container">
  <div style="margin-top:3%">
  <div class="button-wrapper">
   <el-button @click="buttonFunction()" ref="button" id="button"></el-button>
   <!-- <div class="button-text">Trait Ontology</div> -->
  </div>
   <!-- 灰色title横幅 -->
   <transition name="el-zoom-in-top">
    <div class="trait-box trait-infoBox" v-show="showTitle">
      <div style="padding-left:29px;margin-top: -15px;">
        <h2 class="trait-title">Expression : {{ expItem.expName }} </h2>
      </div>
      <el-button @click="clearTraitFilter" icon="el-icon-remove-outline" round class="remove-button">Remove filter</el-button>
    </div>
    </transition>
    </div>
    <el-card shadow="none" class="border-card">
    <el-tabs v-model="classification" style="margin-top:10px;margin-bottom: 1%;" @tab-click="changeClass">
    <!-- 动物tab -->
    <el-tab-pane label="Animal" name="animal" >
      <!-- <el-card shadow="none" class="tableCard" style="margin-top: 10px"> -->
      <div>
        <div>
            <div class="filt-div">
                <el-input v-model="form.gene" placeholder="Query gene" class="gene-input" clearable></el-input>
                <el-select v-model="form.querySpecies" class="filt-spe-select" placeholder="Query species" clearable>
                    <el-option
                        v-for="(item,index) in querySpeciesList_animal"
                        :key="index"
                        :label="item.commonName"
                        :value="item.taxonId"
                        >
                    </el-option>
                </el-select>
                <el-button icon="el-icon-search" type="success"  plain id="filter-search" @click="searchFilter()">Search</el-button>
                <el-button icon="el-icon-delete" type="primary"  plain id="filter-search2" @click="clearFilter()">Clear</el-button>
                <el-button type="primary" icon="el-icon-download" plain id="filterCol" @click="exportToExcel('animal')"></el-button>

                <el-popover
                  placement="bottom-end"
                  width="400"
                  trigger="manual"
                  class="popbox"
                  v-model="visible_1">
                    <div class="checkbox-box">
                      <el-checkbox v-for="item of speciesList_animal_1" :key="item.commonName" :label="item.commonName" class="chooseColBox" ref="one" v-model="item.checked"></el-checkbox>
                    </div>
                    <div class="botton-wrapper">
                      <el-button size="mini" type="primary"  @click="confirmbutton" class="choose-col-button">Close</el-button>
                      <!-- <el-button size="mini" type="text" plain @click="visible_1 = false" class="choose-col-button">cancel</el-button> -->
                    </div>
                  <el-button slot="reference" icon="el-icon-s-grid" type="primary" plain id="filterCol" @click="visible_1 = !visible_1" :disabled="speciesList_animal_1.length==0"></el-button>
                </el-popover>



            </div>
          <!-- </el-card> -->
          <div id="wrapper"  >
          <div class="trait-info">
            
      <!-- 使用中的trait表格 -->
      <!-- <el-container style="width:100%;border: 1px solid darkgray;"> -->
        
          <el-table
            :data="traitData_animal"
            class="trait-table"
            row-key="id"
            ref="table"
            :header-cell-style="hiligtDbCols"
            v-loading="loading_animal"
            border
          >
            <el-table-column align="center" class="titleCell" prop="annotation" label="Expression" fixed width="280px" style="background-color:white"></el-table-column>
            <el-table-column align="center" prop="geneId" label="Gene Id" width="220px" fixed>
              <template slot-scope="scope">
                <a :href="'./gene-detail?hdbId='+scope.row.hdbId+'&taxonId='+scope.row.taxonId">
                    {{ scope.row.geneId }}
                </a>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="commonName" label="Species Name" width="150px" fixed></el-table-column>
            <el-table-column align="center" prop="taxonId" label="Taxon Id" width="160px" fixed>
              <template slot-scope="scope">
                <a :href="'https://www.ncbi.nlm.nih.gov/Taxonomy/Browser/wwwtax.cgi?mode=Info&id='+scope.row.taxonId+'&lvl=3&lin=f&keep=1&srchmode=1&unlock'">
                    {{ scope.row.taxonId }}
                </a>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="speciesCommonName" label="Homologous Species Name">
            <template v-for="(item,index) in speciesList_animal_1">
              <el-table-column align="center" :label="item.commonName" :prop="item.commonName" v-if="item.checked" :key="item.commonName">
                <template slot-scope="scope" >
                  <img
                    :src="orthoIcon"
                    min-width="70"
                    height="70"
                    class="iconImg"
                    v-if='scope.row.speciesListData.indexOf(index)>-1'
                    style="cursor:pointer !important"
                    @click=showOrthoInfoTable(scope.row,index)
                  />
                  <!--这个v-if，先判断在当前的cell内有数值，然后再判断值 -->
                  <img
                    :src="singleTraitIcon"
                    min-width="70"
                    height="70"
                    class="iconImg"
                    v-if='scope.row.traitListData2.indexOf(index)>-1'
                    style="cursor:pointer !important"
                    @click=showGwasInfoTable(scope.row,index)
                  />
                  <img
                    :src="sameTraitIcon"
                    min-width="70"
                    height="70"
                    class="iconImg"
                    v-if='scope.row.traitListData1.indexOf(index)>-1'
                    style="cursor:pointer !important"
                    @click=showGwasInfoTable(scope.row,index)
                  />
                </template>
              </el-table-column>
            </template>
            </el-table-column>
          </el-table>
      
          <div style="position: absolute;float: left;">
          <div style="display: flex;">
                    <img :src="orthoIcon"
                      style="margin-right: 6px;min-width=70px;height=70px;"
                      class="iconImg" />
                      <div class="note-info">exists homologous gene</div>
          </div>
          <div style="display: flex;">
                    <img :src="singleTraitIcon"   
                      style="margin-right: 6px;min-width=70px;height=70px;"
                      class="iconImg" /><div class="note-info">exists homologous gene with expression annotation</div>  
          </div>
          <div style="display: flex;">
                    <img :src="sameTraitIcon"   
                      style="margin-right: 6px;min-width=70px;height=70px;"
                      class="iconImg" /><div class="note-info">exists homologous gene with same expression annotation</div>  
          </div>
          
          </div>
          <el-pagination
          
            class="trait-pag"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage4_a"
            :page-sizes="[10, 20, 50, 100]"
            :page-size="10"
            layout="total, sizes, prev, pager, next, jumper"
            :total="totalSize_animal"
          >
          </el-pagination>
      <!-- </el-container> -->
      </div>
</div>

    <!-- 同源表格 -->
    <!-- <el-card shadow="none" class="sub-table-card"> -->
      
      <div class="sub-trait-box " v-if="showOrthoSubTable">
      <el-divider class="divider"></el-divider>
          <div class="title-box">
            <template>
              <el-descriptions title="Selected Gene" >
                <el-descriptions-item label="Gene Id"><a :href="'./gene-detail?hdbId='+selectGene.hdbId+'&taxonId='+selectGene.taxonId">
                    {{ selectGene.geneId }}
                </a></el-descriptions-item>
                <el-descriptions-item label="Protein Id"><a :href="'https://www.uniprot.org/uniprot/'+selectGene.hdbId" target='_blank'>
                    {{ selectGene.hdbId }}
                </a></el-descriptions-item>
                <el-descriptions-item label="Species Name">{{selectGene.commonName}}</el-descriptions-item>
                <el-descriptions-item label="Classification">{{selectGene.classification}}</el-descriptions-item>
                <el-descriptions-item label="Expression">
                  <el-tag size="small">{{selectGene.annotation}}</el-tag>
                </el-descriptions-item>
                <!-- <el-descriptions-item label="Address"
                  >No.1188, Wuzhong Avenue, Wuzhong District, Suzhou, Jiangsu
                  Province</el-descriptions-item
                > -->
              </el-descriptions>
            </template>
            <el-divider></el-divider>
            <h2 class="trait-sub-title">Homologous Gene Detail Information</h2>
            <el-button type="primary" icon="el-icon-download" plain id="filterCol1" @click="exportToSubOrthoTableExcel(orthoTableData)"></el-button>

          </div>
      </div>
      <!-- <div id="wrapper"> -->
          <div class="trait-info">
            <el-table
              :data="orthoTableData"
              class="info-table"
              v-loading="orthoLoading"
              v-if="showOrthoSubTable"
              :border="false"
              fixed
              :header-cell-style="{background:'#F5F7FA'}"
          >
              <el-table-column
                  prop="commonName"
                  label="Species"
              >
              </el-table-column>
              <el-table-column
                  prop="tax"
                  label="Taxon Id"
              >
              <template slot-scope="scope">
                <a :href="'https://www.ncbi.nlm.nih.gov/Taxonomy/Browser/wwwtax.cgi?mode=Info&id='+scope.row.tax+'&lvl=3&lin=f&keep=1&srchmode=1&unlock'">
                    {{ scope.row.tax }}
                </a>
              </template>
              </el-table-column>
              <el-table-column
                  prop="ensemblGeneId"
                  label="Ensembl Id"
              >
              </el-table-column>
               <el-table-column
                prop="geneSymbol"
                label="Gene Symbol"
              >
              </el-table-column>
              <el-table-column
                  prop="hdbId"
                  label="Protein Id"
                >
                  <template slot-scope="scope">
                    <a :href="'https://www.uniprot.org/uniprot/'+scope.row.hdbId" target='_blank' v-if="scope.row.hdbId.slice(0,2)!=='EN'">
                        {{ scope.row.hdbId }}
                    </a>
                    <a :href="'https://asia.ensembl.org/Multi/Search/Results?q='+scope.row.hdbId+';site=ensembl'" target='_blank' v-if="scope.row.hdbId.slice(0,2)=='EN'">
                        {{ scope.row.hdbId }}
                    </a>
                  </template>
              </el-table-column>
              <el-table-column  label="Data Source" align="center">
                <template slot-scope="scope">
                    <el-popover
                        placement="top"
                        title="Data Source"
                        width="200"
                        trigger="hover"
                        :content="scope.row.dbEvidence">
                        <i class="el-icon-s-order" slot="reference" width="30px"></i>
                    </el-popover>
                    
                </template>
            </el-table-column>
            </el-table>
          </div>

      <!-- </div> -->
    <!-- </el-card> -->

      <!-- gwas 表格 -->
    <!-- <el-card shadow="none"  class="sub-table-card"> -->
       
      <div class="sub-trait-box" v-if="showSubTableBox">
        <div  class="title-box" >
          <h3 class="trait-sub-title">Expression Detail Information</h3>
          <el-button type="primary" icon="el-icon-download" plain id="filterCol1" @click="exportToSubGwasTableExcel(gwasInfoData)"></el-button>

        </div>
        <div id="trait-info">
            <!-- <el-card shadow="none" class="gwasDetailCard"> -->
              <el-table 
                :data="gwasInfoData"
                id="subTable"
                :row-style="{height: '0'}"
                max-height="800"
                v-loading="gwasLoading"
                :header-cell-style="{background:'#F5F7FA'}"
              >
                <el-table-column prop="geneId" label="Gene Id">
                  <template slot-scope="scope">
                      <a :href="'https://ngdc.cncb.ac.cn/gen/gene/'+scope.row.genOrgId+'/'+scope.row.geneId" target='_blank'>
                          {{ scope.row.geneId }}
                      </a>
                  </template>
                </el-table-column>
                <el-table-column prop="annotation" label="Expression"></el-table-column>
                <el-table-column prop="bioProjectIdList" label="Bioproject Id">
                  <template slot-scope="scope">
                    <div v-for="(item,index) of scope.row.bioProjectIdList" :key="index" style="display: flex;float:left">
                      <a :href="'https://ngdc.cncb.ac.cn/gen/project/accession/'+item" target='_blank'>
                          {{ item }}
                      </a>
                      <div v-if="index<scope.row.bioProjectIdList.length-1">,</div>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column prop="commonName" label="Species"></el-table-column>
                <el-table-column prop="taxonId" label="Taxon Id">
                  <template slot-scope="scope">
                    <a :href="'https://www.ncbi.nlm.nih.gov/Taxonomy/Browser/wwwtax.cgi?mode=Info&id='+scope.row.taxonId+'&lvl=3&lin=f&keep=1&srchmode=1&unlock'">
                        {{ scope.row.taxonId }}
                    </a>
                  </template>
                </el-table-column>
                <el-table-column label="Box-plot">
                  <template slot-scope="scope" >
                     
                    <el-popover
                      placement="left"
                      width="300"
                      trigger="manual"
                      class="popbox"
                      v-model=scope.row.showpop
                    >
                    <div class="checkbox-box">
                      <el-radio 
                        v-for="(item,index) in scope.row.bioProjectIdList"
                        :key="index"
                        :label="item"
                        v-model="scope.row.selectPrj">
                      </el-radio >
                    </div>
                      <div class="botton-wrapper">
                        <el-button size="mini" type="primary"  @click=showDetailExpression(scope.row) class="choose-col-button">Confirm</el-button>
                        <el-button size="mini" type="text" plain @click="scope.row.showpop = false" class="choose-col-button">Cancel</el-button>
                      </div>
                      <img
                      :src="detailIcon"
                      min-width="25"
                      height="25"
                      style="cursor:pointer !important"
                      slot="reference"
                      @click="scope.row.showpop = true"
                    />
                    </el-popover>
                   
                  </template>
                  
                </el-table-column>
              </el-table>
            <!-- </el-card> -->
        </div>
      </div>
    <!-- </el-card> -->
      </div>
      </div>
      <!-- </div> -->
      <!-- </el-card> -->
    </el-tab-pane>
     <!-- 植物tab -->
    <el-tab-pane label="Plant" name="plant">
      <div class="filt-div">
            <el-input v-model="form.gene" placeholder="Query gene" class="gene-input" clearable></el-input>
            <el-select v-model="form.querySpecies" class="filt-spe-select" placeholder="Query species" clearable>
                <el-option
                    v-for="(item,index) in querySpeciesList_plant"
                    :key="index"
                    :label="item.commonName"
                    :value="item.taxonId"
                    >
                </el-option>
            </el-select>
            <el-button icon="el-icon-search" type="success" plain id="filter-search" @click="searchFilter()">Search</el-button>
            <el-button icon="el-icon-delete" type="primary" plain id="filter-search2" @click="clearFilter()">Clear</el-button>
            <el-button type="primary" icon="el-icon-download" plain id="filterCol" @click="exportToExcel('plant')"></el-button>
            <el-popover
              placement="bottom-end"
              width="400"
              trigger="manual"
              class="popbox"
              v-model="visible">
                <div class="checkbox-box">
                  <el-checkbox v-for="item of speciesList_plant_1" :key="item.commonName" :label="item.commonName" class="chooseColBox" ref="one" v-model="item.checked"></el-checkbox>
                <!-- <el-checkbox  class="chooseColBox" @change="chooseall" ref="all">Choose All</el-checkbox> -->
                </div>
                <div class="botton-wrapper">
                  <el-button size="mini" type="primary"  @click="confirmbutton" class="choose-col-button">Close</el-button>
                  <!-- <el-button size="mini" type="text" plain @click="visible = false" class="choose-col-button">cancel</el-button> -->
                </div>
                <el-button slot="reference" icon="el-icon-s-grid" type="primary" plain id="filterCol" @click="visible = !visible" :disabled="speciesList_plant_1.length==0"></el-button>
              </el-popover>

      </div>
      <!-- <div class="wraper"> -->
        
      <div class="trait-info">
   <!-- 使用中的表格 -->
        <el-table
            :data="traitData_plant"
            class="trait-table"
            row-key="id"
            v-loading="loading_plant"
            ref="table"
            :header-cell-style="hiligtDbCols"
            border
          >
            <el-table-column align="center" class="titleCell" prop="annotation" label="Expression" fixed width="280px" style="background-color:white"></el-table-column>
            <el-table-column align="center" prop="geneId" label="Gene Id" width="220px" fixed>
              <template slot-scope="scope">
                <a :href="'./gene-detail?hdbId='+scope.row.hdbId+'&taxonId='+scope.row.taxonId">
                    {{ scope.row.geneId }}
                </a>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="commonName" label="Species Name" width="150px" fixed></el-table-column>
            <el-table-column align="center" prop="taxonId" label="Taxon Id" width="160px" fixed>
               <template slot-scope="scope">
                <a :href="'https://www.ncbi.nlm.nih.gov/Taxonomy/Browser/wwwtax.cgi?mode=Info&id='+scope.row.taxonId+'&lvl=3&lin=f&keep=1&srchmode=1&unlock'">
                    {{ scope.row.taxonId }}
                </a>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="speciesCommonName" label="Homologous Species Name">
            <template v-for="(item,index) in speciesList_plant_1">
              <el-table-column align="center" :label="item.commonName" :prop="item.commonName" v-if="item.checked" :key="item.commonName">
            <!-- <el-table-column :label="item" :show-overflow-tooltip="true" :prop="item" v-for="(item,index) in speciesList_plant" :key="index"
              class="imglink" height="100px"> -->
                  <template slot-scope="scope" >
                   <img
                    :src="orthoIcon"
                    min-width="70"
                    height="70"
                    class="iconImg"
                    v-if='scope.row.speciesListData.indexOf(index)>-1'
                    style="cursor:pointer !important"
                    @click=showOrthoInfoTable(scope.row,index)
                  />
                  <!--这个v-if，先判断在当前的cell内有数值，然后再判断值 -->
                  <img
                    :src="singleTraitIcon"
                    min-width="70"
                    height="70"
                    class="iconImg"
                    v-if='scope.row.traitListData2.indexOf(index)>-1'
                    style="cursor:pointer !important"
                    @click=showGwasInfoTable(scope.row,index)
                  />
                   <img
                    :src="sameTraitIcon"
                    min-width="70"
                    height="70"
                    class="iconImg"
                    v-if='scope.row.traitListData1.indexOf(index)>-1'
                    style="cursor:pointer !important"
                    @click=showGwasInfoTable(scope.row,index)
                  />
                  </template>
              </el-table-column>
            </template>
            </el-table-column>
          </el-table>
        <div style="position: absolute;float: left;">
       <div style="display: flex;">
                    <img :src="orthoIcon"
                      style="margin-right: 6px;min-width=70px;height=70px;"
                      class="iconImg" />
                      <div class="note-info">exists homologous gene</div>
          </div>
          <div style="display: flex;">
                    <img :src="singleTraitIcon"   
                      style="margin-right: 6px;min-width=70px;height=70px;"
                      class="iconImg" /><div class="note-info">exists homologous gene with expression annotation</div>  
          </div>
          <div style="display: flex;">
                    <img :src="sameTraitIcon"   
                      style="margin-right: 6px;min-width=70px;height=70px;"
                      class="iconImg" /><div class="note-info">exists homologous gene with same expression annotation</div>  
          </div>
        </div>
        <el-pagination
          class="trait-pag"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage4_p"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="10"
          layout="total, sizes, prev, pager, next, jumper"
          :total="totalSize_plant"
        >
        </el-pagination>
        <div class="sub-trait-box " v-if="showOrthoSubTable">
      <el-divider style="padding-top:5px"></el-divider>
          <div class="title-box">
            <template>
              <el-descriptions title="Selected Gene" >
                <el-descriptions-item label="Gene Id"><a :href="'./gene-detail?hdbId='+selectGene.hdbId+'&taxonId='+selectGene.taxonId">
                    {{ selectGene.geneId }}
                </a></el-descriptions-item>
                <el-descriptions-item label="Protein Id"><a :href="'https://www.uniprot.org/uniprot/'+selectGene.hdbId" target='_blank'>
                    {{ selectGene.hdbId }}
                </a></el-descriptions-item>
                <el-descriptions-item label="Species Name">{{selectGene.speciesCommonName}}</el-descriptions-item>
                <el-descriptions-item label="Classification">{{selectGene.classification}}</el-descriptions-item>
                <el-descriptions-item label="Expression">
                  <el-tag size="small">{{selectGene.annotation}}</el-tag>
                </el-descriptions-item>
                <!-- <el-descriptions-item label="Address"
                  >No.1188, Wuzhong Avenue, Wuzhong District, Suzhou, Jiangsu
                  Province</el-descriptions-item
                > -->
              </el-descriptions>
            </template>
            <el-divider></el-divider>
            <h2 class="trait-sub-title">Homologous Gene Detail Information</h2>
            <el-button type="primary" icon="el-icon-download" plain id="filterCol1" @click="exportToSubOrthoTableExcel(orthoTableData)"></el-button>

          </div>
      </div>
    <!-- 同源表格 -->
          <el-table
            :data="orthoTableData"
            class="info-table"
            v-loading="orthoLoading"
            v-if="showOrthoSubTable"
            :border="false"
            :header-cell-style="{background:'#F5F7FA'}"
        >
            <el-table-column
                  prop="commonName"
                  label="Species"
            >
              </el-table-column>
              <el-table-column
                  prop="tax"
                  label="Taxon Id"
            >
             <template slot-scope="scope">
                <a :href="'https://www.ncbi.nlm.nih.gov/Taxonomy/Browser/wwwtax.cgi?mode=Info&id='+scope.row.tax+'&lvl=3&lin=f&keep=1&srchmode=1&unlock'">
                    {{ scope.row.tax }}
                </a>
              </template>
              </el-table-column>
              <el-table-column
                  prop="ensemblGeneId"
                  label="Ensembl Id"
              >
              </el-table-column>
               <el-table-column
                prop="geneSymbol"
                label="Gene Symbol"
              >
              </el-table-column>
              <el-table-column
                  prop="hdbId"
                  label="Protein Id"
              >
                  <template slot-scope="scope" v-if="scope.row.hdbId.slice(0,2)!=='EN'">
                
                    <a :href="'https://www.uniprot.org/uniprot/'+scope.row.hdbId" target='_blank'>
                        {{ scope.row.hdbId }}
                    </a>
                  </template>
              </el-table-column>
              <el-table-column  label="Data Source" align="center">
                <template slot-scope="scope">
                    <el-popover
                        placement="top"
                        title="Data Source"
                        width="200"
                        trigger="hover"
                        :content="scope.row.dbEvidence">
                        <i class="el-icon-s-order" slot="reference" width="30px"></i>
                    </el-popover>
                    
                </template>
            </el-table-column>
          </el-table>
    <!-- gwas detail info表格 -->
      <div class="sub-trait-box" v-if="showSubTableBox">
        <div  class="title-box" style="padding-left:1.5%;margin-bottom:1.3%">
          <h3 class="trait-sub-title">Expression Detail Information</h3>
          <el-button type="primary" icon="el-icon-download" plain id="filterCol1" @click="exportToSubGwasTableExcel(gwasInfoData)"></el-button>

        </div>
        <div id="trait-info">
            <!-- <el-card shadow="none" class="gwasDetailCard"> -->
              <el-table 
                :data="gwasInfoData"
                style="width: 100%;margin-top:3%;"
                id="subTable"
                :row-style="{height: '0'}"
                max-height="800"
                v-loading="gwasLoading"
                :header-cell-style="{background:'#F5F7FA'}"
              >
                <el-table-column prop="geneId" label="Gene Id">
                  <template slot-scope="scope">
                      <a :href="'https://ngdc.cncb.ac.cn/gen/gene/'+scope.row.genOrgId+'/'+scope.row.geneId" target='_blank'>
                          {{ scope.row.geneId }}
                      </a>
                  </template>
                </el-table-column>
                <el-table-column prop="annotation" label="Expression"></el-table-column>
                <el-table-column prop="bioProjectIdList" label="Bioproject Id">
                  <template slot-scope="scope">
                    <div v-for="(item,index) of scope.row.bioProjectIdList" :key="index" style="display: flex;float:left">
                      <a :href="'https://ngdc.cncb.ac.cn/gen/project/accession/'+item" target='_blank'>
                          {{ item }}
                      </a>
                      <div v-if="index<scope.row.bioProjectIdList.length-1">,</div>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column prop="speciesCommonName" label="Species"></el-table-column>
                <el-table-column prop="taxonId" label="Taxon Id">
                   <template slot-scope="scope">
                    <a :href="'https://www.ncbi.nlm.nih.gov/Taxonomy/Browser/wwwtax.cgi?mode=Info&id='+scope.row.taxonId+'&lvl=3&lin=f&keep=1&srchmode=1&unlock'">
                        {{ scope.row.taxonId }}
                    </a>
                  </template>
                </el-table-column>
                <el-table-column label="Box-plot">
                  <template slot-scope="scope" >
                    <el-popover
                      placement="left"
                      width="300"
                      trigger="manual"
                      class="popbox"
                      v-model=scope.row.showpop
                    >
                    <div class="checkbox-box">
                      <el-radio 
                        v-for="(item,index) in scope.row.bioProjectIdList"
                        :key="index"
                        :label="item"
                        v-model="scope.row.selectPrj">
                      </el-radio >
                    </div>
                      <div class="botton-wrapper">
                        <el-button size="mini" type="primary"  @click=showDetailExpression(scope.row) class="choose-col-button">Confirm</el-button>
                        <el-button size="mini" type="text" plain @click="scope.row.showpop = false" class="choose-col-button">Cancel</el-button>
                      </div>
                    </el-popover>
                    <img
                      :src="detailIcon"
                      min-width="25"
                      height="25"
                      style="cursor:pointer !important"
                      @click="scope.row.showpop = true"
                    />
                  </template>
                  
                </el-table-column>
              </el-table>
            <!-- </el-card> -->
        </div>
      </div>
      </div>
    <!-- </div> -->
     
     
      <!-- </el-card> -->
    </el-tab-pane>
    
    
    </el-tabs>
    </el-card>
    </el-container>
    <FooterBar />
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
  </div>
</template>

<style src="../assets/css/traits.css" scoped></style>
<style>
.el-message-box{
  width: auto !important;
}
</style>
<script>
// @ is an alias to /src
// import Navigator from "@/components/navigator.vue";
import expOntology from "@/assets/static/expression.json";
import Banner from "@/components/banner.vue";
import FooterBar from "@/components/FooterBar.vue";
import HeaderBar from "@/components/HeaderBar.vue";
// import qs from "qs";
// // icons
import sameTraitIcon from "@/assets/img/icon/star.svg";
import detailIcon from "@/assets/img/icon/details.svg";
import orthoIcon from "@/assets/img/icon/huafu.svg";
import singleTraitIcon from "@/assets/img/icon/orange.svg";
// import "@/assets/css/traits.css";
export default {
  name: "Traits",
  components: {
    // Navigator,
    FooterBar,
    HeaderBar,
    Banner,
    
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    },
  },
  data() {
    return {
      sameTraitIcon,
      detailIcon,
      orthoIcon,
      singleTraitIcon,
      visible:false,
      visible_1:false,
      filterText: "",
      treedata: expOntology.tree,
      showSubTableBox:false,
      gwasLoading:true,
      orthoLoading:true,
      drawer:false,
      direction:"ltr",
       // 保存同源物种，用于表格生成列
      speciesList_animal:[],
      speciesList_plant:[],
       // 保存同源物种obj，用于动态选择列
      speciesList_animal_1:[],
      speciesList_plant_1:[],
      traitData_animal: [],
      traitData_plant:[],
      orthoTableData: [],
      value: "",
      expItem:{
        expName:"Cardiovascular system"
      },
      currentPage4_a: 1,
      currentPage4_p: 1,
      totalSize_animal: 0,
      totalSize_plant: 0,
      form: {
        querySpecies:"",
        gene:""
      },
      pageSize: 10,
      gwasInfoData:[],
      loading_animal:false,
      loading_plant:false,
      showOrthoSubTable:false,
      classification:"animal",
      navBarFixed:"false",
      showTraitSameIcon:false,
       // 从接口获取所有有trait数据的物种，用于物种选择，提交search
      querySpeciesList_animal:[],
      querySpeciesList_plant:[],
      querySpeciesList:[],
      selectGene:{},
      exp_prjList:[],
      project_val:"",
      visible_2:false,
      showTitle:true
    };
  },

  methods: {
   
    changeClass(tab){
        this.showOrthoSubTable=false;
        this.showSubTableBox=false;
        this.$refs['table'].doLayout();
        this.form.querySpecies='';
        this.form.gene='';
        this.classification=tab.name
    },

    confirmbutton(){
    this.visible_1 = false;
    this.visible = false;
    this.$refs.table.doLayout();
   },
   confirmbutton2(){
    this.visible_2 = false;
    this.visible2 = false;
   },
    handleSizeChange(val){
      // this.loading = true;
      if(this.classification=="animal"){
        this.currentPage4_a=1
        this.loading_animal=true;
      }else{
        this.loading_plant=true
        this.currentPage4_p=1
      }
      this.pageSize = val;
      this.getTraitData(this.expItem.expName,this.classification,this.hdbId)
    },
    handleCurrentChange(val) {
      if(this.classification=="animal"){
        this.loading_animal=true;
        this.currentPage4_a = val;
        // traitId,classification,pagesize,pagenum,speciesName
      }else{
        this.loading_plant=true;
        this.currentPage4_p = val;
      }
      this.getTraitData(this.expItem.expName,this.classification,this.hdbId)

    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    getInfoByTrait(value, data) {
      this.hdbId="";
      this.showTitle=true;
      this.expItem.expName=value.label;
      this.expItem.classification=value.classification
      if(value.classification!==""&&value.classification!==null){
        if(value.classification=="animal"){
          this.classification="animal"
        }else{
          this.classification="plant"
        }
      }
      this.loading_animal=true;
      this.getTraitData(value.label,"animal");
      this.loading_plant=true;
      this.getTraitData(value.label,"plant")
      this.currentPage4_p=1;
      this.currentPage4_a=1;
      
      data.checked = true;
      this.showSubTableBox=false;
      this.showOrthoSubTable=false;
    },
    showSelectedGene(rowData){
      this.selectGene=rowData;
  },
    
  showOrthoInfoTable(rowData,index){
     this.showSelectedGene(rowData,index)
    let ortholist=[];
    rowData.speciesListData.forEach((id,idx)=>{
      if(id==index){
        ortholist.push(rowData.ortholist[idx])
    }})
    this.showOrthoSubTable = true;
    this.showSubTableBox=false;
    this.orthoLoading=false;
    this.orthoTableData=[]
    ortholist.forEach(item=>{
      let hdbid=item.hdbId;
      this.$axios.get("https://ngdc.cncb.ac.cn/hapi/api/gene-detail-ortho",{params:{"hdbId":hdbid}}).then((res)=>{
        item.ensemblGeneId=res.data.ensemblGeneId.length>0?res.data.ensemblGeneId:"-";
        item.geneSymbol=res.data.geneSymbol.length>0?res.data.geneSymbol:"-";
        this.orthoTableData.push(item)
        this.orthoLoading=false;
      })
    })
  },
 
  // 表格高度自适应
  getTableMaxHeight(){
      this.$nextTick(function () {
      let box = document.querySelector(".trait-info").attributes
      let box_clientHeight = box[0].ownerElement.clientHeight;
      this.tableHeight = box_clientHeight - 100;
    })
  },
  buttonFunction(){
    this.drawer = true;
   },
  drawBoxPlot(expTabledataTotal,index,nameList){
    var chartDom = document.getElementById('main');
    var myChart = this.$echarts.init(chartDom);
    var option;

    option = {
      title: [
        {
          text: 'Expression value Box-plot in Bioproject',
          left: 'center'
        },
        
      ],
      dataset: [
        {
          // prettier-ignore
          source: expTabledataTotal[index][0]
        },
        {
          transform: {
            type: 'boxplot',
            config: { itemNameFormatter: function(params){
                return nameList[index][params.value]
            } }
          }
        },
        {
          fromDatasetIndex: 1,
          fromTransformResult: 1
        }
      ],
      tooltip: {
        trigger: 'item',
        axisPointer: {
          type: 'shadow'
        }
      },
      grid: {
        left: '10%',
        right: '10%',
        bottom: '15%'
      },
      xAxis: {
        type: 'category',
        boundaryGap: true,
        name: 'Tissue',
        nameGap: 30,
        splitArea: {
          show: false
        },
        splitLine: {
          show: false
        }
      },
      yAxis: {
        type: 'value',
        name: 'TPM value',
        splitArea: {
          show: true
        }
      },
      series: [
        {
          name: 'boxplot',
          type: 'boxplot',
          datasetIndex: 1
        },
        {
          name: 'outlier',
          type: 'scatter',
          datasetIndex: 2
        }
      ]
    };

    option && myChart.setOption(option);

   },
  showDetailExpression(value){
    if(value.selectPrj !==null && value.selectPrj!==undefined){
    value.showpop=false;
    this.visible_2 = !this.visible_2
    let PATH="https://ngdc.cncb.ac.cn/gen/api/json/gene/expressions?taxonomyId="+value.taxonId+"&accession="+value.geneId+"&bioProjectId="+value.bioProjectId
    // const h = this.$createElement;
    this.$axios.get(PATH).then(response=>{
        let expdata=response.data.result.expressionProfiles
        this.exp_prjList=value.bioProjectId.split(',');
        this.$alert(`
          <div id="main" style="width:1300px;height:800px"></div>
        `, {
          dangerouslyUseHTMLString: true
        })
        let expTabledataTotal=[];
        let nameList=[];
        let prjListall=[];
        for(let keyaa in expdata ){
          if(prjListall.indexOf(keyaa)==-1){
            prjListall.push(keyaa)
          }
            let expTabledata=[];
            let prjList=expdata[keyaa]
            let expNum=0;
            // let sampleNum=0;
            let tissue={};
            let subnamelist=[];
            for(let item of prjList){
                // sampleNum += 1;
                expNum += item.tpm;
                let itemTissue=item.tissue
                if(Object.keys(tissue).indexOf(itemTissue) == -1 ){
                    tissue[itemTissue]=[expNum]
                    subnamelist.push(itemTissue);
                }else{
                    tissue[itemTissue].push(expNum)
                }
            }
            
            for(let tissueName in tissue){
                let valueList=tissue[tissueName];
                expTabledata.push(valueList);
                
            }
            expTabledataTotal.push([expTabledata])
            nameList.push(subnamelist)
        }
        let index=prjListall.indexOf(value.selectPrj)
        setTimeout(() => {
          this.drawBoxPlot(expTabledataTotal,index,nameList);
        }, 100);
    })
    }else{
       this.$message.error('Plese select a Bioproject');
    }
  },
  showGwasInfoTable(rowValue,index){
    this.showSelectedGene(rowValue,index)

    let ortholist=[];
    let hdblist=[rowValue.hdbId]
    rowValue.speciesListData.forEach((id,idx)=>{
      if(id==index){
        let item=rowValue.ortholist[idx]
        ortholist.push(item)
        hdblist.push(item.hdbId)
    }})

     ortholist.forEach(item=>{
      let hdbid=item.hdbId;
      this.$axios.get("https://ngdc.cncb.ac.cn/hapi/api/gene-detail-ortho",{params:{"hdbId":hdbid}}).then((res)=>{
        item.ensemblGeneId=res.data.ensemblGeneId.length>0?res.data.ensemblGeneId:"-";
        item.geneSymbol=res.data.geneSymbol.length>0?res.data.geneSymbol:"-";
        this.orthoTableData.push(item)
        this.orthoLoading=false;
      })
    })
    // this.orthoTableData=ortholist;
    // this.orthoLoading=false;
    this.showSubTableBox=true;
    this.gwasInfoData=[];
    hdblist.forEach(hdbid=>{
      this.$axios.get("https://ngdc.cncb.ac.cn/hapi/api/expression-gene",{params:{'hdbId': hdbid}})
      .then(response=>{
        for(let item of response.data){
          item.bioProjectIdList=item.bioProjectId.split(",")
        }
        this.gwasInfoData=this.gwasInfoData.concat(response.data);
        this.gwasLoading=false;
      })
    })
    this.showOrthoSubTable = true;

   },
    hiligtDbCols({rowIndex}){
      if(rowIndex===1){
          return "writing-mode: vertical-lr; !important;text-align:right"
      }
      
    },
 
   
    searchFilter(){
      if(this.form.querySpecies=="" && this.form.gene==""){
        this.$message.error('Please enter query criteria');
      }else{
        if(this.classification=="animal"){
          this.loading_animal=true;
        }else{
          this.loading_plant=true;
        }
        this.getTraitData(this.expItem.expName,this.classification)
        setTimeout(() => {
          this.messagError(this.classification)
        }, 1000);
      }
    }, 
    messagError(type){
      if(type=='plant'){
        if(this.totalSize_plant==0){
            this.$message.error('No data found');
          }
      }else{
          if(this.totalSize_animal==0){
            this.$message.error('No data found');
        }
      }
    },
    clearFilter(){
      if(this.form.querySpecies!=="" || this.form.gene!==""){

        if(this.classification=="animal"){
          this.loading_animal=true;
        }else{
          this.loading_plant=true;
        }
        this.form.querySpecies="";
        this.form.gene=""
        this.getTraitData(this.expItem.expName,"animal")
        this.getTraitData(this.expItem.expName,"plant")
        
      }
    },
    async showTableIcon(data,classss){
      
      if(classss == "plant"){
        this.speciesList_plant=[];
        this.speciesList_plant_1=[];
        if(data.length>0){
          for(let varData of data){
            // 增加一个属性保存物种的index，用来显示表格里的icon
            varData.speciesListData=[];
            // 增加一个属性，保存显示有trait数据的情况
            varData.traitListData1=[];
            varData.traitListData2=[];
            if(varData.ortholist){
              for(let item of varData.ortholist){
                let speciesName=item.species.commonName
                if(this.speciesList_plant.indexOf(speciesName)==-1){
                  this.speciesList_plant.push(speciesName);
                  item.species.checked=true;
                  this.speciesList_plant_1.push(item.species);
                }
                varData.speciesListData.push(this.speciesList_plant.indexOf(speciesName));
                if(item.annotation !== null & item.eoName!==null){
                  varData.traitListData2.push(this.speciesList_plant.indexOf(speciesName))
                  let varlist=item.eoName.split(',')
                  if(varlist.indexOf( varData.annotation)>-1){
                      varData.traitListData1.push(this.speciesList_plant.indexOf(speciesName))
                  }
                }
              }
            }
          }
          this.$refs['table'].doLayout();
        }
        
    }else{
      this.speciesList_animal=[];
      this.speciesList_animal_1=[];
        if(data.length>0){
          for(let varData of data){
            // 增加一个属性保存物种的index，用来显示表格里的icon
            varData.speciesListData=[];
            varData.traitListData1=[];
            varData.traitListData2=[];
            if(varData.ortholist){
              for(let item of varData.ortholist){
                let speciesName=item.species.commonName;
                if(this.speciesList_animal.indexOf(speciesName)==-1){
                  this.speciesList_animal.push(speciesName);
                  item.species.checked=true;
                  this.speciesList_animal_1.push(item.species);
                }
                varData.speciesListData.push(this.speciesList_animal.indexOf(speciesName));
                if(item.eoName !== null){
                  // 有trait但不相同
                   varData.traitListData2.push(this.speciesList_animal.indexOf(speciesName))
                  let varlist=item.eoName.split(',')
                  if(varlist.indexOf( varData.annotation)>-1){
                    // 有相同trait
                      varData.traitListData1.push(this.speciesList_animal.indexOf(speciesName))
                  }
                }
              }
            }
          }
        }
    }
    return Promise.resolve(data);
    },
    // 主要获取数据的方法
    // 根据动植物分开获取
    async getTraitData(expName,classification,hdbId){
      if(this.form.gene!=='' && this.form.gene !==undefined){
        this.form.gene=this.form.gene.trim()
      }
      let pageNo;
      if(classification=="animal"){pageNo=this.currentPage4_a}else if(classification=="plant"){
        pageNo=this.currentPage4_p
      }else{pageNo=1}
      this.$axios.get("https://ngdc.cncb.ac.cn/hapi/api/expression-data",{params:{'classification': classification,'expName':expName,'length':this.pageSize,'pageNo':pageNo,'taxonid':this.form.querySpecies,'geneid':this.form.gene,"hdbId":hdbId}})
      .then((response) => {
        if(classification=="animal"){
          this.getSpecies(expName,"animal")
          this.totalSize_animal = response.data.recordsTotal;
          this.showTableIcon(response.data.data,classification).then((res)=>{ this.traitData_animal=res;this.loading_animal=false;});
          
        }else{
          this.getSpecies(expName,"plant")
          this.totalSize_plant = response.data.recordsTotal;
          this.showTableIcon(response.data.data,classification).then((res)=>{ this.traitData_plant=res; this.loading_plant=false;});
          
        }

      })
      .finally(()=>{ return Promise.resolve()})
   
    },
    // 根据当前classification判断获取动物或植物列表
    getSpecies(expName,speciesType){
      this.$axios.get('https://ngdc.cncb.ac.cn/hapi/api/species-exp',{params: {"expName":expName,speciesType: speciesType}})
        .then(response=>{
          // 2是植物，1是动物
          if(speciesType=='plant'){
            this.querySpeciesList_plant=response.data;
          }else{
            this.querySpeciesList_animal=response.data;
          }
      })
    },
    clearTraitFilter(){
        this.expItem={
          expName:""
        }
        if(this.classification=='animal'){this.loading_animal=true}else{this.loading_plant=true}
        this.showTitle=false;
        // let params1={classification:"animal",pagesize:this.pageSize,pagenum:1}
        // // let params={traitId:this.traitItem.traitDefID,classification:this.traitItem.classification,pagesize:this.pageSize,pagenum:1,speciesName:this.form.querySpecies}
        // let params2={classification:"plant",pagesize:this.pageSize,pagenum:1}
        this.getTraitData("","animal");
        this.getTraitData("","plant");
        // this.getTraitData("","plant");
      },
      exportToExcel(classs) {
      let oriData;
      if(classs=='animal'){oriData=this.traitData_animal}else{oriData=this.traitData_plant}
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = [ 'Expression Term',"Gene Id","Species Common Name","Taxon Id","Homolog Info Data"]
        const filterVal = ['annotation','geneId','commonName','taxonId','ortholist']
        const data = this.formatJson(filterVal, oriData)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename:"public_outer"
        })
      })
    },
    exportToSubOrthoTableExcel(orthodata) {
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = [ 'Species',"Taxon Id","Ensembl Id",'Gene Symbol',"Protein Id","DataSource"]
        const filterVal = ['commonName','tax','ensemblGeneId','geneSymbol',"hdbId","dbEvidence"]
        const data = this.formatJson(filterVal, orthodata)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename:"Homolog_Outer"
        })
      })
    },
    exportToSubGwasTableExcel(gwasdata) {
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = [ 'Gene Id',"Expression Term","Bioproject Id",'Species',"Taxon Id"]
        const filterVal = ['geneId','annotation','bioProjectId','speciesCommonName','taxonId']
        const data = this.formatJson(filterVal, gwasdata)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename:"Expression_Outer"
        })
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if(j=="ortholist"){
            let orthoData=v[j];
            let orthoList=[]
            for(let item of orthoData){
              let orthodataInfo={"Species Common Name":item.commonName,"Taxon Id":item.tax,"Expression Term":item.eoName,"Homologous Protein Id":item.hdbId,"Data Source":item.dbEvidence}
              orthoList.push(JSON.stringify(orthodataInfo))
            }
            return orthoList
        }else{
         return  v[j]
        }
      }))
    },

  },
  

  mounted() {
  


    let hdbId = this.$route.query.hdbId;
    this.hdbId=hdbId;
    // if(hdbId!=null && hdbId!=""){}
    if(hdbId!="" && hdbId!=null){
      let classification;
      if(this.$route.query.speciesType==1){
        classification="animal";
        this.classification="animal";
        this.loading_animal = true;
      }else if(this.$route.query.speciesType==2){
        classification="plant";
        this.classification="plant";
        this.loading_plant = true;
      }
     
      this.expItem={
        expName:""
      },
        this.showTitle=false;
        // let params={hdbId:hdbId,classification:classification}
        this.getTraitData(this.expItem.expName,classification,hdbId);
    }else{
      this.loading_animal = true;
      this.loading_plant=true;
      this.getTraitData(this.expItem.expName,"animal");
      this.getTraitData(this.expItem.expName,"plant");
    }

  // expName,classification,pagesize,pagenum,speciesName
   

    window.addEventListener("scroll", this.watchScroll);
   // 获取动植物名称列表，用于下拉选择框
    // this.getSpecies(this.expItem.expName,this.classification)
    // this.getSpecies(this.expItem.expName,"plant")
    
     window.addEventListener("scroll", this.watchScroll);
      this.getTableMaxHeight(); 
      let _this = this;
      window.onresize = function () {//用于使表格高度自适应的方法  
      _this.getTableMaxHeight();//获取容器当前高度，重设表格的最大高度
    }
    
  }
}

</script>
