# Progetto_WebApp_2024-25
Questo file è da documento temporaneo per tracciare gli obiettivi e le fasi di sviluppo del progetto. Verranno annotati tutti i passi da seguire e le funzionalità da implementare. Una volta completato il progetto, questo README sarà sostituito o aggiornato con una versione più dettagliata/decorosa/strutturata, per la consegna finale.

# Obiettivi del Progetto
1. Sistema di Registrazione e Autenticazione Utenti
  -Implementazione di un sistema di registrazione per gli utenti (amministratori, venditori, acquirenti).
  -Autenticazione sicura tramite login e gestione delle sessioni utente.
2. Gestione dei Permessi in Base al Tipo di Utente
  -Differenziazione dei permessi per gli utenti: amministratori, venditori/scambiatori e acquirenti/scambiatori.
3. Gestione delle Schede delle Carte Collezionabili
  -Creazione, modifica e cancellazione delle carte da parte dei venditori/scambiatori.
  -Possibilità di aggiungere immagini, descrizioni, prezzo, stato (nuova/usata), categoria (espansione/set) e opzioni di scambio.
4. Funzionalità di Ricerca Avanzata per Carte
  -Ricerca delle carte per nome, espansione, prezzo, stato, categoria, ecc.
  -Ordinamento dei risultati per prezzo, stato, ecc.
5. Gestione degli Scambi e Desideri
  -I venditori/scambiatori possono dichiarare se una carta è scambiabile e specificare i propri desideri di scambio.
6. Modulo di Contatto tra Venditori e Acquirenti/Scambiatori
  -Implementazione di un modulo di contatto che permette agli acquirenti/scambiatori di contattare i venditori.
7. Integrazione con le API di Facebook
  -Permettere ai venditori/scambiatori di promuovere gli annunci su Facebook tramite API.
8. Funzionalità di Amministrazione
  -Gli amministratori possono modificare e cancellare annunci o recensioni.
  -Gestione degli utenti: possibilità di bannare utenti violatori e nominare nuovi amministratori.
9. Testing e Debugging
  -Testing funzionale di tutte le funzionalità implementate.
  -Debugging per risolvere eventuali problemi.
10. Ottimizzazione delle Performance e Sicurezza
  -Ottimizzazione dell'applicazione per migliorare la performance.
  -Implementazione di misure di sicurezza per proteggere i dati degli utenti e prevenire attacchi.
11. Aggiornamento del README
  -Aggiornamento del file README con la documentazione finale e le istruzioni per l'uso del progetto.


Traccia proposta:
Sito di vendita e scambio di carte collezionabili

Si realizzi un’applicazione web per la compravendita e lo scambio di carte collezionabili (es. Pokémon, Magic: The Gathering, Yu-Gi-Oh!). Gli utenti potranno iscriversi per vendere, acquistare o scambiare carte. Sono previste 3 tipologie di utenti: amministratore, venditori/scambiatori, acquirenti/scambiatori.

Funzionalità comuni:

Esiste un utente amministratore nel database.
Tutti gli utenti possono registrarsi.
Ogni carta avrà una scheda con immagine, descrizione, stato (es. “nuova”, “usata”), prezzo, richiesta di scambio, categoria (es. espansione/set).
Funzionalità di ricerca avanzata per nome carta, espansione, prezzo, stato, ecc.
Venditori/Scambiatori:

Creano, modificano, cancellano schede carta.
Possono abbassare i prezzi: il vecchio prezzo sarà barrato.
Indicano se la carta è scambiabile e specificano desideri.
Possono essere contattati tramite modulo.
Promuovono annunci su Facebook tramite API.
Acquirenti/Scambiatori:

Cercano carte per stato, categoria, prezzo.
Ordinano risultati per prezzo o stato.
Contattano venditori/scambiatori tramite modulo.
Amministratore:

Modifica e cancella annunci o recensioni.
Può bannare utenti.
Nomina altri amministratori
