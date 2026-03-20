<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarAdmin.vue"

import { ref, onMounted } from "vue"
import { useRouter } from "vue-router"

const router = useRouter()

const tableaux = ref([])
const users = ref({})

const filteredTabs = ref([])
const search = ref('')

const token = localStorage.getItem("jwtToken")
onMounted(() => {
  const admin = localStorage.getItem("isAdmin")
  if (!token) {
    router.push("/connexion")
  }
  if (admin === "0") {
    router.push("/private")
  }
  fetchTabs()
})

async function fetchTabs() {
  try {
    const response = await fetch("http://localhost:10056/tableaux", {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    },
    credentials: "include" 
});
    if (!response.ok) {
      console.error("Erreur récupération tableaux", response.status)
      return
    }
    const data = await response.json()
    tableaux.value = data.data
    filteredTabs.value = tableaux.value
    for(const tab of tableaux.value){
      createur(tab.createurId)
    }
  } catch (err) {
    console.error("Impossible de récupérer les tableaux", err)
  }
}

async function createur(idUser) {
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

async function deleteTab(tab) {
  const confirmed = window.confirm("Êtes-vous sûr de vouloir supprimer ce tableau ? Cette action est irréversible !");
  if (!confirmed) return;
  try {
    const response = await fetch(`http://localhost:10056/tableaux/${tab.tabId}`, {
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
    fetchTabs()
  } catch (err) {
    console.error("Impossible de supprimer le tableau", err);
  }
}

function searchTab() {
  if (!search.value) {
    filteredTabs.value = tableaux.value
    return
  }
  filteredTabs.value = tableaux.value.filter(t =>
    t.tabNom.toLowerCase().includes(search.value.toLowerCase()) 
  )
}

function goToModifier(tab) {
  router.push(`/admin/tableaux/${tab.tabId}/modifier`)
}
function goToColonne(tab) {
  router.push(`/admin/tableaux/${tab.tabId}/colonnes`)
}
function goToPart(tab) {
  router.push(`/admin/tableaux/${tab.tabId}/participants`)
}

</script>

<template>
  <Navbar />
  <div class="home">

    <br/><br/><br/>
    <div class="bandeau">
      <div class="bandeau-item">
        <input 
          id="search" 
          v-model="search" 
          @keyup.enter="searchTab" 
          placeholder="Rechercher un tableau" 
        />
        <button class="gestion" @click="searchTab">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" stroke="#0F171E" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-search">
            <circle cx="11" cy="11" r="8"/>
            <line x1="21" y1="21" x2="16.65" y2="16.65"/>
          </svg>
        </button>
      </div>
    </div>

    <div>
      <div class="table-container">
        <table v-if="filteredTabs.length > 0">
          <caption>Tableaux utilisé sur l'application</caption>
          <thead>
            <tr>
              <th>Titre</th>
              <th>Créateur</th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="tab in filteredTabs" :key="tab.tabId">
              <td>{{ tab.tabNom }}</td> 
              <td>
                {{ users[tab.createurId]?.prenom }} 
                {{ users[tab.createurId]?.nom }}
              </td> 

              <td>
                <button class="gestion" @click="goToModifier(tab)">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" stroke="#0F171E" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-edit-2">
                    <path d="M17 3a2.828 2.828 0 0 1 4 4L7 21H3v-4L17 3z"/>
                  </svg>
                </button>
              </td>

              <td>
                <button class="gestion" @click="deleteTab(tab)">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" stroke="#0F171E" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-trash-2">
                    <polyline points="3 6 5 6 21 6"/>
                    <path d="M19 6l-1 14a2 2 0 0 1-2 2H8a2 2 0 0 1-2-2L5 6"/>
                    <line x1="10" y1="11" x2="10" y2="17"/>
                    <line x1="14" y1="11" x2="14" y2="17"/>
                  </svg>
                </button>
              </td>

              <td>
                <button class="gestion" @click="goToColonne(tab)">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" viewBox="0 0 24 24">
                    <rect x="2" y="4" width="5" height="16" rx="1"/>
                    <rect x="9.5" y="4" width="5" height="16" rx="1"/>
                    <rect x="17" y="4" width="5" height="16" rx="1"/>
                  </svg>
                </button>
              </td>

              <td>
                <button class="gestion" @click="goToPart(tab)">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="#000" viewBox="0 0 24 24">
                    <path d="M16 11c1.66 0 3-1.57 3-3.5S17.66 4 16 4s-3 1.57-3 3.5S14.34 11 16 11zM8 11c1.66 0 3-1.57 3-3.5S9.66 4 8 4 5 5.57 5 7.5 6.34 11 8 11zm0 2c-2.33 0-7 1.17-7 3.5V20h14v-3.5C15 14.17 10.33 13 8 13zm8 0c-.29 0-.62.02-.97.05C16.68 13.69 18 14.76 18 16.5V20h6v-3.5c0-2.33-4.67-3.5-7-3.5z"/>
                  </svg>
                </button>
              </td> 
            </tr>
          </tbody>
        </table>
        <h1 class="vide" v-else>Aucun tableau trouvé</h1>
      </div>
    </div>
  </div>
</template>
