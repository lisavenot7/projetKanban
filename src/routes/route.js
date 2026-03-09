import { createRouter, createWebHistory } from 'vue-router'
import Home from '../vues/Home.vue'
import Connexion from '../vues/Connexion.vue'
import Inscription from '../vues/Inscription.vue'
const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home,
        meta: { requiresAuth: true }
    },{
        path: '/connexion',
        name: 'Connexion',
        component: Connexion,
        meta: { requiresAuth: false }
    },{
        path: '/inscription',
        name: 'Inscription',
        component: Inscription,
        meta: { requiresAuth: false }
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})


export default router
