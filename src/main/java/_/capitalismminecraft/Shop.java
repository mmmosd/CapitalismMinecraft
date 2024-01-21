package _.capitalismminecraft;

public class Shop {
    public void OpenWoodShopGUI() {
        //원목만
    }

    public void OpenMineralShopGUI() {
        //광물 블럭, 주괴, (구리, 철, 금 등 주괴로 만들 수 있는 것은 주괴로만, 섬손 금지)
        // 단, 네더라이트는 파편, 주괴 모두 가능
    }

    public void OpenFoodShopGUI() {
        //먹었을 때 효과가 부여되지 않는 것만 (황금사과류 제외)
    }

    public void OpenPotionShopGUI() {
        //포션 ("시간 + 레벨"에 따라 가격 책정), 인첸트북 ("인첸트 레벨 + 인첸트 개수" 에 따라 가격 책정)
    }


    public void InitPrice() {

    }

    public int SetColumnPrice(int before_price) {
        int result_price = before_price;
        return result_price;
    }
}
