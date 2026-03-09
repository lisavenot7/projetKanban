import { createRouter, createWebHistory } from 'vue-router'
import Home from '../vues/Home.vue'
const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home,
        meta: { requiresAuth: true }
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})


export default router
