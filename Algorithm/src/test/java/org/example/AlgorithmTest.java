package org.example;

import org.example.exceptions.AlgorithmException;
import org.example.exceptions.KeyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

/**
 * Klasa testowa dla algorytmu szyfrowania i deszyfrowania
 */
public class AlgorithmTest {

    /**
     * Test sprawdzający poprawność szyfrowania i deszyfrowania prostego tekstu
     */
    @Test
    void encryptDecryptHelloWorldTest() throws KeyException, AlgorithmException {
        KeyGenerator keyGenerator = new KeyGenerator(Converter.hexToBytes("133457799BBCDFF1"));
        String originalText = "HelloWorld";

        Algorithm algorithm = new Algorithm(originalText.getBytes(StandardCharsets.UTF_8), keyGenerator, false);
        String encryptedText = Converter.bytesToHex(algorithm.encrypt());

        Assertions.assertNotEquals(originalText, encryptedText, "Tekst zaszyfrowany powinien być różny od oryginalnego");
        Assertions.assertEquals("4c166cf59477b19a64998445ddf3155a".toUpperCase(), encryptedText);

        String decryptedText = new String(algorithm.decrypt(), StandardCharsets.UTF_8);

        Assertions.assertEquals(originalText, decryptedText, "Odszyfrowany tekst powinien być identyczny jak oryginalny");
    }

    /**
     * Test szyfrowania i deszyfrowania pustego ciągu znaków
     */
    @Test
    void encryptDecryptEmptyStringTest() throws KeyException, AlgorithmException {
        KeyGenerator keyGenerator = new KeyGenerator(Converter.hexToBytes("01aefc1f9475a6bb"));

        Assertions.assertThrows(AlgorithmException.class, () -> {
            new Algorithm(null, keyGenerator, false);
        });
        String originalText = "  ";
        Algorithm algorithm = new Algorithm(originalText.getBytes(StandardCharsets.UTF_8), keyGenerator, false);
        String encryptedText = Converter.bytesToHex(algorithm.encrypt());

        Assertions.assertEquals("bf220ef5404ce548".toUpperCase(), encryptedText);

        String decryptedText = new String(algorithm.decrypt(), StandardCharsets.UTF_8);

        Assertions.assertEquals(originalText, decryptedText, "Odszyfrowany tekst powinien być identyczny jak oryginalny");
    }

    /**
     * Test szyfrowania i deszyfrowania tekstu zawierającego znaki specjalne
     */
    @Test
    void encryptDecryptWithSpecialCharactersTest() throws KeyException, AlgorithmException {
        KeyGenerator keyGenerator = new KeyGenerator(Converter.hexToBytes("2818CB97CDE2503F"));
        String originalText = "P@ssw0rd!123";

        Algorithm algorithm = new Algorithm(originalText.getBytes(StandardCharsets.UTF_8), keyGenerator, false);
        String encryptedText = Converter.bytesToHex(algorithm.encrypt());

        Assertions.assertNotEquals(originalText, encryptedText, "Tekst zaszyfrowany powinien być różny od oryginalnego");

        String decryptedText = new String(algorithm.decrypt(), StandardCharsets.UTF_8);

        Assertions.assertEquals(originalText, decryptedText, "Odszyfrowany tekst powinien być identyczny jak oryginalny");
    }

    /**
     * Test szyfrowania i deszyfrowania ciągu znaków z literami i spacjami
     */
    @Test
    void encryptDecryptStringTest1() throws KeyException, AlgorithmException {
        KeyGenerator keyGenerator = new KeyGenerator(Converter.hexToBytes("133457799BBCDFF1"));
        String originalText = "Bang Chan";

        Algorithm algorithm = new Algorithm(originalText.getBytes(StandardCharsets.UTF_8), keyGenerator, false);
        String encryptedText = Converter.bytesToHex(algorithm.encrypt());

        Assertions.assertNotEquals(originalText, encryptedText, "Tekst zaszyfrowany powinien być " +
                "różny od oryginalnego");

        String decryptedText = new String(algorithm.decrypt(), StandardCharsets.UTF_8);

        Assertions.assertEquals(originalText, decryptedText, "Odszyfrowany tekst powinien być identyczny " +
                "jak oryginalny");
    }

