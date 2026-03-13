<style src="../assets/css/style.css"></style>
<script setup>
  import Navbar from "../components/NavbarUtilisateur.vue"
  import { useRouter, useRoute } from "vue-router"
  import { computed, ref } from "vue"

  import tabsData from "../bdd/tableaux.json"
  import usersData from "../bdd/users.json"

  const router = useRouter()
  const route = useRoute()

  const idParam = Number(route.params.id)

  const tab = tabsData.find(t => t.id === idParam) || { participants: [] }

  const search = ref("")

  const selectedUsers = ref(
    usersData.filter(u => tab.participants.includes(u.pseudo))
  )

  const filteredUsers = computed(() => {
    const query = search.value.toLowerCase()

    return usersData.filter(user =>
      `${user.prenom} ${user.nom} ${user.pseudo}`
        .toLowerCase()
        .includes(query)
    )
  })

  const annuler = () => {
    router.push(`/private/tableaux/${tab.id}`)
  }

  const valider = () => {
    tab.participants = selectedUsers.value.map(u => u.pseudo)
    router.push(`/private/tableaux/${tab.id}`)
  }
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
              {{ user.prenom }} {{ user.nom }} ({{ user.pseudo }})
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