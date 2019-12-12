package hk.hkucs.yellowobjects.ui.ybmap;


import hk.hkucs.yellowobjects.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShopList {
    List<Shop> list;
    int currentQuestion;
    int correctCount;

    public ShopList() {
        list = new ArrayList<Shop>();
        currentQuestion = 1;
        correctCount = 0;

        Shop s1 = new Shop(1, "HEYTEA", false, R.drawable.shop1);
        Shop s2 = new Shop(2, "Super Super Congee & Noodle", false, R.drawable.shop2);
        Shop s3 = new Shop(3, "Yoshinoya", false, R.drawable.shop3);
        Shop s4 = new Shop(4, "Cafe De Coral", false, R.drawable.shop4);
        Shop s5 = new Shop(5, "Starbucks", false, R.drawable.shop5);
        Shop s6 = new Shop(6, "Pizza Huts", false, R.drawable.shop6);
        Shop s7 = new Shop(7, "Maxim’s ", false, R.drawable.shop7);
        Shop s8 = new Shop(8, "Nam Kee Noodle", false, R.drawable.shop8);
        Shop s9 = new Shop(9, "Genki Sushi", false, R.drawable.shop9);
        Shop s10 = new Shop(10, "The Great Restaurant", false, R.drawable.shop10);
        Shop s11 = new Shop(11, "Joint Publishing", false, R.drawable.shop11);
        Shop s12 = new Shop(12, "Fotomax", false, R.drawable.shop12);
        Shop s13 = new Shop(13, "Best Mart 360", false, R.drawable.shop13);
        Shop s14 = new Shop(14, "Okaishi Land", false, R.drawable.shop14);
        Shop s15 = new Shop(15, "GoGoVan", false, R.drawable.shop15);

        Shop s16 = new Shop(16, "Odonya Shokudo", true, R.drawable.shop16);
        Shop s17 = new Shop(17, "Lung Mun Café", true, R.drawable.shop17);
        Shop s18 = new Shop(18, "CALL4VAN", true, R.drawable.shop18);
        Shop s19 = new Shop(19, "Cheung Wo Noodles", true, R.drawable.shop19);
        Shop s20 = new Shop(20, "Kam Cheung Pork Noodle", true, R.drawable.shop20);
        Shop s21 = new Shop(21, "Station", true, R.drawable.shop21);
        Shop s22 = new Shop(22, "Katoya", true, R.drawable.shop22);
        Shop s23 = new Shop(23, "Katsuisen", true, R.drawable.shop23);
        Shop s24 = new Shop(24, "Kingyo", true, R.drawable.shop24);
        Shop s25 = new Shop(25, "Zeppelin Hot Dog Shop", true, R.drawable.shop25);
        Shop s26 = new Shop(26, "Betsutenjin", true, R.drawable.shop26);
        Shop s27 = new Shop(27, "HEERETEA", true, R.drawable.shop27);
        Shop s28 = new Shop(28, "Cheap Lab", true, R.drawable.shop28);
        Shop s29 = new Shop(29, "Queen's Cafe", true, R.drawable.shop29);
        Shop s30 = new Shop(30, "ChungKee Dessert (Sai Wan)", true, R.drawable.shop30);

        list.add(s1);
        list.add(s2);
        list.add(s3);
        list.add(s4);
        list.add(s5);
        list.add(s6);
        list.add(s7);
        list.add(s8);
        list.add(s9);
        list.add(s10);
        list.add(s11);
        list.add(s12);
        list.add(s13);
        list.add(s14);
        list.add(s15);
        list.add(s16);
        list.add(s17);
        list.add(s18);
        list.add(s19);
        list.add(s20);
        list.add(s21);
        list.add(s22);
        list.add(s23);
        list.add(s24);
        list.add(s25);
        list.add(s26);
        list.add(s27);
        list.add(s28);
        list.add(s29);
        list.add(s30);
    }

    public Shop getRandomShop() {
        Random random = new Random();
        int i = random.nextInt(list.size());

        Shop selected = list.get(i);
        list.remove(i);

        return selected;
    }

    public Shop getShop(int i) {
        for (Shop s : list) {
            if (i == s.getId()) {
                return s;
            }
        }
        return null;
    }

    public void answer(boolean isCorrect) {
        currentQuestion += 1;
        if (isCorrect) {
            correctCount += 1;
        }
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public int getCorrectCount() {
        return correctCount;
    }
}
