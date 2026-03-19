<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarAdmin.vue"

import { ref, onMounted } from "vue"
import { useRouter,useRoute } from "vue-router"

const router = useRouter()
const route = useRoute()

const idTab = Number(route.params.id)
const idColonne = Number(route.params.idcolonne)  
const idTache = Number(route.params.idtache)

const commentaires = ref ([])
const users = ref({})

const tab = ref('')
const colonne = ref('')
const tache = ref('')

const token = localStorage.getItem("jwtToken")
onMounted(() => {
  const admin = localStorage.getItem("isAdmin")
  if (!token) {
    router.push("/connexion")
  }
  if (admin === "0") {
    router.push("/private")
  }
  fetchTab()
  fetchColonne()
  fetchTache()
  fetchCommentaires()
})

async function fetchTab() {
  try {
    const response = await fetch(`http://localhost:10056/tableaux/${idTab}`, {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    },
    credentials: "include" 
});
    if (!response.ok) {
      console.error("Erreur récupération tableau", response.status)
      return
    }
    const data = await response.json()
    tab.value = data.data
  } catch (err) {
    console.error("Impossible de récupérer le tableau", err)
  }
}

async function fetchColonne() {
  try {
    const response = await fetch(`http://localhost:10056/colonnes/${idColonne}`, {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    },
    credentials: "include" 
});
    if (!response.ok) {
      console.error("Erreur récupération colonne", response.status)
      return
    }
    const data = await response.json()
    colonne.value = data.data
  } catch (err) {
    console.error("Impossible de récupérer la colonne", err)
  }
}

async function fetchTache() {
  try {
    const response = await fetch(`http://localhost:10056/taches/${idTache}`, {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    },
    credentials: "include" 
});
    if (!response.ok) {
      console.error("Erreur récupération tache", response.status)
      return
    }
    const data = await response.json()
    tache.value = data.data
  } catch (err) {
    console.error("Impossible de récupérer la tache", err)
  }
}

async function fetchCommentaires() {
  try {
    const response = await fetch(`http://localhost:10056/taches/${idTache}/commentaires`, {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    },
    credentials: "include" 
});
    if (!response.ok) {
      console.error("Erreur récupération commentaires", response.status)
      return
    }
    const data = await response.json()
    commentaires.value = data.data
    for(const com of commentaires.value){
      fetchUser(com.cptId)
    }
    
  } catch (err) {
    console.error("Impossible de récupérer les commentaires", err)
  }
}

async function fetchUser(idUser) {
  try {
    if (users.value[idUser]) return
    const response = await fetch(`http://localhost:10056/comptes/${idUser}`, {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json"
      },
      credentials: "include" 
    });
    if (!response.ok) {
      console.error("Erreur récupération profil", response.status)
      return
    }
    const res = await response.json()
    users.value[idUser] = res.data
  } catch (err) {
    console.error("Impossible de récupérer le profil", err)
  }
}

async function deleteCom(com) {
  const confirmed = window.confirm("Êtes-vous sûr de vouloir supprimer ce commentaire ? Cette action est irréversible !");
  if (!confirmed) return;
  try {
    const response = await fetch(`http://localhost:10056/commentaires/${com.id}`, {
      method: "DELETE",
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json"
      },
      body: JSON.stringify() 
    });
    if (!response.ok) {
      console.error("Erreur lors de la suppression", response.status);
      return;
    }
    fetchCommentaires()
  } catch (err) {
    console.error("Impossible de supprimer le commentaire", err);
  }
}

function goToAjout() {
  router.push(`/admin/tableaux/${idTab}/colonnes/${idColonne}/taches/${idTache}/commentaires/ajout`)
}
function goToTache() {
  router.push(`/admin/tableaux/${idTab}/colonnes/${idColonne}/taches/`)
}
</script>

<template>
  <Navbar />
  <div class="home">

    <br/><br/><br/>
    <button class="ajout" @click="goToTache">   Retour
  </button>
  <button class="ajout" @click="goToAjout">   Ajouter
  </button>
    <div>
      <div class="table-container">
        <table v-if="commentaires.length > 0">
          <caption>Tache du tableau {{tab.tabNom}} de la colonne {{colonne.clnNom}} et de la tache {{tache.tchTitre}}</caption>
          <thead>
            <tr>
              <th>Contenu</th>
              <th>Ecrit le</th>
              <th>Par</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="com in commentaires" :key="com.id">
              <td>{{ com.contenu}}</td> 
              <td>{{ com.datePublication}}</td> 
              <td >{{ users[com.cptId]?.prenom }} 
                {{ users[com.cptId]?.nom }}</td> 

            <td>
                <button class="gestion" @click="deleteCom(com)">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" stroke="#0F171E" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-trash-2">
                    <polyline points="3 6 5 6 21 6"/>
                    <path d="M19 6l-1 14a2 2 0 0 1-2 2H8a2 2 0 0 1-2-2L5 6"/>
                    <line x1="10" y1="11" x2="10" y2="17"/>
                    <line x1="14" y1="11" x2="14" y2="17"/>
                  </svg>
                </button>
              </td>
            </tr>
          </tbody>
        </table>
        <h1 class="vide" v-else>Aucune tâche pour cette colonne trouvée</h1>
      </div>
    </div>
  </div>
</template>
