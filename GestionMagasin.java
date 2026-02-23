//projet-EL-BADRA-HOUMAM-EL-MESSAOUDI-Othmane

import java.util.Scanner;
import java.io.*;

public class GestionMagasin {

    static final int MAX = 100;
    static String[] noms = new String[MAX];
    static double[] prix = new double[MAX];
    static int[] qtes = new int[MAX];
    static int n = 0;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        charger();

        int choix = 0;
        while (choix != 7) {
            System.out.println("\n1.Ajouter 2.Stock 3.Chercher 4.Vendre 5.Alertes 6.Sauver 7.Quitter");
            System.out.print("Choix : ");
            choix = sc.nextInt();
            sc.nextLine();

            switch (choix) {
                case 1: ajouter(); break;
                case 2: afficher(); break;
                case 3: chercher(); break;
                case 4: vendre(); break;
                case 5: alertes(); break;
                case 6: sauvegarder(); break;
                case 7: System.out.println("Au revoir."); break;
                default: System.out.println("Choix invalide !");
            }
        }
    }

    static void ajouter() {
        if (n < MAX) {
            System.out.print("Nom: ");
            noms[n] = sc.nextLine();

            System.out.print("Prix: ");
            prix[n] = sc.nextDouble();

            System.out.print("Quantité: ");
            qtes[n] = sc.nextInt();
            sc.nextLine();

            n++;
        } else {
            System.out.println("Stock plein !");
        }
    }

    static void afficher() {
        System.out.println("NOM | PRIX | QUANTITE");
        for (int i = 0; i < n; i++) {
            System.out.println(noms[i] + " | " + prix[i] + " | " + qtes[i]);
        }
    }

    static void chercher() {
        System.out.print("Nom du produit : ");
        String nomRecherche = sc.nextLine();

        for (int i = 0; i < n; i++) {
            if (noms[i].equalsIgnoreCase(nomRecherche)) {
                System.out.println("Trouvé -> Prix: " + prix[i] + " DH, Stock: " + qtes[i]);
                return;
            }
        }
        System.out.println("Produit non trouvé.");
    }

    static void vendre() {
        System.out.print("Nom du produit à vendre : ");
        String nom = sc.nextLine();

        for (int i = 0; i < n; i++) {
            if (noms[i].equalsIgnoreCase(nom)) {
                System.out.print("Quantité demandée : ");
                int q = sc.nextInt();
                sc.nextLine();

                if (q <= qtes[i]) {
                    double total = prix[i] * q;

                    // Remise automatique de 10%
                    if (total > 1000) {
                        total *= 0.9;
                        System.out.println("Remise de 10% appliquée.");
                    }

                    qtes[i] -= q;
                    System.out.println("Total à payer : " + total + " DH");
                } else {
                    System.out.println("Stock insuffisant !");
                }
                return;
            }
        }
        System.out.println("Produit non trouvé.");
    }

    static void alertes() {
        for (int i = 0; i < n; i++) {
            // Seuil d'alerte
            if (qtes[i] < 5) {
                System.out.println("Alerte Stock Bas : " + noms[i] + " (" + qtes[i] + ")");
            }
        }
    }

    static void sauvegarder() throws Exception {
        PrintWriter p = new PrintWriter(new FileWriter("stock.txt"));
        for (int i = 0; i < n; i++) {
            p.println(noms[i] + ";" + prix[i] + ";" + qtes[i]);
        }
        p.close();
        System.out.println("Fichier stock.txt mis à jour.");
    }

    static void charger() throws Exception {
        File f = new File("stock.txt");
        if (f.exists()) {
            Scanner fs = new Scanner(f);
            while (fs.hasNextLine() && n < MAX) {
                String[] t = fs.nextLine().split(";");
                noms[n] = t[0];
                prix[n] = Double.parseDouble(t[1]);
                qtes[n] = Integer.parseInt(t[2]);
                n++;
            }
            fs.close();
        }
    }
}