    /**
     * Test szyfrowania i deszyfrowania krótkiego tekstu
     */
    @Test
    void encryptDecryptStringTest2() throws KeyException, AlgorithmException {
        KeyGenerator keyGenerator = new KeyGenerator(Converter.hexToBytes("0123456789ABCDEF"));
        String originalText = "HelloWor";

        Algorithm algorithm = new Algorithm(originalText.getBytes(StandardCharsets.UTF_8), keyGenerator, false);
        String encryptedText = Converter.bytesToHex(algorithm.encrypt());

        Assertions.assertNotEquals(originalText, encryptedText, "Tekst zaszyfrowany powinien być różny od " +
                "oryginalnego");

        String decryptedText = new String(algorithm.decrypt(), StandardCharsets.UTF_8);

        Assertions.assertEquals(originalText, decryptedText, "Odszyfrowany tekst powinien być identyczny jak " +
                "oryginalny");
    }

    /**
     * Testuje szyfrowanie i deszyfrowanie dłuższego tekstu
     * Sprawdza, czy wynik szyfrowania jest poprawny oraz czy deszyfrowanie przywraca oryginalny tekst
     */
    @Test
    void encryptDecryptStringTest3() throws KeyException, AlgorithmException {
        KeyGenerator keyGenerator = new KeyGenerator(Converter.hexToBytes("0123456789ABCDEF"));
        String originalText = "The European languages are members of the same family. " +
                "Their separate existence is a myth. For science, music, sport, etc, " +
                "Europe uses the same vocabulary. The languages only differ in their " +
                "grammar, their pronunciation and their most common words. Everyone " +
                "realizes why a new common language would be desirable: one";

        Algorithm algorithm = new Algorithm(originalText.getBytes(StandardCharsets.UTF_8), keyGenerator, false);
        algorithm.encrypt();

//         Sprawdzenie poprawności zaszyfrowanego tekstu
        Assertions.assertEquals("fe4479ddc938c739c48a147023152aadc5b1162112cdb04ca2fcb4bf0ebcff" +
                "33bbbe8efceb6180e47fee95c6fcb67b38749e05914fa1ea77814bb89b814722a5721db7fb1b2916064dd7" +
                "c2868d3728051eeccb09ef0b926b57ea1b6751e9085452c94343b23a4a9897bc4168b509866ea788e449efe" +
                "40aa63bd098f8f7015fc58151cf1496b40fb743c74b79878d8716881c7db13d46428bd6e71a7eec055aaafb" +
                "eccf538ab60d68f7437c31384573568be6d0df92e6ffc7eb645f10091896742ed386fa2ed08ad841898859f" +
                "0b95318baf1d8981b1fd2d27cc87fb902f76b09e8533cf746bcfff4b74a7c8bb39bd59cb97539cae68b5409" +
                "76561032ff3fe856cd9c19fd0e08d490423e5a2a42526abb5552309751dce3c2597defac8e5e911aa0c8b8a" +
                "395a511af93fb1f54b34a52859327492b97826488fe9f9aa899604366", Converter.bytesToHex(algorithm.getText()).toLowerCase());

        algorithm.decrypt();

        // Sprawdzenie, czy deszyfrowanie daje oryginalny tekst
        Assertions.assertEquals("The European languages are members of the same family. " +
                "Their separate existence is a myth. For science, music, sport, etc, " +
                "Europe uses the same vocabulary. The languages only differ in their " +
                "grammar, their pronunciation and their most common words. Everyone " +
                "realizes why a new common language would be desirable: one", new String(algorithm.getText(), StandardCharsets.UTF_8));;
    }

