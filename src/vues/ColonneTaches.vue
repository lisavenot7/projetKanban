<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarAdmin.vue"

import { ref, onMounted } from "vue"
import { useRouter,useRoute } from "vue-router"

const router = useRouter()
const route = useRoute()

const idTab = Number(route.params.id)
const idColonne = Number(route.params.idcolonne)  

const taches = ref([])
const users = ref({})

const tab=ref('')
const colonne=ref('')

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
  fetchTaches()
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

async function fetchTaches() {
  try {
    const response = await fetch(`http://localhost:10056/colonnes/${idColonne}/taches`, {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    },
    credentials: "include" 
});
    if (!response.ok) {
      console.error("Erreur récupération taches", response.status)
      return
    }
    const data = await response.json()
    taches.value = data.data
    for(const t of taches.value){
      if(t.compteId!=null){
        fetchUser(t.compteId)
      }
    }
  } catch (err) {
    console.error("Impossible de récupérer les taches", err)
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

async function deleteTache(t) {
  const confirmed = window.confirm("Êtes-vous sûr de vouloir supprimer cette tache ? Cette action est irréversible !");
  if (!confirmed) return;
  try {
    const response = await fetch(`http://localhost:10056/taches/${t.tchId}`, {
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
    fetchTaches()
  } catch (err) {
    console.error("Impossible de supprimer la tache", err);
  }
}

function goToModifier(t) {
  router.push(`/admin/tableaux/${idTab}/colonnes/${idColonne}/taches/${t.tchId}/modifier`)
}
function goToAjout() {
  router.push(`/admin/tableaux/${idTab}/colonnes/${idColonne}/taches/ajout`)
}
function goToCom(t) {
  router.push(`/admin/tableaux/${idTab}/colonnes/${idColonne}/taches/${t.tchId}/commentaires`)
}
function goToColonne() {
  router.push(`/admin/tableaux/${idTab}/colonnes`)
}

const today = new Date()
today.setHours(0, 0, 0, 0)

</script>

<template>
  <Navbar />
  <div class="home">

    <br/><br/><br/>
    <button class="ajout" @click="goToColonne">   Retour
  </button>
  <button class="ajout" @click="goToAjout">   Ajouter
  </button>
    <div>
      <div class="table-container">
        <table v-if="taches.length > 0">
          <caption>Tache du tableau {{tab.tabNom}} et de la colonne {{colonne.clnNom}}</caption>
          <thead>
            <tr>
              <th>Titre</th>
              <th>Prioritaire</th>
              <th>Date Limite</th>
              <th>Assigné à </th>
              <th>Status</th>
              <th>En retard</th>
              <th></th>
              <th></th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="t in taches" :key="t.tchId">
              <td>{{ t.tchTitre}}</td> 
              <td v-if="t.tchPriorite">oui</td> 
              <td v-else >non</td> 
              <td v-if="t.tchDateLimite===null">non</td> 
              <td v-else >{{ t.tchDateLimite}}</td> 
              <td v-if="t.compteId!=null">{{ users[t.compteId]?.prenom }} 
                {{ users[t.compteId]?.nom }}</td> 
                <td v-else></td>
              <td>{{ t.tchStatus}}</td>
              <td v-if="t.tchDateLimite && new Date(t.tchDateLimite) < today && t.tchStatus!='Terminé'">oui</td> 
              <td v-else >non</td> 

              <td>
                <button class="gestion" @click="goToModifier(t)">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" stroke="#0F171E" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-edit-2">
                    <path d="M17 3a2.828 2.828 0 0 1 4 4L7 21H3v-4L17 3z"/>
                  </svg>
                </button>
              </td>

              <td>
                <button class="gestion" @click="deleteTache(t)">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" stroke="#0F171E" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-trash-2">
                    <polyline points="3 6 5 6 21 6"/>
                    <path d="M19 6l-1 14a2 2 0 0 1-2 2H8a2 2 0 0 1-2-2L5 6"/>
                    <line x1="10" y1="11" x2="10" y2="17"/>
                    <line x1="14" y1="11" x2="14" y2="17"/>
                  </svg>
                </button>
              </td>

              <td>
                <button class="gestion" @click="goToCom(t)">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" stroke="#000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" viewBox="0 0 24 24">
                    <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"/>
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
