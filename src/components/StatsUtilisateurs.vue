<script setup>
import { ref,computed,onMounted } from 'vue'
import { Doughnut } from 'vue-chartjs'
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  ArcElement,
  CategoryScale
} from 'chart.js'

ChartJS.register(Title, Tooltip, Legend, ArcElement, CategoryScale)

const actifCount = ref('')
const desactiveCount = ref('')
const dataActif = computed(() => ({
  labels: ['Activés', 'Désactivés'],
  datasets: [
    {
      label: 'Comptes',
      backgroundColor: ['#4BC0C0', '#FF6384'],
      data: [actifCount.value, desactiveCount.value]
    }
  ]
}))

const adminCount = ref('')
const utilCount = ref('')
const dataRole = computed(() => ({
  labels: ['Admin', 'Utilisateurs'],
  datasets: [
    {
      label: 'Comptes',
      backgroundColor: ['#FFCE56', '#36A2EB'],
      data: [adminCount.value, utilCount.value]
    }
  ]
}))
const token = localStorage.getItem("jwtToken")
const users = ref([])
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
    const res = await response.json()
    users.value = res.data

    adminCount.value = users.value.filter(user => user.cptIsAdmin === 1).length
    utilCount.value = users.value.filter(user => user.cptIsAdmin === 0).length
    actifCount.value = users.value.filter(user => user.cptIsActive === 1).length
    desactiveCount.value = users.value.filter(user => user.cptIsActive === 0).length

  } catch (err) {
    console.error("Impossible de récupérer les utilisateurs", err)
  }
}

onMounted(() => {
  fetchUsers()
})
</script>

<template>
  <div class="charts-container" style="display:flex; gap:50px;">
    <div style="width:250px; height:250px;">
      <h3>Comptes activés vs Comptes désactivés</h3>
      <Doughnut :data="dataActif" />
    </div>

    <div style="width:250px; height:250px;">
      <h3>Administrateurs vs Utilisateurs</h3>
      <Doughnut :data="dataRole" />
    </div>
  </div>
</template>