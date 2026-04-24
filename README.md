# Szyfrator DES 

Aplikacja desktopowa implementująca algorytm szyfrowania symetrycznego DES (Data Encryption Standard). System umożliwia bezpieczne szyfrowanie oraz deszyfrowanie wiadomości przy użyciu własnego klucza. Integralną częścią projektu jest zestaw testów jednostkowych dbających o niezawodność poszczególnych transformacji kryptograficznych algorytmu.

Projekt został zrealizowany w zespole dwuosobowym:
* **Amelia Garnys** – [@amelia6554](https://github.com/amelia6554)
* **Emilia Szczerba** – [@emilycb97](https://github.com/emilycb97)

## Funkcjonalności

* **Szyfrowanie:** Transformacja tekstu jawnego w kryptogram przy użyciu 64-bitowego bloku i klucza DES.
* **Deszyfrowanie:** Odtwarzanie oryginalnej wiadomości z zaszyfrowanego tekstu z wykorzystaniem odpowiedniego klucza.
* **Zarządzanie kluczem:** Wprowadzanie i walidacja klucza symetrycznego niezbędnego do procesów kryptograficznych.
* **Niezawodność:** Testy jednostkowe kluczowych funkcji algorytmu (np. permutacje, funkcje Feistela, generowanie podkluczy) zapewniające poprawność obliczeń.
* **GUI:** Interfejs graficzny w JavaFX pozwalający na intuicyjne testowanie działania szyfru.

## Technologie

* **Język:** Java 17+
* **Framework:** JavaFX
* **Testowanie:** JUnit 5
* **Narzędzie budowania:** Maven

*Data realizacji: Kwiecień 2025*

---
#  DES Encryption Tool

A desktop application that implements the symmetric DES (Data Encryption Standard) algorithm. The project demonstrates the full cryptographic lifecycle, from secure encryption to decryption of messages using a symmetric key, backed by automated unit tests.

Project developed in a two-person team:
* **Amelia Garnys** – [@amelia6554](https://github.com/amelia6554)
* **Emilia Szczerba** – [@emilycb97](https://github.com/emilycb97)

## Features

* **Encryption:** Transforming plaintext into ciphertext using standard 64-bit blocks and a DES key.
* **Decryption:** Recovering the original message from the ciphertext using the correct symmetric key.
* **Key Management:** Handling and validating the symmetric key required for the cryptographic processes.
* **Reliability:** Comprehensive unit tests for core algorithm functions (e.g., permutations, Feistel functions, subkey generation) to ensure mathematical correctness.
* **GUI:** Interactive user interface built with JavaFX for easy testing and visualization.

## Technologies

* **Language:** Java 17+
* **Framework:** JavaFX (GUI)
* **Testing:** JUnit 5
* **Build Tool:** Maven

*Developed: April 2025*
