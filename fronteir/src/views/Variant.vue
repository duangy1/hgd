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
      <el-breadcrumb-item>Variant</el-breadcrumb-item>
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
      <div class="trait-box trait-infoBox"  v-show="showTitle">
        <div style="padding-left:29px;margin-top: -15px;">
          <h2 class="trait-title">Consequence Type : {{ varItem.varName }} </h2>
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
                        :label="item"
                        :value="item"
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
            ref="table_main_animal"
            :header-cell-style="hiligtDbCols"
            v-loading="loading_animal"
            border
          >
            <el-table-column align="center" class="titleCell" prop="varName" label="Consequence Type" fixed width="280px" style="background-color:white"></el-table-column>
            <el-table-column align="center" prop="geneId" label="Gene ID" width="220px" fixed>
               <template slot-scope="scope">
                <a :href="'./gene-detail?hdbId='+scope.row.hdbid+'&taxonId='+scope.row.taxonId">
                    {{ scope.row.geneId }}
                </a>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="speciesCommonName" label="Species Name" width="150px" fixed></el-table-column>
            <el-table-column align="center" prop="taxonId" label="Taxon ID" width="160px" fixed>
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
                      class="iconImg" /><div class="note-info">exists homologous gene with variant annotation</div>  
          </div>
          <div style="display: flex;">
                    <img :src="sameTraitIcon"   
                      style="margin-right: 6px;min-width=70px;height=70px;"
                      class="iconImg" /><div class="note-info">exists homologous gene with same variant annotation</div>  
          </div>
          
          </div>
          <el-pagination
          
            class="trait-pag"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage4_a"
            :page-sizes="[10, 20, 50, 100]"
            :page-size="pageSize"
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
                <el-descriptions-item label="Gene ID"><a :href="'./gene-detail?hdbId='+selectGene.hdbid+'&taxonId='+selectGene.taxonId">
                    {{ selectGene.geneId }}
                </a></el-descriptions-item>
                <el-descriptions-item label="Uniprot ID"><a :href="'https://www.uniprot.org/uniprot/'+selectGene.uniprotId" target='_blank'>
                    {{ selectGene.uniprotId }}
                </a></el-descriptions-item>
                <el-descriptions-item label="Ensembl Protein ID">
                  <!-- <a :href="'https://ensembl.org/Multi/Search/Results?q='+selectGene.ensemblId+';site=ensembl'" target='_blank'>
                    {{ selectGene.ensemblId }}
                </a> -->
                <a target="_blank" v-if="tax_Plant.indexOf(String(selectGene.taxonId))==-1" :href="'https://ensembl.org/Multi/Search/Results?q='+selectGene.ensemblId+';site=ensembl'">{{selectGene.ensemblId}}</a>
                <a target="_blank" v-else :href="'https://plants.ensembl.org/Multi/Search/Results?q='+selectGene.ensemblId+';site=ensembl'">{{selectGene.ensemblId}}</a>

              </el-descriptions-item>
                <el-descriptions-item label="Species Name">{{selectGene.speciesCommonName}}</el-descriptions-item>
                <el-descriptions-item label="Classification">{{selectGene.classification}}</el-descriptions-item>
                <el-descriptions-item label="Consequence type">
                  <el-tag size="small">{{selectGene.varName}}</el-tag>
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
                  label="Taxon ID"
              >
              <template slot-scope="scope">
                <a :href="'https://www.ncbi.nlm.nih.gov/Taxonomy/Browser/wwwtax.cgi?mode=Info&id='+scope.row.tax+'&lvl=3&lin=f&keep=1&srchmode=1&unlock'">
                    {{ scope.row.tax }}
                </a>
              </template>
              </el-table-column>
              <el-table-column
                  prop="ensemblGeneId"
                  label="Ensembl ID"
                  >
                  <template slot-scope="scope">
                    <!-- <a v-if="scope.row.ensemblGeneId!=='-'" target="_blank" :href="'https://ensembl.org/Multi/Search/Results?q='+scope.row.ensemblGeneId+';site=ensembl'">{{scope.row.ensemblGeneId}}</a> -->
                    <div v-if="scope.row.ensemblGeneId!=='-'">
                      <a target="_blank" v-if="tax_Plant.indexOf(String(scope.row.tax))==-1" :href="'https://ensembl.org/Multi/Search/Results?q='+scope.row.ensemblGeneId+';site=ensembl'">{{scope.row.ensemblGeneId}}</a>
                      <a target="_blank" v-else :href="'https://plants.ensembl.org/Multi/Search/Results?q='+scope.row.ensemblGeneId+';site=ensembl'">{{scope.row.ensemblGeneId}}</a>
                    </div>
                    <div v-else>{{scope.row.ensemblGeneId}}</div>
                </template>
              </el-table-column>
               <el-table-column
                prop="geneSymbol"
                label="Gene Symbol"
                >
              </el-table-column>
              <el-table-column
                prop="protein"
                label="Uniprot ID">
                <template slot-scope="scope">
              
                  <a :href="'https://www.uniprot.org/uniprot/'+scope.row.protein" target='_blank'>
                      {{ scope.row.protein }}
                  </a>
                </template>
            </el-table-column>
            <el-table-column
                prop="ensemblId"
                label="Ensembl Protein ID">
                <template slot-scope="scope">
              
                  <!-- <a :href="'https://ensembl.org/Multi/Search/Results?q='+scope.row.ensemblId" target='_blank'>
                      {{ scope.row.ensemblId }}
                  </a> -->
                  <a target="_blank" v-if="tax_Plant.indexOf(String(scope.row.tax))==-1" :href="'https://ensembl.org/Multi/Search/Results?q='+scope.row.ensemblId+';site=ensembl'">{{scope.row.ensemblId}}</a>
                  <a target="_blank" v-else :href="'https://plants.ensembl.org/Multi/Search/Results?q='+scope.row.ensemblId+';site=ensembl'">{{scope.row.ensemblId}}</a>

                </template>
            </el-table-column>
            <el-table-column label="Data Source Count "  width="150px">
                <template slot-scope="scope">
                    <el-rate
                        v-model="scope.row.dbEvidence.split(',').length"
                        disabled
                        text-color="#ff9900"
                        score-template="{value}">
                    </el-rate>
                </template>
              </el-table-column>
              <el-table-column  label="Data Source" align="center">
                  <template slot-scope="scope">
                      <el-popover
                          placement="top"
                          title="Data Source"
                          width="200"
                          trigger="click"
                          :content="scope.row.dbEvidence">
                          <!-- <i class="el-icon-s-order" slot="reference" width="30px"></i> -->
                          <template #reference>
                              <i class="el-icon-s-order" slot="reference"></i>
                          </template>
                          <template #default>
                              <div>{{scope.row.dbEvidence}}</div>
                              <div v-for="(item,index) of scope.row.dbContent" :key="index">
                                  <div v-if="item.split(':')[0]=='Panther'">
                                      Panther:<a target="_blank" :href="'http://www.pantherdb.org/panther/family.do?clsAccession='+item.split(':')[1]" >{{item.split(':')[1]}}</a>
                                  </div>
                                  <div v-if="item.split(':')[0]=='TreeFam'">
                                      TreeFam:<a target="_blank" :href="'http://www.treefam.org/family/'+item.split(':')[1]" >{{item.split(':')[1]}}</a>
                                  </div>
                                  <div v-if="item.split(':')[0]=='eggNOG'">
                                      eggNOG:<a target="_blank" :href="'http://eggnog5.embl.de/#/app/results?seqid='+scope.row.protein+'&target_nogs=ENOG50'+item.split(':')[1]" >{{item.split(':')[1]}}</a>
                                  </div> 
                                  <div v-if="item.split(':')[0]=='Inparanoid'">
                                    Inparanoid:<a target="_blank" :href="'https://inparanoid.sbc.su.se/cgi-bin/efasta.cgi?cluster='+item.split(':')[1].split(' ')[1]+'&species1='+scope.row.inparanoidID1+'&species2='+scope.row.inparanoidID2+'&java=0'">{{item.split(':')[1]}}</a>
                                  </div> 
                              </div>
                          </template>
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
          <h3 class="trait-sub-title">Variants Detail Information</h3>
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
              <el-table-column
                prop="finalgenename"
                label="Gene ID"
              >
                  <!-- <template slot-scope="scope">
                    <a v-bind:href="'https://ngdc.cncb.ac.cn/gvm/snp/getGeneDetail?param1='+scope.row.genename+'&orgId'+scope.row.orgId" target='_blank'>
                      {{ scope.row.genename }}
                    </a>
                  </template> -->

              </el-table-column>
              <el-table-column
                  prop="rsnpId"
                  label="Var ID"
                  >
                  <template slot-scope="scope">
                  <a v-bind:href="'https://ngdc.cncb.ac.cn/gvm/snp/getSNPDetail?snpname='+scope.row.rsnpId+'&chrom='+scope.row.chrom+'&position='+scope.row.snpStart+'&orgId='+scope.row.orgId" target='_blank'>
                    {{ scope.row.rsnpId }}
                  </a>
                </template>
              </el-table-column>
                <el-table-column
                    prop="finalposition"
                    label="Position"
                    
                    >
                </el-table-column>
                <el-table-column
                    prop="finalallele"
                    label="Allele"
                   
                >
                </el-table-column>
              
                <el-table-column
                    prop="finalmaf"
                    label="MAF"
                >
                </el-table-column>
              
                <el-table-column
                    prop="snpClassId"
                    label="Class"
                    
                >
                </el-table-column>
                <el-table-column
                    prop="finalConseqtype"
                    label="Consequence Type/Effect"
                    width="330px"
                >
                </el-table-column>
                <el-table-column
                    prop="finalGenealias"
                    label="Gene Symbol"
                    
                >
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
                    :label="item"
                    :value="item"
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
            ref="table_main_plant"
            :header-cell-style="hiligtDbCols"
            border
          >
            <el-table-column align="center" class="titleCell" prop="varName" label="Consequence Type" fixed width="280px" style="background-color:white"></el-table-column>
            <el-table-column align="center" prop="geneId" label="Gene ID" width="220px" fixed>
               <template slot-scope="scope">
                <a :href="'./gene-detail?hdbId='+scope.row.hdbid+'&taxonId='+scope.row.taxonId">
                    {{ scope.row.geneId }}
                </a>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="speciesCommonName" label="Species Name" width="150px" fixed></el-table-column>
            <el-table-column align="center" prop="taxonId" label="Taxon ID" width="160px" fixed>
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
                      style="cursor:pointer"
                      @click=showOrthoInfoTable(scope.row,index)
                    />
                    <img
                      :src="singleTraitIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='scope.row.traitListData2.indexOf(index)>-1'
                      style="cursor:pointer !important"
                      @click=showGwasInfoTable(scope.row,index)
                    />
                    <!--这个v-if，先判断在当前的cell内有数值，然后再判断值 -->
                    <img
                      :src="sameTraitIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='scope.row.traitListData1.indexOf(index)>-1'
                      style="cursor:pointer"
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
                      class="iconImg" /><div class="note-info">exists homologous gene with variant annotation</div>  
          </div>
          <div style="display: flex;">
                    <img :src="sameTraitIcon"   
                      style="margin-right: 6px;min-width=70px;height=70px;"
                      class="iconImg" /><div class="note-info">exists homologous gene with same variant annotation</div>  
          </div>
        </div>
        <el-pagination
          class="trait-pag"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage4_p"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="totalSize_plant"
        >
        </el-pagination>
        <div class="sub-trait-box " v-if="showOrthoSubTable">
        <el-divider class="divider"></el-divider>
          <div class="title-box">
            <template>
              <el-descriptions title="Selected Gene" >
                <el-descriptions-item label="Gene ID"><a :href="'./gene-detail?hdbId='+selectGene.hdbid+'&taxonId='+selectGene.taxonId">
                    {{ selectGene.geneId }}
                </a></el-descriptions-item>
                <el-descriptions-item label="Uniprot ID"><a :href="'https://www.uniprot.org/uniprot/'+selectGene.uniprotId" target='_blank'>
                    {{ selectGene.uniprotId }}
                </a></el-descriptions-item>
                <el-descriptions-item label="Ensembl Protein ID">
                  <!-- <a :href="'https://ensembl.org/Multi/Search/Results?q='+selectGene.ensemblId+';site=ensembl'" target='_blank'>
                    {{ selectGene.ensemblId }}
                </a> -->
                <a target="_blank" v-if="tax_Plant.indexOf(String(selectGene.taxonId))==-1" :href="'https://ensembl.org/Multi/Search/Results?q='+selectGene.ensemblId+';site=ensembl'">{{selectGene.ensemblId}}</a>
                <a target="_blank" v-else :href="'https://plants.ensembl.org/Multi/Search/Results?q='+selectGene.ensemblId+';site=ensembl'">{{selectGene.ensemblId}}</a>

              </el-descriptions-item>
                <el-descriptions-item label="Species Name">{{selectGene.speciesCommonName}}</el-descriptions-item>
                <el-descriptions-item label="Classification">{{selectGene.classification}}</el-descriptions-item>
                <el-descriptions-item label="Consequence type">
                  <el-tag size="small">{{selectGene.varName}}</el-tag>
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
                label="Taxon ID"
            >
            <template slot-scope="scope">
                <a :href="'https://www.ncbi.nlm.nih.gov/Taxonomy/Browser/wwwtax.cgi?mode=Info&id='+scope.row.tax+'&lvl=3&lin=f&keep=1&srchmode=1&unlock'">
                    {{ scope.row.tax }}
                </a>
              </template>
            </el-table-column>
            <el-table-column
                prop="ensemblGeneId"
                label="Ensembl ID"
                >
                <template slot-scope="scope">
                    <!-- <a v-if="scope.row.ensemblGeneId!=='-'" target="_blank" :href="'https://ensembl.org/Multi/Search/Results?q='+scope.row.ensemblGeneId+';site=ensembl'">{{scope.row.ensemblGeneId}}</a>
                    <div v-else>{{scope.row.ensemblGeneId}}</div> -->
                    <div v-if="scope.row.ensemblGeneId!=='-'">
                      <a target="_blank" v-if="tax_Plant.indexOf(String(scope.row.tax))==-1" :href="'https://ensembl.org/Multi/Search/Results?q='+scope.row.ensemblGeneId+';site=ensembl'">{{scope.row.ensemblGeneId}}</a>
                      <a target="_blank" v-else :href="'https://plants.ensembl.org/Multi/Search/Results?q='+scope.row.ensemblGeneId+';site=ensembl'">{{scope.row.ensemblGeneId}}</a>
                    </div>
                    <div v-else>{{scope.row.ensemblGeneId}}</div>
                </template>
            </el-table-column>
              <el-table-column
              prop="geneSymbol"
              label="Gene Symbol"
            >
            </el-table-column>
            <el-table-column
                prop="protein"
                label="Uniprot ID">
                <template slot-scope="scope">
              
                  <a :href="'https://www.uniprot.org/uniprot/'+scope.row.protein" target='_blank'>
                      {{ scope.row.protein }}
                  </a>
                </template>
            </el-table-column>
            <el-table-column
                prop="ensemblId"
                label="Ensembl Protein ID">
                <template slot-scope="scope">
              
                  <!-- <a :href="'https://ensembl.org/Multi/Search/Results?q='+scope.row.ensemblId+';site=ensembl'" target='_blank'>
                      {{ scope.row.ensemblId }}
                  </a> -->
                  <a target="_blank" v-if="tax_Plant.indexOf(String(scope.row.tax))==-1" :href="'https://ensembl.org/Multi/Search/Results?q='+scope.row.ensemblId+';site=ensembl'">{{scope.row.ensemblId}}</a>
                  <a target="_blank" v-else :href="'https://plants.ensembl.org/Multi/Search/Results?q='+scope.row.ensemblId+';site=ensembl'">{{scope.row.ensemblId}}</a>

                </template>
            </el-table-column>
            <el-table-column label="Data Source Count "  width="150px">
                <template slot-scope="scope">
                    <el-rate
                        v-model="scope.row.dbEvidence.split(',').length"
                        disabled
                        text-color="#ff9900"
                        score-template="{value}">
                    </el-rate>
                </template>
              </el-table-column>
            <el-table-column  label="Data Source" align="center">
                <template slot-scope="scope">
                    <el-popover
                        placement="top"
                        title="Data Source"
                        width="200"
                        trigger="click"
                        :content="scope.row.dbEvidence">
                        <!-- <i class="el-icon-s-order" slot="reference" width="30px"></i> -->
                        <template #reference>
                            <i class="el-icon-s-order" slot="reference"></i>
                        </template>
                        <template #default>
                            <div>{{scope.row.dbEvidence}}</div>
                            <div v-for="(item,index) of scope.row.dbContent" :key="index">
                                <div v-if="item.split(':')[0]=='Panther'">
                                    Panther:<a target="_blank" :href="'http://www.pantherdb.org/panther/family.do?clsAccession='+item.split(':')[1]" >{{item.split(':')[1]}}</a>
                                </div>
                                <div v-if="item.split(':')[0]=='TreeFam'">
                                    TreeFam:<a target="_blank" :href="'http://www.treefam.org/family/'+item.split(':')[1]" >{{item.split(':')[1]}}</a>
                                </div>
                                <div v-if="item.split(':')[0]=='eggNOG'">
                                    eggNOG:<a target="_blank" :href="'http://eggnog5.embl.de/#/app/results?seqid='+scope.row.protein+'&target_nogs=ENOG50'+item.split(':')[1]" >{{item.split(':')[1]}}</a>
                                </div> 
                                <div v-if="item.split(':')[0]=='Inparanoid'">
                                  Inparanoid:<a target="_blank" :href="'https://inparanoid.sbc.su.se/cgi-bin/efasta.cgi?cluster='+item.split(':')[1].split(' ')[1]+'&species1='+scope.row.inparanoidID1+'&species2='+scope.row.inparanoidID2+'&java=0'">{{item.split(':')[1]}}</a>
                                </div> 
                            </div>
                        </template>
                    </el-popover>
                    
                </template>
            </el-table-column>
        </el-table>
    <!-- gwas detail info表格 -->
      <div class="sub-trait-box" v-if="showSubTableBox">
        <div  class="title-box" style="padding-left:1.5%;margin-bottom:1.3%">
          <h3 class="trait-sub-title">Variants Detail Information</h3>
          <el-button type="primary" icon="el-icon-download" plain id="filterCol1" @click="exportToSubGwasTableExcel(gwasInfoData)"></el-button>
        </div>
        <div id="trait-info">
            <!-- <el-card shadow="none" class="gwasDetailCard"> -->
              <!-- <el-table 
                :data="gwasInfoData"
                style="width: 100%;margin-top:3%;"
                id="subTable"
                :row-style="{height: '0'}"
                max-height="400"
                v-loading="gwasLoading"
              >
                <el-table-column prop="geneName" label="Gene ID"></el-table-column>
                <el-table-column prop="varId" label="Var ID"></el-table-column>
                <el-table-column prop="varName" label="Consequence Type"></el-table-column>
                <el-table-column prop="speciesCommonName" label="Species"></el-table-column>
                <el-table-column prop="pmid" label="Pubmed ID"></el-table-column>
                <el-table-column prop="pvalue" label="Pvalue"></el-table-column>
              </el-table> -->
               <el-table 
                :data="gwasInfoData"
                id="subTable"
                :row-style="{height: '0'}"
                max-height="800"
                v-loading="gwasLoading"
                :header-cell-style="{background:'#F5F7FA'}"
              >
              <el-table-column
                prop="finalgenename"
                label="Gene ID"
                width="120"
              >
                  <!-- <template slot-scope="scope">
                    <a v-bind:href="'https://ngdc.cncb.ac.cn/gvm/snp/getGeneDetail?param1='+scope.row.genename+'&orgId'+scope.row.orgId" target='_blank'>
                      {{ scope.row.genename }}
                    </a>
                  </template> -->

              </el-table-column>
              <el-table-column
                  prop="rsnpId"
                  label="Var ID"
                  >
                  <template slot-scope="scope">
                  <a v-bind:href="'https://ngdc.cncb.ac.cn/gvm/snp/getSNPDetail?snpname='+scope.row.rsnpId+'&chrom='+scope.row.chrom+'&position='+scope.row.snpStart+'&orgId='+scope.row.orgId" target='_blank'>
                    {{ scope.row.rsnpId }}
                  </a>
                </template>
              </el-table-column>
                <el-table-column
                    prop="finalposition"
                    label="Position"
                   
                    >
                </el-table-column>
                <el-table-column
                    prop="finalallele"
                    label="Allele"
                   
                >
                </el-table-column>
              
                <el-table-column
                    prop="finalmaf"
                    label="MAF"
                >
                </el-table-column>
              
                <el-table-column
                    prop="snpClassId"
                    label="Class"
                   
                >
                </el-table-column>
                <el-table-column
                    prop="finalConseqtype"
                    label="Consequence Type/Effect"
                    width="330px"
                >
                </el-table-column>
                <el-table-column
                    prop="finalGenealias"
                    label="Gene Symbol"
                    width="120px"
                >
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

