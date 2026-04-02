package com.eltoruz.myprofileapp.data


data class ProfileData(
    val name: String = "Rifael Eurico Sitorus",
    val title: String = "Cyber Security Engineer",
    val bio: String = "Cybersecurity enthusiast focused on securing systems, networks, and applications. Passionate about ethical hacking, threat analysis, and building secure digital solutions.",
    val email: String = "eltoruz@wearehackerone.com",
    val phone: String = "+62 812-3456-7890",
    val location: String = "Lampung, Indonesia",
    val job: String = "Cyber Security Engineer",
    val skills: List<String> = listOf("Python", "Bash", "Web Application Security", "Burpsuite", "Mobile Application Security")
)

data class ProfileUiState(
    val profile: ProfileData = ProfileData(),
    val isDarkMode: Boolean = false,
    val isEditMode: Boolean = false
)