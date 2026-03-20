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
import ParticipantsGestion from '../vues/ParticipantsGestion.vue'
import ColonneAjouter from '../vues/ColonneAjouter.vue'
import ColonneModifier from '../vues/ColonneModifier.vue'
import TacheAjouter from '../vues/TacheAjouter.vue'
import Tache from '../vues/Tache.vue'
import TacheModifier from '../vues/TacheModifier.vue'
import TacheAssigner from '../vues/TacheAssigner.vue'
import CommentaireAjouter from '../vues/CommentaireAjouter.vue'
import CommentaireModifier from '../vues/CommentaireModifier.vue'
import TableauxAdmin from '../vues/TableauxAdmin.vue'
import TableauModifierAdmin from '../vues/TableauModifierAdmin.vue'
import ParticipantsAdmin from '../vues/ParticipantsAdmin.vue'
import ParticipantsGestionAdmin from '../vues/ParticipantsGestionAdmin.vue'
import TableauColonnes from '../vues/TableauColonnes.vue'
import ColonneAjouterAdmin from '../vues/ColonneAjouterAdmin.vue'
import ColonneModifierAdmin from '../vues/ColonneModifierAdmin.vue'
import ColonneTaches from '../vues/ColonneTaches.vue'
import TacheAjouterAdmin from '../vues/TacheAjouterAdmin.vue'
import TacheModifierAdmin from '../vues/TacheModifierAdmin.vue'
import TacheCommentaires from '../vues/TacheCommentaires.vue'
import CommentaireAjouterAdmin from '../vues/CommentaireAjouterAdmin.vue'

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
    },{
        path: '/private/tableaux/:id/participants/gestion',
        name: 'ParticipantsGestion',
        component: ParticipantsGestion,
        meta: { requiresAuth: true }
    },{
        path: '/private/tableaux/:id/colonnes/ajouter',
        name: 'ColonneAjouter',
        component: ColonneAjouter,
        meta: { requiresAuth: true }
    },{
        path: '/private/tableaux/:id/colonnes/:idcolonne/modifier',
        name: 'ColonneModifier',
        component: ColonneModifier,
        meta: { requiresAuth: true }
    },{
        path: '/private/tableaux/:id/colonnes/:idcolonne/taches/ajouter',
        name: 'TacheAjouter',
        component: TacheAjouter,
        meta: { requiresAuth: true }
    },{
        path: '/private/tableaux/:id/colonnes/:idcolonne/taches/:idtache',
        name: 'Tache',
        component: Tache,
        meta: { requiresAuth: true }
    },{
        path: '/private/tableaux/:id/colonnes/:idcolonne/taches/:idtache/modifier',
        name: 'TacheModifier',
        component: TacheModifier,
        meta: { requiresAuth: true }
    },{
        path: '/private/tableaux/:id/colonnes/:idcolonne/taches/:idtache/assigner',
        name: 'TacheAssigner',
        component: TacheAssigner,
        meta: { requiresAuth: true }
    },{
        path: '/private/tableaux/:id/colonnes/:idcolonne/taches/:idtache/commentaires/ajouter',
        name: 'CommentaireAjouter',
        component: CommentaireAjouter,
        meta: { requiresAuth: true }
    },{
        path: '/private/tableaux/:id/colonnes/:idcolonne/taches/:idtache/commentaires/:idcom/modifier',
        name: 'CommentaireModifier',
        component: CommentaireModifier,
        meta: { requiresAuth: true }
    },{
        path: '/admin/tableaux',
        name: 'TableauxAdmin',
        component: TableauxAdmin,
        meta: { requiresAuth: true }
    },{
        path: '/admin/tableaux/:id/modifier',
        name: 'TableauModifierAdmin',
        component: TableauModifierAdmin,
        meta: { requiresAuth: true }
    },{
        path: '/admin/tableaux/:id/participants',
        name: 'ParticipantsAdmin',
        component: ParticipantsAdmin,
        meta: { requiresAuth: true }
    },{
        path: '/admin/tableaux/:id/participants/gestion',
        name: 'ParticipantsGestionAdmin',
        component: ParticipantsGestionAdmin,
        meta: { requiresAuth: true }
    },{
        path: '/admin/tableaux/:id/colonnes',
        name: 'TableauColonnes',
        component: TableauColonnes,
        meta: { requiresAuth: true }
    },{
        path: '/admin/tableaux/:id/colonnes/ajout',
        name: 'ColonneAjouterAdmin',
        component: ColonneAjouterAdmin,
        meta: { requiresAuth: true }
    },{
        path: '/admin/tableaux/:id/colonnes/:idcolonne/modifier',
        name: 'ColonneModifierAdmin',
        component: ColonneModifierAdmin,
        meta: { requiresAuth: true }
    },{
        path: '/admin/tableaux/:id/colonnes/:idcolonne/taches',
        name: 'ColonneTaches',
        component: ColonneTaches,
        meta: { requiresAuth: true }
    },{
        path: '/admin/tableaux/:id/colonnes/:idcolonne/taches/ajout',
        name: 'TacheAjouterAdmin',
        component: TacheAjouterAdmin,
        meta: { requiresAuth: true }
    },{
        path: '/admin/tableaux/:id/colonnes/:idcolonne/taches/:idtache/modifier',
        name: 'TacheModifierAdmin',
        component: TacheModifierAdmin,
        meta: { requiresAuth: true }
    },{
        path: '/admin/tableaux/:id/colonnes/:idcolonne/taches/:idtache/commentaires',
        name: 'TacheCommentaires',
        component: TacheCommentaires,
        meta: { requiresAuth: true }
    },{
        path: '/admin/tableaux/:id/colonnes/:idcolonne/taches/:idtache/commentaires/ajout',
        name: 'CommentaireAjouterAdmin',
        component: CommentaireAjouterAdmin,
        meta: { requiresAuth: true }
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})


export default router
