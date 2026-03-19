<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarAdmin.vue"

import { ref, onMounted } from "vue"
import { useRouter,useRoute } from "vue-router"

const router = useRouter()
const route = useRoute()

const idTab = Number(route.params.id)  

const colonnes = ref([])

const tab=ref('')

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
  fetchColonnes()
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

async function fetchColonnes() {
  try {
    const response = await fetch(`http://localhost:10056/tableaux/${idTab}/colonnes`, {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    },
    credentials: "include" 
});
    if (!response.ok) {
      console.error("Erreur récupération colonnes", response.status)
      return
    }
    const data = await response.json()
    colonnes.value = data.data
  } catch (err) {
    console.error("Impossible de récupérer les colonnes", err)
  }
}

async function deleteCol(col) {
  const confirmed = window.confirm("Êtes-vous sûr de vouloir supprimer cette colonne ? Cette action est irréversible !");
  if (!confirmed) return;
  try {
    const response = await fetch(`http://localhost:10056/colonnes/${col.clnId}`, {
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
    fetchColonnes()
  } catch (err) {
    console.error("Impossible de supprimer la colonne", err);
  }
}

function goToModifier(col) {
  router.push(`/admin/tableaux/${idTab}/colonnes/${col.clnId}/modifier`)
}
function goToAjout() {
  router.push(`/admin/tableaux/${idTab}/colonnes/ajout`)
}
function goToTache(col) {
  router.push(`/admin/tableaux/${idTab}/colonnes/${col.clnId}/taches`)
}
function goToTableau() {
  router.push(`/admin/tableaux`)
}

</script>

<template>
  <Navbar />
  <div class="home">

    <br/><br/><br/>
    <button class="ajout" @click="goToTableau">   Retour
  </button>
  <button class="ajout" @click="goToAjout">   Ajouter
  </button>
    <div>
      <div class="table-container">
        <table v-if="colonnes.length > 0">
          <caption>Colonnes du tableau {{tab.tabNom}}</caption>
          <thead>
            <tr>
              <th>Titre</th>
              <th></th>
              <th></th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="col in colonnes" :key="col.clnId">
              <td>{{ col.clnNom}}</td> 

              <td>
                <button class="gestion" @click="goToModifier(col)">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" stroke="#0F171E" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-edit-2">
                    <path d="M17 3a2.828 2.828 0 0 1 4 4L7 21H3v-4L17 3z"/>
                  </svg>
                </button>
              </td>

              <td>
                <button class="gestion" @click="deleteCol(col)">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" stroke="#0F171E" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-trash-2">
                    <polyline points="3 6 5 6 21 6"/>
                    <path d="M19 6l-1 14a2 2 0 0 1-2 2H8a2 2 0 0 1-2-2L5 6"/>
                    <line x1="10" y1="11" x2="10" y2="17"/>
                    <line x1="14" y1="11" x2="14" y2="17"/>
                  </svg>
                </button>
              </td>

              <td>
                <button class="gestion" @click="goToTache(col)">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" stroke="#000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" viewBox="0 0 24 24">
                    <path d="M21 5H7"/>
                    <path d="M21 12H7"/>
                    <path d="M21 19H7"/>
                    <path d="M3 5l2 2 4-4"/>
                    <path d="M3 12l2 2 4-4"/>
                    <path d="M3 19l2 2 4-4"/>
                  </svg>
                </button>
              </td>
            </tr>
          </tbody>
        </table>
        <h1 class="vide" v-else>Aucun colonne pour ce tableau trouvée</h1>
      </div>
    </div>
  </div>
</template>
