<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarAdmin.vue"
import { ref, onMounted } from "vue"
import { useRouter } from "vue-router"

const router = useRouter()
const users = ref([])
const filteredUsers = ref([])

const selectedRole = ref('')
const search = ref('')

const token = localStorage.getItem("jwtToken")
if (!token) {
  router.push("/connexion")
}

// Récupérer les utilisateurs depuis l'API
async function fetchUsers() {
  try {
    const response = await fetch("http://localhost:10056/comptes", {
  method: "GET",
  headers: {
    "Authorization": `Bearer ${token}`,
    "Content-Type": "application/json"
  },
  credentials: "include" // utile si allowCredentials(true)
});
    if (!response.ok) {
      console.error("Erreur récupération utilisateurs", response.status)
      return
    }
    const data = await response.json()
    users.value = data.data
    filteredUsers.value = users.value
  } catch (err) {
    console.error("Impossible de récupérer les utilisateurs", err)
  }
}

// Filtrer par rôle
function filterByRole() {
  if (!selectedRole.value) {
    filteredUsers.value = users.value
    return
  }
  filteredUsers.value = users.value.filter(
    user => user.cptIsAdmin === Number(selectedRole.value)
  )
}

// Rechercher un utilisateur
function searchUser() {
  if (!search.value) {
    filteredUsers.value = users.value
    return
  }
  filteredUsers.value = users.value.filter(user =>
    user.cptPseudo.toLowerCase().includes(search.value.toLowerCase())
    // ajouter user.nom / user.prenom si dispo
  )
}

// Navigation
function goToModifier(user) {
  router.push(`/admin/utilisateurs/${user.cptPseudo}/modifier`)
}
function goToAjouter() {
  router.push(`/admin/utilisateurs/ajout`)
}
function goToStat(user) {
  router.push(`/admin/utilisateurs/${user.cptPseudo}/statistiques`)
}

// Au montage du composant
onMounted(() => {
  fetchUsers()
})
</script>

<template>
  <Navbar />
  <div class="home">
    <br/><br/><br/>

    <!-- Bandeau recherche / filtre (commenté pour l'instant) -->
    <!--
    <div class="bandeau">
      <div class="bandeau-item">
        <input id="search" v-model="search" @keyup.enter="searchUser" placeholder="Rechercher un utilisateur" />
        <button class="gestion" @click="searchUser">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" stroke="#0F171E" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-search">
            <circle cx="11" cy="11" r="8"/>
            <line x1="21" y1="21" x2="16.65" y2="16.65"/>
          </svg>
        </button>
      </div>

      <div class="bandeau-item filter">
        <label for="genreRole">Filtrer par role :</label>
        <select id="genreRole" v-model="selectedRole" @change="filterByRole">
          <option value="">Tous</option>
          <option value="1">Administrateur</option>
          <option value="0">Utilisateur</option>
        </select>
      </div>
    </div>
    -->

    <div>
      <button class="ajout" @click="goToAjouter">Ajouter</button>
      <div class="table-container">
        <table v-if="filteredUsers.length > 0">
          <caption>Utilisateurs inscrits sur l'application</caption>
          <thead>
            <tr>
              <th>Pseudo</th>
              <th>Nom</th>
              <th>Prénom</th>
              <th>Mail</th>
              <th>Role</th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="user in filteredUsers" :key="user.cptId">
              <td>{{ user.cptPseudo }}</td>
              <td>{{ user.cptPseudo }}</td> <!-- remplacer par nom si tu ajoutes ce champ -->
              <td>{{ user.cptPseudo }}</td> <!-- remplacer par prénom si tu ajoutes ce champ -->
              <td>{{ user.cptMail }}</td>
              <td v-if="user.cptIsAdmin === 1">Administrateur</td>
              <td v-else>Utilisateur</td>

              <td>
                <button class="gestion" @click="goToModifier(user)">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" stroke="#0F171E" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-edit-2">
                    <path d="M17 3a2.828 2.828 0 0 1 4 4L7 21H3v-4L17 3z"/>
                  </svg>
                </button>
              </td>

              <td>
                <button class="gestion" >
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" stroke="#0F171E" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-trash-2">
                    <polyline points="3 6 5 6 21 6"/>
                    <path d="M19 6l-1 14a2 2 0 0 1-2 2H8a2 2 0 0 1-2-2L5 6"/>
                    <line x1="10" y1="11" x2="10" y2="17"/>
                    <line x1="14" y1="11" x2="14" y2="17"/>
                  </svg>
                </button>
              </td>

              <td>
                <button class="gestion" @click="goToStat(user)">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" stroke="#0F171E" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-bar-chart-2">
                    <line x1="18" y1="20" x2="18" y2="10"/>
                    <line x1="12" y1="20" x2="12" y2="4"/>
                    <line x1="6" y1="20" x2="6" y2="14"/>
                  </svg>
                </button>
              </td>

              <!-- État actif désactivé pour l'instant -->
              <td>
                <svg xmlns="http://www.w3.org/2000/svg" width="50" height="28" viewBox="0 0 50 28" fill="none">
                  <rect x="1" y="1" width="48" height="26" rx="13" fill="#4CAF50" stroke="#0F171E" stroke-width="2"/>
                  <circle cx="37" cy="14" r="11" fill="white"/>
                </svg>
              </td>
            </tr>
          </tbody>
        </table>
        <h1 class="vide" v-else>Aucun utilisateur trouvé</h1>
      </div>
    </div>
  </div>
</template>
