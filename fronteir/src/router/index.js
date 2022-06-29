import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Traits from '../views/Traits.vue'
import Species from '../views/Species.vue'
import Gene from '../views/Gene.vue'
import GeneDetail from '../views/gene-detail.vue'
// import dbDetail from '../views/db-detail.vue'
import Variants from '../views/Variant.vue'
import Statistics from '../views/Statistics.vue'
import Downloads from '../views/Downloads.vue'
import Documentation from '../views/Documentation.vue'
import Expression from '../views/Expression.vue'
import Go from '../views/Go.vue'
import Contact from '../views/Contact.vue'
Vue.use(VueRouter)

const routes = [
  {
    path: '/hgd',
    name: 'Home',
    component: Home
  },
  {
    path: '/hgd/traits',
    name: 'Traits',
    component: Traits
  },
  {
    path: '/hgd/species',
    name: 'Species',
    component: Species
  },
  {
    path: '/hgd/gene',
    name: 'Gene',
    component: Gene
  },
  {
    path: '/hgd/gene-detail',
    name: 'GeneDetail',
    component: GeneDetail
  },
  {
    path: '/hgd/variants',
    name: 'Variants',
    component: Variants
  },
  {
    path: '/hgd/statistics',
    name: 'Statistics',
    component: Statistics
  },
  {
    path: '/hgd/downloads',
    name: 'Downloads',
    component: Downloads
  },
  {
    path: '/hgd/expression',
    name: 'Expression',
    component: Expression
  },
  {
    path: '/hgd/go',
    name: 'Go',
    component: Go
  },
  {
    path: '/hgd/documentation',
    name: 'Documentation',
    component: Documentation
  },
  {
    path: '/hgd/contact',
    name: 'Contact',
    component: Contact
  },
  // {
  //   path: '/about',
  //   name: 'About',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  // }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
