package com.example.data.model

data class MentalModelPrinciple(
    val id: String,
    val title: String,
    val author: String,
    val tag: String,
    val summary: String,
    val actionableGuideline: String
)

object MentalModelRepository {
    val principles = listOf(
        MentalModelPrinciple(
            id = "dalio_principles",
            title = "Réalité + Rêves + Détermination = Vie Réussie",
            author = "Ray Dalio (Bridgewater)",
            tag = "STRATÉGIE GLOBALE",
            summary = "Regardez la réalité en face, acceptez vos erreurs sans complaisance et itérez rapidement à travers le cycle de 5 étapes vers l'excellence.",
            actionableGuideline = "Traitez chaque revers financier ou personnel comme un problème mathématique à résoudre, jamais comme une fatalité émotionnelle."
        ),
        MentalModelPrinciple(
            id = "compound_effect",
            title = "L'Effet Cumulé des Habitudes & du Capital",
            author = "Darren Hardy & James Clear",
            tag = "DISCIPLINE & FINANCE",
            summary = "De minuscules améliorations quotidiennes de 1% composent un progrès exponentiel (1.01^365 = 37.78). Il en va de même pour l'épargne réinvestie.",
            actionableGuideline = "Ne sous-estimez jamais l'impact de 30 minutes de lecture quotidienne ou d'un virement automatique de 200€ par mois."
        ),
        MentalModelPrinciple(
            id = "rule_50_30_20",
            title = "Allocation Budgétaire Anti-Fragile (50/30/20)",
            author = "Ingénierie Financière SaaS",
            tag = "CASHFLOW",
            summary = "50% de vos revenus nets pour les besoins vitaux incompressibles, 30% pour le confort/style de vie, 20% minimum alloués aux actifs productifs.",
            actionableGuideline = "Automatisez le prélèvement des 20% vers vos comptes d'investissement le lendemain du virement de vos revenus."
        ),
        MentalModelPrinciple(
            id = "naval_leverage",
            title = "Les 4 Leviers de Richesse de Naval",
            author = "Naval Ravikant",
            tag = "CRÉATION DE VALEUR",
            summary = "La fortune se construit par les leviers : Le code, les médias, le capital et les talents humains. Code et médias travaillent pendant votre sommeil à coût marginal nul.",
            actionableGuideline = "Passez de la vente de votre temps à la construction d'actifs numériques, intellectuels ou financiers scalables."
        ),
        MentalModelPrinciple(
            id = "inversion_munger",
            title = "Principe d'Inversion de Charlie Munger",
            author = "Charlie Munger (Berkshire Hathaway)",
            tag = "PRISE DE DÉCISION",
            summary = "« Dites-moi où je vais mourir pour que je n'y aille jamais ». Au lieu de chercher comment réussir, listez ce qui garantit la ruine et évitez-le rigoureusement.",
            actionableGuideline = "Évitez à tout prix les crédits à la consommation toxiques, l'ego dans les investissements et le manque de sommeil chronique."
        ),
        MentalModelPrinciple(
            id = "fire_rule_4_percent",
            title = "Indépendance Financière (Règle des 4% / 25x)",
            author = "Trinité / Mouvement FIRE",
            tag = "INDÉPENDANCE",
            summary = "Votre chiffre FIRE correspond à 25 fois vos dépenses annuelles. Une fois atteint, retirer 4% par an protège votre capital initial à perpétuité.",
            actionableGuideline = "Suivez avec précision vos dépenses réelles pour connaître à l'euro près votre palier de liberté absolue."
        )
    )
}
