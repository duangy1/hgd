import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Traits from '../views/Traits.vue'
import Species from '../views/Species.vue'
import Gene from '../views/Gene.vue'
import GeneDetail from '../views/gene-detail.vue'
import dbDetail from '../views/db-detail.vue'
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
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/traits',
    name: 'Traits',
    component: Traits
  },
  {
    path: '/species',
    name: 'Species',
    component: Species
  },
  {
    path: '/gene',
    name: 'Gene',
    component: Gene
  },
  {
    path: '/gene-detail',
    name: 'GeneDetail',
    component: GeneDetail
  },
  {
    path: '/db-detail',
    name: 'dbDetail',
    component: dbDetail
  },
  {
    path: '/variants',
    name: 'Variants',
    component: Variants
  },
  {
    path: '/statistics',
    name: 'Statistics',
    component: Statistics
  },
  {
    path: '/downloads',
    name: 'Downloads',
    component: Downloads
  },
  {
    path: '/expression',
    name: 'Expression',
    component: Expression
  },
  {
    path: '/go',
    name: 'Go',
    component: Go
  },
  {
    path: '/documentation',
    name: 'Documentation',
    component: Documentation
  },
  {
    path: '/contact',
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
