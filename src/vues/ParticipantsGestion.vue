<style src="../assets/css/style.css"></style>
<script setup>
  import Navbar from "../components/NavbarUtilisateur.vue"
  import { useRouter, useRoute } from "vue-router"
  import { computed, ref , onMounted} from "vue"

  const router = useRouter()
  const route = useRoute()

  const idTab = Number(route.params.id)

  const users = ref([])

  const search = ref("")

  const selectedUsers = ref([])

  const createur = ref('')

  const error = ref('')

  const filteredUsers = computed(() => {
    const query = search.value.toLowerCase()
    return users.value.filter(user => {
      if (createur.value && user.cptId === createur.value.cptId) {
        return false
      }
      return `${user.prenom} ${user.nom}`
        .toLowerCase()
        .includes(query)
    })
  })

  async function fetchParticipants(idTab) {
  try {
    const response = await fetch(`http://localhost:10056/tableaux/${idTab}/participants`, {
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
    selectedUsers.value = data.data
  } catch (err) {
    console.error("Impossible de récupérer le tableau", err)
  }
}

async function fetchCreateur(idTab) {
  try {
    const response = await fetch(`http://localhost:10056/tableaux/${idTab}/createur`, {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    },
    credentials: "include" 
});
    if (!response.ok) {
      console.error("Erreur récupération créateur", response.status)
      return
    }
    const data = await response.json()
    createur.value = data.data
    console.log(createur.value)
    if(createur.value.cptId != Number(idUser)){
      router.push("/private/tableaux")
    }
  } catch (err) {
    console.error("Impossible de récupérer le créateur", err)
  }
}

async function fetchUsers() {
  try {
    const response = await fetch("http://localhost:10056/comptes", {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    },
    credentials: "include" 
});
    if (!response.ok) {
      console.error("Erreur récupération utilisateurs", response.status)
      return
    }
    const data = await response.json()
    users.value = data.data
  } catch (err) {
    console.error("Impossible de récupérer les utilisateurs", err)
  }
}

  const annuler = () => {
    router.push(`/private/tableaux/${idTab}`)
  }

  async function valider() {
    try {
      const response = await fetch(`http://localhost:10056/tableaux/${idTab}/participants`, {
        method: "PATCH",
        headers: {
          "Authorization": `Bearer ${token}`,
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          ids: selectedUsers.value.map(u => u.cptId)
        })
        
      })
      if (!response.ok) {
        console.error("Erreur modification liste participants", response.status)
        return
      }
      router.push(`/private/tableaux/${idTab}`) 
    } catch (err) {
      console.error("Impossible de récupérer la liste des paticipants", err)
    }
  }

const token = localStorage.getItem("jwtToken")
const idUser = localStorage.getItem("cptId")
onMounted(() => {
  const admin = localStorage.getItem("isAdmin")
  if (!token) {
    router.push("/connexion")
  }
  if (admin === "1") {
    router.push("/admin")
  }
  fetchParticipants(idTab)
  fetchCreateur(idTab)
  fetchUsers()
})
</script>

<template>
  <Navbar />
  <div class="container">
    <div class="box">
      <h1>Gestion des participants</h1>
      <div class="selection-container">
        <div class="selection-box">
          <h2>Utilisateurs :</h2>
          <input
            v-model="search"
            placeholder="Rechercher un utilisateur..."
            class="search-input"
          />
          <select v-model="selectedUsers" multiple>
            <option
              v-for="user in filteredUsers"
              :key="user.id"
              :value="user"
            >
              {{ user.prenom }} {{ user.nom }} 
            </option>
          </select>
          <div class="selected-list">
            <span
              class="tag"
              v-for="user in selectedUsers"
              :key="user.id"
            >
              - {{ user.prenom }} {{ user.nom }}
            </span>
          </div>
        </div>
      </div>
      <div class="nav">
        <button class="boutonsNav" @click="valider">Valider</button>
        <button class="boutonsNav" @click="annuler">Annuler</button>
      </div>
    </div>
  </div>
</template>