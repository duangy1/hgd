package cn.ac.cncb.ngdc.syndb.controller;

import cn.ac.cncb.ngdc.syndb.bean.OrthoGeneBean;
import cn.ac.cncb.ngdc.syndb.entity.GeneBasicInfo;
import cn.ac.cncb.ngdc.syndb.service.OrthologGeneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;

@Controller
@RequestMapping("download")
public class DownloadController {

    @Value("${app.root}")
    private String root;

    @Autowired
    private OrthologGeneService orthologGeneService;

    @RequestMapping(value = "homolog/{homolog}/{filetype}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> downloadHomolog(@PathVariable String homolog, @PathVariable int filetype) {

        try {
            List<String> homologList = new ArrayList<String>();
            if (homolog != null && homolog.length() > 0) {
                if (homolog.indexOf(",") > -1) {
                    String[] homologArry = homolog.split("\\,");
                    if (homologArry != null && homologArry.length > 0) {
                        for (String homo : homologArry) {
                            homologList.add(homo);
                        }
                    }
                } else {
                    homologList.add(homolog);
                }

                Map param = new HashMap();
                param.put("hdbgeneList", homologList);


                String fileName = "";
                if (filetype == 1) {
                    fileName = "ortholog.txt";
                } else if (filetype == 2) {
                    fileName = "ortholog.csv";
                }

                String time = new Date().getTime() + "";


                String fileloc = root + File.separator + time + "_" + fileName;
                BufferedWriter bw = new BufferedWriter(new FileWriter(fileloc));
                bw.write("GeneName/ID\tSynonym\tTaxon Id\tBioType\tHomolog Gene(GeneName SpeciesName,multiple separated by |)\n");
                List<GeneBasicInfo> geneBasicInfoList = orthologGeneService.filterHomologGene(param);
                if (geneBasicInfoList != null && geneBasicInfoList.size() > 0) {

                    for (GeneBasicInfo geneBasicInfo : geneBasicInfoList) {
                        String geneid = geneBasicInfo.getHdbGeneId();
                        int taxonId = geneBasicInfo.getTaxonId();
                        int speciesType = geneBasicInfo.getSpeciesType();
                        String gene = "";
                        if (geneBasicInfo.getGeneSymbol() != null && geneBasicInfo.getGeneSymbol().length() > 0) {
                            gene = geneBasicInfo.getGeneSymbol();
                            geneBasicInfo.setShowGeneType(1);
                        } else if (geneBasicInfo.getEnsemblGeneId() != null && geneBasicInfo.getEnsemblGeneId().length() > 0) {
                            gene = geneBasicInfo.getEnsemblGeneId();
                            geneBasicInfo.setShowGeneType(2);
                        } else if (geneBasicInfo.getEntrezGene() != null && geneBasicInfo.getEntrezGene().length() > 0) {
                            gene = geneBasicInfo.getEntrezGene();
                            geneBasicInfo.setShowGeneType(3);
                        } else {
                            gene = geneBasicInfo.getHdbGeneId();
                            geneBasicInfo.setShowGeneType(4);
                        }
                        List orthoGeneBeanList = orthologGeneService.getHomologGene(speciesType, geneid, gene, taxonId, null);


                        String symbol = "";
                        if (geneBasicInfo.getGeneSymbol() != null && geneBasicInfo.getGeneSymbol().length() > 0) {
                            symbol = geneBasicInfo.getGeneSymbol();
                        }
                        String synonym = "";
                        if (geneBasicInfo.getGeneSynonym() != null && geneBasicInfo.getGeneSynonym().length() > 0 && geneBasicInfo.getGeneSynonym().equals("null") == false) {
                            synonym = geneBasicInfo.getGeneSynonym();
                        }
                        String genetype = "";
                        if (geneBasicInfo.getGeneType() != null && geneBasicInfo.getGeneType().length() > 0 && geneBasicInfo.getGeneType().equals("null") == false) {
                            genetype = geneBasicInfo.getGeneType();
                        }

                        if (filetype == 1) {
                            bw.write(gene + "\t" + synonym + "\t"
                                    + geneBasicInfo.getTaxonId() + "\t" + genetype + "\t");
                            if (orthoGeneBeanList != null && orthoGeneBeanList.size() > 0) {
                                for (int i = 0; i < orthoGeneBeanList.size(); i++) {
                                    List<OrthoGeneBean> orthoGeneEmbedBeanList = (List<OrthoGeneBean>) orthoGeneBeanList.get(i);

                                    for (OrthoGeneBean orthoGeneBean : orthoGeneEmbedBeanList) {
                                        bw.write(orthoGeneBean.getGeneName() + " " + orthoGeneBean.getTaxonName() + "|");
                                    }
                                }

                            }
                            bw.write("\n");
                            bw.flush();
                        } else if (filetype == 2) {
                            bw.write(gene + "," + synonym + ","
                                    + geneBasicInfo.getTaxonId() + "," + genetype + ",");
                            if (orthoGeneBeanList != null && orthoGeneBeanList.size() > 0) {
                                for (int i = 0; i < orthoGeneBeanList.size(); i++) {
                                    List<OrthoGeneBean> orthoGeneEmbedBeanList = (List<OrthoGeneBean>) orthoGeneBeanList.get(i);

                                    for (OrthoGeneBean orthoGeneBean : orthoGeneEmbedBeanList) {
                                        bw.write(orthoGeneBean.getGeneName() + " " + orthoGeneBean.getTaxonName() + "|");
                                    }
                                }
                            }
                            bw.write("\n");
                            bw.flush();
                        }

                    }

                }

                bw.close();
                System.out.println("homolog=" + homolog);
                System.out.println("filetype=" + filetype);


                File file = new File(fileloc);


                if (file != null && file.exists() == true) {
                    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                    HttpHeaders headers = new HttpHeaders();

//        headers.add("Content-Disposition", String.format("inline;filename=\"%s", fileName))
                    headers.add("Content-Disposition", String.format("attachment;filename=\"%s", fileName));
                    headers.add("Cache-Control", "no-cache,no-store,must-revalidate");
                    headers.add("Pragma", "no-cache");
                    headers.add("Expires", "0");

                    ResponseEntity<Object> responseEntity = ResponseEntity.ok()
                            .headers(headers)
                            .contentLength(file.length())
//                .contentType(MediaType.parseMediaType("application/pdf"))
                            .body(resource);
                    return responseEntity;
                } else {
                    return ResponseEntity.notFound().build();
                }
            }else{
                return ResponseEntity.notFound().build();
            }

        } catch (FileNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

    }
}