    /**
     * Testuje szyfrowanie i deszyfrowanie tekstu zawierającego różne znaki specjalne i symbole.
     */
    @Test
    void encryptDecryptStringTest4() throws KeyException, AlgorithmException {
        KeyGenerator keyGenerator = new KeyGenerator(Converter.hexToBytes("01aefc1f9475a6bb"));
        String originalText = "abc def ghi jkl mno pqrs tuv wxyz ABC DEF GHI JKL MNO PQRS " +
                "TUV WXYZ !\"§ $%& /() =?* '<> #|; ²³~ @`´ ©«» ¤¼× {}abc def ghi jkl mno " +
                "pqrs tuv wxyz ABC DEF GHI JKL MNO PQRS TUV WXYZ !\"§ $%& /() =?* '<> #|; ²³~";

        Algorithm algorithm = new Algorithm(originalText.getBytes(StandardCharsets.UTF_8), keyGenerator, false);
        algorithm.encrypt();

        // Sprawdzenie poprawności zaszyfrowanego tekstu
        Assertions.assertEquals("471c3be61d95c50d25deb73c8d2366bee8d3e515bf920a27cea0299685f52a239c851c99" +
                        "336acab1430a3e9f33d4afed30a19d6489502af7c9afe9898547b3c8bea0980ac13c54dfb5927852fbb7f276f17badc5" +
                        "360ad4eddbd6c0bb59460c53d3effb64f73848f0e788a6a6ccd4020dc5a7258a5caed1fa471c3be61d95c50d25deb73c" +
                        "8d2366bee8d3e515bf920a27cea0299685f52a239c851c99336acab1430a3e9f33d4afed30a19d6489502af7c9afe989" +
                        "8547b3c8bea0980ac13c54dfb5927852fbb7f276f17badc5360ad4eddbd6c0bb59460c53f6e832da7d1cfdde",
                Converter.bytesToHex(algorithm.getText()).toLowerCase());

        algorithm.decrypt();

        // Sprawdzenie, czy deszyfrowany tekst jest identyczny z oryginalnym
        Assertions.assertEquals("abc def ghi jkl mno pqrs tuv wxyz ABC DEF GHI JKL MNO PQRS " +
                "TUV WXYZ !\"§ $%& /() =?* '<> #|; ²³~ @`´ ©«» ¤¼× {}abc def ghi jkl mno " +
                "pqrs tuv wxyz ABC DEF GHI JKL MNO PQRS TUV WXYZ !\"§ $%& /() =?* '<> #|; ²³~", new String(algorithm.getText(), StandardCharsets.UTF_8));
    }

    /**
     * Testuje szyfrowanie i deszyfrowanie tekstu w języku polskim zawierającego znaki diakrytyczne i nowe linie.
     */
    @Test
    public void encryptDecryptStringTest5() throws KeyException, AlgorithmException {
        KeyGenerator keyGenerator = new KeyGenerator(Converter.hexToBytes("0123456789ABCDEF"));
        String originalText = "Litwo! Ojczyzno moja! ty jesteś jak zdrowie.\n" +
                "\n" +
                "Ile cię trzeba cenić, ten tylko się dowie,\n" +
                "\n" +
                "Kto cię stracił. Dziś piękność twą w całej ozdobie\n" +
                "\n" +
                "Widzę i opisuję, bo tęsknię po tobie.";

        Algorithm algorithm = new Algorithm(originalText.getBytes(StandardCharsets.UTF_8), keyGenerator, false);
        algorithm.encrypt();

        // Sprawdzenie poprawności zaszyfrowanego tekstu
        Assertions.assertEquals("834e04bc832f3187e0d52ecc11c5e74551c268755cad56bfcd102adb41a05bd3fd63fbef8fbf5" +
                "d7ae0dbe5e010cf5c7d1eb926513c019f85c48312c4e5bfba4fef54726e861ff8de77ac7b19d0c7796fd383e67752325b705fc" +
                "f8f99b0fc970f62e215595f670b1dc0c717e18ca2182fe3ed8ab42323c3bd7c3d63e6c672ca2a97b4a8bb0745356878c39666a" +
                "8384913790b67654baae8bde8fcae4317052f57b142dab27c9414d8610c33666c8fba503756e4a4a2108fb6895c95c0c2f29f7" +
                "91e7846c91e2fe784", Converter.bytesToHex(algorithm.getText()).toLowerCase());

        algorithm.decrypt();

        // Sprawdzenie poprawności deszyfrowanego tekstu
        Assertions.assertEquals(originalText, new String(algorithm.getText(), StandardCharsets.UTF_8));
    }