<script>
// @ is an alias to /src
// import Navigator from "@/components/navigator.vue";
import varOntology from "@/assets/static/varOntology.json";
import Banner from "@/components/banner.vue";
import FooterBar from "@/components/FooterBar.vue";
import HeaderBar from "@/components/HeaderBar.vue";
// import qs from "qs";
// // icons
import sameTraitIcon from "@/assets/img/icon/star.svg";
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
      orthoIcon,
      singleTraitIcon,
      visible:false,
      visible_1:false,
      filterText: "",
      treedata: varOntology.tree,
      showSubTableBox:false,
      gwasLoading:true,
      orthoLoading:true,
      activeName: 'animal',
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
      varItem:{
        varName:"frameshift_variant"
      },
      currentPage4_a: 1,
      currentPage4_p: 1,
      totalSize_animal:0,
      totalSize_plant:0,
      form: {
        querySpecies:"",
        gene:""
      },
      pageSize: 10,
      gwasInfoData:[],
      // loading:true,
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
      showTitle:true,
      tax_Plant:["3702","3708","4072","3659","3635","3983","4530","3847","42345","3694","4081","4558","4565","29760","4577","4113"]

    };
  },

  methods: {
   
    changeClass(tab){
        this.speciesList=[];
        this.showOrthoSubTable=false;
        this.showSubTableBox=false;
        this.classification=tab.name;
        // this.$refs['table'].doLayout();
        this.form.querySpecies="";
        this.form.gene="";
    },

    confirmbutton(){
    this.visible_1 = false;
    this.visible = false;
    // this.$refs.table.doLayout();
   },
    handleSizeChange(val){
      // this.loading = true;
      if(this.classification=="animal"){this.loading_animal=true;}else{this.loading_plant=true}
      this.pageSize = val;
      this.pageNo=1;
      this.getTraitData(this.varItem.varName,this.classification)
    },
    handleCurrentChange(val) {
      if(this.classification=="animal"){
        this.loading_animal=true;
        this.currentPage4_a = val;
        // traitId,classification,pagesize,pagenum,speciesName
        this.getTraitData(this.varItem.varName,this.classification)
      }else{
        this.loading_plant=true;
        this.currentPage4_p = val;
        this.getTraitData(this.varItem.varName,this.classification)
      }
      

    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    getInfoByTrait(value, data) {
      this.showTitle=true;
      this.showSubTableBox=false;
      this.showOrthoSubTable=false;
      this.varItem.varName=value.label;
      this.pageNo=1;
      this.form= {
        querySpecies:"",
        gene:""
      }
      if(this.classification=="animal"){
        this.currentPage4_a=1;
        this.loading_animal=true;
      }else{
        this.loading_plant=true;
        this.currentPage4_p=1;
      }
      this.getTraitData(value.label)
      
      data.checked = true;
      
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
    showSelectedGene(rowData){
      this.selectGene=rowData;
      this.selectGene.uniprotId=rowData.hdbid.split("_")[0];
      this.selectGene.ensemblId=rowData.hdbid.split("_")[1];
  },
  showOrthoInfoTable(rowData,index){
    console.log("rowData:",rowData);
    this.showSelectedGene(rowData,index)
    let ortholist=[];
    // 通过hdbid筛选保留unique的同源值,需要筛选吗？
    let orthohdblist=[];
    rowData.speciesListData.forEach((id,idx)=>{
      if(id==index){
        let item=rowData.orthoList[idx];
        if(orthohdblist.indexOf(item.hdbId)==-1){
          ortholist.push(item);
          orthohdblist.push(item.hdbId);
        }
        // ortholist.push(rowData.orthoList[idx])
    }})
    this.showOrthoSubTable = true;
    this.showSubTableBox=false;
    this.orthoLoading=true;

    this.orthoTableData=[]
    ortholist.forEach(item=>{
      let hdbid=item.hdbId;
      this.$axios.get("https://ngdc.cncb.ac.cn/hapi/api/gene-detail-ortho",{params:{"hdbId":hdbid}}).then((res)=>{
        if(res.data!==""){
          item.ensemblGeneId=res.data.ensemblGeneId.length>0?res.data.ensemblGeneId:"-";
          item.geneSymbol=res.data.geneSymbol.length>0?res.data.geneSymbol:"-";
        }else{
          item.ensemblGeneId="-";
          item.geneSymbol="-";
        }
        let dbContent=item.fromdb.split(',');
        item.dbContent=dbContent;
        this.orthoTableData.push(item);
        this.orthoLoading=false;
      })
    })
    // this.orthoTableData=ortholist;
    // console.log("orthoTableData:",this.orthoTableData);

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
  showGwasInfoTable(rowValue,index){
    this.showSelectedGene(rowValue,index)
    let ortholist=[];
    // 通过hdbid筛选保留unique的同源值
    let orthohdblist=[];
    rowValue.speciesListData.forEach((id,idx)=>{
    if(id==index){
        let item=rowValue.orthoList[idx];
        if(orthohdblist.indexOf(item.hdbId)==-1){
          ortholist.push(item);
          orthohdblist.push(item.hdbId);
        }
    }})
    this.orthoTableData=[];
    ortholist.forEach(item=>{
      let hdbid=item.hdbId;
      this.$axios.get("https://ngdc.cncb.ac.cn/hapi/api/gene-detail-ortho",{params:{"hdbId":hdbid}}).then((res)=>{
        item.ensemblGeneId=res.data.ensemblGeneId.length>0?res.data.ensemblGeneId:"-";
        item.geneSymbol=res.data.geneSymbol.length>0?res.data.geneSymbol:"-";
        if(this.orthoTableData.indexOf(item)==-1){
          this.orthoTableData.push(item)
        }
        this.orthoLoading=false;
      })
    })
    
    this.showOrthoSubTable = true;
    this.orthoLoading=false;
    this.showSubTableBox=true;

    // 左侧基因的接口数据
    let snpId1 = rowValue.snpId;
    let dataSource1=rowValue.dataSource
    let BASEPATH="";
    this.gwasInfoData=[];
    if(dataSource1!= null && dataSource1.length!==0){
      if(dataSource1.indexOf("v2")>0){BASEPATH="https://ngdc.cncb.ac.cn/gvmRESTV2/v2/variants/getlist?dataSource="}
      else{BASEPATH="https://ngdc.cncb.ac.cn/gvmRESTV3/v2/variants/getlist?dataSource="}
      // let snpAll=i.snpList.join(',')
      let PATH="";
		if(BASEPATH.length>0)
			PATH=BASEPATH+dataSource1+"&snplist="+snpId1;
		
      if(snpId1.length>0 && PATH.length>0){
          this.getVarData(PATH)
      }
    }
    // 右侧基因接口数据
    ortholist.forEach((item)=>{
    let dataSource2=item.dataSource;
    let hdbid=item.hdbId;
    let varName=item.varName;
    let gwasOrgId=item.gwasOrgId;
    let PATH="";
    if(dataSource2!= null && dataSource2.length>0){
      this.$axios.get("https://ngdc.cncb.ac.cn/hapi/api/var-snpid",{params:{'hdbId': hdbid,"varName":varName}}).then(res=>{
          if(res !=null && res.data != null && res.data.length !==0){
            let BASEPATH="";
            let snpId2=res.data;
            this.gwasLoading=true;
            if(dataSource2.indexOf("v2")>0){BASEPATH="https://ngdc.cncb.ac.cn/gvmRESTV2/v2/variants/getlist?dataSource="
            }else{BASEPATH="https://ngdc.cncb.ac.cn/gvmRESTV3/v2/variants/getlist?dataSource="}
			if(BASEPATH.length>0)
            PATH=BASEPATH+dataSource2+"&snplist="+snpId2;
          }
        }).finally(
          ()=>{
			if(PATH.length>0){
				
				this.getVarData(PATH,gwasOrgId)
			}

          }
        )
    }
   })
 
    
    
   },
   getVarData(PATH,gwasOrgId){
    this.$axios.get(PATH).then(response=>{
      this.gwasLoading=false;
      this.gwasInfoData=[];
      //this.gwasInfoData=response.data.snp;
      this.varLoading=false;
      let datas=response.data.snp
      if(datas.length>0){
        // 返回是对象
        for(let item of datas){
           item.orgId=gwasOrgId
        }
        this.gwasInfoData=this.gwasInfoData.concat(datas);
      }else{
        // 返回是对象
        datas.orgId=gwasOrgId
        this.gwasInfoData.push(datas);
      }
      // 对返回数据进行格式处理
      for(let item of this.gwasInfoData){
          let pos=item.chrom+":"+item.position;
          let allele=item.refallele+"/"+item.allele;
          let maf=item.maf+":"+item.maffreq.slice(0,7);
          let classsnp="SNP";
					item.finalposition=pos;
					item.finalallele=allele;
					item.finalmaf=maf;
          item.snpClassId=classsnp;
          if(item.gene.length>1){
            let name=[];
            let conseqtype=[];
            let allials=[];
            item.gene.forEach(item=>{
              name.push(item.genename);
              if(conseqtype.indexOf(item.conseqtype)==-1){
                conseqtype.push(item.conseqtype);
              }
              if(allials.indexOf(item.genealias)==-1){
                allials.push(item.genealias);
              }
              
            })
            item.finalgenename=name.join(',');
            item.finalConseqtype=conseqtype.join(',');
            item.finalGenealias=allials.join(',');
            item.finalGenealias=item.finalGenealias.length>0?item.finalGenealias:"-";
        }else{
          item.finalgenename=item.gene.genename;
          item.finalConseqtype=item.gene.conseqtype;
          item.finalGenealias=item.gene.genealias;
        }
      }
      })
   },
    hiligtDbCols({rowIndex}){
      if(rowIndex===1){
          return "writing-mode: vertical-lr; !important;text-align:right"
      }
      
    },
    iconClass(data,index){
        data.speciesListData.forEach((id,idx)=>{
          if(id==index){
            if( data.orthoList[idx].varName!=null ){
              let traitlist=data.orthoList[idx].varName.split(",")
              if(traitlist.indexOf(data.varName)>-1){
                return 1;
              }else{
                return 2;
            }
          }else{
            return false
          }
          }else{
            return false
          }
        })
      // }
    },
   
    searchFilter(){
      // 仅在输入了查询物种和基因的时候发送请求
      if(this.form.querySpecies=="" && this.form.gene==""){
        this.$message.error('Please enter query criteria');
      }else{
        if(this.classification=="animal"){
          this.loading_animal=true;
        }else{
          this.loading_plant=true;
        }
        
        this.pageNo=1;
        this.getTraitData(this.varItem.varName,this.classification)
        setTimeout(() => {
          this.messagError(this.classification)
        }, 1000);
      }
    }, 
    clearFilter(){
      if(this.form.querySpecies!=="" || this.form.gene!==""){
        
        this.form.querySpecies="";
        this.form.gene="";
        if(this.classification=="animal"){
          this.loading_animal=true;
        }else{
          this.loading_plant=true;
        }
        this.getTraitData(this.varItem.varName,this.classification)
        // this.getTraitData(this.varItem.varName,"plant")
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
            if(varData.orthoList){
              for(let item of varData.orthoList){
                let speciesName=item.species.commonName
                if(this.speciesList_plant.indexOf(speciesName)==-1){
                  this.speciesList_plant.push(speciesName);
                  item.species.checked=true;
                  this.speciesList_plant_1.push(item.species);
                }
                varData.speciesListData.push(this.speciesList_plant.indexOf(speciesName));
                if(item.varName !== null){
                  varData.traitListData2.push(this.speciesList_plant.indexOf(speciesName))
                  let varlist=item.varName.split(',')
                  if(varlist.indexOf( varData.varName)>-1){
                      varData.traitListData1.push(this.speciesList_plant.indexOf(speciesName))
                  }
                }
              }
            }
          }
          // this.$refs['table'].doLayout();
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
            if(varData.orthoList){
              for(let item of varData.orthoList){
                let speciesName=item.species.commonName;
                if(this.speciesList_animal.indexOf(speciesName)==-1){
                  this.speciesList_animal.push(speciesName);
                  item.species.checked=true;
                  this.speciesList_animal_1.push(item.species);
                }
                varData.speciesListData.push(this.speciesList_animal.indexOf(speciesName));
                if(item.varName !== null){
                  // 有trait但不相同
                   varData.traitListData2.push(this.speciesList_animal.indexOf(speciesName))
                  let varlist=item.varName.split(',')
                  if(varlist.indexOf( varData.varName)>-1){
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
    async getTraitData(varname,classification,hdbId,taxonId){
      // 分两种情况，如果不传入classification，就动植物都请求
      if(this.form.gene!=='' && this.form.gene !==undefined){
        this.form.gene=this.form.gene.trim()
      }
      // 如果传入了classification，就请求该分类
      // 没传入就都请求
      if(classification !=='' && classification !==undefined){
        // this.both=false;
        this.axiosRequest(varname,classification,hdbId,taxonId)
      }else{
        // this.both=true;
        this.axiosRequest(varname,"animal",hdbId,taxonId);
        this.axiosRequest(varname,"plant",hdbId,taxonId);
      }
   
    },
    axiosRequest(varname,classification,hdbId,taxonId){
      if(classification=="animal"){this.pageNo=this.currentPage4_a}else if(classification=="plant"){
        this.pageNo=this.currentPage4_p
      }else{this.pageNo=1}
       this.$axios.get("https://ngdc.cncb.ac.cn/hapi/api/variants",{params:{'classification': classification,'varname':varname,'length':this.pageSize,'pageNo':this.pageNo,'speciesName':this.form.querySpecies,'geneid':this.form.gene,'hdbId':hdbId,'taxonId':taxonId}})
          .then((response) => {
            this.getSpecies(varname,classification)
            
            if(classification=='animal'){
              this.totalSize_animal = response.data.recordsTotal;
              this.showTableIcon(response.data.data,classification).then((res)=>{ this.traitData_animal=res; this.loading_animal=false;});
            }else{
              this.totalSize_plant = response.data.recordsTotal;
              this.showTableIcon(response.data.data,classification).then((res)=>{ this.traitData_plant=res; this.loading_plant=false;});
            }
            // 判断什么时候该现实no data的提示
        }).finally(()=>{
          return Promise.resolve()})
    },
    // 根据当前classification判断获取动物或植物列表
    getSpecies(varName,speciesType){
      this.$axios.get('https://ngdc.cncb.ac.cn/hapi/api/species-var',{params: {"varName":varName,speciesType: speciesType}})
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
        this.varItem={
          varName:""
        }
        if(this.classification=='animal'){this.loading_animal=true}else{this.loading_plant=true}
        this.showTitle=false;
        // let params1={classification:"animal"}
        // let params={traitId:this.traitItem.traitDefID,classification:this.traitItem.classification,pagesize:this.pageSize,pagenum:1,speciesName:this.form.querySpecies}
        // let params2={classification:"plant"}
        this.getTraitData("","animal");
        this.getTraitData("","plant");
        // this.getTraitData("","plant");
      },
    exportToExcel(classs) {
      let oriData;
      if(classs=='animal'){oriData=this.traitData_animal}else{oriData=this.traitData_plant}
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = [ 'Variant Term',"Gene ID","Species Common Name","Taxon ID","Homologous Info Data"]
        const filterVal = ['varName','geneId','speciesCommonName','taxonId','orthoList']
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
        const tHeader = [ 'Species',"Taxon ID","Ensembl ID",'Gene Symbol',"Protein ID","DataSource"]
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
        const tHeader = [ 'Gene ID',"Var ID","Position",'Allele',"MAF","Class","Consequence Type/Effect","Gene Symbol"]
        const filterVal = ['finalgenename','rsnpId','finalposition','finalallele','finalmaf',"snpClassId",'finalConseqtype','finalGenealias']
        const data = this.formatJson(filterVal, gwasdata)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename:"Variants_Outer"
        })
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if(j=="orthoList"){
            let orthoData=v[j];
            let orthoList=[]
            for(let item of orthoData){
              let orthodataInfo={"Species Common Name":item.commonName,"Taxon ID":item.tax,"Variant Term":item.varName,"Homologous Protein ID":item.hdbId,"Data Source":item.dbEvidence}
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
    let taxonId=this.$route.query.taxonId;
    if(taxonId!==''&&taxonId!=null){
      let commonName=this.$route.query.commonName;
      this.form.querySpecies=commonName;
    }
    // if(hdbId!=null && hdbId!=""){}
    if(hdbId!=""&&hdbId!=null||(taxonId!==''&&taxonId!=null)){
      let classification;
      if(this.$route.query.speciesType==1){
        classification="animal";
        this.classification="animal";
        this.loading_animal=true;
      }else if(this.$route.query.speciesType==2){
        classification="plant";
        this.classification="plant";
        this.loading_plant=true;
      }
     
      this.varItem={
        varName:""
      }
        this.showTitle=false;
        this.getTraitData("",classification,hdbId,taxonId);
    }else{
      this.loading_animal = true;
      this.loading_plant=true;
      this.getTraitData(this.varItem.varName,"",hdbId,taxonId);
    }

    window.addEventListener("scroll", this.watchScroll);
    // this.getTableMaxHeight(); 
    // let _this = this;
    // window.onresize = function () {//用于使表格高度自适应的方法  
    // _this.getTableMaxHeight();//获取容器当前高度，重设表格的最大高度
    // }
   // 获取动植物名称列表，用于下拉选择框
    this.getSpecies(this.varItem.varName,"animal")
    this.getSpecies(this.varItem.varName,"plant")
    
     window.addEventListener("scroll", this.watchScroll);
      this.getTableMaxHeight(); 
      let _this = this;
      window.onresize = function () {//用于使表格高度自适应的方法  
      _this.getTableMaxHeight();//获取容器当前高度，重设表格的最大高度
    }
    
  }
}

</script>
