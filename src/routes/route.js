import { createRouter, createWebHistory } from 'vue-router'
import Home from '../vues/Home.vue'
import Connexion from '../vues/Connexion.vue'
import Inscription from '../vues/Inscription.vue'
import HomeAdmin from '../vues/HomeAdmin.vue'
import Utilisateurs from '../vues/Utilisateurs.vue'
import UtilisateurAjout from '../vues/UtilisateurAjout.vue'
import UtilisateurModifier from '../vues/UtilisateurModifier.vue'
import Statistiques from '../vues/Statistiques.vue'

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
    },{
        path: '/admin',
        name: 'HomeAdmin',
        component: HomeAdmin,
        meta: { requiresAuth: true }
    },{
        path: '/admin/utilisateurs',
        name: 'Utilisateurs',
        component: Utilisateurs,
        meta: { requiresAuth: true }
    },{
        path: '/admin/utilisateurs/ajout',
        name: 'UtilisateurAjout',
        component: UtilisateurAjout,
        meta: { requiresAuth: true }
    },{
        path: '/admin/utilisateurs/:id/modifier',
        name: 'UtilisateurModifier',
        component: UtilisateurModifier,
        meta: { requiresAuth: true }
    },{
        path: '/admin/statistiques',
        name: 'Statistiques',
        component: Statistiques,
        meta: { requiresAuth: true }
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})


export default router