    /**
     * Testuje proces szyfrowania i deszyfrowania tekstu zawierającego tylko jeden znak spacji
     * Sprawdza, czy tekst zaszyfrowany różni się od oryginału, oraz czy po deszyfrowaniu tekst jest identyczny z oryginalnym.
     */
    @Test
    void encryptDecryptStringTest6() throws KeyException, AlgorithmException {
        KeyGenerator keyGenerator = new KeyGenerator(Converter.hexToBytes("0123456789ABCDEF"));
        String originalText = " ";

        // Szyfrowanie tekstu
        Algorithm algorithm = new Algorithm(originalText.getBytes(StandardCharsets.UTF_8), keyGenerator, false);
        String encryptedText =  Converter.bytesToHex(algorithm.encrypt());

        // Sprawdzenie, czy zaszyfrowany tekst różni się od oryginału
        Assertions.assertNotEquals(originalText, encryptedText, "Tekst zaszyfrowany powinien być różny od oryginalnego");

        // Deszyfrowanie tekstu
        String decryptedText = new String(algorithm.decrypt(), StandardCharsets.UTF_8);

        // Sprawdzenie, czy odszyfrowany tekst jest identyczny z oryginalnym
        Assertions.assertEquals(originalText, decryptedText, "Odszyfrowany tekst powinien być identyczny jak oryginalny");
    }
    @Test
    void encryptDecryptStringTest8() throws KeyException, AlgorithmException {
        KeyGenerator keyGenerator = new KeyGenerator(Converter.hexToBytes("0123456789ABCDEF"));
        String originalText = "One morning, when Gregor Samsa woke from troubled dreams, he found himself" +
                " transformed in his bed into a horrible vermin. He lay on his armour-like back, and if he" +
                " lifted his head a little he could see his brown belly, slightly domed and divided by arch" +
                "es into stiff sections. The bedding was hardly able to cover it and seemed ready to slide " +
                "off any moment. His many legs, pitifully thin compared with the size of the rest of him, w" +
                "aved about helplessly as he looked. \"What's happened to me?\" he thought. It wasn't a dr" +
                "eam. His room, a proper human room although a little too small, lay peacefully between it" +
                "s four familiar walls. A collection of textile samples lay spread out on the table - Samsa" +
                " was a travelling salesman - and above it there hung a picture that he had recently cut ou" +
                "t of an illustrated magazine and housed in a nice, gilded frame. It showed a lady fitted ou" +
                "t with a fur hat and fur boa who sat upright, raising a heavy fur muff that covered the who" +
                "le of her lower arm towards the viewer. Gregor then turned to look out the window at the du" +
                "ll weather. Drops of rain could be heard hitting the pane, which made him feel quite sad. \"" +
                "How about if I sleep a little bit longer and forget all this nonsense\", he thought, but th" +
                "at was something he was unable to do because he was used to sleeping on his right, and in h" +
                "is present state couldn't get into that position. However hard he threw himself onto his ri" +
                "ght, he always rolled back to where he was. He must have tried it a hundred times, shut his" +
                " eyes so that he wouldn't have to look at the floundering legs, and only stopped when he be" +
                "gan to feel a mild, dull pain there that he had never felt before. \"Oh, God\", he thought," +
                " \"what a strenuous career it is that I've chosen! Travelling day in and day out. Doing bus" +
                "iness like this takes much more effort than doing your own business at home, and on top of " +
                "that there's the curse of travelling, worries about making train connections, bad and irreg" +
                "ular food, contact with different people all the time so that you can never get to know any" +
                "one or become friendly with them. It can all go to Hell!\" He felt a slight itch up on his " +
                "belly; pushed himself slowly up on his back towards the headboard so that he could lift his " +
                "head better; found where the itch was, and saw that it was covered with lots of little white" +
                " spots which he didn't know what to make of; and when he tried to feel the place with one of" +
                " his legs he drew it quickly back because as soon as he touched it he was overcome by a cold" +
                " shudder. He slid back into his former position. \"Getting up early all the time\", he thoug" +
                "ht, \"it makes you stupid. You've got to get enough sleep. Other travelling salesmen live a " +
                "life of luxury. For instance, whenever I go back to the guest house during the morning to co" +
                "py out the contract, these gentlemen are always still sitting there eating their breakfasts." +
                " I ought to just try that with my boss; I'd get kicked out on the spot. But who knows, maybe" +
                " that would be the best thing for me. If I didn't have my parents to think about I'd have gi" +
                "ven in my notice a long time ago, I'd have gone up to the boss and told him just what I thin" +
                "k, tell him everything I would, let him know just what I feel. He'd fall right off his desk! " +
                "And it's a funny sort of business to be sitting up there at your desk, talking down at your " +
                "subordinates from up there, especially when you have to go right up close because the boss is" +
                " hard of hearing. Well, there's still some hope; once I've got the money together to pay off " +
                "my parents' debt to him - another five or six years I suppose - that's definitely what I'll do" +
                ". That's when I'll make the big change. First of";


        // Szyfrowanie tekstu
        Algorithm algorithm = new Algorithm(originalText.getBytes(StandardCharsets.UTF_8), keyGenerator, false);
        String encryptedText = Converter.bytesToHex(algorithm.encrypt());

        // Sprawdzenie, czy zaszyfrowany tekst różni się od oryginału
        Assertions.assertNotEquals(originalText, encryptedText, "Tekst zaszyfrowany powinien " +
                "być różny od oryginalnego");

        // Deszyfrowanie tekstu
        String decryptedText = new String(algorithm.decrypt(), StandardCharsets.UTF_8);

        // Sprawdzenie, czy odszyfrowany tekst jest identyczny z oryginalnym
        Assertions.assertEquals(originalText, decryptedText, "Odszyfrowany tekst powinien " +
                "być identyczny jak oryginalny");
    }

