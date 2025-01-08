# Font-backend code of HGD 

### Abstract
Homology is fundamental to infer genes’ evolutionary processes and relationships with shared ancestry. Existing homolog gene resources vary in terms of inferring methods, homologous relationship and identifiers, posing inevitable difficulties for choosing and mapping homology results from one to another. Here, we present HGD (Homologous Gene Database, https://ngdc.cncb.ac.cn/hgd), a comprehensive homologs resource integrating multi-species, multi-resources and multi-omics, as a complement to existing resources providing public and one-stop data service. Currently, HGD houses a total of 112 383 644 homologous pairs for 37 species, including 19 animals, 16 plants and 2 microorganisms. Meanwhile, HGD integrates various annotations from public resources, including 16 909 homologs with traits, 276 670 homologs with variants, 398 573 homologs with expression and 536 852 homologs with gene ontology (GO) annotations. HGD provides a wide range of omics gene function annotations to help users gain a deeper understanding of gene function.

HGD is seprated to front-end parts. This repository hosts the frontier and backend code.

The Frontier interface is developed by using several web packages as following:

- Frontend Framework: Vue.js  
- Routing: Vue Router  
- State Management: Vuex  
- HTTP Client: Axios  
- UI Frameworks: Vuetify, ElementUI  
- Table Component: vue-table  
- Utility Library: Lodash  
- Data Visualization: ECharts, D3.js  
- Internationalization: vue-i18n 

The Backend system is developed by using several backend technologies as following:

- Backend Framework: Spring Boot  
- Template Engine: Thymeleaf  
- Dependency Management: Maven  
- Database Access: Spring Data JPA, MyBatis   
- Authentication & Authorization: Spring Security, JWT (JSON Web Tokens)  
- Caching: Redis
- Logging: Logback
- Testing: JUnit
- Containerization: Docker

If you are interested in details of HGD, please visit the [HGD document](https://ngdc.cncb.ac.cn/hgd/documentation)
