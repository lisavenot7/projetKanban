import { createRouter, createWebHistory } from 'vue-router'
import Home from '../vues/Home.vue'
import Connexion from '../vues/Connexion.vue'
import Inscription from '../vues/Inscription.vue'
import HomeAdmin from '../vues/HomeAdmin.vue'
import Utilisateurs from '../vues/Utilisateurs.vue'
import UtilisateurAjout from '../vues/UtilisateurAjout.vue'
import UtilisateurModifier from '../vues/UtilisateurModifier.vue'
import Statistiques from '../vues/Statistiques.vue'
import StatistiquesUser from '../vues/StatistiquesUser.vue'
import Profil from '../vues/Profil.vue'
import ProfilModifier from '../vues/ProfilModifier.vue'
import HomeUtilisateur from '../vues/HomeUtilisateur.vue'
import Tableaux from '../vues/Tableaux.vue'
import Tableau from '../vues/Tableau.vue'
import TableauAjouter from '../vues/TableauAjouter.vue'
import TableauModifier from '../vues/TableauModifier.vue'
import Participants from '../vues/Participants.vue'

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
        path: '/admin/utilisateurs/:id/statistiques',
        name: 'StatistiquesUser',
        component: StatistiquesUser,
        meta: { requiresAuth: true }
    },{
        path: '/admin/statistiques',
        name: 'Statistiques',
        component: Statistiques,
        meta: { requiresAuth: true }
    },{
        path: '/admin/profil',
        name: 'Profil',
        component: Profil,
        meta: { requiresAuth: true }
    },{
        path: '/admin/profil/modifier',
        name: 'ProfilModifier',
        component: ProfilModifier,
        meta: { requiresAuth: true }
    },{
        path: '/private',
        name: 'HomeUtilisateur',
        component: HomeUtilisateur,
        meta: { requiresAuth: true }
    },{
        path: '/private/tableaux',
        name: 'Tableaux',
        component: Tableaux,
        meta: { requiresAuth: true }
    },{
        path: '/private/tableaux/:id',
        name: 'Tableau',
        component: Tableau,
        meta: { requiresAuth: true }
    },{
        path: '/private/tableaux/ajouter',
        name: 'TableauAjouter',
        component: TableauAjouter,
        meta: { requiresAuth: true }
    },{
        path: '/private/tableaux/:id/modifier',
        name: 'TableauModifier',
        component: TableauModifier,
        meta: { requiresAuth: true }
    },{
        path: '/private/profil',
        name: 'ProfilUtilisateur',
        component: Profil,
        meta: { requiresAuth: true }
    },{
        path: '/private/profil/modifier',
        name: 'ProfilUtilisateurModifier',
        component: ProfilModifier,
        meta: { requiresAuth: true }
    },{
        path: '/private/tableaux/:id/participants',
        name: 'Participants',
        component: Participants,
        meta: { requiresAuth: true }
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})


export default router