    @Test
    void test() throws KeyException, AlgorithmException {
        byte[] input = {1, 35, 69, 103, -119, -85, -51, -17};
        //String originalText = "HelloWor";
        //48 65 6C 6C 6F 57 6F 72
        byte[] message = {72, 101, 108, 108, 111, 87, 111, 114};
        KeyGenerator keyGenerator = new KeyGenerator(input);

        Algorithm algorithm = new Algorithm(message, keyGenerator, false);
//        AB 7D A8 7A C0 B2 2C 44
        byte[] encryptedMessage = {-85, 125, -88, 122, -64, -78, 44, 68};

        Assertions.assertArrayEquals(encryptedMessage, algorithm.encrypt());

        Assertions.assertArrayEquals(message, algorithm.decrypt());

    }


    @Test
    void test2() throws KeyException, AlgorithmException {
        byte[] input = {1, 35, 69, 103, -119, -85, -51, -17};
        // tajna wiadomość
//        01110100 01100001 01101010 01101110 01100001 00100000 01110111 01101001 01100001 01100100/ 01101111 01101101 01101111 10101101 11000001 11000000
        byte[] message = {116, 97, 106, 110, 97, 32, 119, 105, 97, 100, 111, 109, 111, -83, -63, -64};
//        AB 7D A8 7A C0 B2 2C 44

        Algorithm algorithm = new Algorithm(message, new KeyGenerator(input), false);
        algorithm.encrypt();

        Assertions.assertArrayEquals(message, algorithm.decrypt());

    }

    @Test
    void test3() throws KeyException, AlgorithmException {
        byte[] input = {1, 35, 69, 103, -119, -85, -51, -17};
        byte[] message = {72, 101, 108, 108, 111, 87, 111, 114, 108, 100};
        String originalText = "HelloWorld";
        KeyGenerator keyGenerator = new KeyGenerator(input);

        Algorithm algorithm = new Algorithm(message, keyGenerator, false);
        byte[] encryptedText = algorithm.encrypt();
        byte[] decryptedText = algorithm.decrypt();
        Assertions.assertArrayEquals(message, decryptedText);
    }

}
